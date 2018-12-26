package com.jusfoun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jusfoun.event.FinishExchangeEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jusfoun.app.MyApplication.context;
import static com.jusfoun.constants.CommonConstant.GIFT_TYPE;
import static com.jusfoun.constants.CommonConstant.GIFT_TYPE_NO_PWD;
import static com.jusfoun.constants.CommonConstant.REQ_CAPTURE;

/**
 * 卡密兑奖页面
 *
 * @时间 2017/8/10
 * @作者 LiuGuangDan
 */

public class CardPwdExchangeActivity extends BaseActivity {

    @BindView(R.id.etCardNo)
    EditText etCardNo;
    @BindView(R.id.etCardPwd)
    EditText etCardPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_carpwd_exchange);
        ButterKnife.bind(this);
        setTitle("卡密兑奖");
        ivTitleRight.setImageResource(R.drawable.img_ac);
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQ_CAPTURE);
    }

    public void exchanger(View view) {
        if (TextUtils.isEmpty(getValue(etCardNo))) {
            showToast("请输入卡号");
            return;
        }
        if (TextUtils.isEmpty(getValue(etCardPwd))) {
            showToast("请输入密码");
            return;
        }

        Intent intent = new Intent(context, SelectGiftActivity.class);
        intent.putExtra(GIFT_TYPE, GIFT_TYPE_NO_PWD);
        intent.putExtra("no", getValue(etCardNo));
        intent.putExtra("pwd", getValue(etCardPwd));
        startActivity(intent);
    }

    @Override
    public void onRxEvent(RxIEvent event) {
        super.onRxEvent(event);
        if (event instanceof FinishExchangeEvent) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppUtils.onActivityResultForCard(this, requestCode, resultCode, data);
    }
}
