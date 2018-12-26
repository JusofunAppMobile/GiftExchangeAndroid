package com.jusfoun.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;

import com.jusfoun.giftexchange.R;
import com.jusfoun.ui.widget.MyProgressBar;

/**
 * 说明
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */


public class LoadingDialog extends Dialog {

    private AsyncTask<?, ?, ?> asyncTask;

    private MyProgressBar myProgressBar;

    private OnKeyCancelListener keyCancelListener;

    private int event = -1;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        myProgressBar = (MyProgressBar) findViewById(R.id.progress_bar);
    }

    public void setEvent(int event) {
        this.event = event;
    }

    @Override
    public void show() {
        super.show();
        myProgressBar.show();
    }

    public AsyncTask<?, ?, ?> getAsyncTask() {
        return asyncTask;
    }

    public void setAsyncTask(AsyncTask<?, ?, ?> asyncTask) {
        this.asyncTask = asyncTask;
    }

    @Override
    public void cancel() {
        super.cancel();
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCancelListener != null) {
                cancel();
                keyCancelListener.cancel(LoadingDialog.this.event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void dismiss() {
        if(myProgressBar != null){
            myProgressBar.stop();
        }
        super.dismiss();
    }

    public void setOnKeyCancelListener(OnKeyCancelListener keyCancelListener) {
        this.keyCancelListener = keyCancelListener;
    }

    public interface OnKeyCancelListener {
        public void cancel(int event);
    }

}