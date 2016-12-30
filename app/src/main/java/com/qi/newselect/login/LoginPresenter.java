package com.qi.newselect.login;

import android.text.TextUtils;

import java.util.Calendar;

import com.qi.newselect.base.BasePresenter;
import com.qi.newselect.http.ApiService;
import com.qi.newselect.http.HttpClient;
import com.qi.newselect.http.HttpObser;
import com.qi.newselect.http.MyConstants;
import com.qi.newselect.httpentity.LoginReq;
import com.qi.newselect.utils.ErrorCode;
import com.qi.newselect.utils.UserUtils;
import com.qi.newselect.utils.Utils;

public class LoginPresenter extends BasePresenter<ILogin> {
    private final String TAG=LoginPresenter.class.getSimpleName();
    protected ILogin iView;
    public LoginPresenter(ILogin iView){
        this.attchView(iView);
        this.iView = getIView();
    }


    public void startLogin() {
        if (TextUtils.isEmpty(iView.getUsernameText())) {
            iView.setUsernameErrorText("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(iView.getPwdText())) {
            iView.setPwdErrorText("请输入密码");
            return;
        }
        iView.showProgress(false);
        LoginReq req = new LoginReq(iView.getCtx());
        req.username = iView.getUsernameText();
        req.password = Utils.md5(iView.getPwdText());
        HttpObser.getInstance().getSubscription(iView.getCtx()
                , HttpClient.getInstance(MyConstants.HTTP_IP, iView.getCtx())
                        .createService(ApiService.class).API_LOGIN(req), new HttpObser.OnRetrofitListener() {
                    @Override
                    public void onSuccess(Object o) {
                        if (o instanceof String) {
                            UserUtils.saveLoginInfo(iView.getCtx(),(String)o);
                            iView.goActMain();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                    }

                    @Override
                    public void onErrCode(String jsonStr) {
                        iView.showToast(ErrorCode.getMsg(jsonStr));
                    }

                    @Override
                    public void onProgressHide() {
                        iView.hideProgress();
                    }
                });

    }


    public void setBtn1Text() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,1);
        calendar.roll(Calendar.DATE,-1);
        iView.setButtonText1(calendar.get(Calendar.DATE)+"/" + calendar.get(Calendar.MONTH));
    }
}