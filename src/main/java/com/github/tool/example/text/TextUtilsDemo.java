package com.github.tool.example.text;

import com.github.tool.core.text.TextUtils;

public class TextUtilsDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        //language=JSON
        String content = "{\n" +
                "  \"consignmentNo\": \"OC202301010001\",\n" +
                "  \"skus\": [\n" +
                "    {\n" +
                "      \"skuCode\": \"SKU-001\",\n" +
                "      \"batchNo\": \"B001\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"skuCode\": \"SKU-002\",\n" +
                "      \"batchNo\": \"B001\"\n" +
                "    }\n" +
                "  ]\n" +
                "}" +
                "\n";
        String value;
        value = TextUtils.findFirstKeyValue(content, "consignmentNo");
        System.out.println("value: " + value);
        value = TextUtils.findFirstKeyValue(content, "skuCode");
        System.out.println("value: " + value);
    }

}
