package com.github.tool.fulfillment.armlogi;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.github.tool.core.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://apisandbox.armlogi.com/doc.html#/0-%E5%85%A8%E9%83%A8%E5%88%86%E7%BB%84/%E4%BA%A7%E5%93%81/listsSkuStockUsingPOST">大方广开放平台</a>
 */
public class ArmLogiClient {

    private static final String URL = "https://openapi.armlogi.com";

    private static final String SANDBOX_URL = "https://apisandbox.armlogi.com";

    private final ArmLogiProperties armlogiProperties = new ArmLogiProperties();

    /**
     * 发送请求
     *
     * @param httpMethod HttpMethod
     * @param apiMethod  API接口名称，例如：/api/v1/prodcut/listsSkuStock
     * @param data       请求体
     * @return 返回数据
     */
    public String request(Method httpMethod, String apiMethod, String data) {
        String url = String.format("%s%s",
                armlogiProperties.isSandBox() ? SANDBOX_URL : URL,
                apiMethod);
        // createRequest
        HttpRequest request = HttpClient.createRequest(httpMethod, url)
                .body(data)
                .addHeaders(getHeaders())
                .timeout(HttpGlobalConfig.getTimeout());
        return HttpClient.execute(request);
    }

    /**
     * 获取请求头
     */
    public Map<String, String> getHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("apiSecret", armlogiProperties.getApiSecret());
        return map;
    }

}