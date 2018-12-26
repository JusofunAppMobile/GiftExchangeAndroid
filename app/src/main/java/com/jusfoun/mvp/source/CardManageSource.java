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

public class CardManageSource extends BaseSoure {

    public void getData(int pageNum, int pageSize, final NetWorkCallBack callBack) {
        Map<String, Object> map = getMap();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        getData(RetrofitUtil.getInstance().service.bankSendCard(map), callBack);
    }
}
