//
//  YSSPayListModel.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/21.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YSSPayListModel : NSObject

@property (nonatomic, strong) NSString *updateUserId;

@property (nonatomic, strong) NSString *updateTime;

@property (nonatomic, strong) NSString *touchTime;

@property (nonatomic, strong) NSString *bankCardId;

@property (nonatomic, strong) NSString *askTime;

@property (nonatomic, strong) NSString *effectTime;

@property (nonatomic, strong) NSString *recDisAskprice;

@property (nonatomic, strong) NSString *intoAccountTime;

@property (nonatomic, assign) NSInteger version;

@property (nonatomic, assign) CGFloat recDisPrice;

@property (nonatomic, assign) CGFloat recDisBalance;

@property (nonatomic, strong) NSString *delflag;

@property (nonatomic, strong) NSString *id;

@property (nonatomic, strong) NSString *commdityId;

@property (nonatomic, strong) NSString *recDisStatus;

@property (nonatomic, strong) NSString *orderCode;

@property (nonatomic, strong) NSString *recDisWay;

@property (nonatomic, strong) NSString *transAccountTime;

@property (nonatomic, strong) NSString *recIdsCode;

@property (nonatomic, strong) NSString *recDisType;

@property (nonatomic, strong) NSString *backup;

@property (nonatomic, strong) NSString *createTime;

@property (nonatomic, strong) NSString *touchBy;

@property (nonatomic, strong) NSString *createUserId;

@property (nonatomic, strong) NSString *orderId;

@end
