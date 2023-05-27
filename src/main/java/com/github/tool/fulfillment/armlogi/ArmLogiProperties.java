package com.github.tool.fulfillment.armlogi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArmLogiProperties {

    /**
     * 签名密钥
     */
    private String apiSecret = "xxx";

    /**
     * 是否沙箱环境
     */
    private boolean sandBox = true;

}