package com.jusfoun.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.event.FinishExchangeEvent;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.mvp.contract.ExchangeRecordContract;
import com.jusfoun.mvp.presenter.ExchangeRecordPresenter;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.ui.adapter.ExchangeRecordAdapter;

import java.util.List;

/**
 * 礼品兑换记录页面
 *
 * @时间 2017/8/10
 * @作者 LiuGuangDan
 */

public class ExchangeRecordListActivity extends BaseListActivity implements ExchangeRecordContract.IView {

    private ExchangeRecordPresenter presenter;

    @Override
    public BaseQuickAdapter getAdapter() {
        return adapter = new ExchangeRecordAdapter();
    }

    @Override
    protected void initUi() {
        setTitle("兑换记录");
        presenter = new ExchangeRecordPresenter(this);
        RxBus.getDefault().post(new FinishExchangeEvent());
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    protected void startLoadData() {
        presenter.loadData();
    }

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void suc(List<ExchangeRecordModel> list) {
        completeLoadData(list);
    }

    @Override
    public void error() {
        completeLoadDataError();
    }
}
