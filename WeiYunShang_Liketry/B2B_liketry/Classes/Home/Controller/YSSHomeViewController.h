//
//  YSSHomeViewController.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

typedef void(^YSSHomeViewControllerGetMessageComplectionHandel)(void);

@interface YSSHomeViewController : YSSBaseViewController

@property (nonatomic, copy) YSSHomeViewControllerGetMessageComplectionHandel getMessageComplectionHandel;

@end
