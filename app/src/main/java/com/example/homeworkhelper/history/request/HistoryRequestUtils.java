package com.example.homeworkhelper.history.request;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.utils.TransferUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoryRequestUtils {

    private String responseResult;
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getAllHistory(String url, String u_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("u_id", u_id);
        request.url(urlBuilder.build());
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseResult = response.body().string();
                Message msg = new Message();
                msg.obj = responseResult;
                handler.sendMessage(msg);
            }
        });
//        Handler handler = new Handler(Looper.myLooper());
        return responseResult;
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
