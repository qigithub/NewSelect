package com.qi.newselect.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by dongqi on 2016/11/28.
 */
public class EventMsg {

    public static class TopFragmentMsg {
        private RecyclerView recyclerView;
        public TopFragmentMsg(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        public void setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }
    }

    public static class FinishLoginAct {

    }


}
