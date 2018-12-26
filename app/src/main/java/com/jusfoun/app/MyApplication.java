package com.jusfoun.app;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;

/**
 * 说明
 *
 * @时间 2017/7/20
 * @作者 LiuGuangDan
 */

public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
