package com.jusfoun.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jusfoun.app.MyApplication;
import com.jusfoun.ui.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明
 *
 * @时间 2017/8/8
 * @作者 LiuGuangDan
 */

public abstract class BaseAdapter<T>  extends RecyclerView.Adapter {

    private List<T> mDatas = new ArrayList<>();

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

    protected abstract  int getItemLayoutId();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder viewHolder = (BaseViewHolder) holder;
        viewHolder.updateView(position, mDatas.get(position));
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public T getData(int position) {
        return mDatas.get(position);
    }

    public void addDatas(List<T> datas) {
        mDatas.addAll(datas);
    }

    public void clear() {
        mDatas.clear();
    }

    public void addData(T data) {
        mDatas.add(data);
    }

    public void setNewDatas(List<T> datas){
        mDatas.clear();
        mDatas.addAll(datas);
    }

    public void addData(int index, T data) {
        mDatas.add(index, data);
    }

    public int getDataIndex(T data) {
        return mDatas.indexOf(data);
    }

    public boolean deleteData(T data) {
        return mDatas.remove(data);
    }

    public void clearDatas() {
        mDatas.clear();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    protected View getView() {
        return View.inflate(MyApplication.context, getItemLayoutId(), null);
    }
}
