package com.qi.newselect.top_frag;

import android.support.v7.widget.LinearLayoutManager;
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
import com.yalantis.phoenix.PullToRefreshView;

/**
 * Created by dongqi on 2016/11/28.
 */
public class TopFragment extends BaseFragment<ITopFrag,TopPresenter> implements ITopFrag{

//    @BindView(R.id.mRv)
//    PullLoadMoreRecyclerView mRv;

    @BindView(R.id.mRv)
    RecyclerView mRv;

    @BindView(R.id.pull_to_refresh)
    PullToRefreshView pull_to_refresh;

    List<TopBean> list;
    TopAdapter mAdapter;
    int PAGE=20;
    boolean isExistMore = true;
    boolean isLoading = false;
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
        mRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRv.setLayoutManager(manager);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pull_to_refresh.setRefreshing(false);
                    }
                },2000);
            }
        });
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                if (totalItemCount < 10)
                    return;
                //获取最后一个不完全可见的item的position
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isLoading ) {
                    isLoading = true;
                    if (list != null && list.size() > 0) {
                        list.add(null);

                        // notifyItemInserted(int position)，这个方法是在第position位置
                        // 被插入了一条数据的时候可以使用这个方法刷新，
                        // 注意这个方法调用后会有插入的动画，这个动画可以使用默认的，也可以自己定义。
                        mAdapter.notifyItemInserted(list.size() - 1);
                    }

                    mRv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (list.size() == 0) {
//                                list.addAll(list);
                                int oldSize = list.size();
                                list.add(new TopBean("0","more 1","aaaa"));
                                mAdapter.notifyItemRangeChanged(oldSize,list.size());
                            } else {
                                int oldSize = list.size()-1;
                                //删除 footer
                                list.remove(list.size() - 1);
//                                list.addAll(list);
                                list.add(new TopBean("0","more 1","aaaa"));
                                list.add(new TopBean("0","more 2","aaaa"));
                                mAdapter.notifyItemRangeChanged(oldSize,list.size());
                                isLoading = false;
                            }
                        }
                    },2000);
                }
            }
        });
//        mRv.setHasMore(true);
//        mRv.setLinearLayout();
//        mRv.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<Long>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onNext(Long aLong) {
//                                refreshList();
//                                mRv.setPullLoadMoreCompleted();
//                            }
//                        });
//            }
//
//            @Override
//            public void onLoadMore() {
//                Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<Long>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onNext(Long aLong) {
//                                loadMoreList();
//                                mRv.setPullLoadMoreCompleted();
//                            }
//                        });
//
//            }
//        });

//        EventBus.getDefault().post(new EventMsg.TopFragmentMsg(mRv.getRecyclerView()));
        EventBus.getDefault().post(new EventMsg.TopFragmentMsg(mRv));

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
        return mRv;
    }
}
