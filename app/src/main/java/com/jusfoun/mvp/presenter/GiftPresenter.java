package com.jusfoun.mvp.presenter;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.GiftModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.GiftContract;
import com.jusfoun.mvp.source.GiftSource;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.LogUtils;

/**
 * 首页礼品列表 Presenter
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class GiftPresenter implements GiftContract.IPresenter {

    private GiftContract.IView view;

    private GiftSource source;

    public GiftPresenter(GiftContract.IView view) {
        this.view = view;
        source = new GiftSource();
    }

    @Override
    public void loadData(String type) {
        source.getData(type, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                NetModel model = (NetModel) data;
                if (model.success()) {
                    view.suc(model.fromData(GiftModel.class));
                } else {
//                    view.showToast(model.msg);
                    view.error();
                }
            }

            @Override
            public void onFail(String error) {
                LogUtils.e(error);
//                ToastUtils.showHttpError();
                view.error();
            }
        });
    }

    @Override
    public void getDataByNoAndPwd(String no, String pwd) {
        source.getDataByNoAndPwd(no, pwd, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.hideLoading();
                NetModel model = (NetModel) data;
                if (model.success()) {
                    view.suc(model.fromData(GiftModel.class));
                } else {
                    view.suc(null);
                }
            }

            @Override
            public void onFail(String error) {
                LogUtils.e(error);
                view.error();
            }
        });
    }

    @Override
    public void getDataByQrcode(String qrcode) {
        qrcode = AppUtils.parseCode(qrcode);
        source.getDataByQrcode(qrcode, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.hideLoading();
                NetModel model = (NetModel) data;
                if (model.success()) {
                    view.suc(model.fromData(GiftModel.class));
                } else {
                    view.suc(null);
                }
            }

            @Override
            public void onFail(String error) {
                LogUtils.e(error);
                view.error();
            }
        });
    }
}
