package com.cfysu.util.strategy.core.exception;

/**
 * @Author canglong
 * @Date 2020/7/9
 */
public class StrategyNotFoundException extends RuntimeException{
    public StrategyNotFoundException(String reason){
        super(reason);
    }
}
