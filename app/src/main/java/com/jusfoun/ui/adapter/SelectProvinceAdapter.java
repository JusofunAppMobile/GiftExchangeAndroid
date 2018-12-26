package com.jusfoun.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.giftexchange.R;

/**
 * 选择收货地址 adapter
 *
 * @时间 2017/8/11
 * @作者 LiuGuangDan
 */

public class SelectProvinceAdapter extends BaseQuickAdapter<String, com.chad.library.adapter.base.BaseViewHolder> {

    public SelectProvinceAdapter() {
        super(R.layout.item_select_province);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, String item) {
        helper.setText(R.id.tvName, item);
    }
}
