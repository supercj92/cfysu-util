package com.cfysu.util.listener.autoconfigure.register;


import com.cfysu.util.listener.autoconfigure.annotation.BotEventListener;
import com.cfysu.util.listener.core.exception.ListenerRegisterException;
import com.cfysu.util.listener.core.listener.BotListener;
import com.cfysu.util.listener.autoconfigure.publisher.BotEventComponent;
import com.cfysu.util.listener.core.listener.BotListenerWrapper;
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
            BotEventComponent.listenerList.add(
                new BotListenerWrapper(annotation.timeout(), annotation.order(), (BotListener)bean));
        }
        return bean;
    }
}
