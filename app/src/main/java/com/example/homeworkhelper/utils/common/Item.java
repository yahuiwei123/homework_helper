package com.example.homeworkhelper.utils.common;

public class Item {
    private String itemId;
    private String contentURL;   // 搜索结果图片
    private String answerURL;   // 答案图片
    private String hintURL;  // 解析图片
    private String remarkURL;    // 点评
    private int subject;    // 题目学科

    public Item(String itemId, String contentURL, String answerURL, String hintURL, String remarkURL, int subject) {
        this.itemId = itemId;
        this.contentURL = contentURL;
        this.answerURL = answerURL;
        this.hintURL = hintURL;
        this.remarkURL = remarkURL;
        this.subject = subject;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getAnswerURL() {
        return answerURL;
    }

    public void setAnswerURL(String answerURL) {
        this.answerURL = answerURL;
    }

    public String getHintURL() {
        return hintURL;
    }

    public void setHintURL(String hintURL) {
        this.hintURL = hintURL;
    }

    public String getRemarkURL() {
        return remarkURL;
    }

    public void setRemarkURL(String remarkURL) {
        this.remarkURL = remarkURL;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }
}
