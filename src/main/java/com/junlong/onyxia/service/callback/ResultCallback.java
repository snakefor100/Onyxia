package com.junlong.onyxia.service.callback;

/**
 * 监控结果回调
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ResultCallback<T> {
    void doCallback(T monitorResult);
}
