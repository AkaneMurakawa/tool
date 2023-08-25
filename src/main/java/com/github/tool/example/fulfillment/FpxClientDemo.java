package com.github.tool.example.fulfillment;

import cn.hutool.http.Method;
import com.github.tool.fulfillment.fpx.FpxClient;
import com.github.tool.fulfillment.fpx.FpxProperties;

public class FpxClientDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        FpxClient client = new FpxClient(new FpxProperties());
        // getInventory(client);
        // getInventoryAge(client);
        getInventoryLog(client);
    }

    /**
     * 查询库存
     */
    public static void getInventory(FpxClient client) {
        //language=JSON
        String data = "{\n" +
                "\"page_no\": 1,\n" +
                "\"page_size\": 10,\n" +
                "\"warehouse_code\": \"USUSAE\"\n" +
                "}";
        String apiMethod = "fu.wms.inventory.get";
        client.request(Method.POST, apiMethod, data);
    }

    /**
     * 查询库龄
     */
    public static void getInventoryAge(FpxClient client) {
        //language=JSON
        String data = "{\n" +
                "\"warehouse_code\": \"USUSAE\",\n" +
                "\"lstsku\": [\n" +
                "  \"WWH001\"\n" +
                "]\n" +
                "}";
        String apiMethod = "fu.wms.inventory.getdetail";
        client.request(Method.POST, apiMethod, data);
    }

    /**
     * 查询库存流水
     */
    public static void getInventoryLog(FpxClient client) {
        //language=JSON
        String data = "{\n" +
                "\"page_no\": 1,\n" +
                "\"page_size\": 10,\n" +
                "\"create_time_start\": 1689493344000,\n" +
                "\"warehouse_code\": \"AUSYDA\"\n" +
                "}";
        String apiMethod = "fu.wms.inventory.getlog";
        client.request(Method.POST, apiMethod, data);
    }
}
