package com.github.tool.diff.util;

import com.github.tool.diff.annotation.DiffNode;
import com.github.tool.diff.converters.Converter;
import com.github.tool.diff.node.DiffRow;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * 对比工具
 */
public class DiffUtil {

    private DiffUtil() {
    }

    private static final String DEFAULT_SEPARATOR = ",";

    /**
     * 对比
     */
    public static String diff(Object source, Object target) {
        return diff(source, target, DiffUtil::defaultFormat, DEFAULT_SEPARATOR);
    }

    /**
     * 对比
     *
     * @param format    格式化
     * @param separator 分隔符
     */
    public static String diff(Object source, Object target,
                              Function<DiffRow, String> format,
                              String separator) {
        if (Objects.isNull(source) && Objects.isNull(target)) {
            throw new IllegalArgumentException("source target can not be null");
        }

        StringBuilder sb = new StringBuilder();
        List<DiffRow> diffRows = getDiffRows(source, target);
        for (int i = 0; i < diffRows.size(); i++) {
            DiffRow diffRow = diffRows.get(i);
            String s = format.apply(diffRow);
            // 跳过EQUAL
            if (StringUtils.isBlank(s)) {
                continue;
            }
            sb.append(s);
            if (i != diffRows.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    @SneakyThrows
    private static String defaultFormat(DiffRow diffRow) {
        if (DiffRow.Tag.EQUAL.is(diffRow.getTag())) {
            return "";
        }
        // 转换器
        Method[] methods = diffRow.getConverter().getDeclaredMethods();
        Optional<Method> convert = Arrays.stream(methods).filter(m -> m.getName().equals("convert")).findFirst();
        Assert.isTrue(convert.isPresent(), "convert can not found");
        Method method = convert.get();
        Converter<?> converter = diffRow.getConverter().newInstance();
        // 对象
        return String.format(diffRow.getFormat(),
                diffRow.getFieldName(),
                method.invoke(converter, diffRow, diffRow.getLeft()),
                method.invoke(converter, diffRow, diffRow.getRight()));
    }


    @SneakyThrows
    private static List<DiffRow> getDiffRows(Object source, Object target) {
        Class<?> clazz = Objects.nonNull(source) ? source.getClass() : target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<DiffRow> diffRows = new ArrayList<>(fields.length);
        for (Field field : fields) {
            DiffNode diffNode = field.getAnnotation(DiffNode.class);
            if (null == diffNode) {
                continue;
            }
            int order = diffNode.order();
            diffRows.add(DiffRow.builder()
                    .fieldName(diffNode.value())
                    .left(Objects.nonNull(source) ? field.get(source) : null)
                    .right(Objects.nonNull(target) ? field.get(target) : null)
                    .type(field.getType())
                    .converter(diffNode.converter())
                    .format(diffNode.format())
                    .order(order)
                    .build());
        }
        // sort
        diffRows.sort(Comparator.comparing(DiffRow::getOrder));
        return diffRows;
    }
}
