package com.example.homeworkhelper.history.bean;

import android.graphics.Bitmap;

import java.net.URL;

public class RecordData {
    private String u_id;
    private String request_id;
    private String search_id;
    private int ques_class;
    private int ans_num;
    private int dev_id;
    private String search_image;
    private String items;
    private int is_marked;

    public int getIs_marked() {
        return is_marked;
    }

    public void setIs_marked(int is_marked) {
        this.is_marked = is_marked;
    }

    public int getDev_id() {
        return dev_id;
    }

    public void setDev_id(int dev_id) {
        this.dev_id = dev_id;
    }

    public String getSearch_image() {
        return search_image;
    }

    public void setSearch_image(String search_image) {
        this.search_image = search_image;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getSearch_time() {
        return search_time;
    }

    public void setSearch_time(String search_time) {
        this.search_time = search_time;
    }

    private String search_time;

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getSearch_id() {
        return search_id;
    }

    public void setSearch_id(String search_id) {
        this.search_id = search_id;
    }

    public int getQues_class() {
        return ques_class;
    }

    public void setQues_class(int ques_class) {
        this.ques_class = ques_class;
    }

    public int getAns_num() {
        return ans_num;
    }

    public void setAns_num(int ans_num) {
        this.ans_num = ans_num;
    }
}
