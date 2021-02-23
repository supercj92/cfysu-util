package com.cfysu.util.chain.core.node;

import java.util.List;

import com.cfysu.util.chain.core.context.ChainContext;

/**
 * @Author canglong
 * @Date 2020/7/15
 */
public class DefaultNodeChain implements NodeChain{

    private int nodeSize;
    private int currentNodeIndex;

    private List<Node> nodes;

    public DefaultNodeChain(List<Node> nodes){
        this.nodes = nodes;
        this.nodeSize = nodes.size();
        this.currentNodeIndex = 0;
    }

    @Override
    public void invoke(ChainContext chainContext) {
        if(currentNodeIndex < nodeSize){
            Node node = nodes.get(currentNodeIndex++);
            node.invoke(chainContext, this);
        }
    }
}
