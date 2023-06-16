package com.github.tool.core.list;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * ListUtils
 */
public final class ListUtils {

    private ListUtils() {
    }

    /**
     * 不存在于指定集合中
     */
    public static <T> Collection<String> noneMatch(Collection<String> list1, Collection<T> list2, Function<T, String> mapper) {
        if (CollectionUtils.isEmpty(list1)) {
            return Lists.newArrayList();
        }
        List<String> strings = convert(list2, mapper);
        return filter(list1, str -> !strings.contains(str));
    }

    /**
     * 是否包含指定元素
     */
    public static <T> boolean contains(Collection<T> list, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return list.stream().anyMatch(predicate);
    }

    /**
     * 过滤
     */
    public static <T> List<T> filter(Collection<T> list, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 过滤出存在于指定集合中的對象
     *
     * @param list       原集合
     * @param mapper     集合中對象的屬性
     * @param containers 存在的屬性集合
     * @return 存在的對象
     */
    public static <T, K> Collection<T> filter(Collection<T> list, Function<T, K> mapper, Collection<K> containers) {
        if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(containers)) {
            return Lists.newArrayList();
        }
        return filter(list, t -> containers.contains(mapper.apply(t)));
    }

    /**
     * 过滤后合计
     */
    public <T> Integer filterAfterSum(Collection<T> list, Predicate<? super T> predicate, Function<T, Integer> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return NumberUtils.INTEGER_ZERO;
        }
        return list.stream().filter(predicate).map(mapper).reduce(Integer::sum).get();
    }

    /**
     * 根据表达式获取最大元素
     */
    public static <T, U extends Comparable<? super U>> T findLargest(Collection<T> list, Function<T, U> keyExtractor) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().sorted(Comparator.comparing(keyExtractor).reversed()).findFirst().get();
        }
        return null;
    }

    /**
     * 根据表达式过滤出第一个符合的元素
     */
    public static <T> T findFirst(Collection<T> list, Predicate<? super T> predicate) {
        if (CollectionUtils.isNotEmpty(list)) {
            Optional<T> option = list.stream().filter(predicate).findFirst();
            if (option.isPresent()) {
                return option.get();
            }
        }
        return null;
    }

    /**
     * 根据表达式获取唯一泛型，否则抛出异常
     */
    public static <T> T findOne(Collection<T> list, Predicate<? super T> predicate) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<T> collect = list.stream().filter(predicate).collect(Collectors.toList());
            if (collect.size() != 1) {
                throw new RuntimeException(String.format("execute Method There are  %s results.", collect.size()));
            }
            return collect.get(0);
        }
        return null;
    }

    /**
     * 转换集合属性
     */
    public static <T, R> List<R> convert(Collection<T> list, Function<T, R> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(mapper).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 转换集合属性并去重
     */
    public static <T, R> List<R> convertDistinct(Collection<T> list, Function<T, R> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(mapper).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * 转换集合属性(过滤null)
     */
    public static <T, R> Set<R> toSet(Collection<T> list, Function<T, R> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Sets.newHashSet();
        }
        return list.stream().map(mapper).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * List<T> 转为 Map<K,T>，过滤重复key值
     */
    public static <K, T> Map<K, T> toMap(Collection<T> list, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (key1, key2) -> key1));
    }

    /**
     * List<T> 转为 Map<K,U>，重复取第一个
     */
    public static <K, T, U> Map<K, U> toMap(Collection<T> list, Function<T, K> keyMapper, Function<T, U> valMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, valMapper, (v1, v2) -> v1));
    }

    /**
     * List<T> 转为 Map<K, T>,只保留一个,需提供合并规则
     */
    public static <T, K> Map<K, T> toMap(Collection<T> list, Function<T, K> keyFunction, BinaryOperator<T> mergeFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(keyFunction, Function.identity(), mergeFunction));
    }

    /**
     * 将key value 数组转为 map
     *
     * @param keysValues key value 数组
     * @param <K>        key
     * @param <V>        value
     * @return map 集合
     */
    public static <K, V> Map<K, V> toMap(Object... keysValues) {
        int kvLength = keysValues.length;
        if (kvLength % 2 != 0) {
            throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
        }
        Map<K, V> keyValueMap = new HashMap<>(kvLength);
        for (int i = kvLength - 2; i >= 0; i -= 2) {
            Object key = keysValues[i];
            Object value = keysValues[i + 1];
            keyValueMap.put((K) key, (V) value);
        }
        return keyValueMap;
    }

    /**
     * Map转List
     */
    public static <K, V, R> List<R> toList(Map<K, V> map, BiFunction<K, V, R> function) {
        List<R> respList = Lists.newArrayList();
        if (MapUtils.isNotEmpty(map)) {
            map.forEach((key, value) -> respList.add(function.apply(key, value)));
        }
        return respList;
    }

    /**
     * 获取集合的差集
     */
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        return list1.stream().filter(property -> !list2.contains(property)).collect(Collectors.toList());
    }

    /**
     * 按属性分组(相同属性Map.get->List)
     */
    public static <K, T> Multimap<K, T> group(Collection<T> list, Function<? super T, K> keyFunction) {
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        return Multimaps.index(list, keyFunction::apply);
    }

    /**
     * 按指定key规则分组，分组后map-list支持修改操作
     */
    public static <K, T> Map<K, List<T>> groupBy(Collection<T> list, Function<T, K> keyFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Map<K, List<T>> group = Maps.newHashMap();
        for (T item : list) {
            group.merge(keyFunction.apply(item), Lists.newArrayList(item), (o1, o2) -> {
                o1.addAll(o2);
                return o1;
            });
        }
        return group;
    }

    /**
     * 按指定key规则分组成不同堆，每个堆中key不重复，每个堆是一个list
     *
     * @param list        待分组集合
     * @param keyFunction key表达式
     * @param <K>         key类型
     * @param <T>         list->item类型
     * @return 堆集合
     */
    public static <K, T> List<List<T>> groupDiffHeap(Collection<T> list, Function<T, K> keyFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<Map<K, T>> group = Lists.newArrayList();
        for (T item : list) {
            K key = keyFunction.apply(item);
            boolean processed = false;
            for (Map<K, T> map : group) {
                if (!map.containsKey(key)) {
                    map.put(key, item);
                    processed = true;
                    break;
                }
            }
            if (!processed) {
                Map<K, T> map = Maps.newHashMap();
                map.put(key, item);
                group.add(map);
            }
        }
        List<List<T>> result = Lists.newArrayListWithExpectedSize(group.size());
        group.forEach(entry -> result.add(Lists.newArrayList(entry.values())));
        return result;
    }

    /**
     * 合计
     */
    public <T> BigDecimal summary(Collection<T> list, Function<T, BigDecimal> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return BigDecimal.ZERO;
        }
        return list.stream().map(mapper).reduce(BigDecimal::add).get();
    }

    /**
     * 合计
     */
    public <T> Integer sum(Collection<T> list, Function<T, Integer> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return NumberUtils.INTEGER_ZERO;
        }
        return list.stream().map(mapper).reduce(Integer::sum).get();
    }

    /**
     * 数据根据条件去重
     */
    public static <T, U extends Comparable<? super U>> List<T> deduplication(List<T> list, Function<? super T, ? extends U> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(function))), ArrayList::new)
        );
    }

    /**
     * 去重统计
     */
    public <T> Long distinctCount(Collection<T> list, Function<T, Object> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return NumberUtils.LONG_ZERO;
        }
        return list.stream().map(mapper).distinct().count();
    }

    /**
     * 过滤统计
     */
    public <T> Long filterCount(Collection<T> list, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(list)) {
            return NumberUtils.LONG_ZERO;
        }
        return list.stream().filter(predicate).count();
    }

    /**
     * 分组汇总
     *
     * @param list            集合数据
     * @param groupFunction   分组的表达式
     * @param summaryFunction 汇总的表达式
     */
    public static <K, T> Map<K, Integer> groupSummary(Collection<T> list,
                                                      Function<? super T, K> groupFunction,
                                                      Function<T, Integer> summaryFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(groupingBy(groupFunction, reducing(0, summaryFunction, Integer::sum)));
    }

    public static <T, R> List<R> forEachFunc(Collection<T> list,
                                             Predicate<? super T> predicate,
                                             Function<T, R> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<R> rList = Lists.newArrayList();
        list.forEach(t -> {
            if (predicate.test(t)) {
                rList.add(function.apply(t));
            }
        });
        return rList;
    }

    public static <T> void forEach(Collection<T> list,
                                   Predicate<? super T> predicate,
                                   Consumer<T> consumer) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(t -> {
            if (predicate.test(t)) {
                consumer.accept(t);
            }
        });
    }

    public static <T> String joinWith(Collection<T> list, Function<T, String> mapper, String separator) {
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return Joiner.on(separator).skipNulls().join(convert(list, mapper));
    }

    @SafeVarargs
    public static <T> List<T> sort(Comparator<T> comparator, T... items) {
        List<T> list = Lists.newArrayList(Arrays.asList(items));
        list.sort(comparator);
        return list;
    }

    /**
     * 按指定属性分组且将分组的值(去重复)按','连接
     *
     * @return {a='1,2,3',b='4,5,6'}
     */
    public static <T, K> Map<K, String> groupAfterJoin(Collection<T> list,
                                                       Function<T, K> keyMapper,
                                                       Function<T, String> valueMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Function<T, String> distinctKey = t -> keyMapper.apply(t) + "-" + valueMapper.apply(t);
        return list.stream().filter(distinctByKey(distinctKey)).collect(groupingBy(keyMapper, mapping(valueMapper, joining(","))));
    }

    private static <T, K> Predicate<T> distinctByKey(Function<T, K> keyExtractor) {
        Set<K> containers = Sets.newConcurrentHashSet();
        return t -> containers.add(keyExtractor.apply(t));
    }

    /**
     * 按指定属性分组且将分组的值(去重复)计数
     */
    public static <T, K, R> Map<K, Long> groupAfterCounting(Collection<T> list,
                                                            Function<T, K> keyMapper,
                                                            Function<T, R> valueMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Function<T, String> distinctKey = t -> keyMapper.apply(t) + "-" + valueMapper.apply(t);
        return list.stream().filter(distinctByKey(distinctKey)).collect(Collectors.groupingBy(keyMapper, Collectors.counting()));
    }

    private static <T, K> Predicate<T> addByKey(Function<T, K> keyExtractor) {
        List<K> containers = Lists.newArrayList();
        return t -> containers.add(keyExtractor.apply(t));
    }

    /**
     * 按指定属性分组且将分组的值(不去重复)计数
     */
    public static <T, K> Map<K, Long> groupAfterListCounting(Collection<T> list,
                                                             Function<T, K> keyMapper,
                                                             Function<T, String> valueMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Function<T, String> distinctKey = t -> keyMapper.apply(t) + "-" + valueMapper.apply(t);
        return list.stream().filter(addByKey(distinctKey)).collect(Collectors.groupingBy(keyMapper, Collectors.counting()));
    }

    /**
     * 多个List合并成一个
     */
    @SafeVarargs
    public static <T, K, U> List<U> merge(Function<? super T, ? extends K> keyMapper,
                                          Function<? super T, ? extends U> valueMapper,
                                          BinaryOperator<U> mergeFunction, Collection<T>... list) {
        if (list.length == 0) {
            return Lists.newArrayList();
        }
        return new ArrayList<>(Stream.of(list).filter(Objects::nonNull).flatMap(Collection::stream).distinct().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction)).values());
    }

    /**
     * 将集合按指定规则合并成key不重复的list返回
     *
     * @param list          集合
     * @param keyMapper     键获取规则
     * @param mergeFunction 合并规则
     * @param <K>           键类型
     * @param <V>           值类型
     */
    public static <K, V> List<V> merge(Collection<V> list, Function<V, K> keyMapper, BinaryOperator<V> mergeFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        Map<K, V> map = new HashMap<>(list.size());
        for (V v : list) {
            K k = keyMapper.apply(v);
            map.merge(k, v, mergeFunction);
        }
        return new ArrayList<>(map.values());
    }

    /**
     * List<T> 转为 Map<K,U>，重复取第一个
     */
    public static <K, T, U> Map<K, U> toConcurrentMap(Collection<T> list, Function<T, K> keyMapper, Function<T, U> valMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.toConcurrentMap(keyMapper, valMapper, (v1, v2) -> v1));
    }

    /**
     * 清理
     */
    public static void clear(Collection<?> collection) {
        if (CollectionUtil.isNotEmpty(collection)) {
            collection.clear();
        }
    }
}
