package com.cfysu.util.diagnosis.core;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;

import com.cfysu.util.common.TargetLengthBasedClassNameAbbreviator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;

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

    @Pointcut(
        "@within(com.cfysu.util.diagnosis.core.EnableDebug) || @annotation(com.cfysu.util.diagnosis.core.EnableDebug)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object debug(final ProceedingJoinPoint pjp) throws Throwable {
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
        EnableDebug annotation = method.getAnnotation(EnableDebug.class);
        if (annotation == null) {
            annotation = userClass.getAnnotation(EnableDebug.class);
        }
        boolean rt = annotation.rt();
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

    @Pointcut(
        "@within(com.cfysu.util.diagnosis.core.HandleException) || @annotation(com.cfysu.util.diagnosis.core"
            + ".HandleException)")
    public void pointcut2() {}

    @Around("pointcut2()")
    public Object handleException(final ProceedingJoinPoint pjp) throws Throwable {
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
        HandleException annotation = method.getAnnotation(HandleException.class);
        if (annotation == null) {
            annotation = userClass.getAnnotation(HandleException.class);
        }

        ExceptionHandler handlerInstance;
        try {
            handlerInstance = applicationContext.getBean(annotation.handler());
        } catch (NoSuchBeanDefinitionException e) {
            Class<? extends ExceptionHandler> customHandler = annotation.handler();
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
