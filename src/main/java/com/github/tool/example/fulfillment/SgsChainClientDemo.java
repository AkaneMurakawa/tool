package com.github.tool.example.fulfillment;

import cn.hutool.http.Method;
import com.github.tool.fulfillment.sgschain.ApiTypeEnum;
import com.github.tool.fulfillment.sgschain.SgsChainClient;
import com.github.tool.fulfillment.sgschain.SgsChainProperties;

public class SgsChainClientDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        SgsChainClient client = new SgsChainClient(new SgsChainProperties());
        getWarehouse(client);
    }

    public static void getWarehouse(SgsChainClient client) {
        //language=JSON
        String data = "{\n" +
                "\"page\": \"1\",\n" +
                "\"pageSize\": \"50\"\n" +
                "}";
        String apiMethod = "cdm.api.basics.warehouse.getWarehouseService";
        client.request(Method.POST, apiMethod, ApiTypeEnum.CDM_API.getType(), data);
    }
}
