package com.github.tool.core.http.interceptor;

import cn.hutool.http.HttpResponse;
import com.github.tool.core.http.HttpRequestInterceptor;
import com.github.tool.core.http.HttpRequestWrapper;
import org.springframework.util.StopWatch;

/**
 * 耗时
 */
public class HttpRequestTimeInterceptor implements HttpRequestInterceptor {

    @Override
    public boolean beforeHandle(HttpRequestWrapper httpRequestWrapper) {
        StopWatch stopWatch = httpRequestWrapper.getStopWatch();
        stopWatch.start();
        return true;
    }

    @Override
    public boolean afterHandle(HttpRequestWrapper httpRequestWrapper, HttpResponse response) {
        StopWatch stopWatch = httpRequestWrapper.getStopWatch();
        stopWatch.stop();
        httpRequestWrapper.setTotalTimeMillis(stopWatch.getTotalTimeMillis());
        return true;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
