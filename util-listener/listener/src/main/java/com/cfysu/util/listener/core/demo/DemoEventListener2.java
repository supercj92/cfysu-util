package com.cfysu.util.listener.core.demo;

import com.cfysu.util.listener.core.listener.BotEventListener;
import com.cfysu.util.listener.core.listener.BotListener;
import org.springframework.core.annotation.Order;

/**
 * @Author canglong
 * @Date 2019/11/27
 */
@Order(1)
@BotEventListener
public class DemoEventListener2 implements BotListener<FlowEndEvent> {
    @Override
    public void onEvent(FlowEndEvent endEvent) {
        System.out.println("DemoEventListener2");
    }
}
