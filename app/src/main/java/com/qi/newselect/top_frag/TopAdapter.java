package com.qi.newselect.top_frag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import com.qi.newselect.R;

/**
 * Created by dongqi on 2016/11/28.
 */
public class TopAdapter extends RecyclerView.Adapter<TopFragViewHolder> {


    private List<TopBean> list;

    public TopAdapter(List<TopBean> stringList) {
        this.list = stringList;
    }

    @Override
    public void onBindViewHolder(TopFragViewHolder holder, int position) {
        TopBean bean = list.get(position);
        holder.tvTitle.setText(bean.title+"");
    }

    @Override
    public TopFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TopFragViewHolder vh = new TopFragViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topfrag,parent,false));
        return vh;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


