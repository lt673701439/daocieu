//
//  YSSOrderListModel.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/19.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YSSOrderListModel : NSObject

@property (nonatomic, strong) NSString *orMerchantGain;  /**< 商户收入金额 */
@property (nonatomic, strong) NSString *orMerchantBackPrice;  /**< 商户退款金额 */
@property (nonatomic, strong) NSString *orOrderTime;  /**< 下单时间 */
@property (nonatomic, assign) CGFloat orPayPrice;  /**< 订单收入金额 */
@property (nonatomic, assign) NSInteger orType;  /**< 订单类型 */
@property (nonatomic, strong) NSString *orInfo;  /**< 订单所有数据 */
@property (nonatomic, assign) NSInteger orStatus;  /**< 订单状态：0-草稿，1-待支付，2-已支付，3-已播放，4-已取消，5-已退单，6-已退款。*/
@property (nonatomic, strong) NSString *orRefundPrice;  /**< 订单退款金额 */
@property (nonatomic, strong) NSString *orCode;  /**< 订单号 */
@property (nonatomic, assign) CGFloat orBalance;  /**< 余额 */
@property (nonatomic, strong) NSString *orUserIcon; /**< 头像 */
@property (nonatomic, strong) NSString *updateUserId;
@property (nonatomic, strong) NSString *orRefundTime;
@property (nonatomic, strong) NSString *orMobile;
@property (nonatomic, strong) NSString *updateTime;
@property (nonatomic, strong) NSString *orSourceId;
@property (nonatomic, strong) NSString *orUserName;
@property (nonatomic, strong) NSString *orSourceCode;
@property (nonatomic, strong) NSString *orUserId;
@property (nonatomic, strong) NSString *orMerchantId;
@property (nonatomic, strong) NSString *id;
@property (nonatomic, strong) NSString *orBackTime;
@property (nonatomic, strong) NSString *orPayTime;
@property (nonatomic, strong) NSString *createTime;
@property (nonatomic, strong) NSString *createUserId;

@end
