package com.ts_xiaoa.mylib_demo;

import android.app.Application;

import com.ts_xiaoa.lib.TsLib;

/**
 * create by ts_xiaoA on 2020-03-28 17:42
 * email：443502578@qq.com
 * desc：
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TsLib.init(this);
    }
}
