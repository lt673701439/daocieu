//
//  YSSChooseLocationHeaderView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSChooseLocationHeaderView;

@protocol YSSChooseLocationHeaderViewDelegate <NSObject>

/** 搜索文字改变 */
- (void)yssChooseLocationHeaderView:(YSSChooseLocationHeaderView *)yssChooseLocationHeaderView searchDidChanged:(NSString *)searchText;

/** 点击地理位置 */
- (void)yssChooseLocationHeaderView:(YSSChooseLocationHeaderView *)yssChooseLocationHeaderView didClickLabel:(NSString *)text;

@end

@interface YSSChooseLocationHeaderView : UIView

- (void)configUIWithData:(NSString *)city;

@property (nonatomic, weak) id<YSSChooseLocationHeaderViewDelegate> delegate;

@end
