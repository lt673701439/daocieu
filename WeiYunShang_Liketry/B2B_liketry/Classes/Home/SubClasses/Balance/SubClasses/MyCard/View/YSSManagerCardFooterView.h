//
//  YSSManagerCardFooterView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/10.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSManagerCardFooterView;

@protocol YSSManagerCardFooterViewDelegate <NSObject>

- (void)yssManagerCardFooterViewDidClickFooterView:(YSSManagerCardFooterView *)yssManagerCardFooterView;

@end

@interface YSSManagerCardFooterView : UIView

@property (nonatomic, weak) id<YSSManagerCardFooterViewDelegate> delegate;

@end
