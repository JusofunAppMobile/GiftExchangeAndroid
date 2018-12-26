package com.jusfoun.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.event.RefreshAuditEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.mvp.contract.ExchangeRecordContract;
import com.jusfoun.mvp.presenter.ExchangeRecordPresenter;
import com.jusfoun.ui.adapter.BankAuditAdapter;
import com.jusfoun.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 银行审核 Fragment
 *
 * @时间 2017/8/14
 * @作者 LiuGuangDan
 */

public class BankAuditListFragment extends BaseListFragment implements ExchangeRecordContract.IView {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.ivTitleRight)
    ImageView ivTitleRight;
    private ExchangeRecordPresenter presenter;
    private String startDate;

    private DatePicker picker;

    @Override
    public BaseQuickAdapter getAdapter() {
        return new BankAuditAdapter(activity, getArguments().getInt("index"));
    }

    @Override
    public void onRxEvent(RxIEvent event) {
        super.onRxEvent(event);
        if (event instanceof RefreshAuditEvent) {
            refresh();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_audit;
    }

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    protected void initUi() {
        presenter = new ExchangeRecordPresenter(this);
        int index = getArguments().getInt("index");
        switch (index) {
            case 0:
                tvTitle.setText("待审核");
                break;
            case 1:
                tvTitle.setText("待发货");
                break;
            case 2:
                tvTitle.setText("已发货");
                break;
            case 3:
                tvTitle.setText("待发货");
                break;
            case 4:
                tvTitle.setText("已发货");
                break;
        }
    }

    @Override
    protected void startLoadData() {
        int type = getArguments().getInt("index") + 1;
        if (type > 3)
            type -= 3;
        if (TextUtils.isEmpty(startDate))
            presenter.loadManageAudit(type, null, null, pageIndex, pageSize);
        else
            presenter.loadManageAudit(type, startDate + "-01", AppUtils.getLastDayOfMonth(startDate), pageIndex, pageSize);
    }

    @Override
    public void suc(List<ExchangeRecordModel> list) {
        completeLoadData(list);
        ivTitleRight.setImageResource(R.drawable.img_bc);
        tvTitleRight.setText(list == null ? "共0单" : "共" + list.size() + "单");
    }

    @Override
    public void error() {
        completeLoadDataError();
        ivTitleRight.setImageResource(R.drawable.img_bc);
        tvTitleRight.setText("共0单");
    }

    public void showDatePicker() {
        if (picker == null) {
            picker = AppUtils.showDateDialog(activity, new DatePicker.OnYearMonthPickListener() {
                @Override
                public void onDatePicked(String year, String month) {
                    startDate = year + "-" + month;
                    refresh();
                }
            });
        }

        picker.show();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        ExchangeRecordModel model = (ExchangeRecordModel) adapter.getItem(position);
        AppUtils.goWebActivityForGift(model.detailUrl);
    }

    @OnClick({R.id.ivTitleLeft, R.id.tvTitleRight, R.id.ivTitleRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTitleLeft:
                activity.finish();
                break;
            case R.id.tvTitleRight:
                showDatePicker();
                break;
            case R.id.ivTitleRight:
                showDatePicker();
                break;
        }
    }
}
