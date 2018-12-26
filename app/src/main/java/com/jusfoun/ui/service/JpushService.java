package com.jusfoun.ui.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jusfoun.utils.LogUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class JpushService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtils.e("------------->onReceive");

        Bundle bundle = intent.getExtras();
        // 极光推送注册
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            showData(bundle);
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtils.e("极光推送设备号：" + regId);
//            Toast.makeText(context, "极光推送设备号：" + regId , Toast.LENGTH_LONG).show();
//            PreferenceUtils.setString(context, PreferenceConstants.REGISTRATIONID, regId);
        }
        // 接收到推送下来的自定义消息
        else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            showData(bundle);
        }

        // 接收到推送下来的通知
        else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            showData(bundle);
        }
        // 用户点击打开了通知
        else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            showData(bundle);
            String extra = bundle.getString("cn.jpush.android.EXTRA");
            LogUtils.e("extra=" + extra);
        }

        // 用户收到到RICH PUSH CALLBACK
        // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            showData(bundle);
        }
        // connected state change to
        else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            showData(bundle);
        }
        // 未处理的通知
        else {
            showData(bundle);
        }
    }

    private void showData(Bundle bundle) {
        LogUtils.e("------------->showData");
        for (String key : bundle.keySet())
            LogUtils.e(key + " = " + bundle.get(key).toString());
    }
}
