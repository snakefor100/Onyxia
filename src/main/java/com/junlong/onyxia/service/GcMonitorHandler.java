package com.junlong.onyxia.service;

import com.junlong.onyxia.constants.OnyxiaConstants;
import com.junlong.onyxia.domain.GcInfoEntity;
import com.junlong.onyxia.domain.MemoryCommonEntity;
import com.junlong.onyxia.service.callback.ResultCallback;
import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/10
 * @since 1.0.0
 */
public class GcMonitorHandler extends DefaultMonitorHandler<GcInfoEntity> {
    private static final Logger logger = LoggerFactory.getLogger(OnyxiaConstants.LOGGER_GC_NAME);

    @Override
    GcInfoEntity doMonitor() {
        return null;
    }

    @Override
    void doMonitor(final ResultCallback callback) {
        List<GarbageCollectorMXBean> garbageCollectorMXBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        if (CollectionUtils.isEmpty(garbageCollectorMXBeanList)) {
            return;
        }

        for (GarbageCollectorMXBean gcBean : garbageCollectorMXBeanList) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            //每个内存区域，new一个监听器
            NotificationListener listener = new NotificationListener() {
                @Override
                public void handleNotification(Notification notification, Object handback) {
                    GcInfoEntity gcInfoEntity = new GcInfoEntity();
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    GcInfo gcInfo = info.getGcInfo();
                    Map<String, MemoryUsage> memoryUsageBeforeGc = gcInfo.getMemoryUsageBeforeGc();
                    Map<String, MemoryUsage> memoryUsageAfterGc = gcInfo.getMemoryUsageAfterGc();

                    gcInfoEntity.setGcCause(info.getGcCause());
                    gcInfoEntity.setName(info.getGcAction());
                    gcInfoEntity.setCount(info.getGcInfo().getId());
                    gcInfoEntity.setStartTime(new DateTime(gcInfo.getStartTime()).toString(OnyxiaConstants.DATE_FORMAT_Y_M_D_H_M_S_S));
                    gcInfoEntity.setEndTime(new DateTime(gcInfo.getEndTime()).toString(OnyxiaConstants.DATE_FORMAT_Y_M_D_H_M_S_S));
                    if (!CollectionUtils.isEmpty(memoryUsageAfterGc)) {
                        for (Map.Entry<String, MemoryUsage> entry : memoryUsageAfterGc.entrySet()) {
                            String name = entry.getKey();

                            MemoryUsage beforeUsage = memoryUsageBeforeGc.get(name);
                            MemoryCommonEntity beforeMemory = new MemoryCommonEntity();
                            beforeMemory.setCommitted(beforeUsage.getCommitted());
                            beforeMemory.setUsed(beforeUsage.getUsed());
                            beforeMemory.setMax(beforeUsage.getMax());
                            beforeMemory.setInit(beforeUsage.getInit());

                            MemoryUsage afterUsage = entry.getValue();
                            MemoryCommonEntity afterMemory = new MemoryCommonEntity();
                            afterMemory.setCommitted(afterUsage.getCommitted());
                            afterMemory.setUsed(afterUsage.getUsed());
                            afterMemory.setMax(afterUsage.getMax());
                            afterMemory.setInit(afterUsage.getInit());
                            gcInfoEntity.setAfterMemory(afterMemory);
                            gcInfoEntity.setBeforeMemory(beforeMemory);
                        }
                    }
                    callback.doCallback(gcInfoEntity);
                    logger.info(gcInfoEntity.toString());
                }
            };
            //添加监听器
            emitter.addNotificationListener(listener, new NotificationFilter() {
                @Override
                public boolean isNotificationEnabled(Notification notification) {
                    return notification.getType()
                            .equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);
                }
            }, null);
        }
    }
}
