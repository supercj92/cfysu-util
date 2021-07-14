package com.cfysu.util.listener.core.exception;

/**
 * @Author canglong
 * @Date 2021/7/8
 */
public class ListenerTimeoutException extends ListenerException{
    public ListenerTimeoutException(Throwable throwable){
        super(throwable);
    }
}
