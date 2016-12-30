package com.qi.newselect.top_frag;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import com.qi.newselect.R;
import com.qi.newselect.base.BaseFragment;
import com.qi.newselect.utils.EventMsg;

/**
 * Created by dongqi on 2016/11/28.
 */
public class TopFragment extends BaseFragment<ITopFrag,TopPresenter> implements ITopFrag{

    @BindView(R.id.mRv)
    PullLoadMoreRecyclerView mRv;

    List<TopBean> list;
    TopAdapter mAdapter;
    int PAGE=20;
    @Override
    protected TopPresenter createPresenter() {
        return new TopPresenter(this);
    }

    @Override
    protected void createView(View view) {
        list = new ArrayList<>(30);
        for (int i = 0; i < PAGE; i++) {
            list.add(new TopBean(i+"",i+"_title","img"));
        }
        mAdapter = new TopAdapter(list);
        mRv.setAdapter(mAdapter);
        mRv.setHasMore(true);
        mRv.setLinearLayout();
        mRv.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                refreshList();
                                mRv.setPullLoadMoreCompleted();
                            }
                        });
            }

            @Override
            public void onLoadMore() {
                Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                loadMoreList();
                                mRv.setPullLoadMoreCompleted();
                            }
                        });

            }
        });

        EventBus.getDefault().post(new EventMsg.TopFragmentMsg(mRv.getRecyclerView()));

    }

    private void refreshList() {
        list.clear();
        for (int i = 0; i < PAGE; i++) {
            list.add(new TopBean(i+"",i+"_title","img"));
        }
        mAdapter.notifyDataSetChanged();
    }
    private void loadMoreList() {
        int i = PAGE+1;
        list.add(new TopBean(i+"",i+"_title","img"));
        mAdapter.notifyDataSetChanged();
    }




    @Override
    protected int getLayoutId() {
        return R.layout.frag_main_top;
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showProgress(boolean isCancel) {

    }

    @Override
    public void hideProgress() {

    }

    public RecyclerView getRecycleView() {
        return mRv.getRecyclerView();
    }
}
