package com.jusfoun.mvp.contract;

import com.jusfoun.model.GiftModel;
import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

/**
 * 首页礼品列表 contract
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class GiftContract {

    public interface IView extends BaseView {
        void suc(GiftModel mode);

        void error();
    }

    public interface IPresenter extends BasePresenter {

        void loadData(String type);

        void getDataByNoAndPwd(String no, String pwd);

        void getDataByQrcode(String qrcode);

    }

}
