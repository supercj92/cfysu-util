package com.cfysu.util.strategy.core.api;

import com.cfysu.util.strategy.core.domain.StrategyInstanceBucket;

import org.springframework.util.Assert;

/**
 * @Author canglong
 * @Date 2020/7/9
 */
public class StrategyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T findStrategy(Class<T> clazz, String strategyCode) {
        Assert.notNull(clazz, "clazz could not be null");
        Assert.notNull(strategyCode, "strategyCode could not null");
        return (T)StrategyInstanceBucket.getInstance().findStrategyInstanceGently(clazz.getName(), strategyCode);
    }
}
