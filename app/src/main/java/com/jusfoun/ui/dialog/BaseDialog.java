package com.jusfoun.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jusfoun.giftexchange.R;


public class BaseDialog extends Dialog implements View.OnClickListener {

    protected Activity activity;

    /**
     * 宽度为屏幕的宽度比例
     */
    public static final int MATCH_PARENT = 10;

    public BaseDialog(Activity activity) {
        super(activity, R.style.C_Theme_Widget_Dialog);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        this.activity = activity;
        setOwnerActivity(activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * @param wid 范围值： 0-10,占屏幕宽的比例
     */
    public void setWindowStyle(int wid) {
        setWindowStyle(wid, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }

    /**
     * @param wid          范围值： 0-10,占屏幕宽的比例
     * @param heightParams ViewGroup.LayoutParams.WRAP_CONTENT 或者ViewGroup.LayoutParams.MATCH_PARENT
     * @param gravity
     */
    public void setWindowStyle(int wid, int heightParams, int gravity) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int parentWidth = dm.widthPixels;
        int width = parentWidth;
        lp.width = width * wid / 10;
        lp.height = heightParams;
        lp.gravity = gravity;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {

    }
}
