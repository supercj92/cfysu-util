package com.cfysu.util.listener.autoconfigure;

import com.alibaba.fastjson.JSON;

import com.cfysu.util.listener.autoconfigure.publisher.BotEventComponent;
import com.cfysu.util.listener.core.domain.EventResult;
import com.cfysu.util.listener.core.event.BotEvent;
import com.cfysu.util.listener.core.listener.BotListener;
import com.cfysu.util.listener.core.listener.BotListenerWrapper;
import com.cfysu.util.listener.core.listener.ListenerRegister;
import com.cfysu.util.listener.core.publisher.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author canglong
 * @Date 2021/7/14
 */
@Slf4j
public class BotEventComponentTests {

    @Test
    public void testPublishEvent() throws InterruptedException {
        ListenerRegister listenerRegister = new BotEventComponent();

        BotListener botListener = new TestListener();
        listenerRegister.registerListener(new BotListenerWrapper(10000, 0, botListener));

        EventPublisher eventPublisher = (EventPublisher)listenerRegister;
        EventResult testEvent = eventPublisher.publishEvent(new TestEvent(this, "testEvent"));
        log.info(JSON.toJSONString(testEvent));
    }

    class TestEvent extends BotEvent<String> {
        public TestEvent(Object source, String context) {
            super(source, context);
        }
    }

    class TestListener implements BotListener<TestEvent> {

        @Override
        public boolean support(TestEvent event) {
            log.info(Thread.currentThread().getName());
            return true;
        }

        @Override
        public TestEvent onEvent(TestEvent event) {
            log.info(Thread.currentThread().getName());
            return event;
        }

    }
}