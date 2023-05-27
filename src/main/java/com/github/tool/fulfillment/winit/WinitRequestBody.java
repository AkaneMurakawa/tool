package com.github.tool.fulfillment.winit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinitRequestBody {

    /**
     * API接口名称
     */
    @JsonProperty("action")
    private String action;

    /**
     * 登录万邑联的账号
     */
    @JsonProperty("app_key")
    private String appKey;

    /**
     * 应用ID client_id，获取方法请参照<a href="https://developer.winit.com.cn/document/detail/id/127.html">《开发者控制台 操作手册》</a>
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 客户签名，签名生成方法请参照：<a href="https://developer.winit.com.cn/document/detail/id/6.html">《签名认证》</a>中sign的生成
     */
    @JsonProperty("client_sign")
    private String clientSign;

    /**
     * 应用签名，签名生成方法请参照：<a href="https://developer.winit.com.cn/document/detail/id/6.html">《签名认证》</a>中client_sign的生成
     */
    @JsonProperty("sign")
    private String sign;

    /**
     * 应用Code，获取方法请参照<a href="https://developer.winit.com.cn/document/detail/id/127.html">《开发者控制台 操作手册》</a>
     */
    @JsonProperty("platform")
    private String platform;

    /**
     * 接口版本
     */
    @JsonProperty("version")
    private String version;

    /**
     * 格式
     */
    @JsonProperty("format")
    private String format;

    /**
     * 签名方式
     */
    @JsonProperty("sign_method")
    private String signMethod;

    /**
     * 时间戳 格式为 yyyy-MM-dd HH:mm:ss
     */
    @JsonProperty("timestamp")
    private String timestamp;

    /**
     * 语言
     */
    @JsonProperty("language")
    private String language;

    /**
     * 请求参数
     */
    @JsonProperty("data")
    private Object data;

}
