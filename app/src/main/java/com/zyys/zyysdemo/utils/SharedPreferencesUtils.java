package com.zyys.zyysdemo.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zyys.zyysdemo.bean.Msg;

public class SharedPreferencesUtils {

    public SharedPreferencesUtils() {

    }
    /**
     * 保存共享参数到文件
     * @param context
     * @param params
     * @param fileName
     * @return
     */
    public static boolean saveSharedPreferences(Context context, Map<String, String> params, String fileName) {
        boolean flag = false;
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Integer) {
                    Integer i = (Integer) value;
                    editor.putInt(key, i);
                }
                if (value instanceof Float) {
                    Float f = (Float) value;
                    editor.putFloat(key, f);
                }
                if (value instanceof Boolean) {
                    Boolean b = (Boolean) value;
                    editor.putBoolean(key, b);
                }
                if (value instanceof Long) {
                    Long l = (Long) value;
                    editor.putLong(key, l);
                }
                if (value instanceof String) {
                    String str = (String) value;
                    editor.putString(key, str);
                }
            }
        }
        flag = editor.commit();
        return flag;
    }

    /**
     * 得到所有的共享参数
     * @param context
     * @param fileName
     * @return
     */
    public static Map<String, String> getAllSharedPreferences(Context context, String fileName) {
        Map<String,String> map = null;
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        map = (Map<String, String>) preferences.getAll();
        return map;
    }
}