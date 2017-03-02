package com.qi.newselect.top_frag;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qi.newselect.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dongqi on 2017/3/2.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.progress)
    public ContentLoadingProgressBar progressBar;
    public FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);

    }
}
