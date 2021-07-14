package com.cfysu.util.listener.core.listener;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author canglong
 * @Date 2021/7/14
 */
@Data
@AllArgsConstructor
public class BotListenerWrapper {

    private long timeout;
    private int order;
    private BotListener botListener;
}
