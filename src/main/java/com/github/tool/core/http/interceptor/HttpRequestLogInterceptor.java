package com.github.tool.core.http.interceptor;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.github.tool.core.http.HttpRequestInterceptor;
import com.github.tool.core.http.HttpRequestWrapper;
import com.github.tool.core.http.HttpUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StopWatch;

import java.util.Objects;

/**
 * 日志打印
 */
@Log4j2
public class HttpRequestLogInterceptor implements HttpRequestInterceptor {

    @Override
    public boolean beforeHandle(HttpRequestWrapper httpRequestWrapper) {
        HttpRequest httpRequest = httpRequestWrapper.getHttpRequest();
        StopWatch stopWatch = httpRequestWrapper.getStopWatch();
        stopWatch.start();
        if (httpRequestWrapper.isLogEnabled()) {
            log.info("HTTP Request");
            log.info("HTTP Method = {}", httpRequest.getMethod());
            log.info("Request URI = {}", httpRequest.getUrl());
            log.info("Form = {}", httpRequest.form());
            log.info("Headers = {}", httpRequest.headers());
            log.info("Body = {}", new String(httpRequest.bodyBytes()));
            log.info("");
        }
        return true;
    }

    @Override
    public boolean afterHandle(HttpRequestWrapper httpRequestWrapper, HttpResponse response) {
        if (Objects.isNull(response)) {
            return true;
        }
        StopWatch stopWatch = httpRequestWrapper.getStopWatch();
        stopWatch.stop();
        if (httpRequestWrapper.isLogEnabled()) {
            log.info("HTTP Response");
            log.info("Status = {}", response.getStatus());
            log.info("Time = {} ms", stopWatch.getTotalTimeMillis());
            log.info("Size = {}", HttpUtils.getSize(response.body()));
            log.info("Headers = {}", response.headers());
            log.info("Body = {}", response.body());
            log.info("Cookies = {}", response.getCookies());
            log.info("");
        }
        return true;
    }
}
