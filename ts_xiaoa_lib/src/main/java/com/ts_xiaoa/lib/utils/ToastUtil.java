package com.ts_xiaoa.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ts_xiaoa.lib.TsLib;

public class ToastUtil {

    private static Context context = TsLib.context;
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void showShort(String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, DensityUtil.dpToPx(212));
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tvToast = (TextView) layout.getChildAt(0);
            tvToast.setGravity(Gravity.CENTER);
            tvToast.setTextSize(16);
            int paddingVertical = DensityUtil.dpToPx(14);
            int paddingHorizontal = DensityUtil.dpToPx(20);
            tvToast.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
