package com.ts_xiaoa.lib.dialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;

import com.ts_xiaoa.lib.R;
import com.ts_xiaoa.lib.base.TsBaseDialogFragment;
import com.ts_xiaoa.lib.databinding.TsDialogLoadingBinding;

import androidx.annotation.Nullable;


/**
 * Created by ts_xiaoA on 2019/9/23 10:08
 * E-Mail Address：443502578@qq.com
 * Desc: 加载中的弹框
 */
public class LoadingDialog extends TsBaseDialogFragment {

    private TsDialogLoadingBinding binding;
    private ObjectAnimator rotation;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomDimDialogStyle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ts_dialog_loading;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        binding = (TsDialogLoadingBinding) rootBinding;
        rotation = ObjectAnimator.ofFloat(binding.ivLoading, "rotation", 0, 360);
        rotation.setDuration(2000);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setRepeatCount(-1);
        rotation.setRepeatMode(ValueAnimator.RESTART);
    }

    @Override
    public void onResume() {
        super.onResume();
        rotation.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        rotation.cancel();
    }

}
