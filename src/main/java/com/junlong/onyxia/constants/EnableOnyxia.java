package com.junlong.onyxia.constants;

import com.junlong.onyxia.config.MonitorAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用onyxia监控注解
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/9
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MonitorAutoConfiguration.class)
public @interface EnableOnyxia {
}
