package com.cfysu.util.strategy.autoconfigure;

import com.cfysu.util.strategy.core.annotation.Strategy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @Author canglong
 * @Date 2020/7/9
 */
@Configuration
@ConditionalOnClass(Strategy.class)
public class StrategyAutoConfiguration {
}
