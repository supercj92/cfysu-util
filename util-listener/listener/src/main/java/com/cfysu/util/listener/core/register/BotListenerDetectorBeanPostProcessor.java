package com.cfysu.util.listener.core.register;


import com.cfysu.util.listener.core.exception.ListenerRegisterException;
import com.cfysu.util.listener.core.listener.BotEventListener;
import com.cfysu.util.listener.core.listener.BotListener;
import com.cfysu.util.listener.core.publisher.BotEventComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @Author canglong
 * @Date 2019/11/27
 */
public class BotListenerDetectorBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        BotEventListener annotation = AnnotationUtils.getAnnotation(bean.getClass(), BotEventListener.class);
        if (annotation != null) {
            if (!BotListener.class.isAssignableFrom(bean.getClass())) {
                throw new ListenerRegisterException(beanName + " must implements BotListener");
            }
            //register Listener
            BotEventComponent.listenerList.add((BotListener)bean);
        }
        return bean;
    }
}
