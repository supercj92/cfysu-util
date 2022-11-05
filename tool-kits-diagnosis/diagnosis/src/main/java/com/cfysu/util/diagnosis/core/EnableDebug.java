package com.cfysu.util.diagnosis.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author canglong
 * @Date 2022/11/4
 * 打印方法的出参入参
 * 修饰在类上，则对类中的所有方法生效。
 * 修饰在方法上，则对此方法生效。
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableDebug {
    /**
     * 是否打印方法耗时统计
     */
    boolean rt() default true;
}
