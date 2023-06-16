package com.github.tool.core.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * JsonUtils
 */
@Log4j2
public final class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 统一日期格式yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 允许出现特殊字符和转义符
        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        objectMapper.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
        // 允许出现单引号
        objectMapper.configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true);
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        // 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 失败处理
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 未知属性忽略
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 解决jackson2无法反序列化LocalDateTime的问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 日期格式化
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();
    }

    private JsonUtils() {
    }

    /**
     * 转换为json字符串
     */
    @SneakyThrows
    public static String toStr(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * 数组转字符串
     */
    @SneakyThrows
    public static String toStr(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 转换为json 数组
     */
    @SneakyThrows
    public static byte[] toByte(Object object) {
        return objectMapper.writeValueAsBytes(object);
    }

    /**
     * 将 JSON 字符串转为 Java 对象
     */
    @SneakyThrows
    public static <T> T toObject(String json, Class<T> type) {
        return objectMapper.readValue(json, type);
    }

    /**
     * 将Json的byte数组转换成对象
     */
    @SneakyThrows
    public static <T> T toObject(byte[] bytes, Class<T> type) {
        return objectMapper.readValue(bytes, type);
    }

    /**
     * json数组转List集合
     *
     * @param json  json数组
     * @param clazz 类型类
     * @param <T>   类
     * @return 集合
     */
    @SneakyThrows
    public static <T> List<T> toArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 复杂类型数据转换
     */
    @SneakyThrows
    public static <T> T toObjectType(String content, TypeReference<T> valueTypeRef) {
        return objectMapper.readValue(content, valueTypeRef);
    }

    /**
     * 转换json字符串<br/>
     * 指定 JsonFilter 在序列化时动态过滤指定的字符
     *
     * @param object       需要序列化的对象
     * @param filterId     object对象中@JsonFilter中指定的ID
     * @param ignoreFields 需要忽略的字段
     * @return JSON字符串
     */
    @SneakyThrows
    public static String toStr(Object object, String filterId, String... ignoreFields) {
        SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept(ignoreFields);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter(filterId, theFilter);
        return objectMapper.writer(filters).writeValueAsString(object);
    }

}
