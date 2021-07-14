package com.cfysu.util.listener.core.exception;

/**
 * @Author canglong
 * @Date 2021/7/9
 */
public class ListenerException extends RuntimeException{

    public ListenerException(String reason){
        super(reason);
    }

    public ListenerException(Throwable throwable){
        super(throwable);
    }
}
