package com.qi.newselect.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import com.qi.newselect.MainActivity;
import com.qi.newselect.R;
import com.qi.newselect.base.BaseActivity;
import com.qi.newselect.register.RegisterAct;
import com.qi.newselect.utils.EventMsg;
import com.qi.newselect.utils.ToastUtil;
import com.qi.newselect.utils.ViewUtil;

/**
 * Created by dongqi on 2016/11/25.
 */
public class LoginAct extends BaseActivity<ILogin,LoginPresenter> implements ILogin {

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPwd)
    EditText etPwd;

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    protected String getTitleText() {
        return "登陆";
    }

    @Override
    protected void onCreate_(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActRegister();
            }
        });

        mPresenter.setBtn1Text();
    }

    @Override
    public void setButtonText1(String text) {
        btnLogin.setText(text);
    }

    @Override
    public String getUsernameText() {
        return etUsername.getText().toString() == null ? "":etUsername.getText().toString().trim();
    }

    @Override
    public String getPwdText() {
        return etPwd.getText().toString() == null ? "": etPwd.getText().toString().trim();
    }

    @Override
    public void showProgress( final boolean isCancel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewUtil.createLoadingDialog(LoginAct.this,"加载中...",isCancel);
            }
        });

    }

    @Override
    public void hideProgress() {
        ViewUtil.cancelLoadingDialog();
    }

    @Override
    public void setUsernameErrorText(String s) {
        etUsername.setError(s);
    }

    @Override
    public void setPwdErrorText(String s) {
        etPwd.setError(s);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(getApplicationContext(),msg);
    }

    @Override
    public void goActRegister() {
        BaseActivity.launchAct(this,RegisterAct.class);
    }

    @Override
    public void goActMain() {
        BaseActivity.launchAct(this,MainActivity.class);
    }
    @Subscribe
    public void finishThis(EventMsg.FinishLoginAct f) {
        finish();
    }

}
