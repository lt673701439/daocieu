//
//  YSSPayDetailHeaderView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSPayDetailHeaderView;

@protocol YSSPayDetailHeaderViewDelegate <NSObject>

- (void)yssPayDetailHeaderView:(YSSPayDetailHeaderView *)yssPayDetailHeaderView DidClickTypeWithIndex:(NSInteger)index;

@end

@interface YSSPayDetailHeaderView : UIView

@property (nonatomic, weak) id<YSSPayDetailHeaderViewDelegate> delegate;

- (void)updateUIWithIndex:(NSInteger)index;

- (void)configUIWithData:(NSDictionary *)data;

@end
