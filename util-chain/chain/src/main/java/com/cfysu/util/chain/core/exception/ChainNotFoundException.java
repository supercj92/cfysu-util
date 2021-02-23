package com.cfysu.util.chain.core.exception;

/**
 * @Author canglong
 * @Date 2020/6/12
 */
public class ChainNotFoundException extends RuntimeException{
    public ChainNotFoundException(String reason){
        super(reason);
    }
}
