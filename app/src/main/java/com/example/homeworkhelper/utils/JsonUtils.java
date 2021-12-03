package com.example.homeworkhelper.utils;

import com.example.homeworkhelper.utils.common.Item;
import com.example.homeworkhelper.utils.common.LogRecord;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class JsonUtils {

    public static LogRecord parseJson(String jsonData) {
        ArrayList<Item> itemArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray items = (JSONArray)jsonObject.get("items"); //得到搜索结果的列表
            JSONObject baseResp = (JSONObject) jsonObject.get("BaseResp"); //得到BaseResp
            String logId = (String) baseResp.get("logId");
            if (items.length() == 0){
                return new LogRecord(null, logId);
            }
            // 处理items保存到一个Item类型的ArrayList里
            for (int i = 0;i < items.length();i++){
                JSONObject item = items.getJSONObject(i);
                String itemId = item.get("itemId").toString();
                String content = item.get("content").toString();
                String answer = item.get("answer").toString();
                String hint = item.get("hint").toString();
                String remark = item.get("remark").toString();
                int subject = Integer.parseInt(item.get("subject").toString());
                Document contentHtml = Jsoup.parse(content);
                Elements contentSrc = contentHtml.getElementsByTag("img");
                Elements answerSrc = contentHtml.getElementsByTag("img");
                Elements hintSrc = contentHtml.getElementsByTag("img");
                Elements remarkSrc = contentHtml.getElementsByTag("img");
                String contentURL = contentSrc.get(0).attr("src");
                String answerURL = answerSrc.get(0).attr("src");
                String hintURL = hintSrc.get(0).attr("src");
                String remarkURL = remarkSrc.get(0).attr("src");
                itemArrayList.add(new Item(itemId, contentURL, answerURL, hintURL, remarkURL, subject));
            }
            return new LogRecord(itemArrayList, logId);
        } catch (Exception e) {
            // 异常返回null
            return null;
        }
    }

}
