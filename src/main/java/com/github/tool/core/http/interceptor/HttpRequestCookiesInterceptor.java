package com.github.tool.core.http.interceptor;

import cn.hutool.http.HttpRequest;
import com.github.tool.core.http.HttpRequestInterceptor;
import com.github.tool.core.http.HttpRequestWrapper;
import lombok.extern.log4j.Log4j2;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加全局Cookies
 */
@Log4j2
public class HttpRequestCookiesInterceptor implements HttpRequestInterceptor {

    @Override
    public boolean beforeHandle(HttpRequestWrapper httpRequestWrapper) {
        HttpRequest httpRequest = httpRequestWrapper.getHttpRequest();
        httpRequest.cookie(getCookies());
        httpRequest.cookie(getCookie());
        return true;
    }

    public List<HttpCookie> getCookies() {
        List<HttpCookie> cookies = new ArrayList<>();
        // 例如：cookies.add(new HttpCookie("JSESSIONID", ""))
        return cookies;
    }

    public String getCookie() {
        // 名称/值对之间用分号和空格 ('; ')
        // https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Cookie
        return "";
    }

}
