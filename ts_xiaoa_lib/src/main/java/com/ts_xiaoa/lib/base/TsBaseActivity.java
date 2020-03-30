package com.ts_xiaoa.lib.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ts_xiaoa.lib.TsLibConfig;
import com.ts_xiaoa.lib.dialog.LoadingDialog;
import com.ts_xiaoa.lib.utils.DensityUtil;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;

/**
 * create by ts_xiaoA on 2020-03-28 14:25
 * email：443502578@qq.com
 * desc：
 */
public abstract class TsBaseActivity extends AppCompatActivity {

    //该类对象的唯一标识
    protected final String TAG = String.valueOf(this);
    //该类对象this
    protected Activity activity;
    //fragmentManager
    protected FragmentManager fragmentManager;
    //dataBinding对象
    protected ViewDataBinding rootBinding;
    //加载中dialog
    private LoadingDialog loadingDialog;
    //加载框显示handler
    private Handler loadingHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ARouter注入（传参时需要）
        ARouter.getInstance().inject(this);
        //屏幕适配
        DensityUtil.initDensity(this, TsLibConfig.getInstance().getDesignWidthDp());

    }

    /**
     * 获取布局文件id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化View
     *
     * @param savedInstanceState savedInstanceState
     */
    protected void initView(Bundle savedInstanceState) {

    }

    /**
     * 初始化事件
     *
     * @param savedInstanceState savedInstanceState
     */
    protected void initEvent(Bundle savedInstanceState) {

    }

    /**
     * 初始化
     *
     * @param savedInstanceState savedInstanceState
     */
    protected void init(Bundle savedInstanceState) {

    }

    /**
     * 获取一个正在加载的pop
     *
     * @return
     */
    public void showLoadingDialog() {
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog();
        loadingDialog.show(fragmentManager);
    }


    /**
     * 指定时间间隔后 显示一个dialog
     *
     * @param delayMillis 时间间隔
     */
    @SuppressLint("HandlerLeak")
    public void showLoadingDialog(long delayMillis) {
        if (loadingHandler == null) {
            loadingHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    showLoadingDialog();
                }
            };
        }
        loadingHandler.sendEmptyMessageDelayed(0, delayMillis);
    }

    /**
     * 取消正在加载的提示
     */
    public void dismissLoading() {
        if (loadingHandler != null) {
            loadingHandler.removeCallbacksAndMessages(null);
        }
        if (loadingDialog != null) {
            loadingDialog.dismissAllowingStateLoss();
        }
    }


}
