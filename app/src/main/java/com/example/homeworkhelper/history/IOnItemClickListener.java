package com.example.homeworkhelper.history;

import android.app.Activity;
import android.content.Intent;

import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.result.ResultDisplayActivity;

public class IOnItemClickListener {

    public void onItemClick(int position, RecordData recordData, Activity activity){
        Intent intent = new Intent(activity, ResultDisplayActivity.class);
        activity.startActivity(intent);
    }

}
