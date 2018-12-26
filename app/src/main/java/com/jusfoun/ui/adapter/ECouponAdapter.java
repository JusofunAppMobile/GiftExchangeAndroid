package com.jusfoun.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ECouponModel;
import com.jusfoun.utils.AppUtils;

/**
 * 我的电子券 adapter
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class ECouponAdapter extends BaseQuickAdapter<ECouponModel, com.chad.library.adapter.base.BaseViewHolder> {

    public ECouponAdapter() {
        super(R.layout.item_ecoupon);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder holder, ECouponModel data) {
        holder.setText(R.id.tvName, data.cardName);
        holder.setText(R.id.tvNo, AppUtils.formatCardNo(data.cardNo));

        ImageView iv = holder.getView(R.id.vStatus);
        //  0：未兑换 1：已兑换 2：已过期
        if (data.status == 2) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(R.drawable.img_guoqi);
        } else if (data.status == 0) {
            iv.setVisibility(View.GONE);
        } else {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(R.drawable.img_xj);
        }
    }
}
