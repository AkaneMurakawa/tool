package com.github.tool.example.serializer;

import com.github.tool.core.json.JsonUtils;
import com.github.tool.example.base.dto.QueryDTO;

public class SerializerDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        QueryDTO queryDTO = new QueryDTO();
        queryDTO.setStatus(1);
        String json = JsonUtils.toStr(queryDTO);
        System.out.println("序列化：" + json);
        System.out.println("反序列化：" + JsonUtils.toObject(json, QueryDTO.class));

        queryDTO = new QueryDTO();
        json = JsonUtils.toStr(queryDTO);
        System.out.println("null 序列化：" + json);
        System.out.println("null 反序列化：" + JsonUtils.toObject(json, QueryDTO.class));
        //language=JSON
        json = "{\"status\":false,\"list\":\"a;b;c\"}";
        System.out.println("str to list 反序列化：" + JsonUtils.toObject(json, QueryDTO.class));
    }
}
