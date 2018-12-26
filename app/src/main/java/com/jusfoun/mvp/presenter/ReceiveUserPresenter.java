package com.jusfoun.mvp.presenter;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.NetModel;
import com.jusfoun.model.ReceiveUserModel;
import com.jusfoun.mvp.contract.ReceiveUserContract;
import com.jusfoun.mvp.source.ReceiveUserSource;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.LogUtils;
import com.jusfoun.utils.ToastUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * 接收用户 Presenter
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ReceiveUserPresenter implements ReceiveUserContract.IPresenter {

    private ReceiveUserContract.IView view;

    private ReceiveUserSource source;

    public ReceiveUserPresenter(ReceiveUserContract.IView view) {
        this.view = view;
        source = new ReceiveUserSource();
    }

    @Override
    public void loadData(int pageNum, int pageSize) {
        view.showLoading();
        source.getData(pageNum, pageSize, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.hideLoading();
                NetModel model = (NetModel) data;
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<ReceiveUserModel> list = (List<ReceiveUserModel>) AppUtils.getList(obj, "list", ReceiveUserModel.class);
                    view.suc(list);
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
