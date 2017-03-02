package me.iwf.photopicker.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by dongqi on 2017/1/10.
 */

public class FileUtils {



    // 获取指定文件夹内所有文件大小的和
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    // 格式化单位
    public static String formatSize(long size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // 按目录删除文件夹文件方法
    private boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (File file1 : files) {
                    deleteFolderFile(file1.getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    if (file.listFiles().length == 0) {
                        file.delete();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新17/02/08
    private static String APP_DIR_NAME = "Mrwater_Shelves";
    private static String FILE_DIR_NAME = "files";
    private static String mRootDir;
    private static String mAppRootDir;
    private static String mFileDir;

    public static void init() {
        mRootDir = getRootPath();
        if (mRootDir != null && !"".equals(mRootDir)) {
            mAppRootDir = mRootDir + "/" + APP_DIR_NAME;
            mFileDir = mAppRootDir + "/" + FILE_DIR_NAME;
            File appDir = new File(mAppRootDir);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            File fileDir = new File(mAppRootDir + "/" + FILE_DIR_NAME);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

        } else {
            mRootDir = "";
            mAppRootDir = "";
            mFileDir = "";
        }
    }

    public static String getFileDir(){
        return mFileDir;
    }


    public static String getRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath(); // filePath:  /sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath:  /data/data/
        }
    }

    /**
     * 打开文件
     * 兼容7.0
     *
     * @param context     activity
     * @param file        File
     * @param contentType 文件类型如：文本（text/html）
     *                    当手机中没有一个app可以打开file时会抛ActivityNotFoundException
     */
    public static void startActionFile(Context context, File file, String contentType)
            throws ActivityNotFoundException {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(getUriForFile(context, file), contentType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity    Activity
     * @param file        File
     * @param requestCode result requestCode
     */
    public static void startActionCapture(Activity activity, File file, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file));
        activity.startActivityForResult(intent, requestCode);
    }



    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //和manifests里的 android:authorities 的值相同
            String authority = context.getApplicationInfo().packageName + ".fileprovider";
            uri = FileProvider.getUriForFile(context.getApplicationContext()
                    , authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
