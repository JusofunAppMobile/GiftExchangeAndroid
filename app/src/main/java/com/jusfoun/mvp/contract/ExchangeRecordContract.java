package com.jusfoun.mvp.contract;

import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

import java.util.List;

/**
 * 礼品兑换记录列表 contract
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ExchangeRecordContract {

    public interface IView extends BaseView {
        void suc(List<ExchangeRecordModel> list);

        void error();
    }

    public interface IPresenter extends BasePresenter {

        void loadData();

        // 管理员订单审核
        void loadManageAudit(int type, String startTime, String endTime, int pageNum, int pageSize);

    }

}
