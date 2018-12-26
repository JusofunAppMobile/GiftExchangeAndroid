package com.jusfoun.mvp.contract;

import com.jusfoun.model.ReceiveUserModel;
import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

import java.util.List;

/**
 * 接收用户 contract
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ReceiveUserContract {

    public interface IView extends BaseView {
        void suc(List<ReceiveUserModel> list);
    }

    public interface IPresenter extends BasePresenter {

        void loadData(int pageNum, int pageSize);

    }

}
