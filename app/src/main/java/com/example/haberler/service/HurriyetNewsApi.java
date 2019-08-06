package com.example.haberler.service;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HurriyetNewsApi {

    public static final String API_KEY = "51e16c7a788b46759eafcea70e60b9c9";
    public static final String SERVICE_URL = "https://api.hurriyet.com.tr/v1/";

    public void getNewsListByCategory(CategoryType categoryType, Callback callback) {
        String url = SERVICE_URL + "articles";
        if (categoryType != CategoryType.ALL) url += "?$filter=Path eq " + categoryType.getUrl();

        Request request = new Request.Builder()
                .header("apikey", API_KEY)
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

    public void getNewsDetail(long id, Callback callback) {
        Request request = new Request.Builder()
                .header("apikey", API_KEY)
                .url(SERVICE_URL + "articles/" + id)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

}
