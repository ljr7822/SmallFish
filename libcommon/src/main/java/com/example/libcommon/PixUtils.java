package com.example.libcommon;

import android.util.DisplayMetrics;

/**
 * 像素工具类
 * author : Iwen大大怪
 * create : 2020/10/27 14:05
 */
public class PixUtils {
    public static int dp2px(int dpValue) {
        DisplayMetrics metrics = AppGlobals.getApplication().getResources().getDisplayMetrics();
        return (int) (metrics.density * dpValue + 0.5f);
    }

    // 获取屏幕宽度
    public static int getScreenWidth() {
        DisplayMetrics metrics = AppGlobals.getApplication().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    // 获取屏幕高度
    public static int getScreenHeight() {
        DisplayMetrics metrics = AppGlobals.getApplication().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
}
