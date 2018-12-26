package com.jusfoun.utils;

/**
 * 获取测试数据的工具类
 *
 * @时间 2017/8/9
 * @作者 LiuGuangDan
 */

public class TestUtils {


    /**
     *  3	礼品列表接口 测试数据
     * @return
     */
    public static String getGiftListData() {
        return "{\"code\":1,\"msg\":\"成功提示信息\",\"data\":{\"cardTypeList\":[{\"pid\":\"1\",\"name\":\"福惠卡\"},{\"pid\":\"2\",\"name\":\"福安卡\"},{\"pid\":\"3\",\"name\":\"福运卡\"},{\"pid\":\"4\",\"name\":\"福和卡\"},{\"pid\":\"5\",\"name\":\"福顺卡\"},{\"pid\":\"6\",\"name\":\"福瑞卡\"}],\"list\":[{\"pid\":\"1\",\"img\":\"http://youlipinpic.b0.upaiyun.com/upload/product/2017_07_28/1701350041.jpg\",\"name\":\"中秋数码套装礼盒蓝牙音箱数据线两件套\",\"url\":\"http://www.baidu.com\"},{\"pid\":\"2\",\"img\":\"https://img14.360buyimg.com/n1/s546x546_jfs/t2236/154/1186206741/329584/4149fea5/56498c80N9bf5d863.jpg\",\"name\":\"哈根达斯优惠券尊礼卡冰淇淋蛋糕现金卡 全国可用 300元面值 端午节福利团购优惠\",\"url\":\"http://www.baidu.com\"},{\"pid\":\"3\",\"img\":\"https://g-search3.alicdn.com/img/bao/uploaded/i4/TB1C6zpGVXXXXXoXpXXSv7yFpXX_092310.jpg_180x180.jpg\",\"name\":\"饮水机 家用迷你 制热型 台式桌面\",\"url\":\"http://www.baidu.com\"},{\"pid\":\"4\",\"img\":\"https://gd4.alicdn.com/imgextra/i4/713757734/TB2HmL4prRkpuFjSspmXXc.9XXa_!!713757734.jpg\",\"name\":\"PlayStation 4 Pro 电脑娱乐游戏主机 1TB\",\"url\":\"http://www.baidu.com\"},{\"pid\":\"5\",\"img\":\"https://img.alicdn.com/imgextra/i4/2240685099/TB2c_mAjVXXXXaDXXXXXXXXXXXX_!!2240685099.jpg_430x430q90.jpg\",\"name\":\"小米米家对讲机 蓝色 民用迷你手台 位置共享 FM收音机\",\"url\":\"http://www.baidu.com\"}]}}";
    }

    /**
     *  6	礼品兑换记录列表接口 测试数据
     * @return
     */
    public static String getExchangeRecordListData() {
        return "{\"code\":1,\"msg\":\"成功提示信息\",\"data\":{\"list\":[{\"pid\":\"1\",\"no\":\"37400629788330386\",\"name\":\"中秋数码套装礼盒蓝牙音箱数据线两件套\",\"imgs\":[{\"img\":\"http://youlipinpic.b0.upaiyun.com/upload/product/2017_07_28/1701350041.jpg\"}],\"num\":1,\"time\":1501812039,\"cardName\":\"福惠卡\",\"addrInfo\":{\"name\":\"刘先生\",\"phone\":\"1888888888\",\"addr\":\"北京市海淀区\",\"addrDetail\":\"清华同方\"},\"flowUrl\":\"http://www.baidu.com\",\"flowNo\":\"3930210877235\",\"detailUrl\":\"http://www.baidu.com\",\"status\":0},{\"pid\":\"2\",\"no\":\"37400629788880384\",\"name\":\"哈根达斯优惠券尊礼卡冰淇淋蛋糕现金卡 全国可用 300元面值 端午节福利团购优惠\",\"imgs\":[{\"img\":\"https://img14.360buyimg.com/n1/s546x546_jfs/t2236/154/1186206741/329584/4149fea5/56498c80N9bf5d863.jpg\"},{\"img\":\"http://www.youlipin.com/img/widemap//serch_con_03.jpg\"},{\"img\":\"https://img.alicdn.com/bao/uploaded/i3/T1RZxaFsNeXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg\"}],\"num\":3,\"time\":1501984839,\"cardName\":\"福和卡\",\"addrInfo\":{\"name\":\"刘先生\",\"phone\":\"1888888888\",\"addr\":\"北京市海淀区\",\"addrDetail\":\"清华同方\"},\"flowUrl\":\"http://www.baidu.com\",\"flowNo\":\"3930210877237\",\"detailUrl\":\"http://www.baidu.com\",\"status\":1},{\"pid\":\"3\",\"no\":\"37400629788880389\",\"name\":\"饮水机 家用迷你 制热型 台式桌面\",\"imgs\":[{\"img\":\"https://g-search3.alicdn.com/img/bao/uploaded/i4/TB1C6zpGVXXXXXoXpXXSv7yFpXX_092310.jpg_180x180.jpg\"}],\"num\":1,\"time\":1502503239,\"cardName\":\"福瑞卡\",\"addrInfo\":{\"name\":\"刘先生\",\"phone\":\"1888888888\",\"addr\":\"北京市海淀区\",\"addrDetail\":\"清华同方\"},\"flowUrl\":\"http://www.baidu.com\",\"flowNo\":\"3930210877231\",\"detailUrl\":\"http://www.baidu.com\",\"status\":2},{\"pid\":\"4\",\"no\":\"37400629788880389\",\"name\":\"PlayStation 4 Pro 电脑娱乐游戏主机 1TB\",\"imgs\":[{\"img\":\"https://gd4.alicdn.com/imgextra/i4/713757734/TB2HmL4prRkpuFjSspmXXc.9XXa_!!713757734.jpg\"}],\"num\":1,\"time\":1502503239,\"cardName\":\"福运卡\",\"addrInfo\":{\"name\":\"刘先生\",\"phone\":\"1888888888\",\"addr\":\"北京市海淀区\",\"addrDetail\":\"清华同方\"},\"flowUrl\":\"http://www.baidu.com\",\"flowNo\":\"3930210877236\",\"detailUrl\":\"http://www.baidu.com\",\"status\":3},{\"pid\":\"5\",\"no\":\"37400629788880389\",\"name\":\"小米米家对讲机 蓝色 民用迷你手台 位置共享 FM收音机\",\"imgs\":[{\"img\":\"https://img.alicdn.com/imgextra/i4/2240685099/TB2c_mAjVXXXXaDXXXXXXXXXXXX_!!2240685099.jpg_430x430q90.jpg\"}],\"num\":1,\"time\":1502503238,\"cardName\":\"福顺卡\",\"addrInfo\":{\"name\":\"刘先生\",\"phone\":\"1888888888\",\"addr\":\"北京市海淀区\",\"addrDetail\":\"清华同方\"},\"flowUrl\":\"http://www.baidu.com\",\"flowNo\":\"3930210877230\",\"detailUrl\":\"http://www.baidu.com\",\"status\":4}]}}";
    }

