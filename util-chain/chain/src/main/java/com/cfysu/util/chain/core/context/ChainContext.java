package com.cfysu.util.chain.core.context;

import com.cfysu.util.chain.core.node.UserData;

/**
 * @Author canglong
 * @Date 2020/6/12
 */
public interface ChainContext<T>{

    T getData();

    void bindToContext(String key, Object val);

    UserData getUserData();

    String getChainMode();
}
