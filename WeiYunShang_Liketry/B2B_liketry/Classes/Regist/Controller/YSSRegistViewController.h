//
//  YSSRegistViewController.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

typedef NS_ENUM(NSUInteger, YSSRegistViewControllerType)
{
    registType,
    forgetPWDType,
    bindPhoneNumType,
};

@interface YSSRegistViewController : YSSBaseViewController

@property (nonatomic, assign) YSSRegistViewControllerType type;
@property (nonatomic, strong) NSString *uid;

@property (nonatomic, strong) NSString *phoneNum;

@end
