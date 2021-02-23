package com.cfysu.util.chain.core.demo;

import com.cfysu.util.chain.core.context.ChainContext;
import com.cfysu.util.chain.core.node.AbstractNode;
import com.cfysu.util.chain.core.node.ChainRequestHandler;

/**
 * @Author canglong
 * @Date 2020/7/15
 */
@ChainRequestHandler
public class DemoNode1 extends AbstractNode<String> {
    @Override
    protected void handle(ChainContext<String> chainContext) {
        System.out.println("DemoNode1");
    }
}
