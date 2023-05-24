package com.github.tool.core.diff.converters;

import com.github.tool.core.diff.node.DiffRow;

/**
 * 转换器
 */
public interface Converter<T> {

    /**
     * 转换
     */
    T convert(DiffRow diffRow, Object obj);
}
