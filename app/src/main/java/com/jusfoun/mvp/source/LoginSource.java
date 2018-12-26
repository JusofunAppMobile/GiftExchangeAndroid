package com.jusfoun.mvp.source;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.retrofit.RetrofitUtil;

import java.util.Map;

/**
 * 登录 Source
 *
 * @时间 2017/9/18
 * @作者 LiuGuangDan
 */

public class LoginSource extends BaseSoure {

    public void login(String phone, String code, final NetWorkCallBack callBack) {

        Map<String, Object> map = getMap();
        map.put("phone", phone);
        map.put("code", code);

        getData(RetrofitUtil.getInstance().service.login(map), callBack);
    }

}
