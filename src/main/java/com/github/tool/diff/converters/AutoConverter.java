package com.github.tool.diff.converters;

import com.github.tool.diff.node.DiffRow;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 自动转换器
 */
public class AutoConverter implements Converter<Object> {

    @Override
    public Object convert(DiffRow diffRow, Object obj) {
        if (Objects.isNull(obj) || StringUtils.isBlank(obj.toString())) {
            return "空";
        }
        return obj;
    }
}
