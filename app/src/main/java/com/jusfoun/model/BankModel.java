package com.jusfoun.model;

import java.util.List;

/**
 * Created by xiaoma on 2018/4/28/028.
 */

public class BankModel {


    /**
     * pid : 19
     * name : 工商银行
     * userList : [{"pid":"f0121a70c544447980ce4d6a2adc8d90","name":"银行管理员张三"}]
     */

    public String pid;
    public String name;
    public List<UserListBean> userList;

    public static class UserListBean {
        /**
         * pid : f0121a70c544447980ce4d6a2adc8d90
         * name : 银行管理员张三
         */

        public String pid;
        public String name;
    }
}
