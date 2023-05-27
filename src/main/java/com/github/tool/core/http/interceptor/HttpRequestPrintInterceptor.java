package com.github.tool.core.http.interceptor;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.github.tool.core.http.HttpRequestInterceptor;
import com.github.tool.core.http.HttpRequestWrapper;
import com.github.tool.core.http.HttpUtils;

import java.util.Objects;

/**
 * System.out.println
 */
public class HttpRequestPrintInterceptor implements HttpRequestInterceptor {

    @Override
    public boolean beforeHandle(HttpRequestWrapper httpRequestWrapper) {
        HttpRequest httpRequest = httpRequestWrapper.getHttpRequest();
        if (httpRequestWrapper.isPrintEnabled()) {
            System.out.println("HTTP Request");
            System.out.println(String.format("HTTP Method = %s", httpRequest.getMethod()));
            System.out.println(String.format("Request URI = %s", httpRequest.getUrl()));
            System.out.println(String.format("Form = %s", httpRequest.form()));
            System.out.println(String.format("Headers = %s", httpRequest.headers()));
            System.out.println(String.format("Body = %s", HttpUtils.getBody(httpRequest)));
            System.out.println();
        }
        return true;
    }

    @Override
    public boolean afterHandle(HttpRequestWrapper httpRequestWrapper, HttpResponse response) {
        if (Objects.isNull(response)) {
            return true;
        }
        if (httpRequestWrapper.isPrintEnabled()) {
            System.out.println(String.format("HTTP Response"));
            System.out.println(String.format("Status = %s", response.getStatus()));
            System.out.println(String.format("Time = %s ms", httpRequestWrapper.getTotalTimeMillis()));
            System.out.println(String.format("Size = %s", HttpUtils.getSize(response.body())));
            System.out.println(String.format("Headers = %s", response.headers()));
            System.out.println(String.format("Cookies = %s", response.getCookies()));
            System.out.println(String.format("Body = %s", response.body()));
            System.out.println();
        }
        return true;
    }
}
