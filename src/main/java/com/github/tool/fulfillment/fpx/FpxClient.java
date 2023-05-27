package com.github.tool.fulfillment.fpx;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.github.tool.core.http.HttpClient;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * <a href="https://open.4px.com/apiInfo/apiDetail?itemId=1">递四方开放平台</a>
 */
public class FpxClient {

    private static final String URL = "https://open.4px.com";

    private static final String SANDBOX_URL = "https://open-test.4px.com";

    private final FpxProperties fpxProperties = new FpxProperties();

    /**
     * 发送请求
     *
     * @param httpMethod HttpMethod
     * @param apiMethod  API接口名称，例如：fu.wms.inventory.getdetail
     * @param data       请求体
     * @return 返回数据
     */
    public String request(Method httpMethod, String apiMethod, String data) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/router/api/service", fpxProperties.isSandBox() ? SANDBOX_URL : URL));
        long timestamp = System.currentTimeMillis();
        LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.set("method", apiMethod);
        queryParams.set("app_key", fpxProperties.getAppKey());
        queryParams.set("v", fpxProperties.getVersion());
        queryParams.set("timestamp", String.valueOf(timestamp));
        queryParams.set("format", fpxProperties.getFormat());
        queryParams.set("sign", getSign(data, apiMethod, timestamp));
        queryParams.set("language", fpxProperties.getLanguage());
        builder.queryParams(queryParams);
        URI uri = builder.build().toUri();
        // createRequest
        HttpRequest request = HttpClient.createRequest(httpMethod, uri.toString())
                .body(data)
                .timeout(HttpGlobalConfig.getTimeout());
        return HttpClient.execute(request);
    }

    /**
     * 获取签名
     */
    public String getSign(String body, String apiMethod, long timestamp) {
        String signString = String.format("app_key%sformat%smethod%stimestamp%sv%s%s%s",
                fpxProperties.getAppKey(),
                fpxProperties.getFormat(),
                apiMethod,
                timestamp,
                fpxProperties.getVersion(),
                body,
                fpxProperties.getAppSecret());
        return DigestUtils.md5DigestAsHex(signString.getBytes(StandardCharsets.UTF_8));
    }
}