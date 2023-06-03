package com.github.tool.example.fulfillment;

import cn.hutool.http.Method;
import com.github.tool.fulfillment.goodcang.GoodCangClient;
import com.github.tool.fulfillment.goodcang.GoodCangProperties;

public class GoodCangClientDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        GoodCangClient client = new GoodCangClient(new GoodCangProperties());
        getInventory(client);
    }

    /**
     * 查询库存
     */
    public static void getInventory(GoodCangClient client) {
        //language=JSON
        String data = "{\n" +
                "\"page\": \"1\",\n" +
                "\"page_size\": \"200\",\n" +
                "\"warehouse_code\": \"UK\",\n" +
                "\"product_sku_list\": [\n" +
                "  \"TEST180717\"\n" +
                "]\n" +
                "}";
        String apiMethod = "/inventory/inventory_age_list";
        client.request(Method.POST, apiMethod, data);
    }
}
