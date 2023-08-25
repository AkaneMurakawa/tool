package com.github.tool.core.excel.handler;

import java.util.List;
import java.util.function.Function;

/**
 * Excel导出处理器
 *
 * @param <T> 查询条件
 * @param <R> 返回结果
 */
public interface ExcelExporterHandler<T, R> {

    /**
     * 获取数据Function
     */
    Function<T, R> getDataFunction();

    /**
     * 获取数据，给数据一次处理的机会
     */
    List<?> getData(R r);

    /**
     * 是否还有下一页
     */
    boolean hasNext(R r);

    /**
     * 下一页
     */
    void next(T t);
}
