package com.jusfoun.constants;

/**
 * 说明
 *
 * @时间 2017/8/7
 * @作者 LiuGuangDan
 */

public class CommonConstant {

    public static final int TYPE_MANAGER_BANK = 5; // 银行管理员
    public static final int TYPE_MANAGER = 4; // 管理员角色
    public static final int TYPE_FLOW = 2; // 物流快递员角色
    public static final int TYPE_USER = 3; // 普通用户角色
    public static final int TYPE_BANK = 1; // 银行用户角色

    public static final int REQ_CAPTURE = 0x112; // 扫描requestcode

    public static final int REQ_CAPTURE_EXPRESS_DELIVERY = 0x113; // 快递号扫描


    public static final String GIFT_TYPE = "gift_type"; // 查询礼品列表类型
    public static final int GIFT_TYPE_ECOUPE = 1; // 我的电子券
    public static final int GIFT_TYPE_QRCODE = 2; // 扫描二维码
    public static final int GIFT_TYPE_NO_PWD = 3; // 我的电子券

}
