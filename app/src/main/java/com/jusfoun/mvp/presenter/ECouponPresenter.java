package com.jusfoun.mvp.presenter;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.ECouponModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.ECouponContract;
import com.jusfoun.mvp.source.ECouponSource;
import com.jusfoun.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * 我的电子券 Presenter
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ECouponPresenter implements ECouponContract.IPresenter {

    private ECouponContract.IView view;

    private ECouponSource source;

    public ECouponPresenter(ECouponContract.IView view) {
        this.view = view;
        source = new ECouponSource();
    }

    @Override
    public void loadData() {
        source.getData(new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                NetModel model = (NetModel) data;
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<ECouponModel> list = (List<ECouponModel>) AppUtils.getList(obj, "list", ECouponModel.class);
                    view.suc(list);
                } else {
//                    view.showToast(model.msg);
                    view.error();
                }
            }

            @Override
            public void onFail(String error) {
//                ToastUtils.showHttpError();
//                view.hideLoading();
                view.error();
            }
        });
    }
}
