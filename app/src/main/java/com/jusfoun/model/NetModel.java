package com.jusfoun.model;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

/**
 * 网络返回格式Model
 *
 * @时间 2017/6/29
 * @作者 LiuGuangDan
 */

public class NetModel extends BaseNetModel {

    public LinkedTreeMap data;

    public <T> T fromData(Class<T> clazz) {
        return new Gson().fromJson(new Gson().toJson(data), clazz);
    }

    public <T> T fromJSONObject(JSONObject obj, Class<T> clazz) {
        return new Gson().fromJson(obj.toString(), clazz);
    }

    public JSONObject getDataJSONObject() {
        try {
            return new JSONObject(new Gson().toJson(this)).getJSONObject("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStringValue(String key) {
        if (data != null) {
            Object obj = data.get(key);
            if (obj != null)
                return obj.toString();
        }
        return null;
    }


    public int getIntValue(String key) {
        if (data != null) {
            Object obj = data.get(key);
            if (obj != null)
                return Integer.parseInt(obj.toString());
        }
        return 0;
    }


    public long getLongValue(String key) {
        if (data != null) {
            Object obj = data.get(key);
            if (obj != null)
                return Long.parseLong(obj.toString());
        }
        return 0;
    }
}
