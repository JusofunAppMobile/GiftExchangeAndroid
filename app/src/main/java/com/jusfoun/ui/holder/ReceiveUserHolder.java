package com.jusfoun.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ReceiveUserModel;

import butterknife.BindView;

/**
 * 接收用户 ViewHolder
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class ReceiveUserHolder extends BaseViewHolder<ReceiveUserModel> {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.vLine)
    View vLine;

    public ReceiveUserHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void updateView(int position, ReceiveUserModel data) {
        tvName.setText(data.name);
        vLine.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
    }

}
