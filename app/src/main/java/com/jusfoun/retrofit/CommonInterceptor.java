package com.jusfoun.retrofit;

import com.jusfoun.app.MyApplication;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timeout.TimeOut;

/**
 * @author zhaoyapeng
 * @version create time:17/9/1514:49
 * @Email zyp@jusfoun.com
 * @Description ${公共参数 intercepter}
 */
public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        TimeOut timeOut = new TimeOut(MyApplication.context);

        Request request = chain.request();
        HttpUrl.Builder builder = request.url()
                .newBuilder().scheme(request.url().scheme())
                .host(request.url().host())
                .addQueryParameter("m", timeOut.getParamTimeMollis() + "")
                .addQueryParameter("t", timeOut.MD5time() + "");

        Request newRequest = request.newBuilder()
                .method(request.method(), request.body())
                .url(builder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
