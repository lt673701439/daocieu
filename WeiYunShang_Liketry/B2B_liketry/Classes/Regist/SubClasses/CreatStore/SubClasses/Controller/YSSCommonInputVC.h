//
//  YSSCommonInputVC.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

typedef void(^YSSCommonInputVCBlock)(NSString *str);
typedef void(^YSSCommonInputVCTimeBlock)(NSString *startTime, NSString *endTime);

@interface YSSCommonInputVC : YSSBaseViewController

@property (nonatomic, strong) NSString *navTitle;
@property (nonatomic, assign) NSInteger index;

@property (nonatomic, copy) YSSCommonInputVCBlock block;
@property (nonatomic, copy) YSSCommonInputVCTimeBlock timeBlock;

@end
