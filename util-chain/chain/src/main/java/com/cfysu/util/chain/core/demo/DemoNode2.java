package com.cfysu.util.chain.core.demo;

import com.cfysu.util.chain.core.context.ChainContext;
import com.cfysu.util.chain.core.node.ChainRequestHandler;
import com.cfysu.util.chain.core.node.Node;
import com.cfysu.util.chain.core.node.NodeChain;

/**
 *
 * @Author canglong
 * @Date 2020/6/4
 */
@ChainRequestHandler
public class DemoNode2 implements Node<DemoData> {

    @Override
    public void invoke(ChainContext<DemoData> chainContext, NodeChain nodeChain) {
        System.out.println("DemoNode2");
        nodeChain.invoke(chainContext);
    }
}
