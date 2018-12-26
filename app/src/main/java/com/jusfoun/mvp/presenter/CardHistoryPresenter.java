package com.jusfoun.mvp.presenter;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.CardHistoryModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.CardHistoryContract;
import com.jusfoun.mvp.source.CardHistorySource;
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

public class CardHistoryPresenter implements CardHistoryContract.IPresenter {

    private CardHistoryContract.IView view;

    private CardHistorySource source;

    public CardHistoryPresenter(CardHistoryContract.IView view) {
        this.view = view;
        source = new CardHistorySource();
    }

    @Override
    public void loadData(int pageNum, int pageSize) {
        source.getData(pageNum, pageSize, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                NetModel model = (NetModel) data;
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<CardHistoryModel> list = (List<CardHistoryModel>) AppUtils.getList(obj, "list", CardHistoryModel.class);
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
