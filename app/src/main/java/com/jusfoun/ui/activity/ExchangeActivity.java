package com.jusfoun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jusfoun.app.MyApplication;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.event.FinishExchangeEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.model.GiftModel;
import com.jusfoun.model.param.ExchangeModel;
import com.jusfoun.mvp.contract.ExchangeContract;
import com.jusfoun.mvp.presenter.ExchangePresenter;
import com.jusfoun.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 确认兑奖 页面
 *
 * @时间 2017/8/10
 * @作者 LiuGuangDan
 */

public class ExchangeActivity extends BaseActivity implements ExchangeContract.IView {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.vName)
    LinearLayout vName;
    @BindView(R.id.tvAddr)
    TextView tvAddr;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tvGiftName)
    TextView tvGiftName;

    private GiftModel.GiftBean model;

    private ExchangePresenter presenter;

    private ExchangeModel exchangeModel;

    private ExchangeRecordModel.AddrInfoBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_exchange);
        ButterKnife.bind(this);
        setTitle("确认兑奖");
        presenter = new ExchangePresenter(this);

        loadLocData();

        model = new Gson().fromJson(getIntent().getStringExtra("model"), GiftModel.GiftBean.class);

        Glide.with(MyApplication.context).load(model.img).into(iv);
        tvGiftName.setText(model.name);
    }

    private void loadLocData() {
        exchangeModel = new Gson().fromJson(PreferenceUtils.getString(this, PreferenceConstant.EXCHANGE), ExchangeModel.class);
        exchangeModel.name = PreferenceUtils.getString(this, PreferenceConstant.LOC_NAME);
        exchangeModel.phone = PreferenceUtils.getString(this, PreferenceConstant.LOC_PHONE);
        exchangeModel.addr = PreferenceUtils.getString(this, PreferenceConstant.LOC_ADDR);
        exchangeModel.addrDetail = PreferenceUtils.getString(this, PreferenceConstant.LOC_ADDR_DETAIL);

        tvName.setText(exchangeModel.name);
        tvPhone.setText(exchangeModel.phone);
        tvAddr.setText(exchangeModel.addr + exchangeModel.addrDetail);

        bean = new ExchangeRecordModel.AddrInfoBean();
        bean.name = exchangeModel.name;
        bean.phone = exchangeModel.phone;
        bean.addr = exchangeModel.addr;
        bean.addrDetail = exchangeModel.addrDetail;
    }

    @OnClick(R.id.vLocation)
    public void location() {
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra("bean", new Gson().toJson(bean));
        startActivityForResult(intent, 0x100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadLocData();
        }
    }

    public void confirm(View view) {
        presenter.exchange(exchangeModel);
    }

    @Override
    public void onRxEvent(RxIEvent event) {
        super.onRxEvent(event);
        if (event instanceof FinishExchangeEvent) {
            finish();
        }
    }

    @Override
    public void suc() {
        showToast("兑换成功");
        startActivity(ExchangeRecordListActivity.class);
    }
}
