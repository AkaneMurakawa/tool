package com.github.tool.core.excel.handler;

/**
 * Excel导出代理处理器，给调用者一次手动处理的机会
 */
public interface ExcelExporterProxyHandler {

    /**
     * 匹配
     *
     * @param bizType 业务类型
     */
    boolean isMatch(String bizType);

    /**
     * 导出
     *
     * @return 文件链接
     */
    String exporter();
}
