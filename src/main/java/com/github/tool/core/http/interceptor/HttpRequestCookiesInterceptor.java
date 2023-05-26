package com.github.tool.core.http.interceptor;

import cn.hutool.http.HttpRequest;
import com.github.tool.core.http.HttpRequestInterceptor;
import com.github.tool.core.http.HttpRequestWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.util.StringUtil;

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
        String cookie = getCookie();
        if (StringUtil.isNotBlank(cookie)) {
            httpRequest.cookie(cookie);
        }
        return true;
    }

    /**
     * 设置Cookie<br>
     * 自定义Cookie后会覆盖Hutool的默认Cookie行为
     *
     * @return cookies，如果为{@code null}则设置无效，使用默认Cookie行为
     */
    public List<HttpCookie> getCookies() {
        List<HttpCookie> cookies = new ArrayList<>();
        // 示例：cookies.add(new HttpCookie("JSESSIONID", ""))
        return cookies;
    }

    /**
     * 设置Cookie<br>
     * 自定义Cookie后会覆盖Hutool的默认Cookie行为
     *
     * @return cookies，如果为{@code null}则设置无效，使用默认Cookie行为
     */
    public String getCookie() {
        // 名称/值对之间用分号和空格 ('; ')
        // https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Cookie
        // 示例：PHPSESSID=298zf09hf012fh2; csrftoken=u32t4o3tb3gg43; _gat=1
        return "";
    }

}
