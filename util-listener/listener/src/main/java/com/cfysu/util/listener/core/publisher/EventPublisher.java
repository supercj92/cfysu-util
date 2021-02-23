package com.cfysu.util.listener.core.publisher;

import com.cfysu.util.listener.core.event.BotEvent;

/**
 * @Author canglong
 * @Date 2020/5/17
 */
public interface EventPublisher {

    void publishEvent(BotEvent event);
}
