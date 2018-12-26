package com.jusfoun.mvp.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.ExchangeRecordModel;
import com.jusfoun.retrofit.RetrofitUtil;

import java.util.Map;

/**
 * 管理员发卡历史 source
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class UpdateAddrSource extends BaseSoure {

    public void getData(ExchangeRecordModel.AddrInfoBean bean, String id, final NetWorkCallBack callBack) {
        Map<String, Object> map = getMap();
        map.putAll(new ObjectMapper().convertValue(bean, Map.class));
        map.put("pid", id);
        getData(RetrofitUtil.getInstance().service.updateAddress(map), callBack);
    }

}
