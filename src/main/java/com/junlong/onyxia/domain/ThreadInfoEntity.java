package com.junlong.onyxia.domain;

/**
 * 线程信息监控类
 *
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/10
 * @since 1.0.0
 */
public class ThreadInfoEntity {
    /**
     * 当前活动线程数
     */
    private int activityCount;
    /**
     * 峰值线程数
     */
    private int peakCount;
    /**
     * 线程总数
     */
    private long totalStartedThreadCount;
    /**
     * 守护线程数
     */
    private int daemonCount;


    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public int getPeakCount() {
        return peakCount;
    }

    public void setPeakCount(int peakCount) {
        this.peakCount = peakCount;
    }

    public long getTotalStartedThreadCount() {
        return totalStartedThreadCount;
    }

    public void setTotalStartedThreadCount(long totalStartedThreadCount) {
        this.totalStartedThreadCount = totalStartedThreadCount;
    }

    public int getDaemonCount() {
        return daemonCount;
    }

    public void setDaemonCount(int daemonCount) {
        this.daemonCount = daemonCount;
    }

    @Override
    public String toString() {
        return "ThreadInfoEntity{" +
                "activityCount=" + activityCount +
                ", peakCount=" + peakCount +
                ", totalStartedThreadCount=" + totalStartedThreadCount +
                ", daemonCount=" + daemonCount +
                '}';
    }
}
