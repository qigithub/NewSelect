package com.qi.newselect;

import com.qi.newselect.base.BasePresenter;

/**
 * Created by dongqi on 2016/11/25.
 */
public class MainPresenter extends BasePresenter<IMain> {
    private final String TAG=MainPresenter.class.getSimpleName();
    protected IMain iView;
    public MainPresenter(IMain iView){
        this.attchView(iView);
        this.iView = getIView();
    }
}
