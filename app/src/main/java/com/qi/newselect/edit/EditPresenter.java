package com.qi.newselect.edit;

import com.qi.newselect.MainPresenter;
import com.qi.newselect.base.BasePresenter;

/**
 * Created by dongqi on 2016/11/29.
 */
public class EditPresenter extends BasePresenter<IEdit> {
    private final String TAG=MainPresenter.class.getSimpleName();
    protected IEdit iView;
    public EditPresenter(IEdit iView){
        this.attchView(iView);
        this.iView = getIView();
    }
}
