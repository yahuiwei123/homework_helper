package com.example.homeworkhelper.history;

import android.graphics.Bitmap;

import java.net.URL;

public class RecordData {
    private String url;
    private String className;
    private String resultNum;

    public RecordData(String  url, String className, String resultNum) {
        this.url = url;
        this.className = className;
        this.resultNum = resultNum;
    }

    public String  getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getResultNum() {
        return resultNum;
    }

    public void setResultNum(String resultNum) {
        this.resultNum = resultNum;
    }
}
