//
//  YSSStoreBannerView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSStoreBannerView;

@protocol YSSStoreBannerViewDelegate <NSObject>

- (void)ySSStoreBannerView:(YSSStoreBannerView *)ySSStoreBannerView didClickTypeBtnWithIndex:(NSInteger)index;

@end

@interface YSSStoreBannerView : UIView

@property (nonatomic, weak) id<YSSStoreBannerViewDelegate> delegate;

- (void)updateUIWithIndex:(NSInteger)index;

@end
