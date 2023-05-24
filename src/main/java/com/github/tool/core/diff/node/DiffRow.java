package com.github.tool.core.diff.node;

import com.github.tool.core.diff.converters.Converter;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * diff row
 */
@Getter
@Builder
public class DiffRow {

    private String fieldName;

    private Object left;

    private Object right;

    private Class<?> type;

    private Class<? extends Converter<?>> converter;

    private String format;

    private int order;

    private Tag tag;

    public Tag getTag() {
        if (StringUtils.isEmpty(left) && !StringUtils.isEmpty(right)) {
            return Tag.INSERT;
        }
        if (!StringUtils.isEmpty(left) && StringUtils.isEmpty(right)) {
            return Tag.DELETE;
        }
        if (StringUtils.isEmpty(left) && StringUtils.isEmpty(right)) {
            return Tag.EQUAL;
        }
        if (left.equals(right)) {
            return Tag.EQUAL;
        }
        return Tag.CHANGE;
    }

    public enum Tag {
        INSERT,
        DELETE,
        CHANGE,
        EQUAL,
        ;

        public boolean is(Tag tag) {
            return this.name().equals(tag.name());
        }
    }
}