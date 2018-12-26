package com.jusfoun.mvp.source;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.retrofit.RetrofitUtil;

import java.util.Map;

/**
 * 管理员发卡历史 source
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ECouponSource extends BaseSoure {
    public void getData(final NetWorkCallBack callBack) {
        Map<String, Object> map = getMap();
        getData(RetrofitUtil.getInstance().service.ecouponList(map), callBack);
    }
}
