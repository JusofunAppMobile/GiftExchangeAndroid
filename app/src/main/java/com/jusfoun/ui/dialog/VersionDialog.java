package com.jusfoun.ui.dialog;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jusfoun.event.FinishEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.VersionModel;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版本升级对话框
 *
 * @时间 2017/9/25
 * @作者 LiuGuangDan
 */

public class VersionDialog extends BaseDialog {

    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.vCancel)
    Button vCancel;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.vSure)
    Button vSure;
    private VersionModel model;

    public VersionDialog(Activity activity, VersionModel model) {
        super(activity);
        this.model = model;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_version);
        ButterKnife.bind(this);
        setWindowStyle(8);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        tvVersion.setText("最新版本：v" + model.versionname);
        tvContent.setText(model.describe);
        tvTitle.setText(model.titletext);
        vCancel.setText(model.cacletext);
        vSure.setText(model.updatetext);
    }

    @OnClick({R.id.vCancel, R.id.vSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                if (isForce())
                    RxBus.getDefault().post(new FinishEvent());
                break;
            case R.id.vSure:
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(model.url));
                //设置在什么网络情况下进行下载
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                //设置通知栏标题
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                request.setTitle(AppUtils.getApplicationName(activity));
                request.setDescription("正在下载最新版本");
                request.setAllowedOverRoaming(false);
                //设置文件存放目录
                request.setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS, "jusfoun");

                DownloadManager downManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                long id = downManager.enqueue(request);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
                sp.edit().putLong(DownloadManager.EXTRA_DOWNLOAD_ID, id).commit();
                ToastUtils.show("正在后台下载，完成后自动运行安装");
                dismiss();
                break;
        }
    }

    //  "update": 0, // 1:强制更新 0：非强制更新
    private boolean isForce() {
        return model.update == 1;
    }
}
