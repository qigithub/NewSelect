package com.qi.newselect.utils;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dongqi on 2016/11/29.
 */
public class PicChooseDialog extends DialogFragment {
    private static DialogFragment dialogFragment;


    public static DialogFragment newPicChooseDialog() {
        dialogFragment = new DialogFragment();
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


}
