package com.cfysu.util.listener.core.listener;

import com.cfysu.util.listener.core.event.BotEvent;

/**
 * @Author canglong
 * @Date 2019/11/27
 */
public interface BotListener<E extends BotEvent> extends java.util.EventListener {
    E onEvent(E event);

    boolean support(E event);
}
