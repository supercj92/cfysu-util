package com.cfysu.util.chain.core.node;

import com.cfysu.util.chain.core.api.ChainInvoker;
import com.cfysu.util.chain.core.context.ChainContext;
import com.cfysu.util.chain.core.context.MonitorableContext;

/**
 * @Author canglong
 * @Date 2020/6/3
 */
public abstract class AbstractNode<T> implements Node<T>{

    protected Node<T> next;
    protected String nodeName = this.getClass().getSimpleName();
    private long nodeStartTime;
    private long nodeEndTime;

    @Override
    public void process(ChainContext<T> chainContext) {
        MonitorableContext<T> monitorableContext = (MonitorableContext<T>)chainContext;
        if(ChainInvoker.RESPONSIBILITY_CHAIN_MODE.equals(chainContext.getChainMode())){
            processAsResponsibilityChain(monitorableContext);
        }else {
            processAsPipeline(monitorableContext);
        }
    }

    private void processAsResponsibilityChain(MonitorableContext<T> chainContext){
        if(support(chainContext)){
            chainContext.markProcessed(nodeName);
            handleWithMonitor(chainContext);
        }else if(next != null){
            next.process(chainContext);
        }
    }

    private void processAsPipeline(MonitorableContext<T> chainContext){
        handleWithMonitor(chainContext);
        if(next != null){
            next.process(chainContext);
        }
    }

    public void setNextNode(Node next) {
        this.next = next;
    }

    private void handleWithMonitor(MonitorableContext<T> chainContext){
        chainContext.onNodeStart(this);
        handle(chainContext);
        chainContext.onNodeEnd(this);
    }

    protected abstract void handle(ChainContext<T> chainContext);

    public String getNodeName() {
        return nodeName;
    }

    public long calculateNodeElapsedTime(){
        return (nodeEndTime - nodeStartTime);
    }

    public long getNodeStartTime() {
        return nodeStartTime;
    }

    public void setNodeStartTime(long nodeStartTime) {
        this.nodeStartTime = nodeStartTime;
    }

    public long getNodeEndTime() {
        return nodeEndTime;
    }

    public void setNodeEndTime(long nodeEndTime) {
        this.nodeEndTime = nodeEndTime;
    }
}
