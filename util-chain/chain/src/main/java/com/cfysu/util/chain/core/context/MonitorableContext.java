package com.cfysu.util.chain.core.context;

import com.cfysu.util.chain.core.node.AbstractNode;

/**
 * @Author canglong
 * @Date 2020/6/12
 */
public interface MonitorableContext<T> extends ChainContext<T>{

    void markProcessed(String nodeName);

    void onNodeStart(AbstractNode node);

    void onNodeEnd(AbstractNode node);

    void onChainStart();

    void onChainEnd();
}
