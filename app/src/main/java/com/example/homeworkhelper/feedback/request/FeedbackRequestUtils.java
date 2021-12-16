package com.example.homeworkhelper.feedback.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import com.example.homeworkhelper.utils.common.Config;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FeedbackRequestUtils {
    public static void postFeedback(Context context, String feedbackText, String emailText, ArrayList<LocalMedia> selectList) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (!selectList.isEmpty()) {
            for (int i = 0; i < selectList.size(); i++) {
                File file = new File(selectList.get(i).getPath());
                if (file == null) break;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    bm.compress(Bitmap.CompressFormat.JPEG, 90, new FileOutputStream(file));
                    bm.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                builder.addFormDataPart("f_image", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }
        }
        builder.addFormDataPart("f_infor", feedbackText);
        builder.addFormDataPart("u_email", emailText);
        builder.addFormDataPart("u_id", "1");
        builder.addFormDataPart("f_id", "2");
        MultipartBody body = builder.build();
        String url = Config.HttpUrlHead + "/helper/feedback/upload";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("text", "failure upload!" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("text", "success upload!");
                String json = response.body().string();
                Log.i("success........", "成功" + json);
            }
        });
    }
}
