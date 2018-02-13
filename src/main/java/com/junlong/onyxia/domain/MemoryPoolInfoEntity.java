package com.junlong.onyxia.domain;

/**
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/11
 * @since 1.0.0
 */
public class MemoryPoolInfoEntity {
    /**
     * 峰值内存
     */
    private MemoryCommonEntity peakMemroy;
    /**
     * 当前内存区域
     */
    private MemoryCommonEntity currentMemory;

    /**
     * 名称
     */
    private String name;
    /**
     * 所属管理者名称
     */
    private String managerName;
    /**
     * objectName
     */
    private String objectName;

    public MemoryCommonEntity getPeakMemroy() {
        return peakMemroy;
    }

    public void setPeakMemroy(MemoryCommonEntity peakMemroy) {
        this.peakMemroy = peakMemroy;
    }

    public MemoryCommonEntity getCurrentMemory() {
        return currentMemory;
    }

    public void setCurrentMemory(MemoryCommonEntity currentMemory) {
        this.currentMemory = currentMemory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String toString() {
        return "MemoryPoolInfoEntity{" +
                "peakMemroy=" + peakMemroy +
                ", currentMemory=" + currentMemory +
                ", name='" + name + '\'' +
                ", managerName='" + managerName + '\'' +
                ", objectName='" + objectName + '\'' +
                '}';
    }
}
