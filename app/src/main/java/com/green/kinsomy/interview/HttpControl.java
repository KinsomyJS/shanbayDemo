package com.green.kinsomy.interview;


import com.green.kinsomy.interview.network.interceptor.CommonLoggerInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kinsomy on 2017/10/20
 * http config 类
 */

public class HttpControl {

    private volatile static HttpControl mInstance;
    private static Retrofit retrofit;
    private static OkHttpClient client;

    private HttpControl() {

        client = new OkHttpClient.Builder()
                .addInterceptor(new CommonLoggerInterceptor("interview",true))
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl((Constants.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static HttpControl getInstance() {
        if (mInstance == null) {
            synchronized (HttpControl.class) {
                if (mInstance == null)
                    mInstance = new HttpControl();
            }
        }
        return mInstance;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            client = HttpControl.getInstance().initClient();
        }
        return client;
    }

    private OkHttpClient initClient() {
        return client;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = HttpControl.getInstance().initRetrofit();
        }
        return retrofit;
    }

    private Retrofit initRetrofit() {
        return retrofit;
    }
}
