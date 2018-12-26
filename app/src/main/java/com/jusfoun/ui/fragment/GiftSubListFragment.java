package com.jusfoun.ui.fragment;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.GiftModel;
import com.jusfoun.mvp.contract.GiftContract;
import com.jusfoun.mvp.presenter.GiftPresenter;
import com.jusfoun.ui.adapter.GiftAdapter;
import com.jusfoun.utils.AppUtils;

/**
 * 首页礼品列表
 *
 * @时间 2017/8/8
 * @作者 LiuGuangDan
 */

public class GiftSubListFragment extends BaseListFragment implements GiftContract.IView {

    private GiftPresenter presenter;


    @Override
    public BaseQuickAdapter getAdapter() {
        return adapter = new GiftAdapter();
    }

    @Override
    protected void initUi() {
        adapter.addHeaderView(View.inflate(activity, R.layout.view_gift_header, null));
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    protected void startLoadData() {
        String bean = getArguments().getString("bean");
        if (TextUtils.isEmpty(bean)) {
            presenter = new GiftPresenter(this);
            presenter.loadData(getArguments().getString("id"));
        } else {
            GiftModel mode = new Gson().fromJson(bean, GiftModel.class);
            suc(mode);
        }
    }

    @Override
    public void suc(GiftModel mode) {
        completeLoadData(mode.list);
    }

    @Override
    public void error() {
        completeLoadDataError();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        AppUtils.goWebActivityForGift(((GiftModel.GiftBean) adapter.getItem(position)).url);
    }
}
