package com.example.homeworkhelper.utils.common;

import java.util.ArrayList;

public class LogRecord {
    private ArrayList<Item> itemArrayList = new ArrayList<>();
    private String logId;

    public LogRecord(ArrayList<Item> itemArrayList, String logId) {
        this.itemArrayList = itemArrayList;
        this.logId = logId;
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void setItemArrayList(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}
