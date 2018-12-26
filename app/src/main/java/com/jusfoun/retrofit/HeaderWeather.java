package com.jusfoun.retrofit;

import com.jusfoun.app.MyApplication;
import com.jusfoun.utils.AppUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timeout.OffsetSharePrerence;

/**
 * @author zhaoyapeng
 * @version create time:17/6/617:27
 * @Email zyp@jusfoun.com
 * @Description ${拦截器 公共请求头}
 */
public class HeaderWeather implements Interceptor {

    private String Version="Version";
    private String VersionCode="VersionCode";
    private String AppType="AppType";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request().newBuilder()
                /**********添加头文件**********/
                .addHeader(Version, AppUtils.getVersionName(MyApplication.context))
                .addHeader(VersionCode,AppUtils.getVersionCode(MyApplication.context)+"")
                .addHeader(AppType,"0")
                .build();

        Response response=chain.proceed(request);

        if(response!=null&&response.headers()!=null){
            OffsetSharePrerence.getComputeOffsetTime(MyApplication.context, response.headers().get("Date"));
        }
        return response;
    }
}
