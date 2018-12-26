package com.jusfoun.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.inter.ICommon;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.ui.dialog.LoadingDialog;
import com.jusfoun.utils.ToastUtils;

import rx.functions.Action1;

/**
 * 说明
 *
 * @时间 2017/8/7
 * @作者 LiuGuangDan
 */

public class BaseFragment extends Fragment implements ICommon {

    protected View rootView;

    protected Activity activity;

    private LoadingDialog loadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.getDefault().register(new Action1<RxIEvent>() {
            @Override
            public void call(RxIEvent rxIEvent) {
                onRxEvent(rxIEvent);
            }
        }, getClass().getName());
        initDialog();
    }

    /**
     * 接收event 重写此方法
     */
    public void onRxEvent(RxIEvent event) {

    }

    private void initDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(activity, R.style.C_Dialog);
            loadingDialog.setCancelable(true);
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (loadingDialog != null) {
                            loadingDialog.cancel();
                        }
                    }
                    return true;
                }
            });
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(activity, clazz);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().removeSubscriptions(getClass().getName());
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void showLoading() {
        if (isDetached()) {
            return;
        }

        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

    protected String getValue(EditText editText) {
        return editText.getText().toString();
    }

    protected String getValue(TextView textView) {
        return textView.getText().toString();
    }
}
