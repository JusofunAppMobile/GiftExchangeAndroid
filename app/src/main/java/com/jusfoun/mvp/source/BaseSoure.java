package com.jusfoun.mvp.source;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.jusfoun.app.MyApplication;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.giftexchange.BuildConfig;
import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.NetModel;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;

/**
 * M层基类
 *
 * @时间 2017/6/30
 * @作者 LiuGuangDan
 */

public class BaseSoure {

    protected RxManager getData(Observable observable, final NetWorkCallBack callBack) {
        RxManager rxManager = new RxManager();
        rxManager.getData(observable, new Observer<NetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (callBack != null)
                    callBack.onFail(e.getMessage());
            }

            @Override
            public void onNext(NetModel model) {
                if (callBack != null)
                    callBack.onSuccess(model);
            }
        });
        return rxManager;
    }

    /**
     * 获取基础的Map参数，
     *
     * @return
     */
    public static Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", BuildConfig.COMPANYID);
        String userId = PreferenceUtils.getString(MyApplication.context, PreferenceConstant.USERID);
        if (!TextUtils.isEmpty(userId)) {
            map.put(PreferenceConstant.USERID, userId);
        }
        return map;
    }

    public interface OnTestCall {
        void call();
    }

    protected void test(final OnTestCall call) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                call.call();
            }
        }).start();
    }

    protected void handler(final OnTestCall call) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                call.call();
            }
        });
    }

}
