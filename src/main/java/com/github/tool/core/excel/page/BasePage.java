package com.github.tool.core.excel.page;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页基础类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BasePage {

    /**
     * 当前页，默认:1
     */
    private int current = 1;

    /**
     * 每页显示条数，默认:20
     */
    private int size = 20;

}
