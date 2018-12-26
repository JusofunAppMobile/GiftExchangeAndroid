package com.jusfoun.mvp.presenter;

import com.jusfoun.app.MyApplication;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.LoginContract;
import com.jusfoun.mvp.source.LoginSource;
import com.jusfoun.utils.LogUtils;
import com.jusfoun.utils.PreferenceUtils;
import com.jusfoun.utils.ToastUtils;

/**
 * 登录 Presenter
 *
 * @时间 2017/9/18
 * @作者 LiuGuangDan
 */

public class LoginPresenter implements LoginContract.IPresenter {

    private LoginContract.IView view;
    private LoginSource source;

    public LoginPresenter(LoginContract.IView view) {
        this.view = view;
        source = new LoginSource();
    }

    @Override
    public void login(final String phone, String code) {
        view.showLoading();
        source.login(phone, code, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.hideLoading();
                NetModel model = (NetModel) data;
                if (model.success()) {
                    PreferenceUtils.setString(MyApplication.context, PreferenceConstant.USERID, model.data.get("userId").toString());
                    PreferenceUtils.setString(MyApplication.context, PreferenceConstant.PHONE, phone);
                    PreferenceUtils.setInt(MyApplication.context, PreferenceConstant.TYPE, Integer.parseInt(model.data.get("type").toString()));
                    view.loginSuc();
                } else {
                    view.showToast(model.msg);
                }
            }

            @Override
            public void onFail(String error) {
                LogUtils.e(error);
                ToastUtils.showHttpError();
                view.hideLoading();
            }
        });
    }
}
