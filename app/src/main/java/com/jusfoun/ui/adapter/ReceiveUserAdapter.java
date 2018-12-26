package com.jusfoun.ui.adapter;

import android.view.ViewGroup;

import com.jusfoun.giftexchange.R;
import com.jusfoun.model.ReceiveUserModel;
import com.jusfoun.ui.holder.BaseViewHolder;
import com.jusfoun.ui.holder.ReceiveUserHolder;

/**
 *  接收用户 adapter
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class ReceiveUserAdapter extends BaseAdapter<ReceiveUserModel> {

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ReceiveUserHolder(getView());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_receive_user;
    }
}
