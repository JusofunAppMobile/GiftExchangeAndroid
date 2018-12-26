package com.jusfoun.ui.activity;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jusfoun.event.FinishExchangeEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ECouponModel;
import com.jusfoun.mvp.contract.ECouponContract;
import com.jusfoun.mvp.presenter.ECouponPresenter;
import com.jusfoun.ui.adapter.ECouponAdapter;

import java.util.List;

import static com.jusfoun.constants.CommonConstant.GIFT_TYPE;
import static com.jusfoun.constants.CommonConstant.GIFT_TYPE_ECOUPE;

/**
 * 我的电子券 页面
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class ECouponListActivity extends BaseListActivity implements ECouponContract.IView {

    private ECouponPresenter presenter;

    @Override
    public BaseQuickAdapter getAdapter() {
        return adapter = new ECouponAdapter();
    }

    @Override
    protected void initUi() {
        setTitle("我的电子券");
        presenter = new ECouponPresenter(this);
        adapter.addHeaderView(View.inflate(this, R.layout.view_empty_gray, null));
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
    public void suc(List<ECouponModel> list) {
        completeLoadData(list);
    }

    @Override
    public void error() {
        completeLoadDataError();
    }

    @Override
    public void onRxEvent(RxIEvent event) {
        super.onRxEvent(event);
        if (event instanceof FinishExchangeEvent)
            finish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        ECouponModel model = (ECouponModel) adapter.getItem(position);
        if (model.status == 0) {
            Intent intent = new Intent(this, SelectGiftActivity.class);
            intent.putExtra("bean", new Gson().toJson(model));
            intent.putExtra(GIFT_TYPE, GIFT_TYPE_ECOUPE);
            startActivity(intent);
        }
    }
}
