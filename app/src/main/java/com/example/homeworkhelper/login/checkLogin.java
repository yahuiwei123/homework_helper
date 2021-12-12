package com.example.homeworkhelper.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.mainPage.MainPageActivity;

public class checkLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin",false);
        if(!isLogin){
            Intent intent = new Intent(checkLogin.this,LoginMain.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(checkLogin.this,MainPageActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
