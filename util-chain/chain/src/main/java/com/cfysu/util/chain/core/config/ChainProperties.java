package com.cfysu.util.chain.core.config;

import com.cfysu.util.chain.core.api.ChainInvoker;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author canglong
 * @Date 2020/6/12
 */
@ConfigurationProperties(ChainProperties.CHAIN_PREFIX)
public class ChainProperties {
    public static final String CHAIN_PREFIX = "spring.kits.chain";

    private String mode = ChainInvoker.RESPONSIBILITY_CHAIN_MODE;

    public boolean isResponsibilityChainMode() {
        return ChainInvoker.RESPONSIBILITY_CHAIN_MODE.equals(mode);
    }

    public boolean isPipelineMode(){
        return ChainInvoker.PIPELINE_MODE.equals(mode);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
