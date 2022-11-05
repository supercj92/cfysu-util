package com.cfysu.util.diagnosis.core;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author canglong
 * @Date 2022/11/4
 */
@Slf4j
public class DefaultExceptionHandler implements ExceptionHandler<Void> {
    @Override
    public Void handle(Throwable throwable, Object[] args) {
        throw new RuntimeException(
            "1.请实现com.alibaba.xlab.kits.diagnosis.core.ExceptionHandler自定义异常处理器,并注册为Spring Bean。2.尝试使用cglib"
                + "生成代理。配置如下@EnableAspectJAutoProxy(proxyTargetClass = true)", throwable);
    }
}
