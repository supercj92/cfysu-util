package com.cfysu.util.listener.autoconfigure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Author canglong
 * @Date 2019/11/27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface BotEventListener {

    long timeout();

    int order() default Ordered.LOWEST_PRECEDENCE;
}
