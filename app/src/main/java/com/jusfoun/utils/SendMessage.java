package com.jusfoun.utils;

import android.app.Activity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class SendMessage {

    private Timer timer = new Timer();
    private TimerTask timerTask;
    private int maxTime;
    private TextView clickView;
    private Activity activity;
    private int defaultMaxTime = 60;
    private String tipText = "获取验证码";

    private SendMessage(Activity activity) {
        this.activity = activity;
    }

    public static SendMessage newInstant(Activity activity) {
        return new SendMessage(activity);
    }

    public SendMessage setClickView(TextView view) {
        this.clickView = view;
        return this;
    }

    public SendMessage setTipText(String text) {
        this.tipText = text;
        return this;
    }

    public SendMessage setTime(int time) {
        defaultMaxTime = time;
        return this;
    }

    public void start() {
        maxTime = defaultMaxTime;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                setCodeTime(maxTime--);
            }
        };
        timer.schedule(timerTask, 0, 1000);
        clickView.setClickable(false);
    }

    public void reset() {
        setCodeTime(0);
    }

    private void setCodeTime(final int time) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (time <= 0) {
                    clickView.setText(tipText);
                    clickView.setClickable(true);
                    maxTime = defaultMaxTime;
                    timerTask.cancel();
                } else {
                    clickView.setText("重新发送(" + time + ")");
                    clickView.setClickable(false);
                }
            }
        });
    }
}
