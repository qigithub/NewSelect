package com.qi.newselect.base;

import android.content.Context;

/**
 * Created by dongqi on 2016/8/9.
 */
public interface IBaseVIew {
    Context getCtx();
    void showToast(String msg);
    void showProgress(boolean isCancel);
    void hideProgress();
}
