package com.jusfoun.mvp.contract;

import com.jusfoun.model.CardHistoryModel;
import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

import java.util.List;

/**
 * 管理员发卡历史 contract
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class CardHistoryContract {

    public interface IView extends BaseView {
        void suc(List<CardHistoryModel> list);

        void error();
    }

    public interface IPresenter extends BasePresenter {

        void loadData(int pageNum, int pageSize);

    }

}
