package com.cfysu.util.listener.core.processor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.cfysu.util.listener.core.exception.ListenerException;
import com.cfysu.util.listener.core.exception.ListenerTimeoutException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author canglong
 * @Date 2021/7/8
 */
@Slf4j
public class ThreadPoolProcessor implements Processor {

    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolProcessor(){
        threadPoolExecutor = new ThreadPoolExecutor(10, 200, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
            new ThreadFactory() {
                private AtomicInteger threadNum = new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "bot-listener-exec-" + threadNum.getAndIncrement());
                }
            }, new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public <T> T process(Callable<T> callable, long timeout, String listenerName) throws Exception{

        Future<T> future = threadPoolExecutor.submit(callable);
        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
           log.error(listenerName + ":InterruptedException", e);
           throw new ListenerException(e);
        } catch (ExecutionException e) {
           log.error(listenerName + ":ExecutionException", e);
            throw new ListenerException(e);
        } catch (TimeoutException e) {
            log.error(listenerName + ":TimeoutException", e);
            throw new ListenerTimeoutException(e);
        }
    }
}
