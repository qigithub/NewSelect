package com.qi.newselect.register;

import android.text.TextUtils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import com.qi.newselect.base.BasePresenter;
import com.qi.newselect.http.ApiService;
import com.qi.newselect.http.HttpClient;
import com.qi.newselect.http.HttpObser;
import com.qi.newselect.http.MyConstants;
import com.qi.newselect.utils.ErrorCode;
import com.qi.newselect.utils.UserUtils;
import com.qi.newselect.utils.Utils;

public class RegisterPresenter extends BasePresenter<IRegister> {
    private final String TAG = RegisterPresenter.class.getSimpleName();
    protected IRegister iView;

    public RegisterPresenter(IRegister iView) {
        this.attchView(iView);
        this.iView = getIView();
    }

    public void httpRegister() {

        if (TextUtils.isEmpty(iView.getNameText())) {
            iView.showToast("请输入账号");
            return;
        }
        if (iView.getNameText().length() < 6) {
            iView.showToast("账号需大于6位");
            return;
        }
        if (TextUtils.isEmpty(iView.getPwdText())) {
            iView.showToast("请输入密码");
            return;
        }

        if (iView.getPwdText().length() < 6) {
            iView.showToast("密码需大于6位");
            return;
        }

        if (TextUtils.isEmpty(iView.getPwdText2())) {
            iView.showToast("请输入确认密码");
            return;
        }

        if (!iView.getPwdText().equals(iView.getPwdText2())) {
            iView.showToast("2次密码输入不相同");
            return;
        }

        iView.showProgress(false);

//        RequestBody json = RequestBody.create(MultipartBody.FORM, g.toJson(req));
        if (iView.getHeadFile() == null) {
            HttpObser.getInstance().getSubscription(iView.getCtx(), HttpClient.getInstance(MyConstants.HTTP_IP, iView.getCtx())
                            .createService(ApiService.class).API_REGISTER(iView.getNameText(), Utils.md5(iView.getPwdText())),
                    new HttpObser.OnRetrofitListener() {
                        @Override
                        public void onSuccess(Object o) {
                            if (o instanceof String) {
                                UserUtils.saveLoginInfo(iView.getCtx(),(String)o);
                                iView.successRegister();
                            }
                        }

                        @Override
                        public void onFailure(Throwable e) {
                            iView.showToast("失败");
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
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg")
                    , iView.getHeadFile());
            MultipartBody.Part part = MultipartBody.Part.createFormData("user_img", iView.getHeadFile().getName(), requestBody);
            HttpObser.getInstance().getSubscription(iView.getCtx(), HttpClient.getInstance(MyConstants.HTTP_IP, iView.getCtx())
                            .createService(ApiService.class).API_REGISTER(iView.getNameText(), Utils.md5(iView.getPwdText()), part),
                    new HttpObser.OnRetrofitListener() {
                        @Override
                        public void onSuccess(Object o) {
                            if (o instanceof String) {
                                UserUtils.saveLoginInfo(iView.getCtx(),(String)o);
                                iView.successRegister();
                            }
                        }

                        @Override
                        public void onFailure(Throwable e) {
                            iView.showToast("失败");
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



    }


}