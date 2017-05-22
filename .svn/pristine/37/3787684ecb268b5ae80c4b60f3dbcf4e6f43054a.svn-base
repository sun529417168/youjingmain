package com.youjing.yjeducation.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedUtil {

    public static SharedPreferences mPreference;

    public static SharedPreferences getPreference(Context context) {
        if (mPreference == null)
            mPreference = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreference;
    }

    public static void setInteger(Context context, String name, int value) {
        getPreference(context).edit().putInt(name, value).commit();
    }

    public static int getInteger(Context context, String name, int default_i) {
        return getPreference(context).getInt(name, default_i);
    }

    public static void setString(Context context, String name, String value) {
        getPreference(context).edit().putString(name, value).commit();
    }

    public static String getString(Context context, String name) {
        return getPreference(context).getString(name, null);
    }

    public static String getString(Context context, String name, String defalt) {
        return getPreference(context).getString(name, defalt);
    }

    /**
     * 获取boolean类型的配置
     *
     * @param context
     * @param name
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Context context, String name, boolean defaultValue) {
        return getPreference(context).getBoolean(name, defaultValue);
    }

    /**
     * 设置boolean类型的配置
     *
     * @param context
     * @param name
     * @param value
     */
    public static void setBoolean(Context context, String name, boolean value) {
        getPreference(context).edit().putBoolean(name, value).commit();
    }

    public static Long getLong(Context context, String name, long defaultValue) {
        return getPreference(context).getLong(name, defaultValue);
    }

    public static void setLong(Context context, String name, long value) {
        getPreference(context).edit().putLong(name, value).commit();
    }

    public static void setMoreService(Context context, String name, String value) {
        getPreference(context).edit().putString(name, value).commit();
    }

    public static String getMoreService(Context context, String name, String defaultValue) {
        return getPreference(context).getString(name, defaultValue);
    }

    // 通过键删除值
    public static void isValue(Context context, String name) {
        getPreference(context).edit().remove(name);
    }

    public static void saveGrade(Context context,String grade,int num){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("grade", grade);
        editor.putInt("gradeNum", num);
        editor.commit();
    }

    public static void saveProject(Context context,String subject,int num){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("subject", subject);
        editor.putInt("subjectNum",num);
        editor.commit();
    }
}
