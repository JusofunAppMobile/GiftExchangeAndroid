package com.jusfoun.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jusfoun.giftexchange.R;
import com.jusfoun.model.GiftModel;
import com.jusfoun.mvp.contract.GiftContract;
import com.jusfoun.mvp.presenter.GiftPresenter;
import com.jusfoun.ui.adapter.FragmentAdapter;
import com.jusfoun.ui.widget.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 礼品列表页面
 *
 * @时间 2017/8/7
 * @作者 LiuGuangDan
 */

public class GiftListFragment extends BaseFragment implements GiftContract.IView {

    @BindView(R.id.vTab)
    PagerSlidingTabStrip slidingTabStrip;
    @BindView(R.id.vp)
    ViewPager viewPager;

    private GiftPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = View.inflate(activity, R.layout.frag_giftlist, null);
            ButterKnife.bind(this, rootView);
            presenter = new GiftPresenter(this);
            presenter.loadData(String.valueOf(0));
        }
        return rootView;
    }

    @Override
    public void suc(final GiftModel mode) {
        if (mode != null && mode.cardTypeList != null && !mode.cardTypeList.isEmpty()) {

            FragmentAdapter adapter = new FragmentAdapter(((FragmentActivity) activity).getSupportFragmentManager()) {
                @Override
                public CharSequence getPageTitle(int position) {
                    return mode.cardTypeList.get(position).name;
                }
            };

            for (int i = 0; i < mode.cardTypeList.size(); i++) {
                GiftModel.CardTypeListBean bean = mode.cardTypeList.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("id", bean.pid);
                if (i == 0)
                    bundle.putString("bean", new Gson().toJson(mode));
                adapter.addFragment(GiftSubListFragment.class, bundle);
            }


            viewPager.setAdapter(adapter);


            slidingTabStrip.setIndicatorColor(getResources().getColor(R.color.common));// 滑动条的颜色
            slidingTabStrip.setIndicatorHeight(6);// 滑动条的高度
            slidingTabStrip.setUnderlineColor(Color.TRANSPARENT); // 滑动条下面的整条的颜色
            slidingTabStrip.setDividerColor(Color.TRANSPARENT); // 设置标签之间的间隔颜色为透明
//            slidingTabStrip.setTabBackground(R.drawable.selector_common_list); //  每个标签的背景
            slidingTabStrip.setShouldExpand(true); //  如果设置为true，每个标签是相同的控件，均匀平分整个屏幕，默认是false
            slidingTabStrip.setTextColor(R.drawable.selector_tab_textcolor);
            slidingTabStrip.setTextSize(R.dimen.tab_text_size, R.dimen.tab_text_size_select);
            slidingTabStrip.setTabPaddingLeftRight(30); // 标签左右边距

            slidingTabStrip.setViewPager(viewPager);
        }
    }

    @Override
    public void error() {

    }
}
