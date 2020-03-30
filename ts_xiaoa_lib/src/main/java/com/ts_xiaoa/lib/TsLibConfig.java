package com.ts_xiaoa.lib;

import android.graphics.Color;

import androidx.annotation.IntDef;

/**
 * create by ts_xiaoA on 2020-03-24 10:53
 * email：443502578@qq.com
 * desc：基础库的相关配置
 */
public class TsLibConfig {

    //默认适配 UI设计的宽度(单位：dp 默认375)
    private int designWidthDp = 375;
    //默认列表分页大小
    private int pageSize = 20;
    //默认占位图
    private int defaultImagePlaceHolder = R.drawable.ts_default_img_place_holder;
    //默认加载失败时的图片
    private int defaultImageError = R.drawable.ts_default_img_error;
    //默认头像
    private int defaultImageHead = R.drawable.ts_default_head;
    //默认SharedPreferences数据库名称
    private String shareDbName = "myShare.db";

    //--------------------------------appbar配置-----------------------------------
    //app模式(dart、light)
    @AppMode
    private int appMode = AppMode.DARK;

    //默认dark模式下返回图标
    private int darkBackImage = R.drawable.ts_ic_back_white;
    //默认dark模式下字体颜色
    private int darkBackTextColor = Color.WHITE;
    //默认dark模式下appbar的背景资源
    private int darkAppbarBg = R.drawable.ts_bg_appbar_layout_dark;

    //默认light模式下返回图标
    private int lightBackImage = R.drawable.ts_ic_back_black;
    //默认light模式下字体颜色
    private int lightBackTextColor = Color.BLACK;
    //默认light模式下appbar的背景资源
    private int lightAppbarBg = R.drawable.ts_bg_appbar_layout_dark;
    //--------------------------------appbar配置-----------------------------------
    //配置单例
    private static TsLibConfig tsLibConfig;

    private TsLibConfig() {
    }

    //获取单例
    public static TsLibConfig getInstance() {
        if (tsLibConfig == null) {
            synchronized (TsLibConfig.class) {
                if (tsLibConfig == null) {
                    tsLibConfig = new TsLibConfig();
                }
            }
        }
        return tsLibConfig;
    }

    public TsLibConfig setDesignWidthDp(int designWidthDp) {
        this.designWidthDp = designWidthDp;
        return this;
    }

    public TsLibConfig setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public TsLibConfig setDefaultImagePlaceHolder(int defaultImagePlaceHolder) {
        this.defaultImagePlaceHolder = defaultImagePlaceHolder;
        return this;
    }

    public TsLibConfig setDefaultImageError(int defaultImageError) {
        this.defaultImageError = defaultImageError;
        return this;
    }

    public TsLibConfig setDefaultImageHead(int defaultImageHead) {
        this.defaultImageHead = defaultImageHead;
        return this;
    }

    public TsLibConfig setShareDbName(String shareDbName) {
        this.shareDbName = shareDbName;
        return this;
    }

    public TsLibConfig setAppMode(int appMode) {
        this.appMode = appMode;
        return this;
    }

    public TsLibConfig setDarkBackImage(int darkBackImage) {
        this.darkBackImage = darkBackImage;
        return this;
    }

    public TsLibConfig setDarkBackTextColor(int darkBackTextColor) {
        this.darkBackTextColor = darkBackTextColor;
        return this;
    }

    public TsLibConfig setDarkAppbarBg(int darkAppbarBg) {
        this.darkAppbarBg = darkAppbarBg;
        return this;
    }

    public TsLibConfig setLightBackImage(int lightBackImage) {
        this.lightBackImage = lightBackImage;
        return this;
    }

    public TsLibConfig setLightBackTextColor(int lightBackTextColor) {
        this.lightBackTextColor = lightBackTextColor;
        return this;
    }

    public TsLibConfig setLightAppbarBg(int lightAppbarBg) {
        this.lightAppbarBg = lightAppbarBg;
        return this;
    }

    public int getDesignWidthDp() {
        return designWidthDp;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getDefaultImagePlaceHolder() {
        return defaultImagePlaceHolder;
    }

    public int getDefaultImageError() {
        return defaultImageError;
    }

    public int getDefaultImageHead() {
        return defaultImageHead;
    }

    public String getShareDbName() {
        return shareDbName;
    }

    public int getAppMode() {
        return appMode;
    }

    public int getDarkBackImage() {
        return darkBackImage;
    }

    public int getDarkBackTextColor() {
        return darkBackTextColor;
    }

    public int getDarkAppbarBg() {
        return darkAppbarBg;
    }

    public int getLightBackImage() {
        return lightBackImage;
    }

    public int getLightBackTextColor() {
        return lightBackTextColor;
    }

    public int getLightAppbarBg() {
        return lightAppbarBg;
    }

    @IntDef({AppMode.DARK, AppMode.LIGHT})
    public @interface AppMode {
        int DARK = 1;
        int LIGHT = 2;
    }

}
