package com.example.homeworkhelper.history;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.homeworkhelper.utils.APIUtils;
import com.example.homeworkhelper.utils.TestOkHttp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOnItemClickListener {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onItemClick(int position, RecordData recordData){
        TestOkHttp.getBaidu();
//        String img_url = "C:/Users/wyh/Desktop/HomeworkHelper/app/src/main/res/drawable/question1.jpg";
//        InputStream in = null;
//        byte[] data = null;
//        // 读取图片字节数组
//        try {
//            in = new FileInputStream(img_url);
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        } catch (IOException e) {
//           e.printStackTrace();
//        }
//        APIUtils.test_api(data);
    }
}
