package com.example.homeworkhelper.history;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.result.ResultDisplayActivity;

public class IOnItemClickListener {

    public void onItemClick(int position, RecordData recordData, Activity activity){
        Intent intent = new Intent(activity, ResultDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("APIresult", recordData.getItems());
        bundle.putString("Bitmap", recordData.getSearch_image());
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
