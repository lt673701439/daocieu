//
//  YSSCategoryVC.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

typedef void(^YSSCategoryVCBlock)(NSString *title, id model);

@interface YSSCategoryVC : YSSBaseViewController

@property (nonatomic, copy) YSSCategoryVCBlock block;


@end
