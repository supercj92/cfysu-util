package com.cfysu.util.chain.core.demo;

import com.cfysu.util.chain.core.node.AbstractNode;
import com.cfysu.util.chain.core.context.ChainContext;
import com.cfysu.util.chain.core.node.ChainRequestHandler;

import org.springframework.core.annotation.Order;

/**
 * @Author canglong
 * @Date 2020/6/4
 */
@Order(100)
@ChainRequestHandler
public class DemoNode extends AbstractNode<Long> {

    @Override
    protected void handle(ChainContext<Long> chainContext) {
        System.out.println("demoNode");
    }

    @Override
    public boolean support(ChainContext<Long> chainContext) {
        return true;
    }
}
