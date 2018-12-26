package com.jusfoun.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jusfoun.giftexchange.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 审核 Diloag
 *
 * @时间 2017/8/15
 * @作者 LiuGuangDan
 */

public class AuditDialog extends BaseDialog {

    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.v1)
    LinearLayout v1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.v2)
    LinearLayout v2;

    private OnSelectListener listener;

    public AuditDialog(Activity activity, OnSelectListener listener) {
        super(activity);
        setContentView(R.layout.dialog_audit);
        ButterKnife.bind(this);
        setWindowStyle(8);
        this.listener = listener;
    }

    @OnClick({R.id.v1, R.id.v2, R.id.vCancel, R.id.vSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v1:
                v1.setBackgroundColor(Color.parseColor("#F6F4F3"));
                iv1.setVisibility(View.VISIBLE);
                v2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                iv2.setVisibility(View.GONE);
                break;
            case R.id.v2:
                v2.setBackgroundColor(Color.parseColor("#F6F4F3"));
                iv2.setVisibility(View.VISIBLE);
                v1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                iv1.setVisibility(View.GONE);
                break;
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:
                if (listener != null)
                    listener.select(iv1.getVisibility() == View.VISIBLE);
                dismiss();
                break;
        }
    }

    public interface OnSelectListener {
        void select(boolean pass);
    }
}
