package com.cfysu.util.chain.core.api;

import java.util.List;
import java.util.Map;

import com.cfysu.util.chain.core.demo.DemoNode;
import com.cfysu.util.chain.core.exception.ChainNotFoundException;
import com.cfysu.util.chain.core.context.ChainContext;
import com.cfysu.util.chain.core.context.MonitorableChainContext;
import com.cfysu.util.chain.core.node.DefaultNodeChain;
import com.cfysu.util.chain.core.node.Node;
import com.cfysu.util.chain.core.node.NodeChain;
import com.cfysu.util.chain.core.node.AbstractNode;
import com.cfysu.util.chain.core.register.ChainFactory;

import com.cfysu.util.chain.core.demo.DemoNode1;
import com.cfysu.util.chain.core.demo.DemoNode2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author canglong
 * @Date 2020/6/12
 * ChainContext<DemoData> chainContext = ChainInvoker.processInResponsibilityMode(new DemoData());
 * DemoData data = chainContext.getData();
 */
@Component
public class ChainInvoker {

    public static final String RESPONSIBILITY_CHAIN_MODE = "responsibility";
    public static final String PIPELINE_MODE = "pipeline";
    public static final String BREAKABLE_PIPELINE_MODE = "breakable_pipeline";

    private static ChainFactory chainFactory;

    /**
     * 责任链模式处理数据
     * 继承AbstractNode并实现handle方法
     * {@link AbstractNode#handle(ChainContext)}
     * 使用示例
     * {@link DemoNode}
     *
     * @param data 待处理的数据
     * @return 处理结果
     */
    public static <T> ChainContext<T> processInResponsibilityMode(T data){
        return process(data, RESPONSIBILITY_CHAIN_MODE);
    }

    /**
     * 流水线模式处理数据
     * 继承AbstractNode并实现handle方法
     * {@link AbstractNode#handle(ChainContext)}
     * 使用示例
     * {@link DemoNode1}
     *
     * @param data 待处理的数据
     * @return 处理结果
     */
    public static <T> ChainContext<T> processInPipelineMode(T data){
        return process(data, PIPELINE_MODE);
    }

    /**
     * 可中断的流水线模式
     * 实现Node接口及invoke方法
     * {@link Node#invoke(ChainContext, NodeChain)}
     * 使用示例
     * {@link DemoNode2}
     *
     * @param data 待处理的数据
     * @return 处理结果
     */
    public static <T> ChainContext<T> processInBreakablePipelineMode(T data){
        List<Node> nodes = chainFactory.findNodes(data.getClass());
        if(nodes == null || nodes.size() == 0){
            throw new ChainNotFoundException(String.format("can not find chain for %s", data.getClass().getName()));
        }
        ChainContext<T> chainContext = buildContext(data, BREAKABLE_PIPELINE_MODE, null);
        new DefaultNodeChain(nodes).invoke(chainContext);
        return chainContext;
    }

    public static <T> ChainContext<T> process(T t, String mode){
        return process(t, mode, null);
    }

    public static <T> ChainContext<T> process(T t, String mode, Map<String, Object> userData){
        Node<T> chain = findChain(t);
        ChainContext<T> chainContext = buildContext(t, mode, userData);
        chain.process(chainContext);
        return chainContext;
    }

    @SuppressWarnings("unchecked")
    private static <T> Node<T> findChain(T t){
        Node<T> chain = chainFactory.getChain(t.getClass());
        if(chain == null){
            throw new ChainNotFoundException(String.format("can not find chain for %s", t.getClass().getName()));
        }
        return chain;
    }

    private static <T> ChainContext<T> buildContext(T t, String mode, Map<String, Object> userData){
        return new MonitorableChainContext<>(t, mode, userData);
    }

    @Autowired
    private void setChainFactory(ChainFactory chainFactory) {
        ChainInvoker.chainFactory = chainFactory;
    }

}
