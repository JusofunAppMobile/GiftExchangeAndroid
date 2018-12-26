package com.jusfoun.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jusfoun.giftexchange.R;

import java.util.ArrayList;
import java.util.List;

public class TabHostManager {

    private FragmentActivity fragmentActivity;
    private Fragment fragment;
    private int tabLayoutId;
    protected FragmentTabHost mTabHost;
    private List<String> tagList = new ArrayList<>();
    private List<View> tabViewList = new ArrayList<>();
    private OnTabChangeListener listener;

    /**
     * @param fragmentActivity
     * @param tabHost
     * @param tabLayoutId      选项卡布局文件id
     */
    public TabHostManager(FragmentActivity fragmentActivity, FragmentTabHost tabHost, int tabLayoutId) {
        this.fragmentActivity = fragmentActivity;
        this.mTabHost = tabHost;
        this.tabLayoutId = tabLayoutId;
        init();
    }

    /**
     * @param fragment
     * @param tabHost
     * @param tabLayoutId 选项卡布局文件id
     */
    public TabHostManager(Fragment fragment, FragmentTabHost tabHost, int tabLayoutId) {
        this.fragment = fragment;
        this.mTabHost = tabHost;
        this.tabLayoutId = tabLayoutId;
        init();
    }

    private void init() {
        mTabHost.setup(getContext(), fragmentActivity != null ? fragmentActivity.getSupportFragmentManager() : fragment.getChildFragmentManager(), android.R.id.tabcontent);
        // 设置无分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabName) {
                if (listener != null) {
                    for (int i = 0; i < tagList.size(); i++) {
                        if (!TextUtils.isEmpty(tagList.get(i)) && tagList.get(i).equals(tabName)) {
                            listener.onTabChanged(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    public Fragment getFragment(int position) {
        return fragmentActivity != null ? fragmentActivity.getSupportFragmentManager().findFragmentByTag(tagList.get(position)) : fragment.getChildFragmentManager().findFragmentByTag(tagList.get(position));
    }

    /**
     * @param fragment
     * @param tabName       每个标签的文本内容， id为textview
     * @param tabResid      每个标签的图片控件资源， id为imageview
     * @param tabBackground 每个控件的背景，可以区分选中、未选中状态
     * @param bundle        传递参数对象
     */
    public void addFragment(Class<?> fragment, String tabName, int tabResid, int tabBackground, Bundle bundle) {
        if (TextUtils.isEmpty(tabName))
            tabName = "#" + tagList.size();
        tagList.add(tabName);
        addTab(new TabHostHolder(fragment, tabName, tabResid, tabBackground, bundle));
    }

    /**
     * @param fragment
     * @param tabName       每个标签的文本内容， id为textview
     * @param tabResid      每个标签的图片控件资源， id为imageview
     * @param tabBackground 每个控件的背景，可以区分选中、未选中状态
     */
    public void addFragment(Class<?> fragment, String tabName, int tabResid, int tabBackground) {
        addFragment(fragment, tabName, tabResid, tabBackground, null);
    }

    /**
     * @param fragment
     * @param tabName  每个标签的文本内容， id为textview
     * @param tabResid 每个标签的图片控件资源， id为imageview
     */
    public void addFragment(Class<?> fragment, String tabName, int tabResid) {
        addFragment(fragment, tabName, tabResid, 0, null);
    }

    public void setOnTabChangeListener(OnTabChangeListener listener) {
        this.listener = listener;
    }

    private Context getContext() {
        return fragmentActivity != null ? fragmentActivity : fragment.getActivity();
    }

    private void addTab(TabHostHolder holder) {
        View view = getTabItemView(holder.tabName, holder.tabResid, holder.tabBackground);
        tabViewList.add(view);
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(holder.tabName).setIndicator(view);
        // 将Tab按钮添加进Tab选项卡中
        mTabHost.addTab(tabSpec, holder.fragment, holder.bundle);
    }

    /**
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(String name, int resid, int backgroudId) {
        View view = View.inflate(getContext(), tabLayoutId, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        if (imageView != null && resid != 0)
            imageView.setImageResource(resid);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        if (textView != null && !name.startsWith("#"))
            textView.setText(name);
        if (backgroudId != 0)
            view.setBackgroundResource(backgroudId);
        return view;
    }

    private class TabHostHolder {

        public Class<?> fragment;
        public String tabName;
        public int tabResid;
        public int tabBackground;
        public Bundle bundle;

        public TabHostHolder(Class<?> fragment, String tabName, int tabResid, int tabBackground, Bundle bundle) {
            this.fragment = fragment;
            this.tabName = tabName;
            this.tabResid = tabResid;
            this.tabBackground = tabBackground;
            this.bundle = bundle;
        }
    }

    public interface OnTabChangeListener {
        void onTabChanged(int position);
    }

    public View getTabIndicatorView(int position) {
        return tabViewList.get(position);
    }
}
