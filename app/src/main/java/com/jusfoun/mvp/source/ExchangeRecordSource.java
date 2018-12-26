package com.jusfoun.mvp.source;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.retrofit.RetrofitUtil;

import java.util.Map;

/**
 * 礼品兑换记录列表 source
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ExchangeRecordSource extends BaseSoure {

    public void getData(final NetWorkCallBack callBack) {

        Map<String, Object> map = getMap();

        getData(RetrofitUtil.getInstance().service.record(map), callBack);

    }

    public void loadManageAudit(int type, String startTime, String endTime, int pageNum, int pageSize, final NetWorkCallBack callBack) {

        Map<String, Object> map = getMap();
        map.put("type", type);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);

        getData(RetrofitUtil.getInstance().service.getOrderListAdmin(map), callBack);

    }

    public void flowList(int type, String startTime, String endTime, int pageNum, int pageSize, final NetWorkCallBack callBack) {

        Map<String, Object> map = getMap();
        map.put("type", type);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);

        getData(RetrofitUtil.getInstance().service.flowList(map), callBack);

    }

}
