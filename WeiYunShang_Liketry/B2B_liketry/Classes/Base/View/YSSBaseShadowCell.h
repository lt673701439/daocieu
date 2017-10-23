//
//  YSSBaseShadowCell.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface YSSBaseShadowCell : UITableViewCell

@property (nonatomic, strong) UIView *shadowMainView;

@property (nonatomic, assign) BOOL canEdit;

@property (nonatomic, assign) BOOL edit;

- (void)setupUI;

@end
