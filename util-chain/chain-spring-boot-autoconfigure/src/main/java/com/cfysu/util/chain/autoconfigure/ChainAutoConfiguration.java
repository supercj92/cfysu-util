package com.cfysu.util.chain.autoconfigure;

import com.cfysu.util.chain.core.config.ChainProperties;
import com.cfysu.util.chain.core.node.Node;
import com.cfysu.util.chain.core.register.ChainFactory;
import com.cfysu.util.chain.core.register.NodeComponent;
import com.cfysu.util.chain.core.register.NodeRegister;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author canglong
 * @Date 2020/6/4
 */
@Configuration
@ConditionalOnClass(Node.class)
@EnableConfigurationProperties(ChainProperties.class)
public class ChainAutoConfiguration {

    @Bean
    public NodeRegister nodeRegister(){
        return new NodeComponent();
    }

    @Bean
    public ChainFactory chainFactory(){
        return (ChainFactory)nodeRegister();
    }
}
