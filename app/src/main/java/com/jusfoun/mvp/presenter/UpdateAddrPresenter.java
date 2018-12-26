package com.jusfoun.mvp.presenter;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.UpdateAddrContract;
import com.jusfoun.mvp.source.UpdateAddrSource;
import com.jusfoun.utils.LogUtils;
import com.jusfoun.utils.ToastUtils;

/**
 * 修改地址 Presenter
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class UpdateAddrPresenter implements UpdateAddrContract.IPresenter {

    private UpdateAddrContract.IView view;

    private UpdateAddrSource source;

    public UpdateAddrPresenter(UpdateAddrContract.IView view) {
        this.view = view;
        source = new UpdateAddrSource();
    }

    @Override
    public void loadData(ExchangeRecordModel.AddrInfoBean bean, String id) {
        view.showLoading();
        source.getData(bean, id, new NetWorkCallBack() {
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
