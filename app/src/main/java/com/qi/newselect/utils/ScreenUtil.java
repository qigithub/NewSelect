package com.qi.newselect.utils;

import android.content.Context;
import android.util.Log;

public class ScreenUtil {
    private static final String TAG = "ScreenUtil";

    public static int getWidth(Context ctx) {
        return ctx.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight(Context ctx) {
        return ctx.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getStatuBarHeight(Context ctx) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        Log.i(TAG, "状态栏-方法1:" + statusBarHeight1);
        return statusBarHeight1;
    }
}
