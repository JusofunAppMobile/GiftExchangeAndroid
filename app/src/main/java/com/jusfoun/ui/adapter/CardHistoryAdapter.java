package com.jusfoun.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.app.MyApplication;
import com.jusfoun.constants.CommonConstant;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.CardHistoryModel;
import com.jusfoun.utils.DateUtils;
import com.jusfoun.utils.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.jusfoun.giftexchange.R.id.tvName;

/**
 * 管理员发卡历史 adapter
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class CardHistoryAdapter extends BaseQuickAdapter<CardHistoryModel, com.chad.library.adapter.base.BaseViewHolder> {

    private int type;

    public CardHistoryAdapter() {
        super(R.layout.item_card_history);
        type = PreferenceUtils.getInt(MyApplication.context, PreferenceConstant.TYPE, CommonConstant.TYPE_USER);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder holder, CardHistoryModel data) {
        if (type == CommonConstant.TYPE_MANAGER)
            holder.setText(tvName, data.bankName + "-" + data.name);
        else
            holder.setText(tvName, data.name);
        holder.setText(R.id.tvTypeName, data.cardName);
        holder.setText(R.id.tvNum, data.num + "张");
        holder.setText(R.id.tvTime, getTime(data.time));
    }

    private SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.TYPE_YYYY_MM_DD_HH_MM);
//    private SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.TYPE_YYYY_MM_DD_HH_MM_SS);

    private String getTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return sdf.format(calendar.getTime());
    }
}
