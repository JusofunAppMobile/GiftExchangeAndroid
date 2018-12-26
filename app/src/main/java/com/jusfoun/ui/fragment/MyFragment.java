package com.jusfoun.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.app.MyApplication;
import com.jusfoun.constants.CommonConstant;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.giftexchange.BuildConfig;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.NetModel;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.ui.activity.AuditActivity;
import com.jusfoun.ui.activity.CardHistoryListActivity;
import com.jusfoun.ui.activity.CardManageActivity;
import com.jusfoun.ui.activity.CouponManageListActivity;
import com.jusfoun.ui.activity.ECouponListActivity;
import com.jusfoun.ui.activity.ExchangeRecordListActivity;
import com.jusfoun.ui.activity.ExpressActivity;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observer;

import static com.jusfoun.app.MyApplication.context;

/**
 * 我的选项卡
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class MyFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.vECouponManage)
    LinearLayout vECouponManage;
    @BindView(R.id.vFlowManage)
    LinearLayout vFlowManage;
    @BindView(R.id.vOrderAudit)
    LinearLayout vOrderAudit;
    @BindView(R.id.vCardManage)
    LinearLayout vCardManage;
    @BindView(R.id.vCardHistory)
    LinearLayout vCardHistory;
    @BindView(R.id.vLine1)
    View vLine1;
    @BindView(R.id.vLine2)
    View vLine2;
    @BindView(R.id.vLine3)
    View vLine3;
    @BindView(R.id.vGray)
    View vGray;

    @BindView(R.id.text_phone)
    TextView phoneText;

    private String kefuPhoneNum = "";


    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = PreferenceUtils.getInt(activity, PreferenceConstant.TYPE, CommonConstant.TYPE_USER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = View.inflate(activity, R.layout.frag_my, null);
            ButterKnife.bind(this, rootView);
            initView();
            tvPhone.setText(PreferenceUtils.getString(activity, PreferenceConstant.PHONE));
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initView() {
        switch (type) {
            case CommonConstant.TYPE_USER:

                break;
            case CommonConstant.TYPE_BANK:
                vECouponManage.setVisibility(View.VISIBLE);
                vGray.setVisibility(View.VISIBLE);
                vLine3.setVisibility(View.VISIBLE);
                break;
            case CommonConstant.TYPE_FLOW:
                vFlowManage.setVisibility(View.VISIBLE);
                vGray.setVisibility(View.VISIBLE);
                vLine3.setVisibility(View.VISIBLE);
                break;
            case CommonConstant.TYPE_MANAGER:
                vCardManage.setVisibility(View.VISIBLE);
                vCardHistory.setVisibility(View.VISIBLE);
                vOrderAudit.setVisibility(View.VISIBLE);

                vGray.setVisibility(View.VISIBLE);
                vLine1.setVisibility(View.VISIBLE);
                vLine2.setVisibility(View.VISIBLE);
                vLine3.setVisibility(View.VISIBLE);
                break;
            case CommonConstant.TYPE_MANAGER_BANK:
                vCardManage.setVisibility(View.VISIBLE);
                vCardHistory.setVisibility(View.VISIBLE);

                vGray.setVisibility(View.VISIBLE);
                vLine2.setVisibility(View.VISIBLE);
                vLine3.setVisibility(View.VISIBLE);
                break;
        }

        kefuPhoneNum = PreferenceUtils.getString(context, PreferenceConstant.PHONE_KEFU);
        if (TextUtils.isEmpty(kefuPhoneNum)) {
            getCode();
        } else {
            phoneText.setText(kefuPhoneNum);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btLogout)
    public void logout(View view) {
        AppUtils.logout(activity);
    }

    @OnClick({R.id.vExchange, R.id.vService})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vExchange:
                startActivity(ExchangeRecordListActivity.class);
                break;
            case R.id.vService:
                try {
                    if (!TextUtils.isEmpty(kefuPhoneNum)) {
                        Uri uri = Uri.parse("tel:" + kefuPhoneNum);
                        Intent it = new Intent(Intent.ACTION_DIAL, uri);
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(it);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @OnClick({R.id.vMyECoupon, R.id.vECouponManage, R.id.vFlowManage, R.id.vOrderAudit, R.id.vCardManage, R.id.vCardHistory})
    public void tabClick(View view) {
        switch (view.getId()) {
            case R.id.vMyECoupon: // 我的电子券
                startActivity(ECouponListActivity.class);
                break;
            case R.id.vECouponManage: // 电子券管理
                startActivity(CouponManageListActivity.class);
                break;
            case R.id.vFlowManage: // 物流管理
                startActivity(ExpressActivity.class);
                break;
            case R.id.vOrderAudit: // 订单审核
                startActivity(AuditActivity.class);
                break;
            case R.id.vCardManage: // 发卡管理
                if (type == CommonConstant.TYPE_MANAGER_BANK)
                    startActivity(CouponManageListActivity.class);
                else
                    startActivity(CardManageActivity.class);
                break;
            case R.id.vCardHistory: // 发卡历史
                startActivity(CardHistoryListActivity.class);
                break;
        }
    }

    public void getCode() {

        Map<String, Object> map = new HashMap<>();
        map.put("companyId", BuildConfig.COMPANYID);
        String userId = PreferenceUtils.getString(MyApplication.context, PreferenceConstant.USERID);
        if (!TextUtils.isEmpty(userId)) {
            map.put(PreferenceConstant.USERID, userId);
        }
        new RxManager().getData(RetrofitUtil.getInstance().service.getPhoneNet(map), new Observer<NetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ToastUtils.showHttpError();
            }

            @Override
            public void onNext(NetModel model) {
                if (model.success()) {
                    if (model.data != null && model.data.get("phone") != null) {
                        kefuPhoneNum = model.data.get("phone").toString();
                        PreferenceUtils.setString(MyApplication.context, PreferenceConstant.PHONE_KEFU, kefuPhoneNum);
                        phoneText.setText(kefuPhoneNum);
                    }
                }
            }
        });
    }
}
