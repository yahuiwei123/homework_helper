package com.example.homeworkhelper.history.bean;

import android.graphics.Bitmap;

import java.net.URL;

public class RecordData {
    private String u_id;
    private String request_id;
    private String search_id;
//    private String search_image;
    private String ques_class;
    private String ans_num;

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
//    public String getSearch_image() {
//        return search_image;
//    }
//
//    public void setSearch_image(String search_image) {
//        this.search_image = search_image;
//    }

    public String getQues_class() {
        return ques_class;
    }

    public void setQues_class(String ques_class) {
        this.ques_class = ques_class;
    }

    public String getAns_num() {
        return ans_num;
    }

    public void setAns_num(String ans_num) {
        this.ans_num = ans_num;
    }
}
