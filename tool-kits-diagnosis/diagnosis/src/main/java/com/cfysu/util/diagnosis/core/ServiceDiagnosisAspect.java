package com.cfysu.util.diagnosis.core;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @Author canglong
 * @Date 2022/11/4
 */
@Slf4j
@Aspect
public class ServiceDiagnosisAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private TargetLengthBasedClassNameAbbreviator abbreviator;

    private ConcurrentHashMap<Class<?>, ExceptionHandler<?>> handlerCache = new ConcurrentHashMap<>();

    public ServiceDiagnosisAspect() {
        abbreviator = new TargetLengthBasedClassNameAbbreviator(10);
    }

    @Around("@target(enableDebug) || @annotation(enableDebug)")
    public Object debug(final ProceedingJoinPoint pjp, EnableDebug enableDebug) throws Throwable {
        Object target = pjp.getTarget();
        Object[] args = pjp.getArgs();
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        String clazzName = target.getClass().getName();
        String methodName = method.getName();
        if (args.length == 0) {
            if ("toString".equals(methodName) || "hashCode".equals(methodName)) {
                return pjp.proceed();
            }
        } else if (args.length == 1 && "equals".equals(methodName)) {
            return pjp.proceed();
        }

        String caller = abbreviator.abbreviate(String.format("%s.%s", clazzName, methodName));
        if (log.isInfoEnabled()) {
            log.info("{} {}", caller, JSON.toJSONString(args));
        }
        Class<?> aClass = target.getClass();
        Class<?> userClass = ClassUtils.getUserClass(aClass);
        EnableDebug classAnnotation = userClass.getAnnotation(EnableDebug.class);
        if (enableDebug == null) {
            enableDebug = classAnnotation;
        }
        boolean rt = enableDebug.rt();
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        if (log.isInfoEnabled()) {
            log.info("{} {}", caller, JSON.toJSONString(result));
        }
        if (rt) {
            log.info("{} cost {}ms", caller, (end - start));
        }

        return result;
    }

    @Around("@target(handleException) || @annotation(handleException)")
    public Object handleException(final ProceedingJoinPoint pjp, HandleException handleException) throws Throwable {
        Object target = pjp.getTarget();
        Object[] args = pjp.getArgs();
        Class<?> aClass = target.getClass();
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        Class<?> userClass = ClassUtils.getUserClass(aClass);
        if (args.length == 0) {
            if ("toString".equals(methodName) || "hashCode".equals(methodName)) {
                return pjp.proceed();
            }
        } else if (args.length == 1 && "equals".equals(methodName)) {
            return pjp.proceed();
        }
        HandleException classAnnotation = userClass.getAnnotation(HandleException.class);
        if (handleException == null) {
            handleException = classAnnotation;
        }

        ExceptionHandler handlerInstance;
        try {
            handlerInstance = applicationContext.getBean(handleException.handler());
        } catch (NoSuchBeanDefinitionException e) {
            Class<? extends ExceptionHandler> customHandler = handleException.handler();
            handlerInstance = handlerCache.get(customHandler);
            if (handlerInstance == null) {
                try {
                    handlerInstance = BeanUtils.instantiateClass(customHandler);
                    handlerCache.put(customHandler, handlerInstance);
                } catch (BeanInstantiationException exception) {
                    handlerInstance = new DefaultExceptionHandler();
                }
            }
        }
        Object result;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            result = handlerInstance.handle(e, args);
        }

        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
