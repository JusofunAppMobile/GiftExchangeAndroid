package com.jusfoun.model;

import java.io.Serializable;

/**
 * model基类
 *
 * @时间 2017/6/29
 * @作者 LiuGuangDan
 */

public class BaseNetModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5268625605268545266L;

    /**
     * 状态码，code = 1 为成功状态，
     */
    public int code;

    /**
     * 描述信息
     */
    public String msg;

    /**
     * 判断是否为成功状态
     * @return
     */
    public boolean success() {
        return code == 1;
    }
}
