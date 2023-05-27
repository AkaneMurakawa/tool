package com.github.tool.core.http.interceptor;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.github.tool.core.http.HttpRequestInterceptor;
import com.github.tool.core.http.HttpRequestWrapper;
import com.github.tool.core.http.HttpUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * 日志打印
 */
@Log4j2
public class HttpRequestLogInterceptor implements HttpRequestInterceptor {

    @Override
    public boolean beforeHandle(HttpRequestWrapper httpRequestWrapper) {
        HttpRequest httpRequest = httpRequestWrapper.getHttpRequest();
        if (httpRequestWrapper.isLogEnabled()) {
            log.info("HTTP Request");
            log.info("HTTP Method = {}", httpRequest.getMethod());
            log.info("Request URI = {}", httpRequest.getUrl());
            log.info("Form = {}", httpRequest.form());
            log.info("Headers = {}", httpRequest.headers());
            log.info("Body = {}", HttpUtils.getBody(httpRequest));
            log.info("");
        }
        return true;
    }

    @Override
    public boolean afterHandle(HttpRequestWrapper httpRequestWrapper, HttpResponse response) {
        if (Objects.isNull(response)) {
            return true;
        }
        if (httpRequestWrapper.isLogEnabled()) {
            log.info("HTTP Response");
            log.info("Status = {}", response.getStatus());
            log.info("Time = {} ms", httpRequestWrapper.getTotalTimeMillis());
            log.info("Size = {}", HttpUtils.getSize(response.body()));
            log.info("Headers = {}", response.headers());
            log.info("Cookies = {}", response.getCookies());
            log.info("Body = {}", response.body());
            log.info("");
        }
        return true;
    }
}
