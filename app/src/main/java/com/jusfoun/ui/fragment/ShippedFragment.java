//package com.jusfoun.ui.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.github.jdsjlzx.interfaces.OnItemClickListener;
//import com.jusfoun.giftexchange.R;
//import com.jusfoun.model.GiftModel;
//import com.jusfoun.mvp.contract.GiftContract;
//import com.jusfoun.mvp.presenter.GiftPresenter;
//import com.jusfoun.ui.adapter.BaseAdapter;
//import com.jusfoun.ui.adapter.GiftAdapter;
//import com.jusfoun.utils.AppUtils;
//
///**
// * @author zhaoyapeng
// * @version create time:17/8/1414:27
// * @Email zyp@jusfoun.com
// * @Description ${已发货页面}
// */
//
//public class ShippedFragment extends BaseListFragment implements GiftContract.IView{
//
//    private GiftAdapter adapter;
//
//    private GiftPresenter presenter;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        presenter = new GiftPresenter(this);
//    }
//
//
//    @Override
//    public BaseAdapter getAdapter() {
//        return adapter = new GiftAdapter();
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.frag_common_list;
//    }
//
//    @Override
//    public void loadData() {
//
//        presenter.loadData("");
//
//        recycleAdapter.addHeaderView(View.inflate(activity, R.layout.view_gift_header, null));
//
//        recycleview.setLoadMoreEnabled(false);
//        recycleview.setPullRefreshEnabled(false);
//
//        recycleAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                AppUtils.goWebActivityForGift(adapter.getData(position).pid);
//            }
//        });
//    }
//
//    @Override
//    public RecyclerView.LayoutManager getLayoutManager() {
//        return new LinearLayoutManager(activity);
//    }
//
//    @Override
//    public void suc(GiftModel mode) {
//        adapter.addDatas(mode.list);
//        notifyDataSetChanged();
//    }
//}
