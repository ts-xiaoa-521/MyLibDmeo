package com.ts_xiaoa.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * SharedPreferences数据库操作工具类
 */
public class SharePreferenceUtil {

    private static SharedPreferences sharedPreferences;

    /**
     * 初始化SharedPreferences数据库
     */
    public static void init(Context context, String dbName) {
        if (sharedPreferences == null) {
            //SharedPreferences数据库的名称
            sharedPreferences = context.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        }
    }

    public static void put(String key, String values) {
        sharedPreferences.edit().putString(key, values).apply();
    }

    public static void put(String key, double values) {
        sharedPreferences.edit().putFloat(key, (float) values).apply();
    }

    public static void put(String key, float values) {
        sharedPreferences.edit().putFloat(key, values).apply();
    }


    public static void put(String key, int values) {
        sharedPreferences.edit().putInt(key, values).apply();
    }

    public static void put(String key, boolean values) {
        sharedPreferences.edit().putBoolean(key, values).apply();
    }

    public static void put(String key, long values) {
        sharedPreferences.edit().putLong(key, values).apply();
    }

    public static void put(String key, Set<String> values) {
        sharedPreferences.edit().putStringSet(key, values).apply();
    }


    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static String getString(String key, String def) {
        return sharedPreferences.getString(key, def);
    }

    public static int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public static boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultVal) {
        return sharedPreferences.getBoolean(key, defaultVal);
    }

    public static float getFloat(String key, float def) {
        return sharedPreferences.getFloat(key, def);
    }

    public static float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0f);
    }

    public static long getLong(String key) {
        return sharedPreferences.getLong(key, 0L);
    }

    public static void clear() {
        sharedPreferences.edit().clear().apply();
    }

}
