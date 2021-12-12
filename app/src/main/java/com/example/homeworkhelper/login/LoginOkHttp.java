package com.example.homeworkhelper.login;

import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.utils.TransferUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginOkHttp{
    private static String res;
    public static UserDto getLogin(String phone, String email,String devid) {
        final UserDto[] resultUser = {new UserDto()};
        String url = "http://192.168.14.1:8888/login/login";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        urlBuilder.addQueryParameter("phone", phone);
        urlBuilder.addQueryParameter("email", email);
        urlBuilder.addQueryParameter("devid", devid);
        request.url(urlBuilder.build());
        Call call = okHttpClient.newCall(request.build());

        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    res = call.execute().body().string();
                } catch (IOException e) {
                    res = null;
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
        TransferUtils transferUtils = new TransferUtils(res, UserDto.class);
        ArrayList<UserDto> result =transferUtils.getResult();
        return result.get(0);
    }

}
