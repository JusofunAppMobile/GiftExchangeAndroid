package com.jusfoun.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.event.FinishExchangeEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.mvp.contract.UpdateAddrContract;
import com.jusfoun.mvp.presenter.UpdateAddrPresenter;
import com.jusfoun.utils.LogUtils;
import com.jusfoun.utils.PreferenceUtils;
import com.jusfoun.utils.ToastUtils;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 收货地址页面
 *
 * @时间 2017/8/11
 * @作者 LiuGuangDan
 */
public class AddressActivity extends BaseActivity implements UpdateAddrContract.IView {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etAddr)
    TextView etAddr;
    @BindView(R.id.etAddrDetial)
    EditText etAddrDetial;

    private UpdateAddrPresenter presenter;

    private ExchangeRecordModel.AddrInfoBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address);
        ButterKnife.bind(this);
        setTitle("收货地址");

        presenter = new UpdateAddrPresenter(this);
        bean = new Gson().fromJson(getIntent().getStringExtra("bean"), ExchangeRecordModel.AddrInfoBean.class);

        if (bean != null) {
            etName.setText(bean.name);
            etPhone.setText(bean.phone);
            etAddr.setText(bean.addr);
            etAddrDetial.setText(bean.addrDetail);
        } else {
            String name = PreferenceUtils.getString(this, PreferenceConstant.LOC_NAME);
            String phone = PreferenceUtils.getString(this, PreferenceConstant.LOC_PHONE);
            String addr = PreferenceUtils.getString(this, PreferenceConstant.LOC_ADDR);
            String addrDetail = PreferenceUtils.getString(this, PreferenceConstant.LOC_ADDR_DETAIL);
            if (!TextUtils.isEmpty(name))
                etName.setText(name);
            if (!TextUtils.isEmpty(phone))
                etPhone.setText(phone);
            if (!TextUtils.isEmpty(addr))
                etAddr.setText(addr);
            if (!TextUtils.isEmpty(addrDetail))
                etAddrDetial.setText(addrDetail);
        }

        initLocation();
        if (TextUtils.isEmpty(getValue(etAddr))) {
            getLocation();
        }
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    LogUtils.e(aMapLocation.getAddress());
                    etAddr.setText(getAddr(aMapLocation));
                }
            }
        });

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        mLocationOption.setOnceLocation(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    private String getAddr(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            String province = aMapLocation.getProvince();
            String city = aMapLocation.getCity();
            String district = aMapLocation.getDistrict();
            if (province.equals(city))
                city = "";
            if (TextUtils.isEmpty(district))
                district = "";
            return province + city + district;
        }
        return "";
    }

    @OnClick(R.id.ivLocation)
    public void getLocation() {

        new RxPermissions(activity)
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            LogUtils.e(">>>>>已授权");
                            mLocationClient.startLocation();
                        } else {
                            LogUtils.e(">>>>>未授权");
                            ToastUtils.show("请在设置中开启定位权限");
                        }

                    }
                });


    }

    @OnClick(R.id.vLocation)
    public void updateLocation() {
        Intent intent = new Intent(this, SelectProvinceActivity.class);
        startActivityForResult(intent, 0x111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            etAddr.setText(data.getStringExtra("addr"));
        }
    }

    public void commit(View view) {
        if (getValue(etName).isEmpty()
                || getValue(etPhone).isEmpty()
                || getValue(etAddr).isEmpty()
                || getValue(etAddrDetial).isEmpty()) {
            showToast("请填写完整");
            return;
        }
        Bundle bundle = new Bundle();

        PreferenceUtils.setString(this, PreferenceConstant.LOC_NAME, getValue(etName));
        PreferenceUtils.setString(this, PreferenceConstant.LOC_PHONE, getValue(etPhone));
        PreferenceUtils.setString(this, PreferenceConstant.LOC_ADDR, getValue(etAddr));
        PreferenceUtils.setString(this, PreferenceConstant.LOC_ADDR_DETAIL, getValue(etAddrDetial));
        bundle.putString("model", getIntent().getStringExtra("model"));

        if (bean == null)
            startActivity(ExchangeActivity.class, bundle);
        else {
            String id = getIntent().getStringExtra("id");
            if (TextUtils.isEmpty(id)) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                ExchangeRecordModel.AddrInfoBean addrInfoBean = new ExchangeRecordModel.AddrInfoBean();
                addrInfoBean.name = getValue(etName);
                addrInfoBean.phone = getValue(etPhone);
                addrInfoBean.addrDetail = getValue(etAddrDetial);
                addrInfoBean.addr = getValue(etAddr);
                presenter.loadData(addrInfoBean, id);
            }
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
    public void suc() {
        showToast("修改成功");
        finish();
    }
}
