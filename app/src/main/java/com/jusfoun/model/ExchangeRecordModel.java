package com.jusfoun.model;

import java.io.Serializable;
import java.util.List;

/**
 * 礼品兑换记录 MODEL
 *
 * @时间 2017/8/10
 * @作者 LiuGuangDan
 */

public class ExchangeRecordModel implements Serializable {


    /**
     * pid : 1
     * no : 37400629788330386
     * name : 中秋数码套装礼盒蓝牙音箱数据线两件套
     * imgs : [{"img":"http://youlipinpic.b0.upaiyun.com/upload/product/2017_07_28/1701350041.jpg"}]
     * num : 1
     * time : 1501812039
     * cardName : 福惠卡
     * addrInfo : {"name":"刘先生","phone":"1888888888","addr":"北京市海淀区"}
     * flowUrl : http://www.baidu.com
     * flowNo : 3930210877235
     * detailUrl : http://www.baidu.com
     * status : 0
     */

    public String pid;
    public String no;
    public String name;
    public int num;
    public long time;
    public String cardName;
    public AddrInfoBean addrInfo;
    public String flowUrl;
    public String flowNo;
    public String detailUrl;
    public int status;
    public List<ImgsBean> imgs;

    public static class AddrInfoBean implements Serializable{
        /**
         * name : 刘先生
         * phone : 1888888888
         * addr : 北京市海淀区
         */

        public String name;
        public String phone;
        public String addr;
        public String addrDetail;
    }

    public static class ImgsBean implements Serializable{
        /**
         * img : http://youlipinpic.b0.upaiyun.com/upload/product/2017_07_28/1701350041.jpg
         */

        public String img;
    }
}
