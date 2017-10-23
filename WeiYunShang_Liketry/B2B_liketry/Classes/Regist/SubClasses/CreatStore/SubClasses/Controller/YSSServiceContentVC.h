//
//  YSSServiceContentVC.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

typedef void(^YSSServiceContentVCBlock)(NSMutableArray *selectArr);

@interface YSSServiceContentVC : YSSBaseViewController

@property (nonatomic, copy) YSSServiceContentVCBlock block;

@end
