package com.github.tool.fulfillment.sgschain;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.github.tool.core.http.HttpClient;
import lombok.SneakyThrows;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <a href="http://scmp.sgschain.com/cdm/#">中链全球开放平台</a>
 */
public class SgsChainClient {

    private static final String URL = "http://scmp.sgschain.com";

    private final SgsChainProperties sgsChainProperties;

    public SgsChainClient(SgsChainProperties sgsChainProperties) {
        this.sgsChainProperties = sgsChainProperties;
    }

    /**
     * 发送请求
     *
     * @param httpMethod HttpMethod
     * @param apiMethod  API接口名称，例如：/inventory/inventory_age_list
     * @param apiType    API类型， {@link ApiTypeEnum}
     * @param data       请求体
     * @return 返回数据
     */
    @SneakyThrows
    public String request(Method httpMethod, String apiMethod, String apiType, String data) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/%s/router/rest", URL, apiType));
        LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        long timestamp = System.currentTimeMillis();
        queryParams.set("method", apiMethod);
        queryParams.set("version", "1");
        queryParams.set("app_key", sgsChainProperties.getAppKey());
        queryParams.set("format", "json");
        queryParams.set("sign_method", "md5");
        queryParams.set("timestamp", String.valueOf(timestamp));
        queryParams.set("sign", SignUtils.getSign(queryParams.toSingleValueMap(), null, "", "md5"));
        builder.queryParams(queryParams);
        URI uri = builder.build().toUri();
        // createRequest
        HttpRequest request = HttpClient.createRequest(httpMethod, uri.toString())
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
        map.put("Connection", "Close");
        map.put("SessionId", getSessionId());
        map.put("Authorization", "Bearer" + sgsChainProperties.getAppToken());
        return map;
    }

    /**
     * 构建唯一会话Id
     */
    public static String getSessionId() {
        String str = UUID.randomUUID().toString();
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }
}