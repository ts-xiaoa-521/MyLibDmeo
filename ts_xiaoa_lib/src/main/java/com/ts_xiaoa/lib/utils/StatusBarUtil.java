package com.ts_xiaoa.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;

/**
 * create by ts_xiaoA on 2020-01-17 17:23
 * email：443502578@qq.com
 * desc：状态栏工具类
 */
public class StatusBarUtil {

    /**
     * ************************Window Flag相关************************
     * <p>
     * 状态栏半透明效果(内容会全屏充满)
     * WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
     * 导航栏半透明效果(内容会全屏充满，虚拟键盘遮挡布局)
     * WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
     * <p>
     * ************************Window Flag相关************************
     */

    /**
     * ************************window.getDecorView() setSystemUiVisibility相关************************
     * <p>
     * 全屏，状态栏会盖在布局上:
     * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     * <p>
     * 保持View Layout不变，隐藏状态栏或者导航栏后，View不会拉伸。
     * View.SYSTEM_UI_FLAG_LAYOUT_STABLE
     * <p>
     * ************************window.getDecorView() setSystemUiVisibility相关************************
     */

    /**
     * 全屏模式（黑色字体）
     *
     * @param activity      activity
     * @param immersionView 状态栏沉浸的View 如：顶部布局AppbarLayout
     */
    public static void lightFull(Activity activity, View immersionView) {
        fullScreen(activity);
        setLightMode(activity);
        setImmersionView(activity, immersionView);
    }


    /**
     * 全屏模式（黑色字体）
     *
     * @param activity activity
     */
    public static void lightFull(Activity activity) {
        lightFull(activity, null);
    }

    /**
     * 全屏模式（白色字体）
     *
     * @param activity      activity
     * @param immersionView 状态栏沉浸的View 如：顶部布局AppbarLayout
     */
    public static void darkFull(Activity activity, View immersionView) {
        fullScreen(activity);
        setDarkMode(activity);
        setImmersionView(activity, immersionView);
    }

    /**
     * 全屏模式（白色字体）
     *
     * @param activity activity
     */
    public static void darkFull(Activity activity) {
        darkFull(activity, null);
    }

    /**
     * 全屏
     *
     * @param activity 全屏显示的activity
     */
    public static void fullScreen(Activity activity) {
        //5.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //清除半透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //全透明效果
            View decorView = window.getDecorView();
            int visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(visibility);
            //设置状态栏颜色全透明
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4+
            //获取window对象
            Window window = activity.getWindow();
            //状态栏半透明效果(内容会全屏充满)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        AndroidBug5497Workaround.assistActivity(activity);
    }


