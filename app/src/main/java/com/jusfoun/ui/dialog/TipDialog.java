package com.jusfoun.ui.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jusfoun.giftexchange.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提示 Diloag
 *
 * @时间 2017/8/15
 * @作者 LiuGuangDan
 */

public class TipDialog extends BaseDialog {

    @BindView(R.id.tvMsg)
    TextView tvMsg;

    private OnSelectListener listener;

    public TipDialog(Activity activity, OnSelectListener listener, String msg) {
        super(activity);
        setContentView(R.layout.dialog_tip);
        ButterKnife.bind(this);
        setWindowStyle(8);
        this.listener = listener;
        if (!TextUtils.isEmpty(msg))
            tvMsg.setText(msg);
    }

    @OnClick({R.id.vCancel, R.id.vSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:
                if (listener != null)
                    listener.select();
                dismiss();
                break;
        }
    }

    public interface OnSelectListener {
        void select();
    }
}
