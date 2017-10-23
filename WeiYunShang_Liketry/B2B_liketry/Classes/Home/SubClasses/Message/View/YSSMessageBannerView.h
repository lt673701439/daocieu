//
//  YSSMessageBannerView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSMessageBannerView;

@protocol YSSMessageBannerViewDelegate <NSObject>

- (void)ySSMessageBannerView:(YSSMessageBannerView *)ySSMessageBannerView didClickTypeBtnWithIndex:(NSInteger)index;

@end

@interface YSSMessageBannerView : UIView

@property (nonatomic, weak) id<YSSMessageBannerViewDelegate> delegate;

- (void)updateUIWithIndex:(NSInteger)index;

@end
