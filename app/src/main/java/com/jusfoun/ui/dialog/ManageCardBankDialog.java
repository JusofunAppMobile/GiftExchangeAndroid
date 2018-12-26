package com.jusfoun.ui.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.BankModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.model.ReceiveUserModel;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.ui.adapter.ReceiveUserAdapter;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.jusfoun.mvp.source.BaseSoure.getMap;

/**
 * 管理员给银行管理员发卡
 *
 * @时间 2017/8/15
 * @作者 LiuGuangDan
 */

public class ManageCardBankDialog extends BaseDialog {

    @BindView(R.id.etNum)
    EditText etNum;
    @BindView(R.id.tvReceiveUser)
    TextView tvReceiveUser;
    @BindView(R.id.tvReceiveUser2)
    TextView tvReceiveUser2;

    @BindView(R.id.recycle)
    LRecyclerView recycleview;
    @BindView(R.id.recycle2)
    LRecyclerView recycleview2;

    private OnSelectListener listener;
    private ReceiveUserAdapter adapter;
    private ReceiveUserAdapter adapter2;

    private LRecyclerViewAdapter recycleAdapter;
    private LRecyclerViewAdapter recycleAdapter2;

    private ReceiveUserModel receiveUserModel, receiveUserModel2;

    private List<BankModel> bankList = new ArrayList<>();
    private String cardTypeId, cardName;

    public ManageCardBankDialog(Activity activity, String cardTypeId, String cardName, OnSelectListener listener) {
        super(activity);
        setContentView(R.layout.dialog_manage_card_bank);
        ButterKnife.bind(this);
        setWindowStyle(8);
        this.listener = listener;
        this.cardTypeId = cardTypeId;
        this.cardName = cardName;

        adapter = new ReceiveUserAdapter();
        recycleAdapter = new LRecyclerViewAdapter(adapter);
        recycleview.setLayoutManager(new LinearLayoutManager(activity));
        recycleview.setAdapter(recycleAdapter);
        recycleview.setPullRefreshEnabled(false);
        recycleview.setLoadMoreEnabled(false);

        recycleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                receiveUserModel = adapter.getData(position);
                recycleview.setVisibility(View.GONE);
                tvReceiveUser.setText(receiveUserModel.name);

                List<BankModel.UserListBean> list = bankList.get(position).userList;
                if (list != null && !list.isEmpty()) {
                    List<ReceiveUserModel> rList = new ArrayList<>();
                    for (BankModel.UserListBean bean : list) {
                        rList.add(new ReceiveUserModel(bean.pid, bean.name));
                    }
                    adapter2.setNewDatas(rList);
                } else {
                    adapter2.clear();
                }
                adapter2.notifyDataSetChanged();

                receiveUserModel2 = null;
                tvReceiveUser2.setText(null);
            }
        });

        adapter2 = new ReceiveUserAdapter();
        recycleAdapter2 = new LRecyclerViewAdapter(adapter2);
        recycleview2.setLayoutManager(new LinearLayoutManager(activity));
        recycleview2.setAdapter(recycleAdapter2);
        recycleview2.setPullRefreshEnabled(false);
        recycleview2.setLoadMoreEnabled(false);

        recycleAdapter2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                receiveUserModel2 = adapter2.getData(position);
                recycleview2.setVisibility(View.GONE);
                tvReceiveUser2.setText(receiveUserModel2.name);
            }
        });

        loadBankData();
    }

    private void loadBankData() {
        Map<String, Object> map = getMap();
        map.put("type", 0);

        RxManager rxManager = new RxManager();
        rxManager.getData(RetrofitUtil.getInstance().service.getBankUser(map), new Observer<NetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showHttpError();
            }

            @Override
            public void onNext(NetModel model) {
//                if (callBack != null)
//                    callBack.onSuccess(model);
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<BankModel> list = (List<BankModel>) AppUtils.getList(obj, "list", BankModel.class);
                    if (list != null && !list.isEmpty()) {
                        bankList.addAll(list);
                        List<ReceiveUserModel> rList = new ArrayList<>();
                        for (BankModel ba : bankList) {
                            rList.add(new ReceiveUserModel(ba.pid, ba.name));
                        }
                        adapter.setNewDatas(rList);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.show(model.msg);
                }
            }
        });
    }

    @OnClick({R.id.vCancel, R.id.vSure, R.id.vSelectUser, R.id.vSelectUser2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:
                if (receiveUserModel == null) {
                    ToastUtils.show("请选择银行");
                    return;
                }

                if (receiveUserModel2 == null) {
                    ToastUtils.show("请选择银行管理员");
                    return;
                }

                if (TextUtils.isEmpty(etNum.getText().toString().trim())) {
                    ToastUtils.show("请输入卡券数量");
                    return;
                }
                final int num = Integer.parseInt(etNum.getText().toString().trim());

                new TipDialog(activity, new TipDialog.OnSelectListener() {
                    @Override
                    public void select() {
                        Map<String, Object> map = getMap();
                        map.put("cardTypeId", cardTypeId);
                        map.put("bankId", receiveUserModel.pid);
                        map.put("pid", receiveUserModel2.pid);
                        map.put("num", num);

                        RxManager rxManager = new RxManager();
                        rxManager.getData(RetrofitUtil.getInstance().service.sendTicketToBankAdminUser(map), new Observer<NetModel>() {
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
                                    dismiss();
                                    ToastUtils.show("发卡成功");
                                } else {
                                    ToastUtils.show(model.msg);
                                }
                            }
                        });
                    }
                }, "确认向" + receiveUserModel.name + "-" + receiveUserModel2.name + "下发" + cardName + num + "张？")
                .show();
                break;
            case R.id.vSelectUser:
                recycleview.setVisibility(recycleview.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                recycleview2.setVisibility(View.GONE);
                break;
            case R.id.vSelectUser2:
                if (receiveUserModel == null) {
                    ToastUtils.show("请选择银行");
                    return;
                }
                recycleview.setVisibility(View.GONE);
                recycleview2.setVisibility(recycleview2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
        }
    }

    public interface OnSelectListener {
        void select(ReceiveUserModel receiveUserModel, int num);
    }
}
