package com.cfysu.util.chain.core.node;

import com.cfysu.util.chain.core.context.ChainContext;

/**
 * @Author canglong
 * @Date 2020/7/15
 */
public interface NodeChain {

    /**
     * 执行下一个节点
     * @param chainContext 执行链上下文
     */
    void invoke(ChainContext chainContext);
}
