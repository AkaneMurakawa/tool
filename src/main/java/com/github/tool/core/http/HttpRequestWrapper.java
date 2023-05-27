package com.github.tool.core.http;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.github.tool.core.http.interceptor.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * HttpRequest包装
 */
@Log4j2
@Getter
public class HttpRequestWrapper {

    @Setter
    private boolean isPrintEnabled = true;

    @Setter
    private boolean isLogEnabled = false;

    @Setter
    private long totalTimeMillis;

    private final StopWatch stopWatch = new StopWatch(Thread.currentThread().getName());

    private static final List<HttpRequestInterceptor> interceptors = new ArrayList<>();

    static {
        // addInterceptors
        interceptors.add(new HttpRequestTimeInterceptor());
        interceptors.add(new HttpRequestPrintInterceptor());
        interceptors.add(new HttpRequestLogInterceptor());
        interceptors.add(new HttpRequestHeadersInterceptor());
        interceptors.add(new HttpRequestCookiesInterceptor());
        interceptors.sort(Comparator.comparing(HttpRequestInterceptor::getOrder));
    }

    private final HttpRequest httpRequest;

    public HttpRequestWrapper(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    private void before() {
        if (CollectionUtil.isEmpty(interceptors)) {
            return;
        }
        for (HttpRequestInterceptor interceptor : interceptors) {
            if (!interceptor.beforeHandle(this)) {
                break;
            }
        }
    }

    private void after(HttpResponse response) {
        if (CollectionUtil.isEmpty(interceptors)) {
            return;
        }
        for (HttpRequestInterceptor interceptor : interceptors) {
            if (!interceptor.afterHandle(this, response)) {
                break;
            }
        }
    }

    public HttpResponse execute() {
        before();
        HttpResponse response = null;
        try {
            response = this.httpRequest.execute();
        } finally {
            after(response);
        }
        return response;
    }

}
