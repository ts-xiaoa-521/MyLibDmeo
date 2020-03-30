package com.ts_xiaoa.mylib_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ts_xiaoa.lib.base.TsBaseActivity;

/**
 * 主界面
 */
public class MainActivity extends TsBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showLoadingDialog();
    }
}
