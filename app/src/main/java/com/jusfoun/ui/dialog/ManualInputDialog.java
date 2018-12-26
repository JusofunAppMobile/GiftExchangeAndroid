package com.jusfoun.ui.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jusfoun.event.RefreshAuditEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.source.BaseSoure;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @author zhaoyapeng
 * @version create time:17/8/1517:02
 * @Email zyp@jusfoun.com
 * @Description ${手动输入dailog}
 */
public class ManualInputDialog extends BaseDialog {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.vCancel)
    Button vCancel;
    @BindView(R.id.vSure)
    Button vSure;

    private String pid;

    public ManualInputDialog(Activity activity, String pid, CallBack callBack) {
        super(activity);
        setContentView(R.layout.dialog_manualinput);
        ButterKnife.bind(this);
        this.pid = pid;
        setWindowStyle(8);
        this.callBack = callBack;
    }


    @OnClick({R.id.vCancel, R.id.vSure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:
                if (TextUtils.isEmpty(editNumber.getText())) {
                    Toast.makeText(activity, "请输入快递单号", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Object> map = BaseSoure.getMap();
                map.put("pid", pid);
                map.put("flowNo", editNumber.getText().toString());
                new RxManager().getData(RetrofitUtil.getInstance().service.updateFlow(map), new Observer<NetModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showHttpError();
                    }

                    @Override
                    public void onNext(NetModel model) {
                        ToastUtils.show("发货成功");
                        dismiss();
                        callBack.afrim("");
                        RxBus.getDefault().post(new RefreshAuditEvent());
                    }
                });
                break;
        }
    }

    public interface CallBack {
        void afrim(String num);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
