package com.example.homeworkhelper.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import okhttp3.Response;

public class DataJsonUtils {
    public ArrayList parseJsonWithJsonObject(Response response, Object obj){
        try{
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            String code = (String) jsonObject.get("code");
            String success = (String) jsonObject.get("success");
            String message = (String) jsonObject.get("message");
            ArrayList result = new ArrayList();
            JSONArray dataList = (JSONArray) jsonObject.get("data");
            for (int i = 0; i < dataList.length(); i++) {
                JSONObject data = dataList.getJSONObject(i);
                JSONArray names = data.names();
                for (int j = 0; j < names.length(); j++) {
                    Object o = obj.getClass().newInstance();
                    Field declaredField = obj.getClass().getDeclaredField(names.getString(j));
//                    declaredField.set(data.get((String) names.get(j)));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}
