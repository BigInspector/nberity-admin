package com.nberity.application.cronjobs.elkoproductsjob.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ElkoProductJobWsClient {

    private OkHttpClient client;

    private static final Long READ_TIMEOUT = 10L;

    private static final String BASE_WS_URL = "https://api.elkogroup.com/v3.0/api";

    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiNDAyMDQ5NkAxOSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IkFwaSIsImV4cCI6MTYyNzQ3MjY0MSwiaXNzIjoiaHR0cHM6Ly9lY29tLmVsa29ncm91cC5jb20iLCJhdWQiOiJodHRwczovL2Vjb20uZWxrb2dyb3VwLmNvbSJ9.IXxo2HU2ILAKMUqBh-Vjnj06szMX6cp0LnQQp6hbxTU";

    private ElkoProductJobWsClient() {
        initializeHttpClient();
    }

    public String getAllElkoProductsJsonArrayString() {
        Request allElkoProductsRequest = new Request.Builder()
                .url(BASE_WS_URL + "/Catalog/Products")
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
                .build();
        Response response2;

        try {
            response2 = client.newCall(allElkoProductsRequest).execute();
            return response2.body().string();
        } catch (IOException e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
        return null;
    }

    public String getProductInformation(Long elkoCode) {
        Request elkoProductDescription = new Request.Builder()
                .url(BASE_WS_URL + "/Catalog/Products/" + elkoCode + "/Description")
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
                .build();
        Response descriptionResponse;

        try {
            descriptionResponse = client.newCall(elkoProductDescription).execute();
            return descriptionResponse.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initializeHttpClient() {
        client = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
                .build();
    }

}
