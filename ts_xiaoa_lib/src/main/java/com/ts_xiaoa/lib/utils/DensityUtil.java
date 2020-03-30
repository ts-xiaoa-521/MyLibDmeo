package com.ts_xiaoa.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


/**
 * 屏幕适配的工具类
 */
public class DensityUtil {

    private static int designWidthDP = 375;//设计图的dp宽度（px = dp * density）
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /**
     * 计算、初始化density相关值,以宽维度适配
     *
     * @param content
     * @param designWidthDP
     */
    public static void initDensity(Context content, int designWidthDP) {
        DensityUtil.context = content;
        DensityUtil.designWidthDP = designWidthDP;
        //设置当前应用的在当前设备的density信息
        setCustomDensity(content);
    }

    /**
     * 设置当前应用的在当前设备的density信息
     */
    private static void setCustomDensity(Context content) {
        //设置不受系统字体大小影响
        Resources resources = content.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        //获取手机显示相关信息
        final DisplayMetrics displayMetrics = content.getResources().getDisplayMetrics();
        //获取屏幕的实际宽度
        final int screenWidth = displayMetrics.widthPixels;
        //计算当前设备应该设定的density值（px = dp * density  ==>density = px / dp）
        float screenDensity = 1.0f * screenWidth / designWidthDP;
        //计算当前设备应该设定的ScaleDensity值（通过比例计算）
        float screenScaleDensity = displayMetrics.scaledDensity * (screenDensity / displayMetrics.density);
        //重新设置density值
        displayMetrics.density = screenDensity;
        displayMetrics.scaledDensity = screenScaleDensity;
        //density值变化 所以屏幕的dpi也发生了变化，重新计算dpi
        displayMetrics.densityDpi = (int) (160 * screenDensity);
    }


    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    public static int dpToPx(float dpValue) {
        if (context == null) {
            throw new IllegalStateException("please use DensityUtil.initDensity(Application content, int designWidthDP) in your content'onCreate()");
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
