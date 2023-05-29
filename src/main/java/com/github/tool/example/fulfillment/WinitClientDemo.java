package com.github.tool.example.fulfillment;

import cn.hutool.http.Method;
import com.github.tool.fulfillment.winit.WinitClient;
import com.github.tool.fulfillment.winit.WinitProperties;

public class WinitClientDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        WinitClient client = new WinitClient(new WinitProperties());
        getInventory(client);
    }

    /**
     * 查询库存
     */
    public static void getInventory(WinitClient client) {
        String data = "{\n" +
                "\"pageNum\": \"1\",\n" +
                "\"pageSize\": \"10\",\n" +
                "\"warehouseID\": \"1000008\",\n" +
                "\"warehouseCode\": \"US0001\"\n" +
                "}";
        String apiMethod = "queryWarehouseStorage";
        client.request(Method.POST, apiMethod, data);
    }
}
