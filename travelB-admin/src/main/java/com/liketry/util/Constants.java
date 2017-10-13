package com.liketry.util;

/**
 * Created by liketry
 */
public final class Constants {

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    public static final String UMENG_IOS_APP_KEY = "59db1e83310c9359db000106";
    public static final String UMENG_ANDROID_APP_KEY = "59A8B696F29D9840DE001409";//umeng
    public static final String UMENG_IOS_APP_MASTER = "tnj9gaoqe8ugjvhu8kn3jwxztw6esilc";//umeng
    public static final String UMENG_ANDROID_APP_MASTER = "ieay5shggkszxb4xouwfd0cmsmef1jek";//umeng
    public static final String WX_APPID = "wx0b06d2c09d05afbd";
    public static final String WX_SECRET = "038986cb1127d8c885265d20b43cf51f";

    public static final String ROOT_SRC = "src/main/resources/";
    public static final String ROOT_LOCAL = "data/";//文件根目录
    public static final String QR_CODE_LOCAL = "qrImg/";
    public static final String LICENSE_LOCAL = "license/";//证件上传根目录
    public static final String MERCHANT_ICON_LOCAL = "merchantIcon/";//证件上传根目录
    public static final String ID_UP_LOCAL = "idUp/";//身份证正面根目录
    public static final String ID_DOWN_LOCAL = "idDown/";//身份证背面根目录

    public static final String LICENSE_PATH = ROOT_SRC + ROOT_LOCAL + LICENSE_LOCAL;//证件上传根目录
    public static final String ID_UP_PATH = ROOT_SRC + ROOT_LOCAL + ID_UP_LOCAL;//身份证正面根目录
    public static final String ID_DOWN_PATH = ROOT_SRC + ROOT_LOCAL + ID_DOWN_LOCAL;//身份证背面根目录
    public static final String QR_CODE_PATH = ROOT_SRC + ROOT_LOCAL + QR_CODE_LOCAL;//二维码存放地方

    public static final String URL_QR_CODE = "/qrCode/";//二维码

    //--------------分成规则管理------------------//
    public static final String divideType_ratio = "0";//按比例
    public static final String divideType_price = "1";//按金额
    public static final String divideType_include = "0";//区间包含
    public static final String divideType_exclusive = "1";//区间不包含

    //-------------审核状态---------------------//
    public static final int CENSOR_DRAFT = 0;//草稿状态
    public static final int CENSOR_SUCCESS = 1;//审核通过1
    public static final int CENSOR_PUT = 2;//提交2
    public static final int CENSOR_FAILED = 3;//审核不通过3
    public static final int CENSOR_STOP_COOPERATION = 4;//停止合作4
    public static final int CENSOR_ABERRATION = 5;//违规5
    public static final int CENSOR_BREAK_LAW = 6; // 违法6

    //--------------用户状态------------------//
    public static final String userStatus_ok = "0";//可用
    public static final String userStatus_notOk = "1";//不可用

    //--------------收付单------------------//
    public static final String rec_dis_status_free = "0";//自有
    public static final String rec_dis_status_pass = "1";//审核通过
    public static final String rec_dis_status_refuse = "2";//审核不通过
    public static final String rec_dis_type_gat = "0";//收款
    public static final String rec_dis_type_pay = "1";//付款

    //--------------修改密码类型-----------------//
    public static final String pwd_login = "0";//登陆
    public static final String pwd_cash = "1";//提现
    public static final String pwd_forget = "2";//忘记密码

    //--------------验证码类型-----------------//
    public static final Integer TYPE_CODE_REGISTER = 0;//注册
    public static final Integer TYPE_CODE_ADDCARD = 1;//添加银行卡
    public static final Integer TYPE_CODE_BIND = 2;//绑定微信
    public static final Integer TYPE_CODE_FINDPWD = 3;//找回密码

    //--------------消息类型-----------------//
    public static final int MSG_NOTICE = 0;//公告
    public static final int MSG_ACTION = 1;//活动
    public static final int MSG_SERVER = 2;//服务

    //--------------推送类型-----------------//
    public static final boolean PRODUCTION_MODE = true;
    public static final int PUSH_ORDER = 1;//订单推送
    //--------------推送类型-----------------//
    public static final String DEVICE_IOS = "ios";
    public static final String DEVICE_ANDROID = "android";//订单推送

    //错误code
    //--------------通用------------------//
    public static final Integer code_ok = 0; //成功
    public static final Integer code_error = 500; //数据库异常
    //--------------用户接口------------------//
    public static final Integer data_null = 10000; //入参为空
    public static final Integer code_mobile_typeError = 10010; //手机格式错误
    public static final Integer code_user_exist = 10020; //用户已存在
    public static final Integer code_user_noExist = 10030; //用户不存在(用户名或密码错误)
    public static final Integer code_identifying_typeError = 10040; //验证码类型错误
    public static final Integer code_identifying_Error = 10041; //验证码错误
    public static final Integer binded_wxNum_error = 10050; //用户微信已绑定手机号
    public static final Integer binded_phone_error = 10060; //用户手机号已绑定微信
    public static final Integer ERROR_UPDATE_FAILED = 10061; //更新失败
    public static final Integer ERROR_INSERT_FAILED = 10062; //插入失败
    public static final Integer ERROR_ID_EMPTY = 10063; //插入失败
    public static final Integer ERROR_DATA_NOT_COMPLETE = 10064; //数据结构不完整
    public static final Integer ERROR_DATA_EMPTY = 10065; //数据不存在
    public static final Integer ERROR_IDENTIFYING_CODE_OFTEN = 10066; //验证码请求过于频繁
    public static final Integer SUCCESS = 0;//通过
    public static final Integer user_Illegal = 10070; //用户违法
    public static final Integer error_pwd = 10080; //密码错误

}
