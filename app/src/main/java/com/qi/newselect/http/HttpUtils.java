package com.qi.newselect.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by dongqi on 2016/10/28.
 */
public class HttpUtils {
    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }


    /**
     *
     --ef4956a0-2ad4-4458-a076-9938608d5847
     Content-Disposition: form-data; name="json"
     Content-Transfer-Encoding: binary
     Content-Type: multipart/form-data; charset=utf-8
     Content-Length: 200

     {"request":{"type":"avatar"},"device":"android","e":"1477906674",
     "sign":"edfbe8c8e16cc0f144c40c3185c491eb","time":"1477906674",
     "token":"9d90e9d03887e70abb4acfde7e6a61ed885b4de7","uid":"173","ver":"1"}
     --ef4956a0-2ad4-4458-a076-9938608d5847
     Content-Disposition: form-data; name="file"; filename="2.png"
     Content-Type: application/otcet-stream
     Content-Length: 94845

     PNG

     --ef4956a0-2ad4-4458-a076-9938608d5847--
     *
     * @param ctx
     * @param file
     * @param uid
     * @param token
     * @param type
     * @param listener
     */
    public static void uploadImg (Context ctx, File file, String uid, String token, String type,
                                  HttpObser.OnRetrofitListener listener) throws FileNotFoundException {
//        if (file == null ){
//            throw new NullPointerException(" File is null");
//        }
//        if (!file.exists()){
//            throw new FileNotFoundException(" File is not exists ");
//        }
//        UploadImgReq req = new UploadImgReq();
//        req.uid = uid;
//        req.token = token;
//        req.request.type = type;
//        Gson g = new Gson(ctx);
//        RequestBody json = RequestBody.create(MultipartBody.FORM, g.toJson(req));
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream")
//                , file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        HttpObser.getInstance().getSubscription( HttpClient.getInstance
//                (NetConstantValue.getCommonUrl(), ctx).createService(ApiService.class)
//                .WORKER_UPLOAD_IMAGE(json, part), ctx, listener);
    }

}
