package com.github.tool.fulfillment.sgschain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API类型
 */
@Getter
@AllArgsConstructor
public enum ApiTypeEnum {

    OMS_API("omsapi"),

    CDM_API("cdmapi"),

    ;

    private final String type;
}
