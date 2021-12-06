package com.example.homeworkhelper.utils.common;

public class Item {
    private String itemId;
    private String content;   // 搜索结果图片
    private String answer;   // 答案图片
    private String hint;  // 解析图片
    private String remark;    // 点评
    private int subject;    // 题目学科

    public Item(String itemId, String content, String answer, String hint, String remark, int subject) {
        this.itemId = itemId;
        this.content = content;
        this.answer = answer;
        this.hint = hint;
        this.remark = remark;
        this.subject = subject;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String gethint() {
        return hint;
    }

    public void sethint(String hint) {
        this.hint = hint;
    }

    public String getremark() {
        return remark;
    }

    public void setremark(String remark) {
        this.remark = remark;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }
}
