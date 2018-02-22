package com.junlong.onyxia.constants;

import com.junlong.onyxia.config.MonitorAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用onyxia监控注解
 * 由于spring.factories里面配置了启动类，此注解可以不用
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MonitorAutoConfiguration.class)
public @interface EnableOnyxia {
}
