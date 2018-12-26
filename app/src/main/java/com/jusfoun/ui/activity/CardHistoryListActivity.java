package com.jusfoun.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.constants.CommonConstant;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.CardHistoryModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.CardHistoryContract;
import com.jusfoun.mvp.presenter.CardHistoryPresenter;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.ui.adapter.CardHistoryAdapter;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.PreferenceUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import rx.Observer;

import static com.jusfoun.mvp.source.BaseSoure.getMap;

/**
 * 管理员发卡历史
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class CardHistoryListActivity extends BaseListActivity implements CardHistoryContract.IView {

    private CardHistoryPresenter presenter;

    @Override
    public BaseQuickAdapter getAdapter() {
        return new CardHistoryAdapter();
    }

    @Override
    protected void initUi() {
        setTitle("发卡历史");
        presenter = new CardHistoryPresenter(this);
        adapter.addHeaderView(View.inflate(this, R.layout.view_empty_gray, null));
    }

    private void loadData() {
        Map<String, Object> map = getMap();
        map.put("pageNum", pageIndex);
        map.put("pageSize", pageSize);

        RxManager rxManager = new RxManager();
        rxManager.getData(RetrofitUtil.getInstance().service.getSendAdminCardLog(map), new Observer<NetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                completeLoadDataError();
            }

            @Override
            public void onNext(NetModel model) {
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<CardHistoryModel> list = (List<CardHistoryModel>) AppUtils.getList(obj, "list", CardHistoryModel.class);
                    completeLoadData(list);
                } else {
                    completeLoadDataError();
                }
            }
        });
    }

    private int type;

    @Override
    protected void startLoadData() {
        type = PreferenceUtils.getInt(activity, PreferenceConstant.TYPE, CommonConstant.TYPE_USER);
//        if (type == CommonConstant.TYPE_MANAGER) {
            loadData();
//        }
//        else
//            presenter.loadData(pageIndex, pageSize);
    }

    @Override
    public void suc(List<CardHistoryModel> list) {
        completeLoadData(list);
    }

    @Override
    public void error() {
        completeLoadDataError();
    }
}
