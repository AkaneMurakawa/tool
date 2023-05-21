package com.github.tool.diff.converters;

import com.github.tool.diff.node.DiffRow;

/**
 * 转换器
 */
public interface Converter<T> {

    /**
     * 转换
     */
    T convert(DiffRow diffRow, Object obj);
}
