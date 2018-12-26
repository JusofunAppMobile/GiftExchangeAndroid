package com.jusfoun.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jusfoun.event.FinishHomeEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.contract.LoginContract;
import com.jusfoun.mvp.presenter.LoginPresenter;
import com.jusfoun.mvp.source.BaseSoure;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.utils.MD5Utils;
import com.jusfoun.utils.RegularUtils;
import com.jusfoun.utils.SendMessage;
import com.jusfoun.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * 登录页面
 *
 * @时间 2017/8/7
 * @作者 LiuGuangDan
 */

public class LoginActivity extends BaseActivity implements LoginContract.IView {

    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.btSendCode)
    Button btSendCode;
    @BindView(R.id.etCode)
    EditText etCode;
    private SendMessage sendMessage;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        setTitle("登录");

        presenter = new LoginPresenter(this);

        sendMessage = SendMessage.newInstant(this)
                .setClickView(btSendCode);

//        ivTitleLeft.setVisibility(View.GONE);
    }

    @OnClick(R.id.btSendCode)
    public void getCode(View view) {

        String phone = getValue(etPhone);

        if (!RegularUtils.checkMobile(phone)) {
            showToast("手机号码格式不正确");
            return;
        }
        sendMessage.start();

        Map<String, Object> map = BaseSoure.getMap();

        String random = String.valueOf(Math.random() * 9000 + 1000);

        //   string md5String = param.phone + param.random + "jiucifang";
        String encryption = MD5Utils.MD5Encode(phone + random + "jiucifang");

        map.put("phone", phone);
        map.put("encryption", encryption);
        map.put("random", random);
        new RxManager().getData(RetrofitUtil.getInstance().service.sendCode(map), new Observer<NetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showHttpError();
            }

            @Override
            public void onNext(NetModel model) {
                if (model.success())
                    showToast("手机验证码已发送成功，请注意查收");
                else {
                    showToast(model.msg);
                    sendMessage.reset();
                }
            }
        });
    }

    public void login(View view) {
        String phone = getValue(etPhone);
        String code = getValue(etCode);
        if (!RegularUtils.checkMobile(phone)) {
            showToast("手机号码格式不正确");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            showToast("请输入验证码");
            return;
        }
        presenter.login(phone, code);
    }

    @Override
    public void loginSuc() {
        showToast("登录成功");
        RxBus.getDefault().post(new FinishHomeEvent());
        startActivity(HomeActivity.class);
        delayFinish();
    }

    @Override
    public void sendCode() {

    }
}
