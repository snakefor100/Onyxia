package com.junlong.onyxia.service;

import com.junlong.onyxia.config.MonitorMenuConfig;
import com.junlong.onyxia.service.callback.ResultCallback;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 默认监控处理
 * 每个监控项，启一个定时线程，每隔固定时间，获取监控内容
 *
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class DefaultMonitorHandler<T> {
    private static Logger logger = LoggerFactory.getLogger(DefaultMonitorHandler.class);

    /**
     * 定时主动拉取监控数据任务
     *
     * @param callback 回调函数
     * @param monitorMenuConfig 监控配置项
     */
    public void monitorTaskStart(final ResultCallback callback, final MonitorMenuConfig monitorMenuConfig) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("onyxia-" + monitorMenuConfig.getMonitorMenu().name() + "-%d").daemon(true).build());
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    T t = doMonitor();
                    callback.doCallback(t);
                } catch (Exception e) {
                    logger.error("监控方法发生异常，监控项：" + monitorMenuConfig.getMonitorMenu().name(), e);
                }

            }
        }, monitorMenuConfig.getInitialDelay(), monitorMenuConfig.getDelay(), TimeUnit.MILLISECONDS);
    }


    /**
     * 被动通过listener监控数据
     * @param callback 回调函数
     */
    public void monitorListenerStart(final ResultCallback callback) {
        doMonitor(callback);
    }


    /**
     * 监控，需各个监控功能点自己实现(主动监控，能拿到返回值）
     * @return
     */
    abstract T doMonitor();

    /**
     * 监控，需各个监控功能点自己实现（被动监控，callback函数透传进监控方法）
     * @param callback 回调函数
     */
    abstract void doMonitor(ResultCallback callback);
}
