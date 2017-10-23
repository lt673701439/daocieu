//
//  YSSChooseLocationVC.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

typedef void(^YSSChooseLocationVCBlock)(NSString *title, id model);

@interface YSSChooseLocationVC : YSSBaseViewController

@property (nonatomic, strong) NSString *navTitle;
@property (nonatomic, assign) NSInteger index;

@property (nonatomic, copy) YSSChooseLocationVCBlock block;

@end
