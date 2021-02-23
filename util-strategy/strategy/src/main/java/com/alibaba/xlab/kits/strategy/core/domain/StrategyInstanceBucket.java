package com.cfysu.util.strategy.core.domain;

import java.util.HashMap;
import java.util.Map;

import com.cfysu.util.strategy.core.exception.StrategyNotFoundException;

import lombok.Data;

/**
 * @Author canglong
 * @Date 2020/7/9
 */
@Data
public class StrategyInstanceBucket {

    public static final StrategyInstanceBucket INSTANCE = new StrategyInstanceBucket();

    private Map<String, StrategyGroup> strategyClazz2Group = new HashMap<>();

    public static StrategyInstanceBucket getInstance(){
        return StrategyInstanceBucket.INSTANCE;
    }

    public Object findStrategyInstanceGently(String strategyClazz, String strategyCode){
        return findStrategyInstance(strategyClazz, strategyCode, false);
    }

    public Object findStrategyInstanceStrictly(String strategyClazz, String strategyCode) throws StrategyNotFoundException{
        return findStrategyInstance(strategyClazz, strategyCode, true);
    }

    private Object findStrategyInstance(String strategyClazz, String strategyCode, boolean strict){
        StrategyGroup strategyGroup = strategyClazz2Group.get(strategyClazz);
        if(strategyGroup == null){
            if(strict){
                throw new StrategyNotFoundException("can not find strategy for class:" + strategyClazz);
            }else {
                return null;
            }
        }
        Object instance = strategyGroup.strategyCode2ObjectMap.get(strategyCode);
        if(instance == null){
            if(strict){
                throw new StrategyNotFoundException("can not find strategy for code:" + strategyCode);
            }
        }
        return instance;
    }

    @Data
    public static class StrategyGroup{
        private Map<String, Object> strategyCode2ObjectMap = new HashMap<>();
        private String strategyClazz;
    }
}
