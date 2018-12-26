package com.jusfoun.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jusfoun.app.MyApplication;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.GiftModel;
import com.jusfoun.utils.AppUtils;

/**
 * 说明
 *
 * @时间 2017/8/8
 * @作者 LiuGuangDan
 */

public class GiftAdapter extends BaseQuickAdapter<GiftModel.GiftBean, BaseViewHolder> {

    public GiftAdapter() {
        super(R.layout.item_gift);
    }

    @Override
    protected void convert(BaseViewHolder holder, final GiftModel.GiftBean data) {
        ImageView imageView = holder.getView(R.id.iv);
        ImageView ivSelect = holder.getView(R.id.ivSelect);
        View vRoot = holder.getView(R.id.vRoot);

        Glide.with(MyApplication.context).load(data.img).into(imageView);

        holder.setText(R.id.tvTitle, data.name);
        if (data.isSelect) {
            ivSelect.setVisibility(View.VISIBLE);
            vRoot.setBackgroundColor(Color.parseColor("#F6F4F3"));
        } else {
            ivSelect.setVisibility(View.GONE);
            vRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.goWebActivityForGift(data.url);
            }
        });
    }
}
