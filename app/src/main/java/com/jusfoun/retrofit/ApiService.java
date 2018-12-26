package com.jusfoun.retrofit;


import com.jusfoun.model.NetModel;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author zhaoyapeng
 * @version create time:17/6/617:19
 * @Email zyp@jusfoun.com
 * @Description ${api service}
 */
public interface ApiService {

    @POST("api/user/login")
    Observable<NetModel> login(@Body Map<String, Object> params);

    @POST("api/present/getcardpresentlist")
    Observable<NetModel> giftList(@Body Map<String, Object> params);

    @POST("api/present/GetPresentListByCardNo")
    Observable<NetModel> giftListByNo(@Body Map<String, Object> params);

    @POST("api/present/GetPresentListByQrcode")
    Observable<NetModel> getDataByQrcode(@Body Map<String, Object> params);

    @POST("api/order/GetCardPresentList")
    Observable<NetModel> ecouponList(@Body Map<String, Object> params);

    @POST("api/order/CreateOrder")
    Observable<NetModel> exchange(@Body Map<String, Object> params);

    @POST("api/order/GetOrderList")
    Observable<NetModel> record(@Body Map<String, Object> params);

    /**
     * 9	修改订单收货地址接口
     * @param params
     * @return
     */
    @POST("api/order/UpdateOrder")
    Observable<NetModel> updateAddress(@Body Map<String, Object> params);

    /**
     * 11	电子券管理列表接口
     * @param params
     * @return
     */
    @POST("api/ticket/GetBankTicketManagerList")
    Observable<NetModel> ecouponManage(@Body Map<String, Object> params);

    /**
     * 12	发卡给普通用户接口
     * @param params
     * @return
     */
    @POST("api/ticket/SendTicketToUser")
    Observable<NetModel> sendCard(@Body Map<String, Object> params);

    /**
     * 13	礼品审核列表接口
     * @param params
     * @return
     */
    @POST("api/order/GetOrderListAdmin")
    Observable<NetModel> getOrderListAdmin(@Body Map<String, Object> params);

    /**
     * 14	发卡管理列表接口
     * @param params
     * @return
     */
    @POST("api/ticket/GetCardManagerList")
    Observable<NetModel> bankSendCard(@Body Map<String, Object> params);

    /**
     * 15	接收用户列表接口
     * @param params
     * @return
     */
    @POST("api/user/GetBankUserList")
    Observable<NetModel> receiveList(@Body Map<String, Object> params);

    /**
     * 16	发卡给银行用户接口
     * @param params
     * @return
     */
    @POST("api/user/SendTicketToBankUser")
    Observable<NetModel> sendCardToCustomer(@Body Map<String, Object> params);

    /**
     * 17	发卡历史记录列表接口
     * @param params
     * @return
     */
    @POST("api/user/GetSendCardLog")
    Observable<NetModel> sendCardLog(@Body Map<String, Object> params);

    /**
     * 18	审核礼品接口
     * @param params
     * @return
     */
    @POST("api/order/AuditOrder")
    Observable<NetModel> auditOrder(@Body Map<String, Object> params);

    /**
     * 19	订单处理（物流管理）列表接口
     * @param params
     * @return
     */
    @POST("api/order/GetOrderListFlow")
    Observable<NetModel> flowList(@Body Map<String, Object> params);

    /**
     * 20	录入、修改物流接口
     * @param params
     * @return
     */
    @POST("api/order/UpdateFlow")
    Observable<NetModel> updateFlow(@Body Map<String, Object> params);

    @POST("api/version/GetVersionNumber")
    Observable<NetModel> checkVersion(@Body Map<String, Object> params);

    /**
     * 1	发送验证码接口
     * @param params
     * @return
     */
    @POST("api/sms/getverifycode")
    Observable<NetModel> sendCode(@Body Map<String, Object> params);

    /**
     * 1	获取手机号
     * @param params
     * @return
     */
    @POST("api/User/GetCantact")
    Observable<NetModel> getPhoneNet(@Body Map<String, Object> params);

    @POST("api/user/GetBankUser")
    Observable<NetModel> getBankUser(@Body Map<String, Object> params);

    @POST("api/user/SendTicketToBankAdminUser")
    Observable<NetModel> sendTicketToBankAdminUser(@Body Map<String, Object> params);

    @POST("api/user/SendTicketToBankUser2")
    Observable<NetModel> sendTicketToBankUser2(@Body Map<String, Object> params);

    @POST("api/user/GetSendAdminCardLog")
    Observable<NetModel> getSendAdminCardLog(@Body Map<String, Object> params);

}
