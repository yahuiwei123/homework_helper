package com.example.homeworkhelper.utils;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Response;

public class TransferUtils {
    private Integer code;
    private Boolean success;
    private String message;
    private ArrayList result = new ArrayList();
    /**
     * 创建该对象时直接调用如下构造器，可以直接将response
     * 解析出来，通过getResult方法可以将数据库返回的数据list拿到
     * 注意：在getResult的时候要将ArrayList里面的数据类型写清楚
     * 例如，ArrayList<TestBean> result = getResult()
     * getCode方法可以拿到请求状态码
     * getSuccess方法返回请求成功与否信息
     * getMessage方法同上
     */
    public TransferUtils(String responseResult, Class dataClass) {
        parseToObject(responseResult, dataClass);
    }

    public TransferUtils(String responseResult, Class dataClass, ArrayList arrayList) {
        parseToObject(responseResult, dataClass, arrayList);
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

    //增加byte[]的adapter类
    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }

    public void parseToObject(String responseResult, Class objClass){
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create();
        try{
            JSONObject jsonObject = new JSONObject(responseResult);
            code = (Integer) jsonObject.get("code");
            success = (Boolean) jsonObject.get("success");
            message = (String) jsonObject.get("message");
            JSONArray dataList = (JSONArray) jsonObject.get("data");
            System.out.println(code);
            for (int i = 0; i < dataList.length(); i++) {
                JSONObject data = dataList.getJSONObject(i);
                Object finalData = gson.fromJson(String.valueOf(data), objClass);
                result.add(finalData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseToObject(String responseResult, Class objClass, ArrayList arrayList){
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create();
        try{
            JSONObject jsonObject = new JSONObject(responseResult);
            code = (Integer) jsonObject.get("code");
            success = (Boolean) jsonObject.get("success");
            message = (String) jsonObject.get("message");
            JSONArray dataList = (JSONArray) jsonObject.get("data");
            for (int i = 0; i < dataList.length(); i++) {
                JSONObject data = dataList.getJSONObject(i);
                Object finalData = gson.fromJson(String.valueOf(data), objClass);
                arrayList.add(finalData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
