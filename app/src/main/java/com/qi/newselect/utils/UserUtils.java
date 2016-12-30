package com.qi.newselect.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dongqi on 2016/11/25.
 */
public class UserUtils {

    public static  final String TOKEN = "token";
    public static  final String USERNAME = "username";
    public static  final String USER_IMG = "user_img";
    public static  final String USER_ALIAS = "user_alias";
    public static  final String CREATE_TIME = "create_time";


    //{"code":"000000","msg":"注册成功",
    // "data":{"username":"0000002","user_img":"","user_alias":"b20e0079940ef5be",
    // "create_time":"2016-11-29 17:40"},"token":"4076756ef9fe0f41c4492d37bccc3d19"}
    public static String getToken(Context ctx) {
        return SharedPreferencesUtil.getInstance(ctx).getString(TOKEN);
    }
    public static String getUsername(Context ctx) {
        return SharedPreferencesUtil.getInstance(ctx).getString(USERNAME);
    }
    public static String getImg(Context ctx) {
        return SharedPreferencesUtil.getInstance(ctx).getString(USER_IMG);
    }
    public static String getAlias(Context ctx) {
        return SharedPreferencesUtil.getInstance(ctx).getString(USER_ALIAS);
    }
    public static String getCreate_time(Context ctx) {
        return SharedPreferencesUtil.getInstance(ctx).getString(CREATE_TIME);
    }




    public static void setToken(Context ctx, String s) {
        SharedPreferencesUtil.getInstance(ctx).putString(TOKEN,s);
    }
    public static void setUsername(Context ctx, String s) {
        SharedPreferencesUtil.getInstance(ctx).putString(USERNAME,s);
    }
    public static void setImg(Context ctx, String s) {
        SharedPreferencesUtil.getInstance(ctx).putString(USER_IMG,s);
    }
    public static void setAlias(Context ctx, String s) {
        SharedPreferencesUtil.getInstance(ctx).putString(USER_ALIAS,s);
    }
    public static void setCreate_time(Context ctx, String s) {
        SharedPreferencesUtil.getInstance(ctx).putString(CREATE_TIME,s);
    }

    public static void saveLoginInfo(Context ctx , String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject data = jsonObject.optJSONObject("data");
            setAlias(ctx,data.optString(USER_ALIAS));
            setCreate_time(ctx,data.optString(CREATE_TIME));
            setImg(ctx,data.optString(USER_IMG));
            setToken(ctx,jsonObject.optString(TOKEN));
            setUsername(ctx,data.optString(USERNAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
