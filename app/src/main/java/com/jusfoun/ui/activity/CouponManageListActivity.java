package com.jusfoun.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.constants.CommonConstant;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.event.RefreshEcouponManageEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.CouponManageModel;
import com.jusfoun.mvp.contract.CouponManageContract;
import com.jusfoun.mvp.presenter.CouponManagePresenter;
import com.jusfoun.ui.adapter.CouponManageAdapter;
import com.jusfoun.utils.PreferenceUtils;

import java.util.List;

/**
 * 电子券管理 页面
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class CouponManageListActivity extends BaseListActivity implements CouponManageContract.IView {

    private CouponManagePresenter presenter;

    @Override
    public BaseQuickAdapter getAdapter() {
        return new CouponManageAdapter(this);
    }

    private int type;

    @Override
    protected void initUi() {
        type = PreferenceUtils.getInt(activity, PreferenceConstant.TYPE, CommonConstant.TYPE_USER);
        if (type == CommonConstant.TYPE_MANAGER_BANK)
            setTitle("发卡管理");
        else
            setTitle("电子券管理");
        presenter = new CouponManagePresenter(this);
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
    public void suc(List<CouponManageModel> list) {
        completeLoadData(list);
    }

    @Override
    public void error() {
        completeLoadDataError();
    }

    @Override
    public void onRxEvent(RxIEvent event) {
        super.onRxEvent(event);
        if (event instanceof RefreshEcouponManageEvent) {
            refresh();
        }
    }
}
