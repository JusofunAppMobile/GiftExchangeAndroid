package com.jusfoun.mvp.source;

import com.jusfoun.inter.NetWorkCallBack;
import com.jusfoun.retrofit.RetrofitUtil;

import java.util.Map;

/**
 * 首页礼品列表 source
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class GiftSource extends BaseSoure {

    public void getData(String type, final NetWorkCallBack callBack) {

        Map<String, Object> map = getMap();
        map.put("type", type);

        getData(RetrofitUtil.getInstance().service.giftList(map), callBack);
    }

    public void getDataByNoAndPwd(String no,String pwd, final NetWorkCallBack callBack) {

        Map<String, Object> map = getMap();
        map.put("cardNo", no);
        map.put("cardPwd", pwd);

        getData(RetrofitUtil.getInstance().service.giftListByNo(map), callBack);
    }

    public void getDataByQrcode(String qrcode, final NetWorkCallBack callBack) {
        Map<String, Object> map = getMap();
        map.put("qrcode", qrcode);
        getData(RetrofitUtil.getInstance().service.getDataByQrcode(map), callBack);
    }

}
