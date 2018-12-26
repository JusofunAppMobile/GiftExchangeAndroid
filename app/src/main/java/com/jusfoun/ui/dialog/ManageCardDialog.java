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
import com.jusfoun.constants.CommonConstant;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.BankModel;
import com.jusfoun.model.NetModel;
import com.jusfoun.model.ReceiveUserModel;
import com.jusfoun.mvp.contract.ReceiveUserContract;
import com.jusfoun.mvp.presenter.ReceiveUserPresenter;
import com.jusfoun.retrofit.RetrofitUtil;
import com.jusfoun.retrofit.RxManager;
import com.jusfoun.ui.adapter.ReceiveUserAdapter;
import com.jusfoun.utils.AppUtils;
import com.jusfoun.utils.PreferenceUtils;
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
 * 管理员给银行业务员发卡
 *
 * @时间 2017/8/15
 * @作者 LiuGuangDan
 */

public class ManageCardDialog extends BaseDialog implements ReceiveUserContract.IView {

    @BindView(R.id.etNum)
    EditText etNum;
    @BindView(R.id.tvReceiveUser)
    TextView tvReceiveUser;

    @BindView(R.id.recycle)
    LRecyclerView recycleview;

    private OnSelectListener listener;
    private ReceiveUserAdapter adapter;

    private LRecyclerViewAdapter recycleAdapter;

    private ReceiveUserPresenter presenter;

    private ReceiveUserModel receiveUserModel;

    private int type;

    private int maxNum;

    public ManageCardDialog(Activity activity,int maxNum, OnSelectListener listener) {
        super(activity);
        setContentView(R.layout.dialog_manage_card);
        ButterKnife.bind(this);
        setWindowStyle(8);
        this.listener = listener;
        this.maxNum = maxNum;

        type = PreferenceUtils.getInt(activity, PreferenceConstant.TYPE, CommonConstant.TYPE_USER);

        adapter = new ReceiveUserAdapter();
        recycleAdapter = new LRecyclerViewAdapter(adapter);
        recycleview.setLayoutManager(new LinearLayoutManager(activity));
        recycleview.setAdapter(recycleAdapter);
        recycleview.setPullRefreshEnabled(false);
        recycleview.setLoadMoreEnabled(false);


        if(type == CommonConstant.TYPE_MANAGER_BANK) {
            loadBankData();
        }else{
            presenter = new ReceiveUserPresenter(this);
            presenter.loadData(1, 10);
        }

        recycleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                receiveUserModel = adapter.getData(position);
                recycleview.setVisibility(View.GONE);
                tvReceiveUser.setText(receiveUserModel.name);
            }
        });
    }

    private void loadBankData() {
        Map<String, Object> map = getMap();
        map.put("type", 1);

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
                if (model.success()) {
                    JSONObject obj = model.getDataJSONObject();
                    List<BankModel> list = (List<BankModel>) AppUtils.getList(obj, "list", BankModel.class);
                    if (list != null && !list.isEmpty()) {

                        BankModel mo = list.get(0);
                        if (mo.userList != null && !mo.userList.isEmpty()) {
                            List<ReceiveUserModel> rList = new ArrayList<>();
                            for (BankModel.UserListBean ub : mo.userList) {
                                rList.add(new ReceiveUserModel(ub.pid, ub.name));
                            }
                            adapter.setNewDatas(rList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    ToastUtils.show(model.msg);
                }
            }
        });
    }

    @OnClick({R.id.vCancel, R.id.vSure, R.id.vSelectUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:

                if (receiveUserModel == null) {
                    ToastUtils.show("请选择接收用户");
                    return;
                }

                if (TextUtils.isEmpty(etNum.getText().toString().trim())) {
                    ToastUtils.show("请输入卡券数量");
                    return;
                }
                int num = Integer.parseInt(etNum.getText().toString().trim());
                if(num > maxNum){
                   ToastUtils.show("卡数量不足");
                    return;
                }
                if (listener != null) {
                    listener.select(receiveUserModel, num);
                }
                dismiss();
                break;
            case R.id.vSelectUser:
                recycleview.setVisibility(recycleview.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
        }
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void suc(List<ReceiveUserModel> list) {
        adapter.addDatas(list);
        recycleAdapter.notifyDataSetChanged();
    }

    public interface OnSelectListener {
        void select(ReceiveUserModel receiveUserModel, int num);
    }
}
