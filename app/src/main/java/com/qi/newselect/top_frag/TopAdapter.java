package com.qi.newselect.top_frag;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import com.qi.newselect.R;

/**
 * Created by dongqi on 2016/11/28.
 */
public class TopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_NORMAL=0;
    public static final int TYPE_FOOTER=1;

    private List<TopBean> list;

    public TopAdapter(List<TopBean> stringList) {
        this.list = stringList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //这时候 article是 null，先把 footer 处理了
        if (holder instanceof FooterViewHolder) {
            return;
        }
        TopFragViewHolder vh = ((TopFragViewHolder) holder);
        TopBean bean = list.get(position);
        vh.tvTitle.setText(bean.title+"");
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) == null) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        switch (viewType) {
            default:
            case TYPE_NORMAL:
                vh = new TopFragViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_topfrag,parent,false));
                break;
            case TYPE_FOOTER:
                vh = new FooterViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.footer_rv,parent,false));
                break;
        }
        return vh;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


