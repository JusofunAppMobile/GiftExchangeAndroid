package com.jusfoun.ui.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.event.FinishEvent;
import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.inter.ICommon;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.ui.dialog.LoadingDialog;
import com.jusfoun.utils.ToastUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import rx.functions.Action1;

/**
 * 说明
 *
 * @时间 2017/8/7
 * @作者 LiuGuangDan
 */

public class BaseActivity extends FragmentActivity implements ICommon {

    protected ImageView ivTitleLeft;
    protected ImageView ivTitleRight;
    protected TextView tvTitleLeft;
    protected TextView tvTitleRight;
    protected TextView tvTitle;

    public FragmentActivity activity;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        if (isSetStatusBar())
            initStatusBar();

        RxBus.getDefault().register(new Action1<RxIEvent>() {
            @Override
            public void call(RxIEvent rxIEvent) {
                onRxEvent(rxIEvent);
            }
        }, getClass().getName());

        initDialog();

    }

    protected void initDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this, R.style.C_Dialog);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().removeSubscriptions(getClass().getName());
    }

    /**
     * 接收event 重写此方法
     */
    public void onRxEvent(RxIEvent event) {
        if (event instanceof FinishEvent)
            finish();
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ivTitleLeft = (ImageView) findViewById(R.id.ivTitleLeft);
        if (ivTitleLeft != null)
            ivTitleLeft.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivTitleRight = (ImageView) findViewById(R.id.ivTitleRight);
        tvTitleLeft = (TextView) findViewById(R.id.tvTitleLeft);
        tvTitleRight = (TextView) findViewById(R.id.tvTitleRight);

        if (ivTitleRight != null) {
            ivTitleRight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onRightClick();
                }
            });
        }
        if (tvTitleRight != null) {
            tvTitleRight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onRightClick();
                }
            });
        }

        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title))
            setTitle(title);
    }

    protected void setTitle(String title) {
        if (tvTitle != null)
            tvTitle.setText(title);
    }


    protected void onRightClick() {

    }

    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 沉侵式状态栏设置
     */
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        if (isSetStatusBar()) {
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(color);

        }
        if (getVertualKeyboardBgColor() != 0) {
            tintManager.setNavigationBarTintEnabled(true);
            //  虚拟键盘的颜色
            tintManager.setNavigationBarTintColor(getVertualKeyboardBgColor());
        }
    }

    public void initStatusBar() {
        setStatusBarColor(ContextCompat.getColor(this, R.color.common));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    /**
     * 是否设置沉侵式状态栏样式
     *
     * @return
     */
    public boolean isSetStatusBar() {
        return true;
    }

    /**
     * 获取虚拟键盘背景颜色，如果为0，不设置
     *
     * @return
     */
    protected int getVertualKeyboardBgColor() {
        return Color.BLACK;
    }

    public void delayFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void showLoading() {
        if ((!isFinishing())) {
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
