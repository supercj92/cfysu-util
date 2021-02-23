package com.cfysu.util.chain.core.node.experiment;

import com.cfysu.util.chain.core.node.AbstractNode;
import com.cfysu.util.chain.core.context.ChainContext;
import com.cfysu.util.chain.core.node.Node;

/**
 * @Author canglong
 * @Date 2020/6/4
 */
public class DefaultNode extends AbstractNode {

    public static final Node INSTANCE = new DefaultNode();


    public static Node getInstance(){
        return DefaultNode.INSTANCE;
    }

    @Override
    protected void handle(ChainContext chainContext) {
        System.out.println("defaultNode");
    }
}
