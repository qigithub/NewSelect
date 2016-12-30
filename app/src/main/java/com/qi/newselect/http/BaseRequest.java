package com.qi.newselect.http;

import android.content.Context;

import com.qi.newselect.utils.UserUtils;


/**
 * Created by dongqi on 2016/8/10.
 */
public class BaseRequest  {


    /**
     * uid : 173
     * token : b7fda1864d890db15ccfd763fd455f5ef0964923
     * ver : 1
     * time : 1477628049
     * sign : 00f79e29f405c0df7813aa23076d5e24
     * device : android
     * request : {"build":40000,"ver":"2.0.3","type":"2"}
     */
    String e = Long.valueOf(System.currentTimeMillis() / 1000L).toString();
    public String token="";

    public BaseRequest(Context ctx) {
        token= UserUtils.getToken(ctx);
    }

}
