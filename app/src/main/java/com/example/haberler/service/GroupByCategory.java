package com.example.haberler.service;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GroupByCategory {
    public static final String API_KEY = "51e16c7a788b46759eafcea70e60b9c9";
    public static final String SERVICE_URL = "https://api.hurriyet.com.tr/v1/";
    public static final String AgendaUrl = "?$filter=Path eq '/gundem/'";
    public static final String WorldUrl = "?$filter=Path eq '/dunya/'";
    public static final String SportUrl = "?$filter=Path eq '/spor/'";
    public static final String TechnologyUrl = "?$filter=Path eq '/teknoloji/'";
    public static final String MagazineUrl = "?$filter=Path eq '/kelebek/'";
    public static final String EconomyUrl = "?$filter=Path eq '/ekonomi/'";


    public void getAgendaNews(Callback callback) {
        Request request = new Request.Builder()
                .header("apikey", API_KEY)
                .url(SERVICE_URL + "articles" + AgendaUrl)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);


    }

    public void getWorldNews(Callback callback) {
        Request request = new Request.Builder()
                .header("apikey", API_KEY)
                .url(SERVICE_URL + "articles" + WorldUrl)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);


    }

    public void getSportNews(Callback callback) {
        Request request = new Request.Builder()
                .header("apikey", API_KEY)
                .url(SERVICE_URL + "articles" + SportUrl)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);


    }

    public void getTechnologyNews(Callback callback) {
        Request request = new Request.Builder()
                .header("apikey", API_KEY)
                .url(SERVICE_URL + "articles" + TechnologyUrl)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);


    }

    public void getEconomyNews(Callback callback) {
        Request request = new Request.Builder()
                .header("apikey", API_KEY)
                .url(SERVICE_URL + "articles" + EconomyUrl)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);


    }

    public void getMagazineNews(Callback callback) {
        Request request = new Request.Builder()
                .header("apikey", API_KEY)
                .url(SERVICE_URL + "articles" + MagazineUrl)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);


    }
}