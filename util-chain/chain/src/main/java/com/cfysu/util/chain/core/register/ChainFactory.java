package com.cfysu.util.chain.core.register;

import java.util.List;

import com.cfysu.util.chain.core.node.Node;

/**
 * @Author canglong
 * @Date 2020/6/4
 */
public interface ChainFactory {

    void buildChain();

    Node buildChain(List<Node> nodes);

    Node getChain(Class<?> clazz);

    List<Node> findNodes(Class<?> clazz);
}
