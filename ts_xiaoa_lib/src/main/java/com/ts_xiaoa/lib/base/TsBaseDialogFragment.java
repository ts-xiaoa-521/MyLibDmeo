package com.ts_xiaoa.lib.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ts_xiaoa.lib.R;
import com.ts_xiaoa.lib.dialog.LoadingDialog;

import java.lang.reflect.Field;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * create by ts_xiaoA on 2020-01-19 15:02
 * email：443502578@qq.com
 * desc：DialogFragment 基类
 */
public abstract class TsBaseDialogFragment extends DialogFragment {

    //该类对象的唯一标识
    protected final String TAG = String.valueOf(this);
    //dataBinding对象
    protected ViewDataBinding rootBinding;
    //该类对象this
    protected Context context;
    //fragmentManager
    protected FragmentManager fragmentManager;
    //加载中dialog
    private LoadingDialog loadingDialog;
    //加载框显示handler
    private Handler loadingHandler;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        fragmentManager = getChildFragmentManager();
        initView(savedInstanceState);
        return rootBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentManager = getChildFragmentManager();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setBackgroundDrawableResource(R.color.transparent);
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        initEvent(savedInstanceState);
        init(savedInstanceState);
    }


    /**
     * 设置布局文件
     *
     * @return
     */
    @LayoutRes
    public abstract int getLayoutId();


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

    /**
     * 显示dialog
     *
     * @param manager FragmentManager
     */
    public void show(FragmentManager manager) {
        try {
            Field mDismissed = DialogFragment.class.getDeclaredField("mDismissed");
            Field mShownByMe = DialogFragment.class.getDeclaredField("mShownByMe");
            mDismissed.setAccessible(true);
            mDismissed.set(this, false);
            mShownByMe.setAccessible(true);
            mShownByMe.set(this, true);
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            if (!isAdded()) {
                fragmentTransaction.add(this, TAG);
            } else {
                fragmentTransaction.show(this);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
