package com.jusfoun.ui.holder;

import android.view.View;

import butterknife.ButterKnife;

/**
 * 说明
 *
 * @时间 2017/8/8
 * @作者 LiuGuangDan
 */

public abstract class BaseViewHolder<T> extends com.chad.library.adapter.base.BaseViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void updateView(int position, T data);
}