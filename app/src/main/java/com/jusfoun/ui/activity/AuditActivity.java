package com.jusfoun.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;

import com.jusfoun.giftexchange.R;
import com.jusfoun.ui.fragment.BankAuditListFragment;
import com.jusfoun.utils.TabHostManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单审核页面
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class AuditActivity extends BaseActivity  {

    @BindView(R.id.tabhost)
    FragmentTabHost tabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_audit);
        ButterKnife.bind(this);

        TabHostManager manager = new TabHostManager(this, tabHost, R.layout.view_common_tabhost_tab);

        manager.addFragment(BankAuditListFragment.class, "待审核", R.drawable.selector_tab4, 0, getPositionBundle(0));
        manager.addFragment(BankAuditListFragment.class, "待发货", R.drawable.selector_tab5, 0, getPositionBundle(1));
        manager.addFragment(BankAuditListFragment.class, "已发货", R.drawable.selector_tab6, 0, getPositionBundle(2));

        setTitle("待审核");
    }

    private Bundle getPositionBundle(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        return bundle;
    }
}
