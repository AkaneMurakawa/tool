package com.github.tool.text;

import org.apache.commons.lang3.StringUtils;

/**
 * TextUtils
 */
public class TextUtils {

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

    /**
     * 查找文本中第一个对应key的value
     *
     * @param content 文本内容
     * @param key     文本key
     */
    public static String findFirstKeyValue(String content, String key) {
        try {
            content = content.replaceAll(" ", "");
            int len = key.length();
            int index = content.indexOf(key);
            // 1. 找不到对应的key，返回空
            if (index == 0) {
                return "";
            }
            int rightIndex = index + len;
            // 2. XML形式的key
            if (content.charAt(index - 1) == '<' && content.charAt(rightIndex) == '>') {
                String rightStr = "</" + key + ">";
                return content.substring(rightIndex + 1, content.indexOf(rightStr, rightIndex));
            }
            // 3. json格式以冒号形式key
            int colonIndex = content.indexOf(":", index + len);
            String subStr = content.substring(index + len, colonIndex);
            if (content.charAt(colonIndex + 1) == '[') {
                String ids = content.substring(colonIndex + 2, content.indexOf(']', colonIndex));
                ids = ids.replace(subStr, ",");
                String[] codeArr = ids.split(",");
                StringBuffer sb = new StringBuffer();
                for (String code : codeArr) {
                    if (StringUtils.isNotBlank(code)) {
                        sb.append(code + ",");
                    }
                }
                if (sb.toString().endsWith(",")) {
                    return sb.toString().substring(0, sb.toString().length() - 1);
                }
                return sb.toString();
            }
            int lastIndex = content.indexOf(subStr, colonIndex + subStr.length() + 1);
            return content.substring(colonIndex + subStr.length() + 1, lastIndex);
        } catch (Exception e) {
            return "";
        }
    }
}
