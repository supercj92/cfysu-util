package com.cfysu.util.chain.core.context;

import java.util.HashMap;
import java.util.Map;

import com.cfysu.util.chain.core.node.AbstractNode;

/**
 * @Author canglong
 * @Date 2020/6/12
 */
public class MonitorableChainContext<T> extends BaseChainContext<T> implements MonitorableContext<T> {

    private long startTime;
    private long endTime;
    private String handledNodeName;
    private Map<String, Long> nodeName2ElapsedTimeMap = new HashMap<>(16);

    public MonitorableChainContext(T t, String mode, Map<String , Object> userData){
        super(t, mode, userData);
        this.onChainStart();
    }

    @Override
    public void markProcessed(String nodeName){
        this.handledNodeName = nodeName;
        this.onChainEnd();
    }

    @Override
    public void onNodeStart(AbstractNode node){
        node.setNodeStartTime(System.currentTimeMillis());
    }

    @Override
    public void onNodeEnd(AbstractNode node){
        node.setNodeEndTime(System.currentTimeMillis());
        this.nodeName2ElapsedTimeMap.put(node.getNodeName(), node.calculateNodeElapsedTime());
    }

    @Override
    public void onChainStart(){
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onChainEnd(){
        this.endTime = System.currentTimeMillis();
    }
}
