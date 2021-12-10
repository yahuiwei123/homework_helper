package com.example.homeworkhelper.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Response;

public class TransferUtils {
    private Integer code;
    private Boolean success;
    private String message;
    private ArrayList result;
    /**
     * 创建该对象时直接调用如下构造器，可以直接将response
     * 解析出来，通过getResult方法可以将数据库返回的数据list拿到
     * 注意：在getResult的时候要将ArrayList里面的数据类型写清楚
     * 例如，ArrayList<TestBean> result = getResult()
     * getCode方法可以拿到请求状态码
     * getSuccess方法返回请求成功与否信息
     * getMessage方法同上
     */
    public TransferUtils(Response response, Class dataClass) {
        parseToObject(response, dataClass);
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList getResult() {
        return result;
    }

    public void parseToObject(Response response, Class objClass){
        Gson gson = new Gson();
        try{
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            code = (Integer) jsonObject.get("code");
            success = (Boolean) jsonObject.get("success");
            message = (String) jsonObject.get("message");
            JSONArray dataList = (JSONArray) jsonObject.get("data");
            for (int i = 0; i < dataList.length(); i++) {
                JSONObject data = dataList.getJSONObject(i);
                Object finalData = gson.fromJson(String.valueOf(data), objClass);
                result.add(finalData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
