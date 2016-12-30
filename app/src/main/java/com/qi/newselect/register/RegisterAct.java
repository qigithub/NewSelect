package com.qi.newselect.register;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PermissionsUtil;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import com.qi.newselect.MainActivity;
import com.qi.newselect.R;
import com.qi.newselect.base.BaseActivity;
import com.qi.newselect.utils.EventMsg;
import com.qi.newselect.utils.ImgUri2Str;
import com.qi.newselect.utils.LogUtil;
import com.qi.newselect.utils.ToastUtil;
import com.qi.newselect.utils.Utils;
import com.qi.newselect.utils.ViewUtil;

/**
 * Created by dongqi on 2016/11/25.
 */
public class RegisterAct extends BaseActivity<IRegister, RegisterPresenter> implements IRegister {

    private static final String TAG = "RegisterAct";
    public static final int REQUEST_CODE_PIC = 1;
    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPwd)
    EditText etPwd;

    @BindView(R.id.etPwd2)
    EditText etPwd2;

    @BindView(R.id.btnSub)
    Button btnSub;

    @BindView(R.id.imgHead)
    ImageView imgHead;

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    List<String> photos = null;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_register;
    }

    @Override
    protected void onCreate_(@Nullable Bundle savedInstanceState) {
        super.onCreate_(savedInstanceState);
        setSupportActionBar(mToolbar);
        if (Utils.isAndroidLOLLIPOP()) {
            mToolbar.setElevation(25);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getTitleText());
        PermissionsUtil.requestPermissions(RegisterAct.this, null, 0, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , new PermissionsUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionAllow() {
                    }
                });
        photos = new ArrayList<>(5);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.httpRegister();
            }
        });
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsUtil.requestPermissions(RegisterAct.this, null, REQUEST_CODE_PIC,
                        Manifest.permission.CAMERA, new PermissionsUtil.OnPermissionListener() {
                            @Override
                            public void onPermissionAllow() {
                                PhotoPickerIntent intent = new PhotoPickerIntent(RegisterAct.this);
                                intent.setPhotoCount(1);
                                intent.setColumn(4);
                                startActivityForResult(intent, REQUEST_CODE_PIC);
                            }
                        });

            }
        });
    }

    @Override
    public String getNameText() {
        return etUsername.getText().toString() == null ? "" : etUsername.getText().toString().trim();
    }

    @Override
    public String getPwdText() {
        return etPwd.getText().toString() == null ? "" : etPwd.getText().toString().trim();
    }

    @Override
    public String getPwdText2() {
        return etPwd2.getText().toString() == null ? "" : etPwd2.getText().toString().trim();
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(getApplicationContext(), msg);
    }

    @Override
    public void showProgress(boolean isCancel) {
        ViewUtil.createLoadingDialog(this, "加载中...", isCancel);
    }

    @Override
    public void hideProgress() {
        ViewUtil.cancelLoadingDialog();
    }

    @Override
    protected String getTitleText() {
        return "注册";
    }

    @Override
    protected void onActionLeftClick(View view) {
        super.onActionLeftClick(view);
        finish();
    }

    @Override
    public File getHeadFile() {
        return new File(photos.get(0));
    }

    @Override
    public void successRegister() {
        //{"code":"000000","msg":"注册成功",
        // "data":{"username":"0000002","user_img":"","user_alias":"b20e0079940ef5be",
        // "create_time":"2016-11-29 17:40"},"token":"4076756ef9fe0f41c4492d37bccc3d19"}
        BaseActivity.launchAct(getCtx(), MainActivity.class);
        EventBus.getDefault().post(new EventMsg.FinishLoginAct());
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if ( requestCode == REQUEST_CODE_PIC) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
//            Glide.with(getCtx()).load(photos.get(0)).into(imgHead);
//            selectedPhotos.clear();
//
//            if (photos != null) {
//                selectedPhotos.addAll(photos);
//            }
//            photoAdapter.notifyDataSetChanged();
            File destinationFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"temp_searchJ_head.jpg");
            if (destinationFile.exists()) {
                destinationFile.delete();
            }
            Uri sourceUri = Uri.fromFile(new File(photos.get(0)));
            Uri destinationUri = Uri.fromFile(destinationFile);
            LogUtil.d(TAG,"sourceUri = "+ sourceUri);
            LogUtil.d(TAG,"destinationUri = "+ destinationUri);
            UCrop.of(sourceUri, destinationUri)
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(300, 300)
                    .start(RegisterAct.this);
        }

        if (requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            LogUtil.d(TAG,"resultUri = "+ resultUri);
            Glide.with(getCtx()).load(resultUri).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.mipmap.ic_user_def).into(imgHead);
            String resultStr = ImgUri2Str.getImageAbsolutePath(getCtx(),resultUri);
            LogUtil.d(TAG,"resultStr = "+ resultStr);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
