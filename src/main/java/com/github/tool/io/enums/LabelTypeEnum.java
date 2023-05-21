package com.github.tool.io.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标签尺寸
 */
@Getter
@AllArgsConstructor
public enum LabelTypeEnum {

    /**
     * 7*2.2cm
     */
    LABEL_7_22(70, 22),

    /**
     * 7*4cm
     */
    LABEL_7_4(70, 40),

    /**
     * 8*9cm
     */
    LABEL_L1(80, 90),

    /**
     * 10*6cm
     */
    LABEL_L2(100, 60),

    /**
     * 10*15cm
     */
    LABEL_L3(100, 150),

    /**
     * 10*20cm
     */
    LABEL_L4(100, 200),

    /**
     * A4：21*29.7cm
     */
    LABEL_A4(210, 297),

    /**
     * A5：14.8*21cm
     */
    LABEL_A5(148, 210),

    /**
     * A6：10.5.8*14.4cm
     */
    LABEL_A6(105, 144),

    ;

    /**
     * 长(mm)
     */
    private int width;

    /**
     * 宽(mm)
     */
    private int height;
}
