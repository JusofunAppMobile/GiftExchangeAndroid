package com.jusfoun.mvp.contract;

import com.jusfoun.model.param.ExchangeModel;
import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

/**
 * 礼品兑换 contract
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ExchangeContract {

    public interface IView extends BaseView {
        void suc();
    }

    public interface IPresenter extends BasePresenter {

        void exchange(ExchangeModel model);

    }

}
