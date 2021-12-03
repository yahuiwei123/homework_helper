package com.example.homeworkhelper.utils;

import android.content.Context;

public class LayoutUtils {
    //  将dp转成像素
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
