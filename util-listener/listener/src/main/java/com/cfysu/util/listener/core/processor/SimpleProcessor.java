package com.cfysu.util.listener.core.processor;

import java.util.concurrent.Callable;

/**
 * @Author canglong
 * @Date 2021/7/12
 */
public class SimpleProcessor implements Processor{
    @Override
    public <T> T process(Callable<T> callable, long timeout, String listenerName) throws Exception {
        return callable.call();
    }
}
