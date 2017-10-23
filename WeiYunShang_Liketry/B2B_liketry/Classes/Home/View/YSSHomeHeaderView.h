//
//  YSSHomeHeaderView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSHomeHeaderView;

@protocol YSSHomeHeaderViewDelegate <NSObject>

/** 点击余额 */
- (void)ySSHomeHeaderView:(YSSHomeHeaderView *)ySSHomeHeaderView didClickPrice:(NSString *)price;

/** 点击成单 */
- (void)ySSHomeHeaderView:(YSSHomeHeaderView *)ySSHomeHeaderView didClickOrder:(NSString *)orderNum;

/** 点击店铺 */
- (void)ySSHomeHeaderViewDidClickStore:(YSSHomeHeaderView *)ySSHomeHeaderView;

/** 点击二维码 */
- (void)ySSHomeHeaderViewDidClickQRCode:(YSSHomeHeaderView *)ySSHomeHeaderView;

/** 点击赚钱课堂 */
- (void)ySSHomeHeaderViewDidClickMoneyLesson:(YSSHomeHeaderView *)ySSHomeHeaderView;

@end

@interface YSSHomeHeaderView : UIView

@property (nonatomic, strong) UIView *bottomView;

@property (nonatomic, weak) id<YSSHomeHeaderViewDelegate> delegate;

/** 余额 */
- (void)configBalanceWithData:(NSDictionary *)data;

/** 订单数 */
- (void)configOrderNumWithData:(NSDictionary *)data;

@end
