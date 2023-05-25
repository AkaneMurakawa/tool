package com.github.tool.core.http;

import cn.hutool.http.HttpResponse;

/**
 * 请求处理器
 */
public interface HttpRequestInterceptor extends Ordered {

    /**
     * 前置处理
     *
     * @param httpRequestWrapper HttpRequest
     * @return 是否继续执行接下来的处理器
     */
    default boolean beforeHandle(HttpRequestWrapper httpRequestWrapper) {
        return true;
    }

    /**
     * 后置处理
     *
     * @param httpRequestWrapper HttpRequest
     * @param response           HttpResponse
     * @return 是否继续执行接下来的处理器
     */
    default boolean afterHandle(HttpRequestWrapper httpRequestWrapper, HttpResponse response) {
        return true;
    }
}
