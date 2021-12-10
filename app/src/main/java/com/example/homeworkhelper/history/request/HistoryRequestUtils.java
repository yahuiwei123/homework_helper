package com.example.homeworkhelper.history.request;

import com.example.homeworkhelper.history.bean.TestBean;
import com.example.homeworkhelper.utils.TransferUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoryRequestUtils {

    private Response response;
    public ArrayList<TestBean> getAllHistory(String url, String u_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("u_id", u_id);
        request.url(urlBuilder.build());
        Call call = okHttpClient.newCall(request.build());
        new Thread() {
            @Override
            public void run() {
                try {
                    response = call.execute();
                } catch (IOException e) {
                    response = null;
                    e.printStackTrace();
                }
            }
        }.start();
        TransferUtils utils = new TransferUtils(response, TestBean.class);
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
