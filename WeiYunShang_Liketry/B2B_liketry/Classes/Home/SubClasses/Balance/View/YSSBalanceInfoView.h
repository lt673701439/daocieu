//
//  YSSBalanceInfoView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSBalanceInfoView;

@protocol YSSBalanceInfoViewDelegate <NSObject>

/** 点击提现 */
- (void)yssBalanceInfoViewDidClickWithdraw:(YSSBalanceInfoView *)yssBalanceInfoView;

/** 点击我的银行卡 */
- (void)yssBalanceInfoViewDidClickCard:(YSSBalanceInfoView *)yssBalanceInfoView;

@end

@interface YSSBalanceInfoView : UIView


@property (nonatomic, weak) id<YSSBalanceInfoViewDelegate> delegate;

@end
