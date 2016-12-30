package com.qi.newselect.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


/**
 * Created by dongqi on 2016/11/28.
 */
public class RecycleViewUtils {
    public static final void configStaggerLayoutManager(Context ctx, RecyclerView rv) {
        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(gaggeredGridLayoutManager);
    }

    public static final void configGridLayoutManager(Context ctx, RecyclerView rv) {
        final GridLayoutManager mgm = new GridLayoutManager(ctx, 2);
        rv.setLayoutManager(mgm);
    }

    public static final void configLinearLayoutManager(Context ctx, RecyclerView rv) {
        //第三参数 是否反向加载
        final LinearLayoutManager mgm = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(mgm);
    }
}
