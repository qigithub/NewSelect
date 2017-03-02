package com.qi.newselect.http;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import com.qi.newselect.utils.LogUtil;

/**
 * Created by dongqi on 2016/10/28.
 */
public class HttpObser<T> {
    private static final String TAG = "HttpObser";
    private static HttpObser httpObser;

    public static HttpObser getInstance() {
        if (httpObser == null) {
            synchronized (HttpObser.class) {
                if (httpObser == null) {
                    httpObser = new HttpObser();
                }
            }
        }
        return httpObser;
    }

    public HttpObser() {
    }

    public Subscription getSubscription(final Context appCtx, final Observable<T> o
            , final OnRetrofitListener listener) {
        return o
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //无网络:HTTP 504 Unsatisfiable Request (only-if-cached)
                        LogUtil.i(TAG, "Throwable = " + e.getMessage());
                        if (listener != null) {
                            listener.onProgressHide();
                        }
                        Toast.makeText(appCtx, "网络不给力", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(T t) {
                        if (listener != null) {
                            listener.onProgressHide();
                        }
                        if (t == null) {
                            listener.onFailure(new Throwable(o.getClass().getName() + " | http resp is null"));
                            return;
                        }
                        if (t instanceof String) {
                            String result = (String) t;
                            if (result == null || "".equals(result)) {
                                if (listener != null)
                                    listener.onFailure(new Throwable(o.getClass().getName()
                                            + " | resp str is null & empty "));
                                return;
                            }
                            try {
                                JSONObject j = new JSONObject(result);
                                String ret = j.optString("code");
                                if (ret.equals("000000")) {
                                    if (listener != null) {
                                        listener.onSuccess(t);
                                    }
                                } else {
                                    if (listener != null) {
                                        listener.onErrCode(result);
                                    }
                                }
                            } catch (JSONException e) {
                                Toast.makeText(appCtx, "接口错误", Toast.LENGTH_SHORT).show();
                                Throwable th = new Throwable(o.getClass().getName()
                                        + " | JSONException ");
                                if (listener != null) {
                                    listener.onFailure(th);
                                }

                                e.printStackTrace();
                            }

                        } else {
                            Throwable th = new Throwable(o.getClass().getName()
                                    + " | type not string ");
                            if (listener != null)
                                listener.onFailure(th);
                        }

                    }
                });
    }

    /**
     * 上传图片时使用
     * @param appCtx
     * @param o
     * @param listener
     * @return
     */
    public Subscription getSubscriptUp( final Observable<T> o,final Context appCtx
            , final OnRetrofitListener listener) {
        return o.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //无网络:HTTP 504 Unsatisfiable Request (only-if-cached)
                        LogUtil.i(TAG, "Throwable = " + e.getMessage());
                        Toast.makeText(appCtx, "网络不给力", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onProgressHide();
                        }
                        if (listener != null) {
                            listener.onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(T t) {
                        if (listener != null) {
                            listener.onProgressHide();
                        }
                        if (t == null) {
                            listener.onFailure(new Throwable(o.getClass().getName() + " | http resp is null"));
                            return;
                        }
                        if (t instanceof String) {
                            String result = (String) t;
                            try {
                                JSONObject j = new JSONObject(result);
                                String ret = j.optString("code");
                                if (ret.equals("000000")) {
                                    if (listener != null) {
                                        listener.onSuccess(t);
                                    }
                                } else {
                                    if (listener != null) {
                                        listener.onErrCode(result);
                                    }
                                }
                            } catch (JSONException e) {
                                Toast.makeText(appCtx, "接口错误", Toast.LENGTH_SHORT).show();
                                Throwable th = new Throwable(o.getClass().getName()
                                        + " | JSONException ");
                                if (listener != null) {
                                    listener.onFailure(th);
                                }
                                e.printStackTrace();
                            }

                        } else {

                            Throwable th = new Throwable(o.getClass().getName()
                                    + " | JSONException ");
                            if (listener != null) {
                                listener.onFailure(th);
                            }
                        }

                    }
                });
    }

    public interface OnRetrofitListener<T> {
        void onSuccess(T t);

        void onFailure(Throwable e);

        void onErrCode(String jsonStr);

        void onProgressHide();
    }


    public static String getException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionMessage = "";
        exceptionMessage = sw.toString();

        return exceptionMessage;
    }
}


