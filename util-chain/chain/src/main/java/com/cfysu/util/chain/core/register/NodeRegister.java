package com.cfysu.util.chain.core.register;

import java.util.Set;

import com.cfysu.util.chain.core.node.Node;

/**
 * @Author canglong
 * @Date 2020/6/4
 */
public interface NodeRegister {

    void register(Node node);

    Set<Node> getAllNodes();
}
