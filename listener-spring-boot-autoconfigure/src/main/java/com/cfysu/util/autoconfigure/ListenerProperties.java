package com.cfysu.util.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author canglong
 * @Date 2020/5/17
 */
@ConfigurationProperties(prefix = ListenerProperties.LISTENER_PREFIX)
public class ListenerProperties {
    public static final String LISTENER_PREFIX = "spring.listener";

    private String testMsg;

    public String getTestMsg() {
        return testMsg;
    }

    public void setTestMsg(String testMsg) {
        this.testMsg = testMsg;
    }
}
