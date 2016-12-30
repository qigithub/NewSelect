package com.qi.newselect;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import me.iwf.photopicker.utils.PermissionsUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import com.qi.newselect.base.BaseActivity;
import com.qi.newselect.login.LoginAct;
import com.qi.newselect.utils.UserUtils;

/**
 * Created by dongqi on 2016/11/25.
 */
public class LauncherAct extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionsUtil.requestPermissions(LauncherAct.this, null, 0, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , new PermissionsUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionAllow() {
                        goMain();
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //第一次打开app时 permissions 是空的
        try {
            if (requestCode == 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                goMain();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void goMain() {
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                if (UserUtils.getToken(getApplicationContext()) == null || "".equals(UserUtils.getToken(getApplicationContext()))) {
                    BaseActivity.launchAct(LauncherAct.this, LoginAct.class);
                } else {
                    BaseActivity.launchAct(LauncherAct.this, MainActivity.class);
                }
                finish();
            }
        });
    }
}
