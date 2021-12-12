package com.example.homeworkhelper.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.homeworkhelper.R;

public class UserRead extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_userread);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfor", Context.MODE_PRIVATE);
        System.out.println( sharedPreferences.getAll().toString());
    }
}