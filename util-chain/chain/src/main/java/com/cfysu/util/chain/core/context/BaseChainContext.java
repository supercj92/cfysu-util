package com.cfysu.util.chain.core.context;


import java.util.Map;

import com.cfysu.util.chain.core.api.ChainInvoker;
import com.cfysu.util.chain.core.node.UserData;

import lombok.Data;

/**
 * @Author canglong
 * @Date 2020/6/3
 */
@Data
public abstract class BaseChainContext<T> implements ChainContext<T>{

    private String mode = ChainInvoker.RESPONSIBILITY_CHAIN_MODE;
    private UserData userData;
    private T data;

    public BaseChainContext(T t){
        this.data = t;
    }

    public BaseChainContext(T t, String mode, Map<String, Object> userData) {
        this(t);
        this.mode = mode;
        if(userData != null) {
            this.userData = new UserData();
            this.userData.putAll(userData);
        }
    }

    @Override
    public void bindToContext(String key, Object val){
        this.userData.putUserData(key, val);
    }

    @Override
    public String getChainMode() {
        return this.mode;
    }

    public boolean isResponsibilityChainMode() {
        return ChainInvoker.RESPONSIBILITY_CHAIN_MODE.equals(mode);
    }

    public boolean isPipelineMode(){
        return ChainInvoker.PIPELINE_MODE.equals(mode);
    }
}
