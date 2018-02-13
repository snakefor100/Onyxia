package com.junlong.onyxia.constants;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/13
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface OnyxiaCallback {
    MonitorMenuEnum monitorMenu();
}
