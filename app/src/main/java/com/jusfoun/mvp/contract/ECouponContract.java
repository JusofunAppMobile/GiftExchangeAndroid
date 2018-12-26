package com.jusfoun.mvp.contract;

import com.jusfoun.model.ECouponModel;
import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

import java.util.List;

/**
 * 我的电子券 contract
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ECouponContract {

    public interface IView extends BaseView {
        void suc(List<ECouponModel> list);

        void error();
    }

    public interface IPresenter extends BasePresenter {

        void loadData();

    }

}
