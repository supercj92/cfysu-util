package com.cfysu.util.listener.core.domain;

import lombok.Data;

/**
 * @Author canglong
 * @Date 2021/7/8
 */
@Data
public class ListenerRunningInfo {

    private String name;
    private long rt;
    private boolean exception;
    private boolean support;

    public ListenerRunningInfo(String name){
        this.name = name;
    }
}
