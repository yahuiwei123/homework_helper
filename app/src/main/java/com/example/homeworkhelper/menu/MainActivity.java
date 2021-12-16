package com.example.homeworkhelper.menu;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.mainPage.MainPageActivity;
import com.example.homeworkhelper.userInfo.UserInfoActivity;


public class MainActivity extends TabActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabHost = getTabHost();
        addTab("main","首页",MainPageActivity.class);
        addTab("person","个人",UserInfoActivity.class);
    }

    public void addTab(String tag, String title, Class clazz){
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(title);

        Intent intent = new Intent(getApplicationContext(), clazz);
        tabSpec.setContent(intent);
        tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);

    }

    @Override
    protected void onStop(){
        super.onStop();
    }
}
