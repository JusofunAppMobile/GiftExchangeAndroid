package com.jusfoun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.View;

import com.jusfoun.event.FinishEvent;
import com.jusfoun.event.FinishHomeEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.NetModel;
import com.jusfoun.model.VersionModel;
import com.jusfoun.mvp.source.BaseSoure;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.ui.dialog.VersionDialog;
import com.jusfoun.ui.fragment.EmptyFragment;
import com.jusfoun.ui.fragment.GiftListFragment;
import com.jusfoun.ui.fragment.MyFragment;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.TabHostManager;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

import static com.jusfoun.constants.CommonConstant.REQ_CAPTURE;

public class HomeActivity extends BaseActivity implements TabHostManager.OnTabChangeListener {

    @BindView(R.id.tabhost)
    FragmentTabHost tabHost;

    private int lastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        ButterKnife.bind(this);

        TabHostManager manager = new TabHostManager(this, tabHost, R.layout.view_common_tabhost_tab);

        setTitle("卡券介绍");
        tvTitleRight.setText("兑奖规则");
        ivTitleLeft.setVisibility(View.GONE);

        manager.addFragment(GiftListFragment.class, "首页", R.drawable.selector_tab1);
        manager.addFragment(EmptyFragment.class, "我要兑奖", R.drawable.selector_tab2);
        manager.addFragment(MyFragment.class, "我", R.drawable.selector_tab3);
        manager.setOnTabChangeListener(this);
        checkVersion();
    }


    @Override
    protected void onRightClick() {
        super.onRightClick();
        AppUtils.goWebActivityRule();
    }


    @Override
    public void onTabChanged(int position) {
        if (!AppUtils.isLogin() && position != 0) {
            tabHost.setCurrentTab(lastPosition);
            startActivity(LoginActivity.class);
            return;
        }
        switch (position) {
            case 0:
                setTitle("卡券介绍");
                tvTitleRight.setVisibility(View.VISIBLE);
                lastPosition = position;
                break;
            case 1:
                Intent intent = new Intent(HomeActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQ_CAPTURE);
                tabHost.setCurrentTab(lastPosition);
                break;
            case 2:
                setTitle("我");
                tvTitleRight.setVisibility(View.GONE);
                lastPosition = position;
                break;
        }
    }

    // 双击退出应用
    private long lastBackPressTime;
    private int pressTime;

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - lastBackPressTime < 3 * 1000 && pressTime == 1) {
            RxBus.getDefault().post(new FinishEvent());
        } else {
            pressTime = 1;
            lastBackPressTime = time;
            showToast("再按一次退出程序");
        }
    }

    @Override
    public void onRxEvent(RxIEvent event) {
        super.onRxEvent(event);
        if (event instanceof FinishHomeEvent) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppUtils.onActivityResultForCard(this, requestCode, resultCode, data);
    }

    private void checkVersion() {
        Map<String, Object> map = BaseSoure.getMap();
        map.put("versionname", AppUtils.getVersionName(this));
        map.put("versioncode", AppUtils.getVersionCode(this));
        map.put("from", "Android");
        map.put("channel", "渠道");
        new RxManager().getData(RetrofitUtil.getInstance().service.checkVersion(map), new Observer<NetModel>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
//                ToastUtils.showHttpError();
            }

            @Override
            public void onNext(NetModel model) {
                VersionModel versionModel = model.fromData(VersionModel.class);
                if (versionModel != null && !TextUtils.isEmpty(versionModel.titletext))
                    new VersionDialog(activity, versionModel).show();
            }
        });
    }
}
