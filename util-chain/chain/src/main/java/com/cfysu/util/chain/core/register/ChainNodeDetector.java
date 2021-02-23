package com.cfysu.util.chain.core.register;

import com.cfysu.util.chain.core.node.ChainRequestHandler;
import com.cfysu.util.chain.core.node.Node;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @Author canglong
 * @Date 2020/6/3
 */
@Slf4j
public class ChainNodeDetector implements BeanPostProcessor {

    @Autowired
    private NodeRegister nodeRegister;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ChainRequestHandler handler = AnnotationUtils.getAnnotation(bean.getClass(), ChainRequestHandler.class);
        if(handler != null){
            nodeRegister.register((Node)bean);
        }
        return bean;
    }

}
