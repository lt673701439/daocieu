//
//  YSSWithDrawView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSWithDrawView, YSSCardModel;

@protocol YSSWithDrawViewDelegate <NSObject>

/** 选择银行卡 */
- (void)yssWithDrawViewDidClickChooseCard:(YSSWithDrawView *)yssWithDrawView;

@end

@interface YSSWithDrawView : UIView

@property (nonatomic, strong) YSSCardModel *cardModel;

@property (nonatomic, weak) id<YSSWithDrawViewDelegate> delegate;

@property (nonatomic, strong) UITextField *inputTF;

- (void)configUIWithData:(NSDictionary *)data;

@end
