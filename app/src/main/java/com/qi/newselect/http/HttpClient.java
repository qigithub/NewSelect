package com.qi.newselect.http;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by dongqi on 2016/8/10.
 */
public class HttpClient {
    private static final String TAG = "HttpClient";
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private static HttpClient httpClient;
    private String baseUrl;
    private Context context;
    /**
     * url没更换的话用这个
     *
     * @param baseUrl
     * @return
     */
    public static HttpClient getInstance(String baseUrl, Context context) {
        if (baseUrl == null || "".equals(baseUrl)) {
            Log.e(TAG, " getInstance:  baseurl is null");
        }
        if (httpClient == null) {
//            synchronized (HttpClient.class) {
//                if (httpClient == null) {
            httpClient = new HttpClient(baseUrl, context);
//                }
//            }
        }
        return httpClient;
    }

    /**
     * url有更换的话用这个
     *
     * @param baseUrl
     * @param context
     * @return
     */
    public static HttpClient getNewInstance(String baseUrl, Context context) {
        return new HttpClient(baseUrl, context);
    }

    public HttpClient(String baseUrl, final Context context) {
        this.baseUrl = baseUrl;
        this.context = context;
        try {
            initOkHttp();
            initRetrofit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOkHttp() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!HttpUtils.isNetworkReachable(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                return chain.proceed(request);

            }
        };

        Cache cache = new Cache(context.getCacheDir(), 10 * 1024 * 1024);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10_000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(mInterceptor)
                .addNetworkInterceptor(getNetWorkInterceptor(context))
                .cache(cache)
                .build();
    }
    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * @param cls 使用了@POST / @GET 的类
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> cls) {
        if (retrofit == null) {
            if (okHttpClient == null) {
                initOkHttp();
            }
            initRetrofit();
        }
        return retrofit.create(cls);
    }

    /**
     * 设置连接器  设置缓存
     */
    private Interceptor getNetWorkInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                if (HttpUtils.isNetworkReachable(context)) {
                    int maxAge = 0 * 60;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
    }


}
