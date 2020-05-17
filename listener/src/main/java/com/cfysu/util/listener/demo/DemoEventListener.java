package com.cfysu.util.listener.demo;

import com.cfysu.util.listener.listener.BotEventListener;
import com.cfysu.util.listener.listener.BotListener;
import org.springframework.core.annotation.Order;

/**
 * @Author canglong
 * @Date 2019/11/27
 */
@Order(2)
@BotEventListener
public class DemoEventListener implements BotListener<FlowStartEvent> {
    @Override
    public void onEvent(FlowStartEvent event) {
        System.out.println("DemoEventListener");
    }
}
