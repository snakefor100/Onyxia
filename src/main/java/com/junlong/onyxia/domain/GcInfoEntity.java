package com.junlong.onyxia.domain;

/**
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public class GcInfoEntity {
    /**
     * 标识是哪个gc动作，一般为：end of major GC，Young Gen GC等，分别表示老年代和新生代的gc结束
     */
    private String name;
    /**
     * 标识这个收集器进行了几次gc
     */
    private long count;
    /**
     * 引起gc的原因,如：System.gc()，Allocation Failure，G1 Humongous Allocation等
     */
    private String gcCause;
    /**
     * gc的开始时间
     */
    private String startTime;
    /**
     * gc的结束时间
     */
    private String endTime;
    /**
     * gc前内存情况
     */
    private MemoryCommonEntity beforeMemory;
    /**
     * gc后内存情况
     */
    private MemoryCommonEntity afterMemory;
    /**
     * GC耗时，单位：毫秒
     */
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
