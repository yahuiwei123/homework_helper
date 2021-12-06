package com.example.homeworkhelper.history.request;

import com.example.homeworkhelper.history.RecordData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HistoryRequestUtils {
    Response res = null;
    List keyList = new ArrayList();
    List valueList = new ArrayList();
    public Response getAllHistory(String url, String u_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("u_id", u_id);
        request.url(urlBuilder.build());
        Call call = okHttpClient.newCall(request.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                res = null;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
                res = response;
//                parseJsonWithJsonObject(res);
                System.out.println(keyList.get(0) + ":" + valueList.get(0));
            }
        });
        return res;
    }

    public static void postBaidu() {
        String url = "https://qcx12s.api.cloudendpoint.cn/getComment";
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "a_id: 1";
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
