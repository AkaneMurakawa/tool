package com.github.tool.core.excel.handler;

/**
 * Excel导出处理器
 *
 * @param <T> 查询条件
 * @param <R> 返回结果
 */
public interface ExcelExporterStrategyHandler<T, R> extends ExcelExporterHandler<T, R> {

    /**
     * 匹配
     *
     * @param bizType 业务类型
     */
    boolean isMatch(String bizType);

}
