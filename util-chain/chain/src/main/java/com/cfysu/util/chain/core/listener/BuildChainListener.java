package com.cfysu.util.chain.core.listener;

import com.cfysu.util.chain.core.register.ChainFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;

/**
 * @Author canglong
 * @Date 2020/6/3
 */
@Order(1)
public class BuildChainListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ChainFactory chainFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        chainFactory.buildChain();
    }
}
