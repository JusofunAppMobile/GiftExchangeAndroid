package com.jusfoun.ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.jusfoun.app.MyApplication;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.ui.activity.AddressActivity;
import com.jusfoun.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 礼品兑换记录 Adapter
 *
 * @时间 2017/8/10
 * @作者 LiuGuangDan
 */

public class ExchangeRecordAdapter extends BaseQuickAdapter<ExchangeRecordModel, BaseViewHolder> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ExchangeRecordAdapter() {
        super(R.layout.item_exchagne_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ExchangeRecordModel data) {

        TextView tvNo = helper.getView(R.id.tvNo);
        TextView tvTime = helper.getView(R.id.tvTime);
        ImageView iv1 = helper.getView(R.id.iv1);
        ImageView iv2 = helper.getView(R.id.iv2);
        ImageView iv3 = helper.getView(R.id.iv3);
        TextView tvTitle = helper.getView(R.id.tvTitle);
        TextView tvType = helper.getView(R.id.tvType);
        TextView tvNum = helper.getView(R.id.tvNum);
        TextView tvStatus = helper.getView(R.id.tvStatus);
        TextView tvOperation = helper.getView(R.id.tvOperation);
        View vDetail = helper.getView(R.id.vDetail);

        tvNo.setText(data.no);
        tvNum.setText("共" + data.num + "个奖品");
        tvTime.setText(getTime(data.time));
        tvTitle.setText(data.name);
        tvType.setText(data.cardName + "X1");

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
        setStatusText(tvStatus, tvOperation, data.status, data);

        vDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.goWebActivityForGift(data.detailUrl);
            }
        });
    }


    /**
     * // "status":0 // 状态  0：待审核 1：审核通过，待发货 2：审核不通过  3：已发货 4：已完成
     *
     * @param text
     * @param tvOperation
     * @param status
     */
    private void setStatusText(TextView text, TextView tvOperation, int status, ExchangeRecordModel data) {
        switch (status) {
            case 0:
                text.setText("待审核");
                text.setTextColor(Color.parseColor("#FF772F"));
                setOperationView(tvOperation, 1, data);
                break;
            case 1:
                text.setText("审核通过 待发货");
                text.setTextColor(Color.parseColor("#0093F7"));
                setOperationView(tvOperation, 1, data);
                break;
            case 2:
                text.setText("审核不通过");
                text.setTextColor(Color.parseColor("#0093F7"));
                tvOperation.setVisibility(View.GONE);
                break;
            case 3:
            case 4:
                text.setText("");
                setOperationView(tvOperation, 2, data);
                break;
        }
    }

    private void setOperationView(TextView textView, int type, final ExchangeRecordModel data) {
        if (type == 1) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("更改地址");
            textView.setTextColor(Color.parseColor("#FF772F"));
            textView.setBackgroundResource(R.drawable.shape_stroke_red);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.context, AddressActivity.class);
                    intent.putExtra("bean", new Gson().toJson(data.addrInfo));
                    intent.putExtra("id", data.pid);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.context.startActivity(intent);
                }
            });
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("查看物流");
            textView.setTextColor(Color.parseColor("#0093F7"));
            textView.setBackgroundResource(R.drawable.shape_stroke_blue);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.goWebActivityForFlow(data.flowNo);
                }
            });
        }
    }

    private String getTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return sdf.format(calendar.getTime());
    }
}
