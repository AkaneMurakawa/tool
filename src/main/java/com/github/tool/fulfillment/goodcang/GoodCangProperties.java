package com.github.tool.fulfillment.goodcang;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodCangProperties {

    /**
     * 应用接入申请的AppKey
     */
    private String appKey = "xxx";

    /**
     * 签名密钥
     */
    private String appToken = "xxx";

    /**
     * 是否沙箱环境
     */
    private boolean sandBox = true;
}