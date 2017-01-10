package com.qi.newselect.edit;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PermissionsUtil;
import me.iwf.photopicker.utils.PhotoPickerIntent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qi.newselect.R;
import com.qi.newselect.base.BaseActivity;
import com.qi.newselect.register.RegisterAct;
import com.qi.newselect.utils.ImgUri2Str;
import com.qi.newselect.utils.LogUtil;
import com.qi.newselect.utils.ToastUtil;
import com.qi.newselect.utils.ViewUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongqi on 2016/11/29.
 */
public class EditAct extends BaseActivity<IEdit, EditPresenter> implements IEdit {

    private static final String TAG = "EditAct";
    public static final int REQUEST_EDIT_CODE_PIC = 2;
    private List<String> photos = new ArrayList<>(9);
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
        ToastUtil.showToast(getCtx(), msg);
    }

    @Override
    public void showProgress(boolean isCancel) {
        ViewUtil.createLoadingDialog(EditAct.this, "正在加载...", isCancel);
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

    @OnClick(R.id.imgCheck)
    public void clickImg(View view) {
        PermissionsUtil.requestPermissions(EditAct.this, null, PermissionsUtil.REQUEST_CAMERA,
                Manifest.permission.CAMERA, new PermissionsUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionAllow() {
                        PhotoPickerIntent intent = new PhotoPickerIntent(EditAct.this);
                        intent.setPhotoCount(1);
                        intent.setColumn(4);
                        startActivityForResult(intent, REQUEST_EDIT_CODE_PIC);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if ( requestCode == REQUEST_EDIT_CODE_PIC) {
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
            File destinationFile = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_PICTURES),"temp_UploadImg.jpg");
            if (destinationFile.exists()) {
                destinationFile.delete();
            }
            Uri sourceUri = Uri.fromFile(new File(photos.get(0)));
            Uri destinationUri = Uri.fromFile(destinationFile);
            LogUtil.d(TAG,"sourceUri = "+ sourceUri);
            LogUtil.d(TAG,"destinationUri = "+ destinationUri);
            Glide.with(getCtx()).load(sourceUri).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.mipmap.ic_user_def).into(imgCheck);
//            UCrop.of(sourceUri, destinationUri)
//                    .withAspectRatio(1, 1)
//                    .withMaxResultSize(300, 300)
//                    .start(EditAct.this);
        }

        if (requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            LogUtil.d(TAG,"resultUri = "+ resultUri);
            Glide.with(getCtx()).load(resultUri).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.mipmap.ic_user_def).into(imgCheck);
            String resultStr = ImgUri2Str.getImageAbsolutePath(getCtx(),resultUri);
            LogUtil.d(TAG,"resultStr = "+ resultStr);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
}
