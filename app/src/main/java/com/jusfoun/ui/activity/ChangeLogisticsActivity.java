package com.jusfoun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jusfoun.event.RxIEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.source.BaseSoure;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.jusfoun.constants.CommonConstant.REQ_CAPTURE_EXPRESS_DELIVERY;

/**
 * 收货地址页面
 *
 * @时间 2017/8/11
 * @作者 LiuGuangDan
 */

public class ChangeLogisticsActivity extends BaseActivity {


    @BindView(R.id.ivTitleLeft)
    ImageView ivTitleLeft;
    @BindView(R.id.tvTitleLeft)
    TextView tvTitleLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.ivTitleRight)
    ImageView ivTitleRight;
    @BindView(R.id.vNavigation)
    RelativeLayout vNavigation;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private ExchangeRecordModel data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelogistics);
        ButterKnife.bind(this);


        if (getIntent() != null && getIntent().getExtras() != null)
            data = (ExchangeRecordModel) getIntent().getExtras().get("data");

        if (data != null) {
            editNumber.setText(data.flowNo);
            editNumber.setSelection(editNumber.getText().length());
        }

        ivTitleRight.setImageResource(R.drawable.img_s_s);
        tvTitle.setText("物流更改");

        if (data != null) {
            tvNumber.setText(data.no);
        }
    }


    @Override
    public void onRxEvent(RxIEvent event) {
        super.onRxEvent(event);
    }


    @OnClick({R.id.ivTitleRight, R.id.edit_number, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTitleRight:
                Intent intent = new Intent(this, CaptureActivity.class);
                intent.putExtra(CaptureActivity.TYPE, CaptureActivity.TYPE_EXPRESS_DELIVERY);
                startActivityForResult(intent, REQ_CAPTURE_EXPRESS_DELIVERY);
                break;
            case R.id.btn_submit:
                if (TextUtils.isEmpty(editNumber.getText())) {
                    Toast.makeText(activity, "请输入快递单号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(data == null) return;

                Map<String, Object> map = BaseSoure.getMap();
                map.put("pid", data.pid);
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
                        ToastUtils.show("修改成功");
                        finish();
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_CAPTURE_EXPRESS_DELIVERY) {
            String result = AppUtils.getCaptureResult(data);
            editNumber.setText(result);
        }
    }
}
