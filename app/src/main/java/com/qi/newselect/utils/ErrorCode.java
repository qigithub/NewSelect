package com.qi.newselect.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dongqi on 2016/11/29.
 */
public class ErrorCode {
    public static String getCode(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.optString("code") ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMsg(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.optString("msg") ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "网络不给力~";
    }

}
