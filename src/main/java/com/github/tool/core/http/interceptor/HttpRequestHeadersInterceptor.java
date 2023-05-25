package com.github.tool.core.http.interceptor;

import cn.hutool.http.HttpRequest;
import com.github.tool.core.http.HttpRequestInterceptor;
import com.github.tool.core.http.HttpRequestWrapper;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加全局Headers
 */
@Log4j2
public class HttpRequestHeadersInterceptor implements HttpRequestInterceptor {

    @Override
    public boolean beforeHandle(HttpRequestWrapper httpRequestWrapper) {
        HttpRequest httpRequest = httpRequestWrapper.getHttpRequest();
        httpRequest.addHeaders(getHeaders());
        return true;
    }

    public Map<String, String> getHeaders() {
        return new HashMap();
    }

}
