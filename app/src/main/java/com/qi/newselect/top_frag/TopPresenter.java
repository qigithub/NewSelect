package com.qi.newselect.top_frag;

import com.qi.newselect.base.BasePresenter;

/**
 * Created by dongqi on 2016/11/28.
 */
public class TopPresenter extends BasePresenter<ITopFrag> {
    private final String TAG=TopPresenter.class.getSimpleName();
    protected ITopFrag iView;
    public TopPresenter(ITopFrag iView){
        this.attchView(iView);
        this.iView = getIView();
    }
}
