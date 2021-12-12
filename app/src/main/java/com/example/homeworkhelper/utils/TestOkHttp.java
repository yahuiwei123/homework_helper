package com.example.homeworkhelper.utils;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestOkHttp {
    public static void getBaidu() {
        String url = "https://qcx12s.api.cloudendpoint.cn/getComment";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("a_id", "1");
        request.url(urlBuilder.build());
        Call call = okHttpClient.newCall(request.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }

    public static void postBaidu() {
        String url = "http://10.0.2.2:8888/helper/history/modify";
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "u_id: 1";
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.protocol() + "::::" + response.message());
                System.out.println(response.body().toString());
            }
        });

    }
}
