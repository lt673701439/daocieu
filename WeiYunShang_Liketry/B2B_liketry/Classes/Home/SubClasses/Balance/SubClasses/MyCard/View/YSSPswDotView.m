//
//  YSSPswDotView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSPswDotView.h"

@implementation YSSPswDotView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    [self addCornerRadius:0 borderColor:[UIColor colorWithHexString:YSSLineDarkColor] borderWidth:0.5];
    
    UIView *circleView = [[UIView alloc] init];
    self.circleView = circleView;
    circleView.hidden = YES;
    [circleView addCornerRadius:Value(7.5) borderColor:nil borderWidth:0];
    circleView.backgroundColor = [UIColor blackColor];
    [self addSubview:circleView];
    [circleView makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.centerY.equalTo(self);
        make.width.height.equalTo(Value(15));
    }];
}

@end
