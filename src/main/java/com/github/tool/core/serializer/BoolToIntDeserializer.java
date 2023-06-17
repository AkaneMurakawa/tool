package com.github.tool.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * 反序列化
 * <p>
 * Boolean 转换为 Integer
 */
public class BoolToIntDeserializer extends JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        boolean value = jsonParser.getBooleanValue();
        if (Boolean.TRUE.equals(value)) {
            return 1;
        } else {
            return 0;
        }
    }
}
