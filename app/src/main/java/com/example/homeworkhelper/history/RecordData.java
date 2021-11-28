package com.example.homeworkhelper.history;

import android.graphics.Bitmap;

public class RecordData {
    private Bitmap bitmap;
    private String className;
    private String resultNum;

    public RecordData(Bitmap bitmap, String className, String resultNum) {
        this.bitmap = bitmap;
        this.className = className;
        this.resultNum = resultNum;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
