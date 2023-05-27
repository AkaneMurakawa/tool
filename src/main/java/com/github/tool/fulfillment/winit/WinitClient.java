package com.github.tool.fulfillment.winit;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tool.core.http.HttpClient;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <a href="https://developer.winit.com.cn/document/detail/id/44.html">Winit万邑通开放平台</a>
 */
public class WinitClient {

    private final WinitProperties winitProperties = new WinitProperties();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String URL = "https://openapi.winit.com.cn/openapi/service";

    private static final String SANDBOX_URL = "https://sandboxopenapi.winit.com.cn/openapi/service";

    /**
     * 发送请求
     *
     * @param httpMethod HttpMethod
     * @param apiMethod  API接口名称，例如：queryWarehouseStorage
     * @param data       请求体
     * @return 返回数据
     */
    @SneakyThrows
    public String request(Method httpMethod, String apiMethod, String data) {
        String url = winitProperties.isSandBox() ? SANDBOX_URL : URL;
        Object dataObj = null;
        if (data != null) {
            dataObj = objectMapper.readValue(data, Object.class);
            data = objectMapper.writeValueAsString(dataObj);
        } else {
            data = "";
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        WinitRequestBody requestBody = BeanUtil.copyProperties(winitProperties, WinitRequestBody.class);
        requestBody.setAction(apiMethod);
        requestBody.setTimestamp(timestamp);
        requestBody.setData(dataObj);
        requestBody.setClientSign(getSign(winitProperties.getClientSecret(), requestBody, data));
        requestBody.setSign(getSign(winitProperties.getToken(), requestBody, data));
        String body;
        try {
            body = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
        // createRequest
        HttpRequest request = HttpClient.createRequest(httpMethod, url)
                .body(body)
                .timeout(HttpGlobalConfig.getTimeout());
        return HttpClient.execute(request);
    }

    /**
     * <a href="https://developer.winit.com.cn/document/detail/id/6.html">签名认证</a>
     */
    public String getSign(String secret, WinitRequestBody requestBody, String data) {
        try {
            String signStr = String.format("%saction%sapp_key%sdata%sformat%splatform%ssign_method%stimestamp%sversion%s%s",
                    secret,
                    requestBody.getAction(),
                    requestBody.getAppKey(),
                    data,
                    requestBody.getFormat(),
                    requestBody.getPlatform(),
                    requestBody.getSignMethod(),
                    requestBody.getTimestamp(),
                    requestBody.getVersion(),
                    secret);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return Hex.encodeHexString(md5.digest(signStr.getBytes())).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
