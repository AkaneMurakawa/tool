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
        getSmCodeTwcToWarehouse(client);
        // getPhysicalInventory(client);
        // getInventory(client);
        // getInventoryLog(client);
    }

    public static void getSmCodeTwcToWarehouse(GoodCangClient client) {
        String apiMethod = "/inbound_order/get_smcode_twc_to_warehouse";
        client.request(Method.POST, apiMethod, null);
    }

    /**
     * 查询物理库存
     */
    public static void getPhysicalInventory(GoodCangClient client) {
        //language=JSON
        String data = "{\n" +
                "\"page\": \"1\",\n" +
                "\"pageSize\": \"10\",\n" +
                "\"wp_code_list\": [\"USEA-5\"],\n" +
                "\"product_sku_list\": [\n" +
                "  \"AB11345MM\"\n" +
                "]\n" +
                "}";
        String apiMethod = "/inventory/physical_warehouse_inventory";
        client.request(Method.POST, apiMethod, data);
    }

    /**
     * 查询库存
     */
    public static void getInventory(GoodCangClient client) {
        //language=JSON
        String data = "{\n" +
                "\"page\": \"1\",\n" +
                "\"pageSize\": \"10\",\n" +
                "\"warehouse_code\": \"USEA\",\n" +
                "\"product_sku_arr\": [\n" +
                "  \"AC21383MM\"\n" +
                "]\n" +
                "}";
        String apiMethod = "/inventory/get_product_inventory";
        client.request(Method.POST, apiMethod, data);
    }

    /**
     * 查询库存流水
     */
    public static void getInventoryLog(GoodCangClient client) {
        //language=JSON
        String data = "{\n" +
                "\"page\": \"1\",\n" +
                "\"pageSize\": \"200\",\n" +
                "\"warehouse_code\": \"USEA\",\n" +
                "\"create_date_from\":\"2023-01-20\",\n" +
                "\"create_date_end\":\"2023-01-30\"\n" +
                "}";
        String apiMethod = "/inventory/get_inventory_log";
        client.request(Method.POST, apiMethod, data);
    }
}
