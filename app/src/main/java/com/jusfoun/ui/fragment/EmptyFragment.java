package com.jusfoun.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 礼品兑换空的Fragmeng
 *
 * @时间 2017/8/11
 * @作者 LiuGuangDan
 */

public class EmptyFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null)
            rootView = new TextView(activity);
        return rootView;
    }
}
