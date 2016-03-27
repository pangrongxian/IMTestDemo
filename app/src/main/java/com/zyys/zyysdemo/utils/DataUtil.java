package com.zyys.zyysdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

public class DataUtil {
	private Context context;
	public DataUtil(Context context) {
		this.context=context;
	}

	//存值
	public boolean putData(String fileName,Map<String, Object> map){
		SharedPreferences preferences = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Boolean) {
				edit.putBoolean(key, (Boolean)value);
			}else if (value instanceof Integer) {
				edit.putInt(key, (Integer)value);
			}else if (value instanceof Float) {
				edit.putFloat(key, (Float)value);
			}else if (value instanceof Long) {
				edit.putLong(key, (Long)value);
			}else if (value instanceof String) {
				edit.putString(key, (String)value);
			}
		}
		boolean commit = edit.commit();
		return commit;
	}

	//取值
	public Map<String, ?> getData(String fileName){
		SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Map<String, ?> map = preferences.getAll();
		return map;
	}
}
