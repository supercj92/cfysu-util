package com.cfysu.util.chain.core.node;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author canglong
 * @Date 2020/6/12
 */
public class UserData {
    private Map<String, Object> userData = new HashMap<>(8);

    public void putUserData(String key, Object val){
        userData.put(key, val);
    }

    @SuppressWarnings("unchecked")
    public <T> T getUserData(String key){
        return (T)userData.get(key);
    }

    public void putAll(Map<String, Object> userData){
        if(userData != null){
            this.userData.putAll(userData);
        }
    }
}
