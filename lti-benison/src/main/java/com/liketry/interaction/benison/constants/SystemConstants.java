package com.liketry.interaction.benison.constants;

/**
 * 系统常量
 */
public interface SystemConstants {
    String RESULT = "result";
    String RESULT_SUCCESS = "success";
    String RESULT_FALSE = "error";
    String WX_APPID = "wx0b06d2c09d05afbd";
    String WX_SECRET = "038986cb1127d8c885265d20b43cf51f";
    String LOG_TAG = "BENISON_API_LOG";


    String ROOT_LOCAL = "/home/bjcenter/workspace/application/lti-opt/data/";//文件根目录
    String QR_CODE_LOCAL = "file/qrcode/";
    String QR_CODE_PATH = ROOT_LOCAL + QR_CODE_LOCAL;//二维码存放地方
    String URL_QR_CODE = "qrcode/";//二维码

    //订单状态常量
    static final String draft = "0"; //草稿
    static final String Payment = "1"; //待支付
    static final String Paid = "2";    //已支付
    static final String played = "3"; //已播放
    static final String canceled = "4"; //已取消
    static final String returnOrder = "5"; //已退单
    static final String refunded = "6"; //已退款

    //排期状态
    static final String scheduleStatus = "0"; //未排期
    static final String scheduleStatused = "1"; //已排期

    //库存状态
    static final String freedom = "1"; //自由
    static final String locked = "2"; //锁定
    static final String used = "3"; //占用
    
    //验证码类型
    static final int CODE_BIND = 1; //绑定

}