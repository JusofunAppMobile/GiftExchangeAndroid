package com.jusfoun.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.CardManageModel;
import com.jusfoun.mvp.contract.CardManageContract;
import com.jusfoun.mvp.presenter.CardManagePresenter;
import com.jusfoun.ui.adapter.CardManageAdapter;

import java.util.List;

/**
 * 发卡管理
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class CardManageActivity extends BaseListActivity implements CardManageContract.IView {

    private CardManagePresenter presenter;

    @Override
    public BaseQuickAdapter getAdapter() {
        return new CardManageAdapter(this);
    }

    @Override
    protected void initUi() {
        setTitle("发卡管理");
        presenter = new CardManagePresenter(this);
        adapter.addHeaderView(View.inflate(this, R.layout.view_empty_gray, null));
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    protected void startLoadData() {
        presenter.loadData(pageIndex, pageSize);
    }

    @Override
    public void suc(List<CardManageModel> list) {
        completeLoadData(list);
    }

    @Override
    public void error() {
        completeLoadDataError();
    }
}
