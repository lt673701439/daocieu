//
//  YSSVerticalButton.m
//  Blessings_liketry
//
//  Created by GentleZ on 2017/7/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSVerticalButton.h"

@implementation YSSVerticalButton

- (void)layoutSubviews
{
    [super layoutSubviews];
    
    self.imageView.yss_centerX = self.yss_width * 0.5;
    self.imageView.yss_y = Value(_image_topOffset);
    
    [self.titleLabel sizeToFit];
    self.titleLabel.yss_centerX = self.yss_width * 0.5;
    self.titleLabel.yss_y = CGRectGetMaxY(self.imageView.frame) + Value(_title_topOffset);
    
}

//扩大点击范围
- (BOOL)pointInside:(CGPoint)point withEvent:(UIEvent *)event
{
    CGRect bounds =self.bounds;
    
    CGFloat widthDelta = 44.0 - bounds.size.width;
    
    CGFloat heightDelta = 44.0 - bounds.size.height;
    
    bounds = CGRectInset(bounds, - 0.5 * widthDelta, - 0.5 * heightDelta);//注意这里是负数，扩大了之前的bounds的范围
    
    return CGRectContainsPoint(bounds, point);
}

@end
