package com.junlong.onyxia.service;

import com.junlong.onyxia.constants.OnyxiaConstants;
import com.junlong.onyxia.domain.ThreadInfoEntity;
import com.junlong.onyxia.service.callback.ResultCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * 线程监控Handler
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public class ThreadMonitorHandler extends DefaultMonitorHandler<ThreadInfoEntity> {

    private static final Logger logger = LoggerFactory.getLogger(OnyxiaConstants.LOGGER_THREAD_NAME);

    /**
     * 当前监控时间的线程对象。每个监控周期会更新
     */
    private static volatile ThreadInfoEntity threadInfoEntity = new ThreadInfoEntity();

    @Override
    ThreadInfoEntity doMonitor() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        threadInfoEntity.setActivityCount(threadMXBean.getThreadCount());
        threadInfoEntity.setPeakCount(threadMXBean.getPeakThreadCount());
        threadInfoEntity.setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());
        threadInfoEntity.setDaemonCount(threadMXBean.getDaemonThreadCount());
        logger.info(threadInfoEntity.toString());
        return threadInfoEntity;
    }

    @Override
    void doMonitor(ResultCallback callback) {

    }
}
