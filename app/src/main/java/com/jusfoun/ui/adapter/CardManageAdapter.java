package com.jusfoun.ui.adapter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.CardManageModel;
import com.jusfoun.ui.dialog.ManageCardBankDialog;

/**
 * 管理员发卡历史 adapter
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class CardManageAdapter extends BaseQuickAdapter<CardManageModel, com.chad.library.adapter.base.BaseViewHolder> {

    private Activity activity;

    public CardManageAdapter(Activity activity) {
        super(R.layout.item_card_manage);
        this.activity = activity;
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder holder, final CardManageModel data) {
        holder.setText(R.id.tvTypeName, data.cardName);
        holder.getView(R.id.vSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ManageCardDialog(activity, new ManageCardDialog.OnSelectListener() {
//                    public void select(final ReceiveUserModel receiveUserModel, final int num) {
//                        new TipDialog(activity, new TipDialog.OnSelectListener() {
//                            @Override
//                            public void select() {
//                                Map<String, Object> map = BaseSoure.getMap();
//                                map.put("cardTypeId", data.pid);
//                                map.put("pid", receiveUserModel.pid);
//                                map.put("num", num);
//                                new RxManager().getData(RetrofitUtil.getInstance().service.sendCardToCustomer(map), new Observer<NetModel>() {
//                                    @Override
//                                    public void onCompleted() {
//
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        ToastUtils.showHttpError();
//                                    }
//
//                                    @Override
//                                    public void onNext(NetModel model) {
//                                        if (model.success()) {
//                                            ToastUtils.show("发卡成功");
//                                        } else {
//                                            ToastUtils.show(model.msg);
//                                        }
//                                    }
//                                });
//                            }
//                        }, "确认向" + receiveUserModel.name + "下发" + data.cardName + num + "张？").show();
//                    }
//                }).show();

                new ManageCardBankDialog(activity,data.pid, data.cardName, null).show();
            }
        });
    }
}
