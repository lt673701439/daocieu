//
//  YSSManageCardViewController.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

@class YSSCardModel;

typedef void(^ChooseCardCallBack)(YSSCardModel *model);

@interface YSSManageCardViewController : YSSBaseViewController

@property (nonatomic, copy) ChooseCardCallBack block;

@end
