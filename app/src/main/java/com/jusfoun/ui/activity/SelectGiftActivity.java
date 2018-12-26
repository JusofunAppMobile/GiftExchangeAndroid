package com.jusfoun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.event.FinishExchangeEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ECouponModel;
import com.jusfoun.model.GiftModel;
import com.jusfoun.model.param.ExchangeModel;
import com.jusfoun.mvp.contract.GiftContract;
import com.jusfoun.mvp.presenter.GiftPresenter;
import com.jusfoun.ui.adapter.GiftAdapter;
import com.jusfoun.utils.PreferenceUtils;

import java.util.List;

import butterknife.BindView;

import static com.jusfoun.constants.CommonConstant.GIFT_TYPE;
import static com.jusfoun.constants.CommonConstant.GIFT_TYPE_ECOUPE;
import static com.jusfoun.constants.CommonConstant.GIFT_TYPE_NO_PWD;
import static com.jusfoun.constants.CommonConstant.GIFT_TYPE_QRCODE;

/**
 * 选择礼品 页面
 *
 * @时间 2017/8/11
 * @作者 LiuGuangDan
 */

public class SelectGiftActivity extends BaseListActivity implements GiftContract.IView {

    private GiftPresenter presenter;

    private GiftModel.CardInfo cardInfo;

    // 电子券
    private ECouponModel eCouponModel;

    private View headView;

    @BindView(R.id.vSubmit)
    View vSubmit;

    @Override
    public BaseQuickAdapter getAdapter() {
        return adapter = new GiftAdapter();
    }

    @Override
    protected void initUi() {
        setTitle("选择礼品");
        presenter = new GiftPresenter(this);
        adapter.addFooterView(View.inflate(this, R.layout.view_select_gift_footer, null));

        eCouponModel = new Gson().fromJson(getIntent().getStringExtra("bean"), ECouponModel.class);
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    protected void startLoadData() {
        int type = getIntent().getIntExtra(GIFT_TYPE, 0);
        if (type == GIFT_TYPE_ECOUPE)
            presenter.loadData(eCouponModel.cardTypeId);
        else if (type == GIFT_TYPE_QRCODE)
            presenter.getDataByQrcode(getIntent().getStringExtra("qrcode"));
        else if (type == GIFT_TYPE_NO_PWD)
            presenter.getDataByNoAndPwd(getIntent().getStringExtra("no"), getIntent().getStringExtra("pwd"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_select_gift;
    }

    public void confirm(View view) {
        int position = -1;
        for (int i = 0; i < adapter.getData().size(); i++) {
            GiftModel.GiftBean bean = (GiftModel.GiftBean) adapter.getData().get(i);
            if (bean.isSelect) {
                position = i;
                break;
            }
        }
        if (position < 0) {
            showToast("请选择一件礼品");
            return;
        }
        int pos;
        pos = position;
        Bundle bundle = new Bundle();
        bundle.putString("model", new Gson().toJson(adapter.getItem(pos)));
        startActivity(AddressActivity.class, bundle);

        ExchangeModel exchangeModel = new ExchangeModel();
        exchangeModel.pid = ((GiftModel.GiftBean) adapter.getItem(pos)).pid;
        if (eCouponModel != null) {
            exchangeModel.no = eCouponModel.cardNo;
            exchangeModel.password = eCouponModel.cardPwd;
        } else {
            exchangeModel.no = cardInfo.cardNo;
            exchangeModel.password = cardInfo.cardPwd;
        }
        PreferenceUtils.setString(this, PreferenceConstant.EXCHANGE, new Gson().toJson(exchangeModel));
    }

    @Override
    public void suc(GiftModel mode) {

        if(mode == null){
            showToast("卡号或密码错误");
            finish();
            return;
        }

        cardInfo = mode.cardInfo;

        if(cardInfo != null){
            if(cardInfo.status == 1){
                showToast("卡券已兑换");
                finish();
                return;
            }
        }

        completeLoadData(mode.list);

        if (mode.list != null && !mode.list.isEmpty()) {
            vSubmit.setVisibility(View.VISIBLE);
        }

        if (cardInfo != null) {
            addHeaderView(cardInfo.cardName);
        } else {
            if (eCouponModel != null) {
                addHeaderView(eCouponModel.cardName);
            }
        }
    }

    private void addHeaderView(String text) {
        if (headView == null) {
            headView = View.inflate(this, R.layout.view_select_gift_header, null);
            TextView tvTypeTip = (TextView) headView.findViewById(R.id.tvTypeTip);
            tvTypeTip.setText("您当前使用的是" + text + "，可选择以下任意一件进行礼品兑换");
            adapter.addHeaderView(headView);
        }
    }

    @Override
    public void error() {
        completeLoadDataError();
        if (pageIndex == INIT_PAGE_INDEX) {
            vSubmit.setVisibility(View.GONE);
            adapter.removeAllHeaderView();
            headView = null;
        }
    }

    @Override
    public void onRxEvent(RxIEvent event) {
        super.onRxEvent(event);
        if (event instanceof FinishExchangeEvent) {
            finish();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        List<GiftModel.GiftBean> list = adapter.getData();
        for (int i = 0; i < list.size(); i++) {
            GiftModel.GiftBean bean = list.get(i);
            bean.isSelect = i == position;
        }
        adapter.notifyDataSetChanged();
    }
}
