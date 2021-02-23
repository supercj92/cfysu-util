package com.cfysu.util.chain.core.node;

import com.cfysu.util.chain.core.context.ChainContext;

/**
 * @Author canglong
 * @Date 2020/6/3
 */
public interface Node<T> {

    /**
     * 责任链、不可中断的pipeline的实现
     * @param chainContext 链表执行上下文
     */
    default void process(ChainContext<T> chainContext){}

    /**
     * 责任链模式下，判断此节点是否支持处理请求
     * @param chainContext 链表执行上下文
     * @return true支持，false不支持
     */
    default boolean support(ChainContext<T> chainContext){return true;}

    /**
     * 可中断的pipeline模式
     * 需要手动执行NodeChain.invoke才会继续链表的执行
     * @param chainContext 链表执行上下文
     * @param nodeChain nodeChain
     */
    default void invoke(ChainContext<T> chainContext, NodeChain nodeChain){}
}
