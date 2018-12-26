package com.jusfoun.mvp.presenter;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.CardManageModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.CardManageContract;
import com.jusfoun.mvp.source.CardManageSource;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.LogUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * 管理员发卡历史 Presenter
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class CardManagePresenter implements CardManageContract.IPresenter {

    private CardManageContract.IView view;

    private CardManageSource source;

    public CardManagePresenter(CardManageContract.IView view) {
        this.view = view;
        source = new CardManageSource();
    }

    @Override
    public void loadData(int pageNum, int pageSize) {
        source.getData(pageNum, pageSize, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                NetModel model = (NetModel) data;
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<CardManageModel> list = (List<CardManageModel>) AppUtils.getList(obj, "list", CardManageModel.class);
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
