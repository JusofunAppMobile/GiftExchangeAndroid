package com.jusfoun.mvp.source;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.retrofit.RetrofitUtil;

import java.util.Map;

/**
 * 电子券管理 source
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class CouponManageSource extends BaseSoure {

    public void getData(final NetWorkCallBack callBack) {
        Map<String, Object> map = getMap();
        getData(RetrofitUtil.getInstance().service.ecouponManage(map), callBack);
    }
}
