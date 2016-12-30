package me.iwf.photopicker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;


/**
 * Created by dongqi on 2016/11/29.
 */
public class PermissionsUtil {

    private static final String TAG = "PermissionsUtil";

    // Storage Permissions
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_LOCATION = 2;
    public static final int REQUEST_CAMERA = 3;
    public static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA
    };

    public interface OnPermissionListener {
        /**
         * 有权限
         */
        void onPermissionAllow();
    }



    /**
     *
     * @param activity 必填
     * @param fragment 填啦就算fragment  否则都是act
     * @param respCode
     * @param permissName 权限名
     * @param listener
     */
    public static void requestPermissions(
            @NonNull final Activity activity, final Fragment fragment
            , final int respCode, final String permissName, OnPermissionListener listener){

        if (!isAndroidM()){
            //判断系统版本,小于6.0
            if (listener!=null){
                listener.onPermissionAllow();
            }
        }else {
            //是否是fragment
            boolean isFrag = isFragment(activity,fragment);
            boolean isShowReqRat = false;

            int permissionState = ContextCompat.checkSelfPermission(activity, permissName);
            if (permissionState != PackageManager.PERMISSION_GRANTED) {
                if (isFrag){
                    fragment.requestPermissions(new String[]{permissName},
                            respCode);
                }else {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{permissName},
                            respCode);
                }
                // Should we show an explanation?
                if (isFrag){
                    isShowReqRat = fragment.shouldShowRequestPermissionRationale(
                            permissName);
                }else {
                    isShowReqRat = ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            permissName);
                }
                if (isShowReqRat) {
                    showAlert(activity,permissName,respCode);
                } else {
                    if (isFrag){
                        fragment.requestPermissions(new String[]{permissName},
                                respCode);
                    }else {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{permissName},
                                respCode);
                    }
                }
            } else {
                // Permission granted 允许
                if (listener != null){
                        listener.onPermissionAllow();
                }
            }
        }

    }

    public static String permissisionText(String text) {
        if (text.isEmpty())
            return "";
        if (text.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return "读取储存权限";
        } else if (text.equals(Manifest.permission.CAMERA)) {
            return "是否获得相机权限";
        }
        return "";
    }

    private static void showAlert(final Activity context , final String PERMISSION, final int code){
        new AlertDialog.Builder(context).setMessage(permissisionText(PERMISSION))//设置显示的内容

                .setPositiveButton("是",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                        ActivityCompat.requestPermissions(context,
                                new String[]{PERMISSION},
                                code);
                    }
                }).setNegativeButton("否",null).show();//在按键响应事件中显示此对话框
    }


    public static boolean isAndroidM() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    private static boolean isFragment(Activity activity, Fragment fragment){
        if (fragment!= null){
            return true;
        }
        if (fragment == null && activity!= null){
            return false;
        }
        return false;
    }


}
