//
//  YSSHttpUrl.h
//  Blessings_liketry
//
//  Created by GentleZ on 2017/5/27.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#ifndef YSSHttpUrl_h
#define YSSHttpUrl_h

//#define URL(url)  [NSString stringWithFormat:@"%@://%@%@", YSSNetWorkProtocol, YSSHeadUrl, url]
#define URL(url)  [NSString stringWithFormat:@"%@%@", YSSHeadUrl, url]


#pragma mark - 用户
//用户注册
#define USER_REGIST (URL(@"/api/user_api/register"))

//用户登录
#define USER_LOGIN (URL(@"/api/user_api/login"))

//用户信息
#define USER_API (URL(@"/api/user_api"))

#pragma mark - 商户
//获取商户信息
#define GET_MERCHANT (URL(@"/api/merchant_api/getMerchant"))

//提交商户信息
#define SAVE_MERCHANT (URL(@"/api/merchant_api/saveMerchant"))

//修改商户信息
#define CHANGE_MERCHANT (URL(@"/api/merchant_api/updateInfo"))

//添加银行卡
#define ADD_BANKCARD (URL(@"/api/bank_card_api/addBankCard"))

//获取银行卡列表
#define GET_BANKCARD (URL(@"/api/bank_card_api/getBankCard"))

//订账单列表
#define GET_ORDERLIST (URL(@"/api/orderApi/getSmartData"))

//提现额度查询
#define GET_CASHBALANCE (URL(@"/api/rec_dis_api/getCashBalance"))

//上传图片
#define UPLOAD_IMG (URL(@"/api/merchant_api/putImg"))

//修改登录/提现密码
#define CHANGE_PWD (URL(@"/api/user_api/changePwd"))

//获取消息
#define GET_SMARTDATA (URL(@"/api/message/getSmartData"))

//获取全部消息
#define GET_MSGLIST (URL(@"/api/message/monthData"))

//获取商户收入支出金额
#define GET_RECANDDISPRICE (URL(@"/api/rec_dis_api/getRecAndDisPrice"))

//提现申请
#define GET_APPCASH (URL(@"/api/rec_dis_api/appCash"))

//支出明细列表查询
#define GET_EXPENSELIST (URL(@"/api/rec_dis_api/getSmartData"))

//获取服务内容
#define GET_SERVICECONTENT (URL(@"/api/dict/getSmartData"))

//获取城市列表
#define GET_CITYLIST (URL(@"/api/dict/getDictTree"))

//获取验证码
#define GET_IDENTIFYCODE (URL(@"/api/user_api/getIdentifyingCode"))

//微信登录
#define USER_WXLOGIN (URL(@"/api/user_api/wxLogin"))

//绑定手机号
#define BIND_MOBILE (URL(@"/api/user_api/bindMobile"))

//找回密码
#define FIND_PWD (URL(@"/api/user_api/findPwd"))

//测试推送
#define TEST_PUSH (URL(@"/api/test_api/c"))

//赚钱课堂列表
#define MAKE_MONEY_LIST (URL(@"/api/make_money_api/getSmartData"))

//赚钱课堂详情
#define MAKE_MONEY_DETAIL (URL(@"/api/make_money_api/"))

//活动详情
#define ACTIVITY_DETAIL (URL(@"/api/promotion_api/"))





#endif
