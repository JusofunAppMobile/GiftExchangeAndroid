package com.jusfoun.mvp.contract;

import com.jusfoun.model.CouponManageModel;
import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

import java.util.List;

/**
 * 电子券管理 contract
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class CouponManageContract {

    public interface IView extends BaseView {
        void suc(List<CouponManageModel> list);

        void error();
    }

    public interface IPresenter extends BasePresenter {

        void loadData();
    }

}
