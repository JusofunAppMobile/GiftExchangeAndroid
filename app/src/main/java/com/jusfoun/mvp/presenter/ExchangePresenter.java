package com.jusfoun.mvp.presenter;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.NetModel;
import com.jusfoun.model.param.ExchangeModel;
import com.jusfoun.mvp.contract.ExchangeContract;
import com.jusfoun.mvp.source.ExchangeSource;
import com.jusfoun.utils.LogUtils;
import com.jusfoun.utils.ToastUtils;

/**
 * 礼品兑换 Presenter
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ExchangePresenter implements ExchangeContract.IPresenter {

    private ExchangeContract.IView view;

    private ExchangeSource source;

    public ExchangePresenter(ExchangeContract.IView view) {
        this.view = view;
        source = new ExchangeSource();
    }

    @Override
    public void exchange(ExchangeModel model) {
        view.showLoading();
        source.exchange(model, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.hideLoading();
                NetModel model = (NetModel) data;
                if (model.success()) {
                    view.suc();
                } else {
                    view.showToast(model.msg);
                }
            }

            @Override
            public void onFail(String error) {
                LogUtils.e(error);
                ToastUtils.showHttpError();
                view.hideLoading();
            }
        });
    }
}
