package com.junlong.onyxia.config;

import com.junlong.onyxia.service.MonitorRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配，若配置监控属性，则自动开启监控
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(MonitorConfig.class)
public class MonitorAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "onyxia.mainSwitch",havingValue = "true",matchIfMissing = false)
    @ConditionalOnMissingBean(value = MonitorRunner.class)
    public MonitorRunner createMonitorRunnerBean(){
        return new MonitorRunner();
    }
}
