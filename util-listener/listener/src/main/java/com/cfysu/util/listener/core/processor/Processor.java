package com.cfysu.util.listener.core.processor;

import java.util.concurrent.Callable;

/**
 * @Author canglong
 * @Date 2021/7/8
 */
public interface Processor {
   <T> T process(Callable<T> callable, long timeout, String listenerName) throws Exception;
}
