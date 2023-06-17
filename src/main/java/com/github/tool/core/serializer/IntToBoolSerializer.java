package com.github.tool.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 序列化
 * <p>
 * Integer 转换为 Boolean
 */
public class IntToBoolSerializer extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer integer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null == integer) {
            return;
        }
        if (Integer.valueOf(1).equals(integer)) {
            jsonGenerator.writeBoolean(Boolean.TRUE);
        } else if (Integer.valueOf(0).equals(integer)) {
            jsonGenerator.writeBoolean(Boolean.FALSE);
        }
    }
}
