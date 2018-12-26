package com.jusfoun.mvp.presenter;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.CouponManageModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.CouponManageContract;
import com.jusfoun.mvp.source.CouponManageSource;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.LogUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * 电子券管理 Presenter
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class CouponManagePresenter implements CouponManageContract.IPresenter {

    private CouponManageContract.IView view;

    private CouponManageSource source;

    public CouponManagePresenter(CouponManageContract.IView view) {
        this.view = view;
        source = new CouponManageSource();
    }

    @Override
    public void loadData() {
        source.getData(new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                NetModel model = (NetModel) data;
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<CouponManageModel> list = (List<CouponManageModel>) AppUtils.getList(obj, "list", CouponManageModel.class);
                    view.suc(list);
                } else {
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
}
