package com.cfysu.util.chain.core.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cfysu.util.chain.core.node.AbstractNode;
import com.cfysu.util.chain.core.node.Node;
import com.cfysu.util.common.GenericUtil;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

/**
 * @Author canglong
 * @Date 2020/6/4
 */

public class NodeComponent implements NodeRegister, ChainFactory {

    private Set<Node> allNodes = new HashSet<>();

    /**
     * T 对应的链表表头
     */
    private Map<String, Node> request2ChainMap = new HashMap<>();

    /**
     * T 对应的节点
     */
    private Map<String, List<Node>> request2NodesMap = new HashMap<>();

    @Override
    public void register(Node node) {
        this.allNodes.add(node);
        String declaredType = GenericUtil.resolveDeclaredGenericTypeName(node.getClass(), AbstractNode.class);
        if(declaredType == null){
            declaredType = GenericUtil.resolveDeclaredGenericTypeName(node.getClass(), Node.class);
        }
        if(declaredType == null){
            throw new IllegalArgumentException(String.format("class %s could not find data type", node.getClass().getName()));
        }
        this.request2NodesMap.computeIfAbsent(declaredType, key -> new ArrayList<>()).add(node);
    }

    @Override
    public Set<Node> getAllNodes() {
        return this.allNodes;
    }

    @Override
    public void buildChain() {
        //处理不同的nodeGroup
        this.request2NodesMap.forEach((key, val) -> request2ChainMap.put(key, buildChain(val)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Node buildChain(List<Node> allNodes) {
        AnnotationAwareOrderComparator.sort(allNodes);
        Node last = null;
        for(int i = allNodes.size() - 1; i >=0; i--){
            Node current = allNodes.get(i);
            if(current instanceof  AbstractNode){
                ((AbstractNode)current).setNextNode(last);
            }
            last = current;
        }
        return last;
    }

    @Override
    public Node getChain(Class<?> clazz) {
        return request2ChainMap.get(clazz.getName());
    }

    @Override
    public List<Node> findNodes(Class<?> clazz) {
        return request2NodesMap.get(clazz.getName());
    }
}
