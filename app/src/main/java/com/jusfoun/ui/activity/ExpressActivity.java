package com.jusfoun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;

import com.jusfoun.event.RefreshAuditEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.source.BaseSoure;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.ui.fragment.BankAuditListFragment;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.TabHostManager;
import com.jusfoun.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

import static com.jusfoun.constants.CommonConstant.REQ_CAPTURE;
import static com.jusfoun.constants.CommonConstant.REQ_CAPTURE_EXPRESS_DELIVERY;

/**
 * 物流管理页面
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class ExpressActivity extends BaseActivity {

    @BindView(R.id.tabhost)
    FragmentTabHost tabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_audit);
        ButterKnife.bind(this);

        TabHostManager manager = new TabHostManager(this, tabHost, R.layout.view_common_tabhost_tab);

        setTitle("待发货");
        manager.addFragment(BankAuditListFragment.class, "待发货", R.drawable.selector_tab4, 0, getPositionBundle(3));
        manager.addFragment(BankAuditListFragment.class, "已发货", R.drawable.selector_tab5, 0, getPositionBundle(4));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_CAPTURE) {
            String result = AppUtils.getCaptureResult(data);
            if (result != null) {
//                startActivity(SelectGiftActivity.class);
            }
        } else if (resultCode == RESULT_OK && requestCode == REQ_CAPTURE_EXPRESS_DELIVERY) {
            Map<String, Object> map = BaseSoure.getMap();
            map.put("pid", data.getStringExtra("pid"));
            map.put("flowNo", AppUtils.getCaptureResult(data));
            new RxManager().getData(RetrofitUtil.getInstance().service.updateFlow(map), new Observer<NetModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showHttpError();
                }

                @Override
                public void onNext(NetModel model) {
                    ToastUtils.show("发货成功");
                    RxBus.getDefault().post(new RefreshAuditEvent());
                }
            });
        }
    }

    private Bundle getPositionBundle(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        return bundle;
    }
}
