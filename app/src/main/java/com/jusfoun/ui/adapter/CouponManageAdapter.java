package com.jusfoun.ui.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusfoun.constants.CommonConstant;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.event.RefreshEcouponManageEvent;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.CouponManageModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.model.QrcodeModel;
import com.jusfoun.model.ReceiveUserModel;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxBus;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.ui.dialog.ManageCardDialog;
import com.jusfoun.ui.dialog.QrcodeDialog;
import com.jusfoun.ui.dialog.SendCardDialog;
import com.jusfoun.ui.dialog.TipDialog;
import com.jusfoun.utils.PreferenceUtils;
import com.jusfoun.utils.ToastUtils;

import java.util.Map;

import rx.Observer;

import static com.jusfoun.mvp.source.BaseSoure.getMap;

/**
 * 电子券管理 adapter
 *
 * @时间 2017/8/29
 * @作者 LiuGuangDan
 */

public class CouponManageAdapter extends BaseQuickAdapter<CouponManageModel, com.chad.library.adapter.base.BaseViewHolder> {

    private Activity activity;
    private int type;

    public CouponManageAdapter(Activity activity) {
        super(R.layout.item_ecoupon_manage);
        this.activity = activity;
        type = PreferenceUtils.getInt(activity, PreferenceConstant.TYPE, CommonConstant.TYPE_USER);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder holder, final CouponManageModel data) {

        holder.setText(R.id.tvName, data.cardName);
        holder.setText(R.id.tvTotalNum, Html.fromHtml("全部卡券：<font color=#7C7974>" + data.totalNum + "</font>"));
        holder.setText(R.id.tvRemainNum, Html.fromHtml("剩余卡券：<font color=#7C7974>" + data.remainNum + "</font>"));

        holder.getView(R.id.vSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == CommonConstant.TYPE_MANAGER_BANK) {
                    new ManageCardDialog(activity, data.remainNum, new ManageCardDialog.OnSelectListener() {
                        @Override
                        public void select(final ReceiveUserModel receiveUserModel, final int num) {
                            new TipDialog(activity, new TipDialog.OnSelectListener() {
                                @Override
                                public void select() {
                                    Map<String, Object> map = getMap();
                                    map.put("cardTypeId", data.pid);
                                    map.put("pid", receiveUserModel.pid);
                                    map.put("num", num);

                                    RxManager rxManager = new RxManager();
                                    rxManager.getData(RetrofitUtil.getInstance().service.sendTicketToBankUser2(map), new Observer<NetModel>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            ToastUtils.showHttpError();
                                        }

                                        @Override
                                        public void onNext(NetModel model) {
                                            if (model.success()) {
                                                ToastUtils.show("发卡成功");
                                                RxBus.getDefault().post(new RefreshEcouponManageEvent());
                                            } else {
                                                ToastUtils.show(model.msg);
                                            }
                                        }
                                    });
                                }
                            }, "确认向" + receiveUserModel.name + "下发" + data.cardName + num + "张？")
                                    .show();
                        }
                    }).show();
                } else {
                    new SendCardDialog(activity, new SendCardDialog.OnSelectListener() {
                        @Override
                        public void select(final String name, final String phone) {
                            new TipDialog(activity, new TipDialog.OnSelectListener() {
                                @Override
                                public void select() {
                                    Map<String, Object> map = getMap();
                                    map.put("pid", data.pid);
                                    map.put("phone", phone);
                                    map.put("name", name);
                                    new RxManager().getData(RetrofitUtil.getInstance().service.sendCard(map), new Observer<NetModel>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            ToastUtils.showHttpError();
                                        }

                                        @Override
                                        public void onNext(NetModel model) {
                                            if (model.success()) {
                                                ToastUtils.show("发卡成功");
                                                RxBus.getDefault().post(new RefreshEcouponManageEvent());
                                                QrcodeModel qrcodeModel = model.fromData(QrcodeModel.class);
                                                if (qrcodeModel != null) {
                                                    new QrcodeDialog(activity, qrcodeModel.link).show();
                                                }
                                            } else {
                                                ToastUtils.show(model.msg);
                                            }
                                        }
                                    });
                                }
                            }, "确认向" + phone + "（" + name + "）" + "赠送" + data.cardName + "？").show();
                        }
                    }).show();
                }
            }
        });
    }
}
