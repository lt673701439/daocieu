//
//  YSSPsyResultView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/25.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSPayListModel;
@interface YSSPsyResultView : UIView

@property (nonatomic, strong) YSSPayListModel *model;

+ (instancetype)payResultView;

@end
