//
//  YSSChooseDateVC.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/12.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

typedef void(^ChooseDateBlock)(NSDate *startDate, NSDate *endDate);

@interface YSSChooseDateVC : YSSBaseViewController

@property (nonatomic, strong) ChooseDateBlock block;

@end
