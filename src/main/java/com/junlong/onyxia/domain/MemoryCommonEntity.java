package com.junlong.onyxia.domain;

/**
 * 内存基础属性
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/11
 * @since 1.0.0
 */
public class MemoryCommonEntity {
    /**
     * 已申请内存
     */
    private long committed;
    /**
     * 初始化内存
     */
    private long init;
    /**
     * 最大内存
     */
    private long max;
    /**
     * 已使用内存
     */
    private long used;

    public long getCommitted() {
        return committed;
    }

    public void setCommitted(long committed) {
        this.committed = committed;
    }

    public long getInit() {
        return init;
    }

    public void setInit(long init) {
        this.init = init;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "MemoryCommonEntity{" +
                "committed=" + committed +
                ", init=" + init +
                ", max=" + max +
                ", used=" + used +
                '}';
    }
}