    /**
     *  16	发卡历史记录接口 测试数据
     * @return
     */
    public static String getCardHistoryListData() {
        return "{\"code\":1,\"msg\":\"成功提示信息\",\"data\":{\"list\":[{\"pid\":\"1\",\"cardName\":\"福惠卡\",\"name\":\"北京工商银行亚运村支行-马大帅\",\"num\":100,\"time\":1503899183124},{\"pid\":\"2\",\"cardName\":\"福安卡\",\"name\":\"北京工商银行亚运村支行-张大嘴\",\"num\":66,\"time\":1503899183124},{\"pid\":\"3\",\"cardName\":\"福运卡\",\"name\":\"北京工商银行亚运村支行-马大帅\",\"num\":55,\"time\":1503899183124}]}}";
    }

    /**
     *  14	接收用户列表接口 测试数据
     * @return
     */
    public static String getReceiveUserListData() {
        return "{\"code\":1,\"msg\":\"成功提示信息\",\"data\":{\"list\":[{\"pid\":\"1\",\"name\":\"北京工商银行亚运村支行-马大帅\"},{\"pid\":\"2\",\"name\":\"北京工商银行亚运村支行-张大嘴\"}]}}";
    }

    /**
     *  7	我的优惠券接口 测试数据
     * @return
     */
    public static String getECouponListData() {
        return "{\"code\":1,\"msg\":\"成功提示信息\",\"data\":{\"list\":[{\"pid\":\"1\",\"cardNo\":\"1111111111111\",\"cardPwd\":\"11111111\",\"cardName\":\"福惠卡\",\"status\":1},{\"pid\":\"2\",\"cardNo\":\"222222222\",\"cardPwd\":\"2222222\",\"cardName\":\"福惠卡\",\"status\":0},{\"pid\":\"3\",\"cardNo\":\"3333333333\",\"cardPwd\":\"3333333\",\"cardName\":\"福运卡\",\"status\":0},{\"pid\":\"4\",\"cardNo\":\"4444444444\",\"cardPwd\":\"4444444444\",\"cardName\":\"福惠卡\",\"status\":1}]}}";
    }

    /**
     *  10	电子券管理接口 测试数据
     * @return
     */
    public static String getECouponManageListData() {
        return "{\"code\":1,\"msg\":\"成功提示信息\",\"data\":{\"list\":[{\"pid\":\"1\",\"cardName\":\"福惠卡\",\"totalNum\":100,\"remainNum\":50},{\"pid\":\"2\",\"cardName\":\"福安卡\",\"totalNum\":100,\"remainNum\":20},{\"pid\":\"3\",\"cardName\":\"福星卡\",\"totalNum\":100,\"remainNum\":19}]}}";
    }
}
