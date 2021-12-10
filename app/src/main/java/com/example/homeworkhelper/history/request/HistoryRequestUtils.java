package com.example.homeworkhelper.history.request;

import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.utils.TransferUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HistoryRequestUtils {

    private String responseResult;
    public ArrayList<RecordData> getAllHistory(String url, String u_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("u_id", u_id);
        request.url(urlBuilder.build());
        Call call = okHttpClient.newCall(request.build());

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    responseResult = call.execute().body().string();
                } catch (IOException e) {
                    responseResult = null;
                    e.printStackTrace();
                }
            }
        };
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TransferUtils utils = new TransferUtils(responseResult, RecordData.class);
        return utils.getResult();
    }
//
//    public static void postBaidu() {
//        String url = "https://qcx12s.api.cloudendpoint.cn/getComment";
//        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
//        String requestBody = "a_id: 1";
//        Request request = new Request.Builder()
//                .url(url)
//                .post(RequestBody.create(mediaType, requestBody))
//                .build();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.protocol() + "::::" + response.message());
//                System.out.println(response.body().toString());
//            }
//        });
//
//    }


}
