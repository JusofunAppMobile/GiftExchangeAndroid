package com.jusfoun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.jusfoun.model.ProvinceModel;
import com.jusfoun.ui.adapter.SelectProvinceAdapter;
import com.jusfoun.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择收货地址 页面
 *
 * @时间 2017/8/11
 * @作者 LiuGuangDan
 */

public class SelectProvinceActivity extends BaseListActivity {

    private List<ProvinceModel> list = new ArrayList<>();

    private ProvinceModel provinceModel;

    private ProvinceModel.ChildrenBeanX cityModel;

    @Override
    public BaseQuickAdapter<String, BaseViewHolder> getAdapter() {
        return adapter = new SelectProvinceAdapter();
    }

    @Override
    protected void initUi() {
        setTitle("选择地区");
        initJsonData();
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    protected void startLoadData() {
        completeLoadData(getList());
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        if (provinceModel == null) {
            provinceModel = list.get(position);

            List<String> strList = new ArrayList<>();
            for (ProvinceModel.ChildrenBeanX child : provinceModel.children) {
                strList.add(child.name);
            }
            adapter.getData().clear();
            adapter.getData().addAll(strList);
            adapter.notifyDataSetChanged();
        } else if (cityModel == null) {
            cityModel = provinceModel.children.get(position);

            List<String> strList = new ArrayList<>();
            for (ProvinceModel.ChildrenBeanX.ChildrenBean child : cityModel.children) {
                strList.add(child.name);
            }

            adapter.getData().clear();
            adapter.getData().addAll(strList);
            adapter.notifyDataSetChanged();
        } else {
            String val = provinceModel.name + cityModel.name + cityModel.children.get(position).name;
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("addr", val);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        provinceModel = null;
        cityModel = null;
    }

    private List<String> getList() {
        List<String> strList = new ArrayList<>();
        for (ProvinceModel model : list)
            strList.add(model.name);
        return strList;
    }

    private void initJsonData() {//解析数据
        String data = AppUtils.getJson(this, "province.json");//获取assets目录下的json文件数据
        try {
            JSONArray arr = new JSONArray(data);
            if (arr != null && arr.length() > 0) {
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++)
                    list.add(gson.fromJson(arr.getString(i), ProvinceModel.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
