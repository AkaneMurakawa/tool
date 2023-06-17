package com.github.tool.example.base.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.tool.core.serializer.BoolToIntDeserializer;
import com.github.tool.core.serializer.IntToBoolSerializer;
import com.github.tool.core.serializer.StrToListDeserializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class QueryDTO implements Serializable {

    @JsonSerialize(using = IntToBoolSerializer.class)
    @JsonDeserialize(using = BoolToIntDeserializer.class)
    private Integer status;

    @JsonDeserialize(using = StrToListDeserializer.class)
    private List<String> list;
}
