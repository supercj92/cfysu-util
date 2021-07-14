package com.cfysu.util.listener.core.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @Author canglong
 * @Date 2021/7/8
 */
@Data
public class ListenerRunningInfos {

    private List<ListenerRunningInfo> runningInfos = new ArrayList<>();

    public void addInfo(ListenerRunningInfo runningInfo){
        runningInfos.add(runningInfo);
    }
}
