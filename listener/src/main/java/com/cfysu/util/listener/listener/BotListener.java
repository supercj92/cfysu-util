package com.cfysu.util.listener.listener;

import com.cfysu.util.listener.event.BotEvent;

/**
 * @Author canglong
 * @Date 2019/11/27
 */
public interface BotListener<E extends BotEvent> extends java.util.EventListener {
    void onEvent(E event);
}
