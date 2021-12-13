package com.example.homeworkhelper.login;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.history.HistoryDisplayActivity;
import com.example.homeworkhelper.mainPage.MainPageActivity;
import com.example.homeworkhelper.utils.TestOkHttp;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
/*
    本地缓存文件名userInfor:
        1、userDto:以json串形式存储的Sys_user表中的所有信息，String（json）类型
        2、isLogin:当前用户是否登录，boolean类型
        3、isReadPracy:当前用户是否阅读过隐私文档，boolean类型
        4、deviceId:设备唯一标识,String类型
* */
public class LoginMain extends AppCompatActivity {
    public static String phonStr;
    public static String pinStr;
    private static String deviceId;
    EventHandler handler;
    private static boolean isReadPrivacy=false;
    private static boolean isLogin=false;
    public final static int REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        setSpan();

        View login = findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTemp();
            }
        });

        View getPin = findViewById(R.id.getpin);
        getPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPin();
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        //缓存deviceId
        SharedPreferences sharedPreferences = getSharedPreferences("userInfor",Context.MODE_PRIVATE);
        getDeviceId();
        sharedPreferences.edit().putString("deviceId",deviceId).commit();

        MobSDK.submitPolicyGrantResult(true,null);//已经获取了使用的权限

        //注册短信验证handler
        MobSDK.init(this, "m34e0c41fb7ec0", "813f7c14bd0b27c54befac3f280555a5");
        handler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginMain.this,"验证成功", Toast.LENGTH_SHORT).show();
                                //获取用户信息
                                LoginOkHttp.setContext(LoginMain.this);
                                UserDto user = LoginOkHttp.getLogin(phonStr,null,deviceId);
                                System.out.println(user.toString());
                                //登录成功后，缓存本地数据
                                SharedPreferences sharedPreferences = getSharedPreferences("userInfor",Context.MODE_PRIVATE);

                                //将User数据以json形式存储
                                Gson gson = new Gson();
                                String userJson =gson.toJson(user);
                                sharedPreferences.edit().putString("userDto",userJson).commit();

                                //将标识符改为已经登录
                                isLogin=true;
                                sharedPreferences.edit().putBoolean("isLogin",isLogin).commit();


                                //跳转到下一页面
                                Intent intent = new Intent(LoginMain.this, MainPageActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginMain.this,"验证码已发送", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginMain.this,des, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(handler);
    }

    //获取本机手机号
//    private void setNumber(String num) {
//        String number = num;
//        TextView phoneNumber = findViewById(R.id.PhoneNumber);
//        phoneNumber.setText(number);
//    }

    private void setSpan(){//设计用户协议，隐私政策的
        TextView spanView = findViewById(R.id.spanView);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                Intent intent = new Intent(LoginMain.this,UserRead.class);
                startActivity(intent);
            };
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(Color.BLUE);
                //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //缓存用户已经阅读隐私政策
                isReadPrivacy=true;
                SharedPreferences sharedPreferences = getSharedPreferences("userInfor",Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("isReadPracy",isReadPrivacy).commit();

                Intent intent = new Intent(LoginMain.this,PrivacyAgreement.class);
                startActivity(intent);
            };
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文本的颜色
                ds.setColor(Color.BLUE);
                //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
                ds.setUnderlineText(false);
            }
        };
        SpannableString spannable = new SpannableString("已阅读并同意《用户协议》和《隐私政策》");
        spanView.setMovementMethod(LinkMovementMethod.getInstance());
        spannable.setSpan(clickableSpan1,6,12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(clickableSpan2,13,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanView.setText(spannable);
    }
    private void getDeviceId() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE);//检查是否有权限
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {//如果没有权限
            System.out.println("9999");
            //有权限: PackageManager.PERMISSION_GRANTED
            //无权限: PackageManager.PERMISSION_DENIED
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            //请求授权，弹出请求框
        } else {
//            TelephonyManager mTelephonyMgr;
//            mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//            number = mTelephonyMgr.getLine1Number().toString();
//            Toast.makeText(LoginMain.this,number,Toast.LENGTH_LONG).show();
            TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
            String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            deviceId = deviceUuid.toString();
            System.out.println(deviceId);
        }
    }
    private void getPin(){
        //确保看过隐私协议
        SharedPreferences sharedPreferences = getSharedPreferences("userInfor",Context.MODE_PRIVATE);
        isReadPrivacy=sharedPreferences.getBoolean("isReadPracy",false);

        if(!isReadPrivacy){
           Toast.makeText(LoginMain.this,"请确定阅读隐私政策后再进行操作（点击下方隐私政策）",Toast.LENGTH_LONG).show();
        }else {
            TextView phoneText = findViewById(R.id.phoneText);
            phonStr = phoneText.getText().toString();
            SMSSDK.getVerificationCode("86",phonStr);
        }
    }

    private void loginTemp() {
        TextView pinText = findViewById(R.id.pinText);
        pinStr = pinText.getText().toString();
        SMSSDK.submitVerificationCode("86",phonStr,pinStr);
//      Toast.makeText(LoginMain.this, pinStr, Toast.LENGTH_LONG).show();
    }
    private void back(){//点击叉号方法
        View back = findViewById(R.id.back);
    }
}
