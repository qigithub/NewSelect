package com.qi.newselect.httpentity;

import android.content.Context;

import com.qi.newselect.http.BaseRequest;

/**
 * Created by dongqi on 2016/11/29.
 */
public class LoginReq extends BaseRequest {
    public String username="";
    public String password="";
    public LoginReq(Context ctx) {
        super(ctx);
    }

}
