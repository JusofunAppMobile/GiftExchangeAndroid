package com.jusfoun.mvp.presenter;

import com.jusfoun.app.MyApplication;
import com.jusfoun.constants.CommonConstant;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.ExchangeRecordContract;
import com.jusfoun.mvp.source.ExchangeRecordSource;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.LogUtils;
import com.jusfoun.utils.PreferenceUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * 礼品兑换记录列表 Presenter
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ExchangeRecordPresenter implements ExchangeRecordContract.IPresenter {

    private ExchangeRecordContract.IView view;

    private ExchangeRecordSource source;

    public ExchangeRecordPresenter(ExchangeRecordContract.IView view) {
        this.view = view;
        source = new ExchangeRecordSource();
    }

    @Override
    public void loadData() {
        source.getData(new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                NetModel model = (NetModel) data;
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<ExchangeRecordModel> list = (List<ExchangeRecordModel>) AppUtils.getList(obj, "list", ExchangeRecordModel.class);
                    view.suc(list);
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
    public void loadManageAudit(int type, String startTime, String endTime, int pageNum, int pageSize) {
        int roleType = PreferenceUtils.getInt(MyApplication.context, PreferenceConstant.TYPE, 0);
        if (roleType == CommonConstant.TYPE_MANAGER) {
            source.loadManageAudit(type, startTime, endTime, pageNum, pageSize, new NetWorkCallBack() {
                @Override
                public void onSuccess(Object data) {
                    NetModel model = (NetModel) data;
                    if (model.success()) {
                        JSONObject obj = model.getDataJSONObject();
                        List<ExchangeRecordModel> list = (List<ExchangeRecordModel>) AppUtils.getList(obj, "list", ExchangeRecordModel.class);
                        view.suc(list);
                    } else {
                        view.error();
                    }
                }

                @Override
                public void onFail(String error) {
                    LogUtils.e(error);
//                    ToastUtils.showHttpError();
                    view.error();
                }
            });
        } else if (roleType == CommonConstant.TYPE_FLOW) {
            source.flowList(type, startTime, endTime, pageNum, pageSize, new NetWorkCallBack() {
                @Override
                public void onSuccess(Object data) {
                    NetModel model = (NetModel) data;
                    if (model.success()) {
                        JSONObject obj = model.getDataJSONObject();
                        List<ExchangeRecordModel> list = (List<ExchangeRecordModel>) AppUtils.getList(obj, "list", ExchangeRecordModel.class);
                        view.suc(list);
                    } else {
                        view.error();
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
}
