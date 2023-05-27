package com.github.tool.example.fulfillment;

import cn.hutool.http.Method;
import com.github.tool.fulfillment.winit.WinitClient;

public class WinitClientDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        WinitClient client = new WinitClient();
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
