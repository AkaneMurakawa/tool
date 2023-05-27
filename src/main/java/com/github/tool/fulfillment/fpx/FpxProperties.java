package com.github.tool.fulfillment.fpx;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FpxProperties {

    /**
     * 应用接入申请的AppKey
     */
    private String appKey = "xxx";

    /**
     * 签名密钥
     */
    private String appSecret = "xxx";

    /**
     * API协议版本，参考接口版本号
     */
    private String version = "1.0.0";

    /**
     * 提交的业务数据，默认为json格式，可选值：json。
     */
    private String format = "json";

    /**
     * 响应信息的语言，支持cn（中文），en（英文）
     */
    private String language = "cn";

    /**
     * 是否沙箱环境
     */
    private boolean sandBox = false;
}