package com.jusfoun.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jusfoun.giftexchange.R;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.LogUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Initial the camera
 * <p>
 * 默认的二维码扫描Activity
 */
@RuntimePermissions
public class CaptureActivity extends BaseActivity {


    @BindView(R.id.btn_exchange)
    Button btnExchange;
    private CaptureFragment captureFragment;
    private int type;
    public static int TYPE_EXPRESS_DELIVERY = 1001;
    public static String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_camera);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", -1);

        if (type == TYPE_EXPRESS_DELIVERY) {
            btnExchange.setVisibility(View.GONE);
            setTitle("扫描录入");
            captureFragment = CaptureFragment.getInstace("将二维码/条码放入框内, 即可自动扫描");
        } else {
            setTitle("扫一扫兑奖");
            captureFragment = CaptureFragment.getInstace("将兑奖卡的兑奖二维码放入框内，即可自动扫描兑奖");
        }


        checkPermission();
        CaptureActivityPermissionsDispatcher.showCameraWithPermissionCheck(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        CaptureActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        LogUtils.e(">>>>>>>>>>showCamera");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (!checkPermissionCameraBelowM()) {
                showNeverAskForCamera();
                return;
            }
        }
        init();
    }

    /**
     * 6.0以下系统，判断是否有拍照权限
     *
     * @return
     */
    public static boolean checkPermissionCameraBelowM() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            LogUtils.e("6.0以下，未授权拍照权限");
            canUse = false;
        }
        if (mCamera != null) {
            LogUtils.e("6.0以下，已授权拍照权限");
            mCamera.release();
        }
        return canUse;
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        LogUtils.e(">>>>>>>>>>showRationaleForCamera");
        new AlertDialog.Builder(this)
                .setMessage("二维码扫描需要使用摄像头权限，请授权")
                .setPositiveButton("授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        showNeverAskForCamera();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        LogUtils.e(">>>>>>>>>>showNeverAskForCamera");
        new AlertDialog.Builder(this)
                .setMessage("请到设置中开启" + AppUtils.getApplicationName(this) + "的摄像头权限")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void checkPermission() {
//        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
//        rxPermissions
//                .request(Manifest.permission.CAMERA)
//                .subscribe(new Action1<Boolean>() {
//
//                    @Override
//                    public void call(Boolean permission) {
//                        showToast("permission=" + permission);
//
//                    }
//                });
    }

    public void cardpwdExchange(View view) {
        startActivity(CardPwdExchangeActivity.class);
        delayFinish();
    }

    private boolean isResultValid(String result) {
        if (!TextUtils.isEmpty(result) && result.contains("#") && result.split("#").length == 2)
            return true;
        return false;
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

            Log.e("tag","result="+result);

            if (type == TYPE_EXPRESS_DELIVERY) {
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                bundle.putString(CodeUtils.RESULT_STRING, result);
                bundle.putString("pid", getIntent().getStringExtra("pid"));
                resultIntent.putExtras(bundle);
                CaptureActivity.this.setResult(RESULT_OK, resultIntent);
                CaptureActivity.this.finish();
                return;
            }

            //TODO 暂时注释逻辑
//            if (!isResultValid(result)) {
//                showToast("二维码错误");
//                captureFragment.restart();
//                return;
//            }
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            bundle.putString("pid", getIntent().getStringExtra("pid"));
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }
    };


    private void init() {
        captureFragment.setAnalyzeCallback(analyzeCallback);
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.vFrame, captureFragment).commitAllowingStateLoss();
        }catch (Exception e){
            Log.e("tag","e="+e);
        }
    }
}