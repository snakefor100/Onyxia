package com.junlong.onyxia.domain;

/**
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public class GcMemoryDetailEntity {
    private String name;
    private MemoryCommonEntity beforeMemory;
    private MemoryCommonEntity afterMemory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemoryCommonEntity getBeforeMemory() {
        return beforeMemory;
    }

    public void setBeforeMemory(MemoryCommonEntity beforeMemory) {
        this.beforeMemory = beforeMemory;
    }

    public MemoryCommonEntity getAfterMemory() {
        return afterMemory;
    }

    public void setAfterMemory(MemoryCommonEntity afterMemory) {
        this.afterMemory = afterMemory;
    }
}