    /**
     * 设置高亮模式（黑色字体）
     *
     * @param activity activity
     */
    public static void setLightMode(Activity activity) {
        //6.0+ 设置黑色字体
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(systemUiVisibility);
        } else {
            String manufacturer = Build.MANUFACTURER;
            if (manufacturer.equals("Meizu")) {
                setMeizuStatusBarDarkIcon(activity, true);
            } else if (manufacturer.equals("Xiaomi")) {
                setMIUIStatusBarDarkIcon(activity, true);
            } else {
                //6.0以下不支持设置黑色字体 设置半透明避免状态栏不显示
                setTranslucent(activity);
            }
        }
    }

    /**
     * 设置深色模式（白色字体）
     *
     * @param activity activity
     */
    public static void setDarkMode(Activity activity) {
        //6.0+ 设置黑色字体
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            systemUiVisibility ^= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(systemUiVisibility);
        } else {
            //6.0以下原生android不支持修改 魅族小米支持
            String manufacturer = Build.MANUFACTURER;
            if (manufacturer.equals("Meizu")) {
                setMeizuStatusBarDarkIcon(activity, false);
            } else if (manufacturer.equals("Xiaomi")) {
                setMIUIStatusBarDarkIcon(activity, false);
            }
        }

    }

    /**
     * ...加个paddingTop
     * 设置状态沉浸的View
     *
     * @param activity      activityg
     * @param immersionView 状态栏背景与该view同步(加padding的View)
     */
    public static void setImmersionView(Activity activity, View immersionView) {
        if (immersionView == null) return;
        ViewGroup contentFrameLayout = activity.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup parentView = (ViewGroup) contentFrameLayout.getChildAt(0);
        if (parentView != null) {
            if (immersionView.getLayoutParams().height != ViewGroup.LayoutParams.MATCH_PARENT
                    && immersionView.getLayoutParams().height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                immersionView.getLayoutParams().height = immersionView.getLayoutParams().height + getStatusBarHeight(activity);
            }
            immersionView.setPadding(immersionView.getPaddingLeft(),
                    getStatusBarHeight(activity) + immersionView.getPaddingTop(),
                    immersionView.getPaddingRight(),
                    immersionView.getPaddingBottom()
            );
        }
    }

    /**
     * 修改 MIUI V6  以上状态栏颜色
     */
    private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkIcon ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            //e.printStackTrace();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.BLACK);
            }
        }
    }

    /**
     * 修改魅族状态栏字体颜色 Flyme 4.0
     */
    private static void setMeizuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkIcon) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            //e.printStackTrace();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.BLACK);
            }
        }
    }

    /**
     * 设置状态栏半透明
     */
    public static void setTranslucent(Activity activity) {
        setColor(activity, Color.BLACK, 128);
    }

    /**
     * 设置状态栏颜色及透明度
     *
     * @param activity activity
     * @param color    状态颜色
     */
    public static void setColor(Activity activity, @ColorInt int color) {
        setColor(activity, color, 255);
    }

    /**
     * 设置状态栏颜色及透明度
     *
     * @param activity activity
     * @param color    状态颜色
     * @param alpha    透明度
     */
    public static void setColor(Activity activity, @ColorInt int color,
                                @IntRange(from = 0, to = 255) int alpha) {
        ViewGroup contentFrameLayout = activity.findViewById(Window.ID_ANDROID_CONTENT);
        StatusBarView view = null;
        for (int i = 0; i < contentFrameLayout.getChildCount(); i++) {
            if (contentFrameLayout.getChildAt(i) instanceof StatusBarView) {
                view = (StatusBarView) contentFrameLayout.getChildAt(i);
                break;
            }
        }
        if (view == null) {
            view = new StatusBarView(activity);
            ContentFrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            view.setLayoutParams(layoutParams);
            contentFrameLayout.addView(view);
        }
        view.setBackgroundColor(color);
        view.getBackground().setAlpha(alpha);

    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 用于设置状态栏颜色的占位View 其实就是一个View
     */
    private static class StatusBarView extends View {

        public StatusBarView(Context context) {
            super(context);
        }

        public StatusBarView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
    }

    public static class AndroidBug5497Workaround {
        // For more information, see https://code.google.com/p/android/issues/detail?id=5497
        // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

        public static void assistActivity(Activity activity) {
            new AndroidBug5497Workaround(activity);
        }

        private View mChildOfContent;
        private int usableHeightPrevious;
        private FrameLayout.LayoutParams frameLayoutParams;

        private AndroidBug5497Workaround(Activity activity) {
            FrameLayout content = activity.findViewById(android.R.id.content);
            mChildOfContent = content.getChildAt(0);
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(() -> possiblyResizeChildOfContent(activity));
            frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
        }

        private void possiblyResizeChildOfContent(Activity activity) {
            int usableHeightNow = computeUsableHeight(activity);
            if (usableHeightNow != usableHeightPrevious) {
                int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
                int heightDifference = usableHeightSansKeyboard - usableHeightNow;
                if (heightDifference > (usableHeightSansKeyboard / 4)) {
                    // keyboard probably just became visible
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                } else {
                    // keyboard probably just became hidden
                    frameLayoutParams.height = usableHeightSansKeyboard;
                }
                mChildOfContent.requestLayout();
                usableHeightPrevious = usableHeightNow;
            }
        }

        private int computeUsableHeight(Activity activity) {
            Rect r = new Rect();
            mChildOfContent.getWindowVisibleDisplayFrame(r);
            if (r.top == 0) {
                r.top = getStatusBarHeight(activity);//状态栏目的高度
            }
            if (checkDeviceHasNavigationBar(activity)) {
                r.bottom += getNavigationBarHeight(activity);
            }
            return r.bottom;
//            return (r.bottom - r.top);
        }

        /**
         * 非全面屏下 虚拟键高度(无论是否隐藏)
         *
         * @param context
         * @return
         */
        public int getNavigationBarHeight(Context context) {
            int result = 0;
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

        //获取是否存在NavigationBar
        public boolean checkDeviceHasNavigationBar(Context context) {
            boolean hasNavigationBar = false;
            Resources rs = context.getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            try {
                Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
                Method m = systemPropertiesClass.getMethod("get", String.class);
                String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
                if ("1".equals(navBarOverride)) {
                    //不存在虚拟按键
                    hasNavigationBar = false;
                } else if ("0".equals(navBarOverride)) {
                    //存在虚拟按键
                    hasNavigationBar = true;
                }
            } catch (Exception e) {
            }
            return hasNavigationBar;
        }
    }
}
