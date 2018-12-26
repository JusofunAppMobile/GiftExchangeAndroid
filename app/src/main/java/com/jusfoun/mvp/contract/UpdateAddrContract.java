package com.jusfoun.mvp.contract;

import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

/**
 * 修改地址 contract
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class UpdateAddrContract {

    public interface IView extends BaseView {
        void suc();
    }

    public interface IPresenter extends BasePresenter {

        void loadData(ExchangeRecordModel.AddrInfoBean bean, String id);

    }

}
