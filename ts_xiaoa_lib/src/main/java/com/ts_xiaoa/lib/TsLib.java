package com.ts_xiaoa.lib;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ts_xiaoa.lib.utils.DensityUtil;
import com.ts_xiaoa.lib.utils.GlideUtil;
import com.ts_xiaoa.lib.utils.SharePreferenceUtil;

/**
 * create by ts_xiaoA on 2020-03-28 16:28
 * email：443502578@qq.com
 * desc：
 */
public class TsLib {

    //全局context
    public static Application context;

    /**
     * 初始化方法
     *
     * @param application application
     */
    public static void init(Application application) {
        TsLib.context = application;
        TsLibConfig config = TsLibConfig.getInstance();
        //屏幕适配
        DensityUtil.initDensity(application, config.getDesignWidthDp());
        //图片加载工具
        GlideUtil.init(config.getDefaultImagePlaceHolder(), config.getDefaultImageHead(), config.getDefaultImageError());
        //SharedPreferences数据库
        SharePreferenceUtil.init(application, config.getShareDbName());
        //ARoute初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);
    }
}
