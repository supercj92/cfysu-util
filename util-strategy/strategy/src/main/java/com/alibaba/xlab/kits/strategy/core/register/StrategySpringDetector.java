package com.cfysu.util.strategy.core.register;


import com.cfysu.util.strategy.core.annotation.Strategy;
import com.cfysu.util.strategy.core.domain.StrategyInstanceBucket;
import com.cfysu.util.strategy.core.domain.StrategyInstanceBucket.StrategyGroup;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @Author canglong
 * @Date 2020/7/9
 */
public class StrategySpringDetector implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Strategy strategy = AnnotationUtils.getAnnotation(bean.getClass(), Strategy.class);
        if(strategy != null){
            String strategyCode = strategy.value();
            if(strategyCode == null || strategyCode.length() == 0){
                throw new IllegalArgumentException(bean.getClass().getName() + " @Strategy must set value");
            }
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if(interfaces == null || interfaces.length > 1){
                throw new IllegalArgumentException(bean.getClass().getName() + " @Strategy must implement one interface");
            }

            String interfaceClazzName = interfaces[0].getName();
            StrategyGroup strategyGroup = StrategyInstanceBucket.getInstance().getStrategyClazz2Group().computeIfAbsent(
                interfaceClazzName, key -> new StrategyGroup());

            strategyGroup.setStrategyClazz(interfaceClazzName);
            strategyGroup.getStrategyCode2ObjectMap().put(strategyCode, bean);
        }
        return bean;
    }
}
