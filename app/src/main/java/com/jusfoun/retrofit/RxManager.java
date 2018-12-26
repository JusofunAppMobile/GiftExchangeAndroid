package com.jusfoun.retrofit;

import com.jusfoun.model.BaseNetModel;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author zhaoyapeng
 * @version create time:17/6/617:45
 * @Email zyp@jusfoun.com
 * @Description ${rxjava 管理相关}
 */
public class RxManager {
    // 订阅管理类 持有所有订阅的引用
    private CompositeSubscription compositeSubscription;

    public RxManager() {
        compositeSubscription = new CompositeSubscription();
    }

    public void addSubToManager(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public <T extends BaseNetModel> void getData(Observable<T> observable, Observer<T> observer) {
        compositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(observer));
    }


}
