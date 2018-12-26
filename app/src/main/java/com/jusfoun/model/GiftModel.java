package com.jusfoun.model;

import java.util.List;

/**
 * 说明
 *
 * @时间 2017/8/8
 * @作者 LiuGuangDan
 */

public class GiftModel {


    /**
     * code : 1
     * msg : 成功提示信息
     * data : {"cardTypeList":[{"pid":"1","name":"福惠卡"},{"pid":"2","name":"福安卡"},{"pid":"3","name":"福运卡"},{"pid":"4","name":"福和卡"},{"pid":"5","name":"福顺卡"},{"pid":"6","name":"福瑞卡"}],"list":[{"pid":"1","img":"http://youlipinpic.b0.upaiyun.com/upload/product/2017_07_28/1701350041.jpg","name":"中秋数码套装礼盒蓝牙音箱数据线两件套","url":"http://www.baidu.com"},{"pid":"2","img":"https://img14.360buyimg.com/n1/s546x546_jfs/t2236/154/1186206741/329584/4149fea5/56498c80N9bf5d863.jpg","name":"哈根达斯优惠券尊礼卡冰淇淋蛋糕现金卡 全国可用 300元面值 端午节福利团购优惠","url":"http://www.baidu.com"},{"pid":"3","img":"https://img.alicdn.com/imgextra/i2/2137490821/TB2IkGObXgkyKJjSspoXXcOPpXa_!!2137490821.jpg","name":"澄沁 阳澄湖大闸蟹礼券 1988型号多选卡 前程似锦 五选一 批发团购享优惠","url":"http://www.baidu.com"},{"pid":"4","img":"https://g-search3.alicdn.com/img/bao/uploaded/i4/TB1C6zpGVXXXXXoXpXXSv7yFpXX_092310.jpg_180x180.jpg_.webp","name":"饮水机 家用迷你 制热型 台式桌面","url":"http://www.baidu.com"},{"pid":"5","img":"https://gd4.alicdn.com/imgextra/i4/713757734/TB2HmL4prRkpuFjSspmXXc.9XXa_!!713757734.jpg","name":"PlayStation 4 Pro 电脑娱乐游戏主机 1TB","url":"http://www.baidu.com"},{"pid":"6","img":"https://img.alicdn.com/imgextra/i4/2240685099/TB2c_mAjVXXXXaDXXXXXXXXXXXX_!!2240685099.jpg_430x430q90.jpg","name":"小米米家对讲机 蓝色 民用迷你手台 位置共享 FM收音机","url":"http://www.baidu.com"}]}
     */
    public List<CardTypeListBean> cardTypeList;
    public List<GiftBean> list;
    public CardInfo cardInfo;

    /**
     * cardNo : 4614860491719159
     * cardPwd : 273151
     * cardName : 福惠卡
     * cardTypeId : 1
     * status : 0
     */


    public static class CardTypeListBean {
        /**
         * pid : 1
         * name : 福惠卡
         */

        public String pid;
        public String name;
    }

    public static class GiftBean {
        /**
         * pid : 1
         * img : http://youlipinpic.b0.upaiyun.com/upload/product/2017_07_28/1701350041.jpg
         * name : 中秋数码套装礼盒蓝牙音箱数据线两件套
         * url : http://www.baidu.com
         */

        public String pid;
        public String img;
        public String name;
        public String url;
        public boolean isSelect;
    }


    public static class CardInfo {
        public String cardNo;
        public String cardPwd;
        public String cardName;
        public int cardTypeId;
        public int status;
    }

}


