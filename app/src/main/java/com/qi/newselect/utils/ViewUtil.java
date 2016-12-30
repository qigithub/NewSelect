package com.qi.newselect.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;


public class ViewUtil {

    public static Dialog loadingDialog;

    //退出等待悬浮
    public static void cancelLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @param flag    true可以点击别处或者返回键取消 false不可取消
     * @return
     */

    public static void createLoadingDialog(@NonNull final Activity context, @NonNull String msg, boolean flag) {
        if (context == null) {
            LogUtil.i(context.getClass().getSimpleName(), " pushSS createLoadingDialog context is null ");
            return;
        }
        if (loadingDialog != null) {
            cancelLoadingDialog();
        }

        loadingDialog = new ProgressDialog(context);
        loadingDialog.setTitle(msg);
        //true可以点击别处或者返回键取消
        //false不可取消
        loadingDialog.setCancelable(flag);// 不可以用“返回键”取消
        loadingDialog.show();
        LogUtil.i("pushSS", System.currentTimeMillis() + " 推送加载 进度条 ");

    }

    public static void showToast(Context con, String content) {
        Toast.makeText(con, content, Toast.LENGTH_LONG).show();
    }


    public static <T> T $(Context context, int id) {
        return (T) ((Activity) context).findViewById(id);
    }

    public static <T> T $(View context, int id) {
        return (T) (context).findViewById(id);
    }

    public static <T> T getView(View view, int id) {
        SparseArray<View> arr = (SparseArray) view.getTag();
        if (null == arr) {
            arr = new SparseArray<View>();
            view.setTag(arr);
        }
        View childView = arr.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            arr.put(id, childView);

        }
        return (T) childView;

    }

}
