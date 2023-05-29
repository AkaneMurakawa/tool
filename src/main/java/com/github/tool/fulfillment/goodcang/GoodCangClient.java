package com.github.tool.fulfillment.goodcang;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.github.tool.core.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://open.goodcang.com/docs_api/inventory/get_inventory_log">谷仓开放平台</a>
 */
public class GoodCangClient {

    private static final String URL = "https://oms.goodcang.net/public_open";

    private static final String SANDBOX_URL = "https://uat-oms.eminxing.com/public_open";

    private final GoodCangProperties goodCangProperties;

    public GoodCangClient(GoodCangProperties goodCangProperties) {
        this.goodCangProperties = goodCangProperties;
    }

    /**
     * 发送请求
     *
     * @param httpMethod HttpMethod
     * @param apiMethod  API接口名称，例如：/inventory/inventory_age_list
     * @param data       请求体
     * @return 返回数据
     */
    public String request(Method httpMethod, String apiMethod, String data) {
        String url = String.format("%s%s",
                goodCangProperties.isSandBox() ? SANDBOX_URL : URL,
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
        map.put("app-key", goodCangProperties.getAppKey());
        map.put("app-token", goodCangProperties.getAppToken());
        return map;
    }

}