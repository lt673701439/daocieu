//
//  YSSMsgModel.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YSSMsgModel : NSObject

@property (nonatomic, strong) NSString *id;

@property (nonatomic, strong) NSString *updateUserId;

@property (nonatomic, strong) NSString *messageMerchantId;

@property (nonatomic, assign) NSInteger messageType;

@property (nonatomic, assign) NSInteger messageCancelTime;

@property (nonatomic, strong) NSString *createTime;

@property (nonatomic, strong) NSString *messagePublishTime;

@property (nonatomic, strong) NSString *createUserId;

@property (nonatomic, assign) NSInteger delflag;

@property (nonatomic, strong) NSString *messageTitle;

@property (nonatomic, strong) NSString *messageContent;

@property (nonatomic, strong) NSString *updateTime;

@property (nonatomic, assign) NSInteger isSelect;  /**< 编辑状态中是否选中 */

@property (nonatomic, assign) NSInteger isRead;  /**< 是否已读 */

@property (nonatomic, assign) NSInteger isDelete;  /**< 是否已删除 */

@property (nonatomic, strong) NSString *messageImg; /**< 活动封面图 */
@property (nonatomic, strong) NSString *messagePromotionId;  /**< 活动id */

@end
