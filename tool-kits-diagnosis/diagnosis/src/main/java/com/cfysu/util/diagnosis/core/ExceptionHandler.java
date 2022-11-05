package com.cfysu.util.diagnosis.core;

/**
 * @Author canglong
 * @Date 2022/11/4
 */
@FunctionalInterface
public interface ExceptionHandler<T> {
    T handle(Throwable throwable, Object[] args);
}
