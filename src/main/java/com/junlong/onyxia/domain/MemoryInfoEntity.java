package com.junlong.onyxia.domain;

import java.util.List;

/**
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public class MemoryInfoEntity {
    /**
     * 堆内存
     */
    private MemoryCommonEntity headMomory;
    /**
     * 非堆内存
     */
    private MemoryCommonEntity noHeadMomory;
    /**
     * 各个区域内存数据
     */
    private List<MemoryPoolInfoEntity> memoryPoolInfoEntityList;


    public MemoryCommonEntity getHeadMomory() {
        return headMomory;
    }

    public void setHeadMomory(MemoryCommonEntity headMomory) {
        this.headMomory = headMomory;
    }

    public MemoryCommonEntity getNoHeadMomory() {
        return noHeadMomory;
    }

    public void setNoHeadMomory(MemoryCommonEntity noHeadMomory) {
        this.noHeadMomory = noHeadMomory;
    }

    public List<MemoryPoolInfoEntity> getMemoryPoolInfoEntityList() {
        return memoryPoolInfoEntityList;
    }

    public void setMemoryPoolInfoEntityList(List<MemoryPoolInfoEntity> memoryPoolInfoEntityList) {
        this.memoryPoolInfoEntityList = memoryPoolInfoEntityList;
    }

    @Override
    public String toString() {
        return "MemoryInfoEntity{" +
                "headMomory=" + headMomory +
                ", noHeadMomory=" + noHeadMomory +
                ", memoryPoolInfoEntityList=" + memoryPoolInfoEntityList +
                '}';
    }
}
