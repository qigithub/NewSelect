package com.qi.newselect.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import com.qi.newselect.R;
import com.qi.newselect.utils.ViewUtil;


/**
 * Created by dongqi on 2016/8/9.
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity
        implements IBaseVIew {
    protected T mPresenter;
    protected abstract T createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            this.getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
//                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            this.getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorActionBarBgColor));
            getWindow().setNavigationBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorActionBarBgColor));
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        TextView tvTitle = ViewUtil.$(this, R.id.tvActionBarTitle);
        if (tvTitle != null) {
            tvTitle.setText(getTitleText()== null ?"" : getTitleText());
        }
        ViewGroup vgBack = ViewUtil.$(this,R.id.actionLeft);
        if (vgBack != null) {
            vgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onActionLeftClick(v);
                }
            });
        }
        onCreate_(savedInstanceState);
    }

    protected void onCreate_(@Nullable Bundle savedInstanceState){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * actionbar左侧按钮点击事件
     * @param view
     */
    protected void onActionLeftClick(View view) {

    }
    @Override
    public Context getCtx() {
        return this;
    }

    /**
     * 设置setContentView
     * @return layout.xml
     */
    protected abstract int getLayoutId();

    protected abstract String getTitleText();

    protected View getRootView(int id) {
        return ViewUtil.$(this,id);
    }

    public static void launchAct(Context act, Class<?> targetAct) {
        launchAct(act,targetAct,null);
    }
    public static void launchAct(Context act, Class<?> targetAct, Bundle bundle) {
        launchAct(act,targetAct,bundle,null);
    }
    public static void launchAct(Context act, Class<?> targetAct, Bundle bundle, String action) {
        Intent intent = new Intent(act,targetAct);
        if (bundle!= null){
            intent.putExtras(bundle);
        }
        if (action!= null && !"".equals(action)){
            intent.setAction(action);
        }
        act.startActivity(intent);
    }

}
