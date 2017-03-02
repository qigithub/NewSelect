package com.qi.newselect.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qi.newselect.R;
import com.qi.newselect.bean.MsgBean;
import com.qi.newselect.http.ApiService;
import com.qi.newselect.http.HttpClient;
import com.qi.newselect.http.HttpObser;
import com.qi.newselect.http.MyConstants;
import com.qi.newselect.httpentity.LoginReq;
import com.qi.newselect.utils.LogUtil;
import com.qi.newselect.utils.RxBus;
import com.qi.newselect.utils.ViewUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by dongqi on 2017/1/13.
 */

public class RxbusTestAct extends AppCompatActivity {
    private static final String TAG = "RxbusTestAct";
    Subscription mSubScription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_rxbus);
        final Button btnSend = ViewUtil.$(this,R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(new MsgBean("yunhen",26));
            }
        });

        mSubScription = RxBus.getInstance().toObservable(MsgBean.class)
                .subscribe(new Action1<MsgBean>() {
                    @Override
                    public void call(MsgBean msgBean) {

                        try {
                            ViewUtil.createLoadingDialog(RxbusTestAct.this,"加载",true);
                            LoginReq req = new LoginReq(getApplicationContext());
                            req.password="123456";
                            req.username="111111";
                            rx.Observable<String> obs = HttpClient.getInstance(MyConstants.HTTP_IP,getApplicationContext())
                                    .createService(ApiService.class).API_LOGIN(req);
                            HttpObser.getInstance().getSubscription(getApplicationContext(), obs, new HttpObser.OnRetrofitListener() {
                                @Override
                                public void onSuccess(Object o) {

                                }

                                @Override
                                public void onFailure(Throwable e) {

                                }

                                @Override
                                public void onErrCode(String jsonStr) {

                                }

                                @Override
                                public void onProgressHide() {
                                    ViewUtil.cancelLoadingDialog();
                                }
                            });


                            LogUtil.i(TAG,"call = "+ msgBean.name);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubScription != null &&
                !mSubScription.isUnsubscribed()) {
            mSubScription.unsubscribe();
        }
    }
}
