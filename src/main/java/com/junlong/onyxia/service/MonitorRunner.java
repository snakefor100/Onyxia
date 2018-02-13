package com.junlong.onyxia.service;

import com.junlong.onyxia.config.MonitorConfig;
import com.junlong.onyxia.config.MonitorMenuConfig;
import com.junlong.onyxia.constants.MonitorMenuEnum;
import com.junlong.onyxia.constants.OnyxiaCallback;
import com.junlong.onyxia.service.callback.DefaultResultCallback;
import com.junlong.onyxia.service.callback.ResultCallback;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 若有配置监控开启，则MonitorRunner放入spring容器管理
 * 在容器启动成功后，执行定时监控
 *
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/9
 * @since 1.0.0
 */
public class MonitorRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(MonitorRunner.class);
    private static volatile Map<MonitorMenuEnum, ResultCallback> callbackMap = new HashMap<MonitorMenuEnum, ResultCallback>(MonitorMenuEnum.values().length);


    @Resource
    MonitorConfig monitorConfig;
    @Resource
    ApplicationContext context;


    @Override
    public void run(String... args) throws Exception {
        logger.info("Spring boot 容器初始化完成");
        initCallback();
        List<MonitorMenuConfig> monitorMenuList = monitorConfig.getMonitorMenu();
        if (CollectionUtils.isEmpty(monitorMenuList) || !monitorConfig.isMainSwitch()) {
            return;
        }
        for (MonitorMenuConfig monitorMenuConfig : monitorMenuList) {
            if(!monitorMenuConfig.isMenuSwitch()){
                continue;
            }
            switch (monitorMenuConfig.getMonitorMenu()) {
                case THREAD:
                    new ThreadMonitorHandler().monitorTaskStart(getCallbackByMonitorMenu(MonitorMenuEnum.THREAD), monitorMenuConfig);
                    break;
                case GC:
                    new GcMonitorHandler().monitorListenerStart(getCallbackByMonitorMenu(MonitorMenuEnum.GC));
                    break;
                case MEMORY:
                    new MemoryMonitorHandler().monitorTaskStart(getCallbackByMonitorMenu(MonitorMenuEnum.MEMORY), monitorMenuConfig);
                    break;
                default:
                    logger.error("Onyxia 监控配置异常，无法找到当前配置 {}", monitorMenuConfig);
            }
        }
    }

    /**
     * 初始化回调函数
     */
    private void initCallback() {
        Map<String, Object> beansWithAnnotationMap = context.getBeansWithAnnotation(OnyxiaCallback.class);
        if (CollectionUtils.isEmpty(beansWithAnnotationMap)) {
            for (MonitorMenuEnum monitorMenuEnum : MonitorMenuEnum.values()) {
                callbackMap.put(monitorMenuEnum, new DefaultResultCallback());
            }
            return;
        }
        for (Object object : beansWithAnnotationMap.values()) {
            OnyxiaCallback annotation = object.getClass().getAnnotation(OnyxiaCallback.class);
            callbackMap.put(annotation.monitorMenu(), (ResultCallback) object);
        }
    }

    private ResultCallback getCallbackByMonitorMenu(MonitorMenuEnum monitorMenuEnum) {
        ResultCallback resultCallback = callbackMap.get(monitorMenuEnum);
        return resultCallback == null ? new DefaultResultCallback() : resultCallback;
    }
}
