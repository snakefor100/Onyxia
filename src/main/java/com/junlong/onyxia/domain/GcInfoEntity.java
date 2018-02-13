package com.junlong.onyxia.domain;

/**
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public class GcInfoEntity {
    private String name;
    private long count;
    private String gcCause;
    private String startTime;
    private String endTime;
    private MemoryCommonEntity beforeMemory;
    private MemoryCommonEntity afterMemory;
    private long GCTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getGcCause() {
        return gcCause;
    }

    public void setGcCause(String gcCause) {
        this.gcCause = gcCause;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public long getGCTime() {
        return GCTime;
    }

    public void setGCTime(long GCTime) {
        this.GCTime = GCTime;
    }

    @Override
    public String toString() {
        return "GcInfoEntity{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", gcCause='" + gcCause + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", beforeMemory=" + beforeMemory +
                ", afterMemory=" + afterMemory +
                ", GCTime=" + GCTime +
                '}';
    }
}
