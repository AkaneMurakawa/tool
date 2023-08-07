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
        // getWarehouse(client);
        getProduct(client);
        getInventory(client);
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

    public static void getProduct(SgsChainClient client) {
        //language=JSON
        String data = "{\n" +
                "\"page\": \"1\",\n" +
                "\"pageSize\": \"50\",\n" +
                "\"status\": \"02\"\n" +
                "}";
        String apiMethod = "cdm.api.basics.product.getCdmProductService";
        client.request(Method.POST, apiMethod, ApiTypeEnum.CDM_API.getType(), data);
    }

    public static void getInventory(SgsChainClient client) {
        //language=JSON
        String data = "{\"productNos\":[\"OT-LMLZBY-1578-US-1\"]}";
        String apiMethod = "oms.api.warehouse.storage.getInventory";
        client.request(Method.POST, apiMethod, ApiTypeEnum.OMS_API.getType(), data);
    }
}
