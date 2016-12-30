package com.qi.newselect.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import com.qi.newselect.R;
import com.qi.newselect.base.BaseActivity;
import com.qi.newselect.utils.ToastUtil;
import com.qi.newselect.utils.ViewUtil;

/**
 * Created by dongqi on 2016/11/29.
 */
public class EditAct extends BaseActivity<IEdit,EditPresenter> implements IEdit {

    @BindView(R.id.etInput)
    EditText etInput;

    @BindView(R.id.btnSub)
    Button btnSub;

    @BindView(R.id.imgCheck)
    ImageView imgCheck;



    @Override
    protected EditPresenter createPresenter() {
        return new EditPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_pic_edit;
    }

    @Override
    protected String getTitleText() {
        return "发布";
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(getCtx(),msg);
    }

    @Override
    public void showProgress(boolean isCancel) {
        ViewUtil.createLoadingDialog(EditAct.this,"正在加载...",isCancel);
    }

    @Override
    public void hideProgress() {
        ViewUtil.cancelLoadingDialog();
    }


    @Override
    protected void onActionLeftClick(View view) {
        super.onActionLeftClick(view);
        finish();
    }

    @Override
    public String getInputText() {
        return null;
    }

    @Override
    public String getPicLocalPath() {
        return null;
    }

    @Override
    protected void onCreate_(@Nullable Bundle savedInstanceState) {
        super.onCreate_(savedInstanceState);


    }

}
