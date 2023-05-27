package com.github.tool.core.http;

import cn.hutool.http.*;

import java.util.Map;

/**
 * HttpClient
 *
 * @see cn.hutool.http.HttpUtil
 */
public class HttpClient {

    /**
     * 创建Http请求对象
     *
     * @param method 方法枚举{@link Method}
     * @param url    请求的URL，可以使HTTP或者HTTPS
     * @return {@link HttpRequest}
     */
    public static HttpRequest createRequest(Method method, String url) {
        return HttpRequest.of(url).method(method);
    }

    /**
     * 创建Http GET请求对象
     *
     * @param url 请求的URL，可以使HTTP或者HTTPS
     * @return {@link HttpRequest}
     */
    public static HttpRequest createGet(String url) {
        return HttpRequest.get(url);
    }

    /**
     * 创建Http POST请求对象
     *
     * @param url 请求的URL，可以使HTTP或者HTTPS
     * @return {@link HttpRequest}
     */
    public static HttpRequest createPost(String url) {
        return HttpRequest.post(url);
    }

    /**
     * 发送get请求
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 发送get请求
     *
     * @param paramMap post表单数据
     */
    public static String get(String url, Map<String, Object> paramMap) {
        return get(url, paramMap, null);
    }

    public static String get(String url, Map<String, Object> paramMap, Map<String, String> headers) {
        return get(url, paramMap, headers, HttpGlobalConfig.getTimeout());
    }

    /**
     * @param timeout 超时时长，-1表示默认超时，单位毫秒
     */
    public static String get(String url, Map<String, Object> paramMap, Map<String, String> headers, int timeout) {
        HttpRequest httpRequest = HttpRequest
                .get(url)
                .form(paramMap)
                .addHeaders(headers)
                .timeout(timeout);
        return execute(httpRequest);
    }

    public static String post(String url, String body) {
        return post(url, body, null);
    }

    public static String post(String url, String body, Map<String, String> headers) {
        return post(url, body, headers, HttpGlobalConfig.getTimeout());
    }

    public static String post(String url, String body, Map<String, String> headers, int timeout) {
        HttpRequest httpRequest = HttpRequest
                .post(url)
                .body(body)
                .addHeaders(headers)
                .timeout(timeout);
        return execute(httpRequest);
    }

    public static HttpRequestWrapper wrapper(HttpRequest httpRequest) {
        return new HttpRequestWrapper(httpRequest);
    }

    /**
     * 执行请求
     *
     * @return 返回数据
     */
    public static String execute(HttpRequest httpRequest) {
        HttpRequestWrapper httpRequestWrapper = new HttpRequestWrapper(httpRequest);
        HttpResponse response = httpRequestWrapper.execute();
        return response.body();
    }
}
