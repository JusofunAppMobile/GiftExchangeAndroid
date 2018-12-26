package com.jusfoun.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jusfoun.app.MyApplication;
import com.jusfoun.event.RefreshAuditEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.mvp.source.BaseSoure;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.ui.activity.CaptureActivity;
import com.jusfoun.ui.activity.ChangeLogisticsActivity;
import com.jusfoun.ui.dialog.AuditDialog;
import com.jusfoun.ui.dialog.ManualInputDialog;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import rx.Observer;

import static com.jusfoun.constants.CommonConstant.REQ_CAPTURE_EXPRESS_DELIVERY;

/**
 * 银行审核 Adapter
 *
 * @时间 2017/8/8
 * @作者 LiuGuangDan
 */

public class BankAuditAdapter extends BaseQuickAdapter<ExchangeRecordModel, BaseViewHolder> {

    private Activity activity;

    private int index;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public BankAuditAdapter(Activity activity, int index) {
        super( R.layout.item_bank_audit);
        this.activity = activity;
        this.index = index;
    }

    @Override
    protected void convert(BaseViewHolder holder, final ExchangeRecordModel data) {

        TextView tvNo = holder.getView(R.id.tvNo);
        TextView tvTime = holder.getView(R.id.tvTime);
        TextView tvName = holder.getView(R.id.tvName);
        TextView tvAddr = holder.getView(R.id.tvAddr);
        TextView tvNum = holder.getView(R.id.tvNum);
        final TextView tvOperation = holder.getView(R.id.tvOperation);
        TextView tvPhone = holder.getView(R.id.tvPhone);
        ImageView iv1 = holder.getView(R.id.iv1);
        ImageView iv2 = holder.getView(R.id.iv2);
        ImageView iv3 = holder.getView(R.id.iv3);
        TextView tvTitle = holder.getView(R.id.tvTitle);
        TextView tvOperationLeft = holder.getView(R.id.tvOperation_left);

        if (data.addrInfo != null) {
            tvName.setText(data.addrInfo.name);
            tvPhone.setText(data.addrInfo.phone);
            tvAddr.setText(data.addrInfo.addr + data.addrInfo.addrDetail);
        }

        tvTitle.setText(data.name);
        tvNo.setText(data.no);
        tvNum.setText("共" + data.num + "件");
        tvTime.setText(getTime(data.time));


        iv1.setVisibility(View.GONE);
        iv2.setVisibility(View.GONE);
        iv3.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);

        if (data.imgs != null && data.imgs.size() > 0) {
            int size = data.imgs.size();
            if (size >= 1) {
                Glide.with(MyApplication.context).load(data.imgs.get(0).img).into(iv1);
                iv1.setVisibility(View.VISIBLE);
            }
            if (size >= 2) {
                Glide.with(MyApplication.context).load(data.imgs.get(1).img).into(iv2);
                iv2.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.GONE);
            }
            if (size >= 3) {
                Glide.with(MyApplication.context).load(data.imgs.get(2).img).into(iv3);
                iv3.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.GONE);
            }
        }

        if (index == 0) {
            tvOperationLeft.setVisibility(View.GONE);

        } else if (index == 1) {
            tvOperationLeft.setVisibility(View.GONE);
            tvOperation.setVisibility(View.GONE);
        } else if (index == 2) {
            tvOperationLeft.setVisibility(View.GONE);
            tvOperation.setText("查看物流");
            tvOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.goWebActivityForFlow(data.flowNo);
                }
            });
        }else if(index==3){
            tvOperationLeft.setText("手动录入");
            tvOperation.setText("扫描录入");
            tvOperationLeft.setVisibility(View.VISIBLE);
        }else if(index==4){
            tvOperationLeft.setText("更改物流");
            tvOperation.setText("物流记录");
            tvOperationLeft.setVisibility(View.VISIBLE);
        }

        tvOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==0) {
                    new AuditDialog(activity, new AuditDialog.OnSelectListener() {
                        @Override
                        public void select(final boolean pass) {

                            Map<String, Object> map = BaseSoure.getMap();
                            map.put("type", pass ? 1 : 2);
                            map.put("no", data.no);
                            new RxManager().getData(RetrofitUtil.getInstance().service.auditOrder(map), new Observer<NetModel>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtils.showHttpError();
                                }

                                @Override
                                public void onNext(NetModel model) {
//                                    tvOperation.setText(pass ? "通过" : "不通过");
//                                    tvOperation.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                                    tvOperation.setEnabled(false);
//                                    notifyDataSetChanged();
                                    RxBus.getDefault().post(new RefreshAuditEvent());
                                }
                            });
                        }
                    }).show();
                }else if(index==2){
                    AppUtils.goWebActivityForFlow(data.flowNo);
                }else if(index==3){
                    Intent intent = new Intent(activity, CaptureActivity.class);
                    intent.putExtra(CaptureActivity.TYPE, CaptureActivity.TYPE_EXPRESS_DELIVERY);
                    intent.putExtra("pid",data.pid);
                    activity.startActivityForResult(intent, REQ_CAPTURE_EXPRESS_DELIVERY);
                }else if(index==4){
                    AppUtils.goWebActivityForFlow(data.flowNo);
                }
            }
        });

        tvOperationLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index==3){
                    new ManualInputDialog(activity, data.pid, new ManualInputDialog.CallBack() {
                        @Override
                        public void afrim(String num) {
//                            Toast.makeText(activity,"录入成功",Toast.LENGTH_SHORT).show();
                        }
                    }).show();

                }else if(index==4){
                    Intent intent = new Intent(activity, ChangeLogisticsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",data);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            }
        });

    }

    private String getTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return sdf.format(calendar.getTime());
    }
}
