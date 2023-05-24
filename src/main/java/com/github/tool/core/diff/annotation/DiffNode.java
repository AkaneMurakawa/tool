package com.github.tool.core.diff.annotation;

import com.github.tool.core.diff.converters.AutoConverter;
import com.github.tool.core.diff.converters.Converter;

import java.lang.annotation.*;

/**
 * Diff node
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DiffNode {

    /**
     * 字段名称
     */
    String value() default "";

    /**
     * 属性转换器
     */
    Class<? extends Converter<?>> converter() default AutoConverter.class;

    /**
     * 格式化
     */
    String format() default "[%s：%s -> %s]";

    /**
     * 顺序
     */
    int order() default 0;
}
