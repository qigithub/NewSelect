package com.qi.newselect.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

    private static final String TAG = "SharedPreferencesUtil";
    private static SharedPreferencesUtil mSharedPreferencesUtil;
    private static SharedPreferences mSharedPreferences;
    private Context ctx;

    private SharedPreferencesUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(context.getPackageName() + "mmm", Context.MODE_PRIVATE);
        ctx = context;
    }

    public static synchronized SharedPreferencesUtil getInstance(Context context) {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return mSharedPreferencesUtil;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public String getString(String key) {
        return this.getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        synchronized (mSharedPreferences) {
            String s = mSharedPreferences.getString(key, defaultValue);
            return s;
        }
    }

    public void putString(String key, String value) {
        synchronized (mSharedPreferences) {
            Editor edit = mSharedPreferences.edit();
            edit.putString(key, value);
            commit(edit);
        }
    }

    public synchronized Editor putStringNotCom(String key, String value) {
        synchronized (mSharedPreferences) {
            LogUtil.d(TAG, "spNotComPut key=" + key + " | value=" + value);
            Editor edit = mSharedPreferences.edit();
            edit.putString(key, value);
            return edit;
        }
    }

    public synchronized void commit(SharedPreferences.Editor e) {
        e.apply();
    }


    public int getInt(String key) {
		return mSharedPreferences.getInt(key, 0);
    }

    public synchronized void putInt(String key, int value) {
        Editor edit = mSharedPreferences.edit();
        edit.putInt(key, value);
        commit(edit);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public synchronized void putLong(String key, long value) {
        Editor edit = mSharedPreferences.edit();
        edit.putLong(key, value);
        commit(edit);
    }

    public Boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public synchronized void putBoolean(String key, boolean value) {
        Editor edit = mSharedPreferences.edit();
        edit.putBoolean(key, value);
        commit(edit);
    }



    /**
     * 通过key进行删除
     *
     * @param key
     */
    public synchronized void remove(String key) {
        Editor edit = mSharedPreferences.edit();
        edit.remove(key);
        commit(edit);
    }


    public String getUIDValues(String sp, String dbStr, String objStr){
        if (sp.equals(dbStr) && sp.equals(objStr) && dbStr.equals(objStr)){
            return sp;
        }else {
            if (sp.length()>3 && sp.endsWith("100")){
                return dbStr.equals(objStr) ? dbStr : (dbStr.length() < objStr.length() ? dbStr : objStr) ;
            }else {
                return sp;
            }
        }
    }

    public String getTokenValues(String sp, String dbStr, String objStr){
        if (sp.equals(dbStr) && sp.equals(objStr) && dbStr.equals(objStr)){
            return sp;
        }else {
            if (sp.length() < 39 || sp.equals("258")){
                return dbStr.equals(objStr) ? dbStr : (dbStr.length() < objStr.length() ? objStr : dbStr) ;
            }else {
                return sp;
            }
        }
    }

}
