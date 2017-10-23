//
//  YSSAlphaView.m
//  Blessings_liketry
//
//  Created by GentleZ on 2017/6/30.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSAlphaView.h"

@implementation YSSAlphaView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    self.userInteractionEnabled = NO;
    
    CAGradientLayer *gradientLayer = [CAGradientLayer layer];
    gradientLayer.colors = @[
                             (__bridge id)[[UIColor lightGrayColor] colorWithAlphaComponent:0.15].CGColor,
                             (__bridge id)[[UIColor lightGrayColor] colorWithAlphaComponent:0.1].CGColor,
                             (__bridge id)[[UIColor lightGrayColor] colorWithAlphaComponent:0.0].CGColor
                             ];
    gradientLayer.locations = @[@0.0, @0.5, @1.0];
    gradientLayer.startPoint = CGPointMake(0, 0);
    gradientLayer.endPoint = CGPointMake(0, 1);
    gradientLayer.frame = self.bounds;
    [self.layer addSublayer:gradientLayer];
}

@end
