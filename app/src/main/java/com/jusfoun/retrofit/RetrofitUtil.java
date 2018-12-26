package com.jusfoun.retrofit;


import com.jusfoun.app.MyApplication;
import com.jusfoun.giftexchange.R;
import com.jusfoun.utils.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author zhaoyapeng
 * @version create time:17/6/617:17
 * @Email zyp@jusfoun.com
 * @Description ${retrofit 工具类}
 */
public class RetrofitUtil  {

    private int TIMEOUT = 30000;
    private OkHttpClient okHttpClient;
    public Retrofit retrofit;
    public ApiService service;

    private static class SingletonHolder {
        private static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }

    public static RetrofitUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitUtil() {
        File cacheFile = new File(MyApplication.context.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//100Mb

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("Jusfoun_Http", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new CommonInterceptor())
                .addInterceptor(new HeaderWeather())
                .addInterceptor(new CacheInterceptor())

                .cache(cache)
                .build();


        retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .client(okHttpClient)
                .baseUrl(MyApplication.context.getString(R.string.host))
                .build();

        service = retrofit.create(ApiService.class);
    }
}
