package com.cfysu.util.diagnosis.autoconfigure;

import com.cfysu.util.diagnosis.core.ServiceDiagnosisAspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author canglong
 * @Date 2022/11/4
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DiagnosisAutoConfiguration {

    @Bean
    public ServiceDiagnosisAspect serviceDiagnosisAspect() {
        return new ServiceDiagnosisAspect();
    }
}
