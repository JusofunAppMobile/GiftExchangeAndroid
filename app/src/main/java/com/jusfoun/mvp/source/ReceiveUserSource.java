package com.jusfoun.mvp.source;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.retrofit.RetrofitUtil;

import java.util.Map;

/**
 * 接收用户 source
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class ReceiveUserSource extends BaseSoure {

    public void getData(int pageNum, int pageSize, final NetWorkCallBack callBack) {

        Map<String, Object> map = getMap();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);

        getData(RetrofitUtil.getInstance().service.receiveList(map), callBack);

    }

}
