package com.jusfoun.ui.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jusfoun.giftexchange.R;
import com.jusfoun.utils.QRCodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 银行用户发卡给普通用户 Diloag
 *
 * @时间 2017/8/15
 * @作者 LiuGuangDan
 */

public class QrcodeDialog extends BaseDialog {

    @BindView(R.id.ivQrcode)
    ImageView ivQrcode;

    public QrcodeDialog(Activity activity, String value) {
        super(activity);
        setContentView(R.layout.dialog_qrcode);
        ButterKnife.bind(this);
        setWindowStyle(10, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        ivQrcode.setImageBitmap(QRCodeUtils.createQRImage(value, 400));
    }

    @OnClick(R.id.vCancel)
    public void onViewClicked() {
        dismiss();
    }
}
