package com.jusfoun.mvp.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.model.param.ExchangeModel;
import com.jusfoun.retrofit.RetrofitUtil;

import java.util.Map;

/**
 * 礼品兑换 source
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ExchangeSource extends BaseSoure {

    public void exchange(ExchangeModel model, final NetWorkCallBack callBack) {

        Map<String, Object> map = getMap();
        map.putAll(new ObjectMapper().convertValue(model, Map.class));

        getData(RetrofitUtil.getInstance().service.exchange(map), callBack);
    }

}
