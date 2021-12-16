package com.example.homeworkhelper.userInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.feedback.FeedBackActivity;
import com.example.homeworkhelper.history.HistoryDisplayActivity;
import com.example.homeworkhelper.mainPage.MainPageActivity;
import com.example.homeworkhelper.userInfo.help.HelpActivity;
import com.example.homeworkhelper.userInfo.setting.SettingActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.Map;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private UserDto userDto = null;
    private Button btnOpenHistory;
    private Button btnOpenSetting;
    private Button btnOpenHelp;


    private TextView tvName;
    private TextView tvRole;
    private TextView tvGrade;

    private TabLayout tabBtm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initButton();
//        initTab();

        initUserDtoFromSharedPreference();
        refreshText();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        TabLayout.Tab tab1 = tabBtm.getTabAt(1);
//        tab1.select();
//
//        initUserDtoFromSharedPreference();
//        refreshText();
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        TabLayout.Tab tab1 = tabBtm.getTabAt(1);
//        tab1.select();
//
//        initUserDtoFromSharedPreference();
//        refreshText();
//    }

//    private void initTab() {
//        tabBtm = findViewById(R.id.user_info_tabs);
//        TabLayout.Tab tabHome = tabBtm.getTabAt(0);
//        TabLayout.Tab tabUserInfo = tabBtm.getTabAt(1);
//        tabUserInfo.select();
//        tabBtm.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab==tabHome){
//                    startNewActivity(MainPageActivity.class);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }

    private void startNewActivity(Class<?> cl){
        startActivity(new Intent(this, cl));
    }

    private void refreshText() {
// 用当前的 userDto 刷新页面
        tvName = findViewById(R.id.text_name);
        tvName.setText(userDto.getU_name());
        tvRole = findViewById(R.id.text_role);
        tvGrade = findViewById(R.id.text_grade);

        tvRole.setVisibility(View.GONE);
        if(userDto.getGrade() != null){
            tvRole.setVisibility(View.VISIBLE);
            tvRole.setText(userDto.getRole_name());
            tvGrade.setText(userDto.getGrade());
        }else{
            tvGrade.setText("请设置身份信息");
        }
    }

    private void initButton() {
        View userInfoLayout = findViewById(R.id.layout_user_info);
        userInfoLayout.setOnClickListener(this);

        btnOpenHistory = findViewById(R.id.btn_open_history);
        btnOpenSetting = findViewById(R.id.btn_open_setting);
        btnOpenHelp = findViewById(R.id.btn_open_help);

        Button btnOpenFeedback = findViewById(R.id.btn_open_feedback);
        btnOpenFeedback.setOnClickListener(this);

        btnOpenHistory.setOnClickListener(this);
        btnOpenHelp.setOnClickListener(this);
        btnOpenSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_history:
                startActivity(new Intent(this, HistoryDisplayActivity.class));
                break;
            case R.id.btn_open_setting:
            case R.id.layout_user_info:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.btn_open_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.btn_open_feedback:
                startActivity(new Intent(this, FeedBackActivity.class));
        }
    }

    private void initUserDtoFromSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfor", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isLogin", false)) {
            Map userJson = sharedPreferences.getAll();
            Gson gson = new Gson();
            String nJson = gson.toJson(userJson);
            userDto = gson.fromJson(nJson, UserDto.class);
        } else {
            userDto = new UserDto();
            writeUserDto(userDto);
        }
    }


    private void writeUserDto(UserDto userDto) {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("u_name", userDto.getU_name());
        editor.putString("u_avatar", userDto.getU_avatar());
        editor.putString("gender", userDto.getGender());
        editor.putString("role_name", userDto.getRole_name());
        editor.putString("phone", userDto.getPhone());
        editor.putString("grade", userDto.getGrade());
        editor.putInt("u_id", userDto.getU_id());
        editor.putInt("role_id", userDto.getRole_id());
        editor.commit();
    }
}