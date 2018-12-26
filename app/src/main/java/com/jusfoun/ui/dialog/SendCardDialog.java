package com.jusfoun.ui.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jusfoun.giftexchange.R;
import com.jusfoun.utils.RegularUtils;
import com.jusfoun.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 银行用户发卡给普通用户 Diloag
 *
 * @时间 2017/8/15
 * @作者 LiuGuangDan
 */

public class SendCardDialog extends BaseDialog {

    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etName)
    EditText etName;

    private OnSelectListener listener;

    public SendCardDialog(Activity activity, OnSelectListener listener) {
        super(activity);
        setContentView(R.layout.dialog_send_card);
        this.listener = listener;
        ButterKnife.bind(this);
        setWindowStyle(8);
    }

    @OnClick({R.id.vCancel, R.id.vSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:

                String phone = etPhone.getText().toString();
                String name = etName.getText().toString();


                if (!RegularUtils.checkMobile(phone)) {
                    ToastUtils.show("手机格式不正确");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    ToastUtils.show("请输入接收人姓名");
                    return;
                }

                if (listener != null) {
                    listener.select(name, phone);
                }
                dismiss();
                break;
        }
    }

    public interface OnSelectListener {
        void select(String name, String phone);
    }
}
