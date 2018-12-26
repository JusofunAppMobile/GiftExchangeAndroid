package com.jusfoun.model;

/**
 * 我的电子券 Model
 *
 * @时间 2017/8/30
 * @作者 LiuGuangDan
 */

public class ECouponModel {


    /**
     * pid : 1
     * cardNo : 1111111111111
     * cardPwd : 11111111
     * cardName : 福惠卡
     * status : 0
     */

    public String pid;
    public String cardNo;
    public String cardPwd;
    public String cardTypeId;
    public String cardName;
    public int status; //  兑换状态 0：未兑换 1：已兑换
}
