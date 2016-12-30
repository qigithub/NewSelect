package com.qi.newselect.http;


import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;
import com.qi.newselect.httpentity.LoginReq;

/**
 * Created by dongqi on 2016/8/10.
 * 服务器地址后缀
 */
public interface ApiService {
    /*
    * 括号里的不要以/开头
    * @FormUrlEncoded
    * @Multipart
    * */

    /**
     * 注意1：必须使用{@code @POST}注解为post请求<br>
     * 注意：使用{@code @Multipart}注解方法，必须使用{@code @Part}/<br>
     * {@code @PartMap}注解其参数<br>
     * 本接口中将文本数据和文件数据分为了两个参数，是为了方便将封装<br>
     * {@link MultipartBody.Part}的代码抽取到工具类中<br>
     * 也可以合并成一个{@code @Part}参数
     * @param params 用于封装文本数据
     * @param parts 用于封装文件数据
     * @return BaseResp为服务器返回的基本Json数据的Model类
     */
//    @Multipart
//    @POST(MyConstants.UPLOAD_WORK)
//    Observable<String> requestUploadWork(@PartMap Map<String, RequestBody> params,
//                                         @Part List<MultipartBody.Part> parts);

    /**
     * 注意1：必须使用{@code @POST}注解为post请求<br>
     * 注意2：使用{@code @Body}注解参数，则不能使用{@code @Multipart}注解方法了<br>
     * 直接将所有的{@link MultipartBody.Part}合并到一个{@link MultipartBody}中
     */
//    @POST(MyConstants.UPLOAD_WORK)
//    Observable<String> requestUploadWork(@Body MultipartBody body);


    @Multipart
    @POST(MyConstants.API_REGISTER)
    Observable<String> API_REGISTER(@Part("username") String username
            , @Part("password") String password
            , @Part MultipartBody.Part file);

    @Multipart
    @POST(MyConstants.API_REGISTER)
    Observable<String> API_REGISTER(@Part("username") String username
            , @Part("password") String password);

    @POST(MyConstants.API_LOGIN)
    Observable<String> API_LOGIN(@Body LoginReq req);
}

