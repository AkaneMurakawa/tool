package com.github.tool.example.fulfillment;

import cn.hutool.http.Method;
import com.github.tool.fulfillment.armlogi.ArmLogiClient;
import com.github.tool.fulfillment.armlogi.ArmLogiProperties;

public class ArmLogiClientDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        ArmLogiClient client = new ArmLogiClient(new ArmLogiProperties());
        getInventory(client);
    }

    /**
     * 查询库存
     */
    public static void getInventory(ArmLogiClient client) {
        String data = "{\n" +
                "\"pageIndex\": 1,\n" +
                "\"pageSize\": 10,\n" +
                "\"skuCode\": \"B2SPU02\"\n" +
                "}";
        String apiMethod = "/api/v1/prodcut/listsSkuStock";
        client.request(Method.POST, apiMethod, data);
    }
}
