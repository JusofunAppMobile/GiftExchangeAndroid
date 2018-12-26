package com.jusfoun.mvp.contract;

import com.jusfoun.mvp.presenter.BasePresenter;
import com.jusfoun.mvp.view.BaseView;

/**
 * 登录 Contract
 *
 * @时间 2017/9/18
 * @作者 LiuGuangDan
 */

public class LoginContract {

    public interface IView extends BaseView {
        void loginSuc();

        void sendCode();
    }

    public interface IPresenter extends BasePresenter {
        void login(String phone, String code);
    }

}
