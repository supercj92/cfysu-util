package com.cfysu.util.listener.core.event;

import java.util.EventObject;

/**
 * @Author canglong
 * @Date 2019/11/27
 */
public abstract class BotEvent<T> extends EventObject {

    private T context;

    public BotEvent(Object source, T context) {
        super(source);
        this.context = context;
    }
}
