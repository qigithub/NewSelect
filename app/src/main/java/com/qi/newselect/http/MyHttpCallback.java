package com.qi.newselect.http;

/**
 * Created by dongqi on 2016/8/10.
 */
public interface MyHttpCallback {
    void onSuccess(BaseResponse b);
    void onFailure(Throwable e);
}
