package com.junlong.onyxia.service;

import com.junlong.onyxia.constants.OnyxiaConstants;
import com.junlong.onyxia.domain.MemoryCommonEntity;
import com.junlong.onyxia.domain.MemoryInfoEntity;
import com.junlong.onyxia.domain.MemoryPoolInfoEntity;
import com.junlong.onyxia.service.callback.ResultCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/11
 * @since 1.0.0
 */
public class MemoryMonitorHandler extends DefaultMonitorHandler<MemoryInfoEntity> {

    private static final Logger logger = LoggerFactory.getLogger(OnyxiaConstants.LOGGER_MEMORY_NAME);
    /**
     * 当前监控时间的内存对象。每个监控周期会更新
     */
    private static volatile MemoryInfoEntity memoryInfoEntity = new MemoryInfoEntity();

    @Override
    MemoryInfoEntity doMonitor() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        List<MemoryPoolMXBean> memoryPoolMXBeanList = ManagementFactory.getMemoryPoolMXBeans();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        MemoryCommonEntity heapEntity = new MemoryCommonEntity();
        heapEntity.setCommitted(heapMemoryUsage.getCommitted());
        heapEntity.setInit(heapMemoryUsage.getInit());
        heapEntity.setMax(heapMemoryUsage.getMax());
        heapEntity.setUsed(heapMemoryUsage.getUsed());
        memoryInfoEntity.setHeadMomory(heapEntity);

        MemoryCommonEntity noHeapEntity = new MemoryCommonEntity();
        noHeapEntity.setCommitted(nonHeapMemoryUsage.getCommitted());
        noHeapEntity.setInit(nonHeapMemoryUsage.getInit());
        noHeapEntity.setMax(nonHeapMemoryUsage.getMax());
        noHeapEntity.setUsed(nonHeapMemoryUsage.getUsed());
        memoryInfoEntity.setNoHeadMomory(noHeapEntity);

        if(!CollectionUtils.isEmpty(memoryPoolMXBeanList)){
            List<MemoryPoolInfoEntity> memoryPoolInfoEntitieList = new ArrayList<MemoryPoolInfoEntity>(memoryPoolMXBeanList.size());
            for(MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeanList){
                MemoryPoolInfoEntity memoryPoolInfoEntity = new MemoryPoolInfoEntity();
                memoryPoolInfoEntity.setName(memoryPoolMXBean.getName());
                memoryPoolInfoEntity.setManagerName(Arrays.deepToString(memoryPoolMXBean.getMemoryManagerNames()));
                memoryPoolInfoEntity.setObjectName(memoryPoolMXBean.getObjectName().toString());

                MemoryUsage usage = memoryPoolMXBean.getUsage();
                MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();

                MemoryCommonEntity currentMemory = new MemoryCommonEntity();
                currentMemory.setCommitted(usage.getCommitted());
                currentMemory.setInit(usage.getInit());
                currentMemory.setMax(usage.getMax());
                currentMemory.setUsed(usage.getUsed());
                memoryPoolInfoEntity.setCurrentMemory(currentMemory);

                MemoryCommonEntity peakMemory = new MemoryCommonEntity();
                peakMemory.setCommitted(peakUsage.getCommitted());
                peakMemory.setInit(peakUsage.getInit());
                peakMemory.setMax(peakUsage.getMax());
                peakMemory.setUsed(peakUsage.getUsed());
                memoryPoolInfoEntity.setPeakMemroy(peakMemory);
                memoryPoolInfoEntitieList.add(memoryPoolInfoEntity);
            }
            memoryInfoEntity.setMemoryPoolInfoEntityList(memoryPoolInfoEntitieList);
        }
        logger.info(memoryInfoEntity.toString());
        return memoryInfoEntity;
    }

    @Override
    void doMonitor(ResultCallback callback) {

    }
}
