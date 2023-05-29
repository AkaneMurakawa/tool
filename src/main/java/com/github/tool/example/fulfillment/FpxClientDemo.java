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
        String data = "{\n" +
                "\"warehouse_code\": \"USUSAE\",\n" +
                "\"lstsku\": [\n" +
                "  \"WWH001\"\n" +
                "]\n" +
                "}";
        String apiMethod = "fu.wms.inventory.getdetail";
        client.request(Method.POST, apiMethod, data);
    }
}
