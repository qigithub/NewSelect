package com.qi.newselect.top_frag;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qi.newselect.R;
import com.qi.newselect.utils.ViewUtil;

/**
 * Created by dongqi on 2016/11/28.
 */
public class TopFragViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    ImageView img;
    public TopFragViewHolder(View view) {
        super(view);
        tvTitle = ViewUtil.$(view,R.id.tvTitle);
        img = ViewUtil.$(view,R.id.img);
    }


}
