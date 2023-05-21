package com.github.tool.list;

import cn.hutool.core.lang.Assert;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.FatalBeanException;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;

/**
 * BeanUtils
 */
public final class BeanUtils extends org.springframework.beans.BeanUtils {

    private BeanUtils() {
    }

    /**
     * 复制属性，忽略null
     *
     * @param src    原对象
     * @param target 目标对象
     */
    public static <T> T copyPropIgnoreNull(Object src, T target) {
        copyProperties(src, target, getNullPropertyNames(src));
        return target;
    }

    public static <R, T> T copyPropIgnoreNull(R orig, Class<T> target) {
        if (orig == null) {
            return null;
        }
        Preconditions.checkNotNull(target);
        return copyPropIgnoreNull(orig, instantiateClass(target));
    }

    /**
     * 转换
     */
    public static <R, T> List<T> copyList(Collection<R> origList, Class<T> target) {
        if (CollectionUtils.isEmpty(origList)) {
            return Lists.newArrayList();
        }
        return ListUtils.convert(origList, t -> copyPropIgnoreNull(t, target));
    }

    /**
     * 忽略空和空字符
     */
    public static <T> T copyPropIgnoreBlank(Object src, T target) {
        copyProperties(src, target, getBlankPropertyNames(src));
        return target;
    }

    /**
     * 忽略空和空字符
     */
    public static <T> T copyPropIgnoreBlank(Object src, Class<T> target) {
        Preconditions.checkNotNull(target);
        return copyPropIgnoreBlank(src, instantiateClass(target));
    }

    private static String[] getNullPropertyNames(Object source) {
        Predicate<Object> predicate = Objects::isNull;
        return excludePropertyNames(source, predicate);
    }

    /**
     * 获取null和空字符的属性
     */
    private static String[] getBlankPropertyNames(Object source) {
        Predicate<Object> predicate = t -> t == null || StringUtils.isBlank(t.toString());
        return excludePropertyNames(source, predicate);
    }

    /**
     * 根据条件排除字段
     */
    private static String[] excludePropertyNames(Object source, Predicate<Object> predicate) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            if (src.isReadableProperty(pd.getName())) {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (predicate.test(srcValue)) {
                    emptyNames.add(pd.getName());
                }
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 将源对象有效的属性复制到目标对象中
     */
    public static void copyPropertiesActive(Object source, Object target, @Nullable String... activeProperties) {

        Assert.notNull(source, "source must not be null");
        Assert.notNull(target, "target must not be null");

        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> activeList = (activeProperties != null ? Arrays.asList(activeProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && activeList.contains(targetPd.getName())) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Exception ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * 对象转map
     */
    public static BeanMap bean2map(Object bean) {
        return BeanMap.create(bean);
    }
}
