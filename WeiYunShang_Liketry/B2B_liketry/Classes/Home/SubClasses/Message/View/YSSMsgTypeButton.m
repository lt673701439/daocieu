
//
//  YSSMsgTypeButton.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMsgTypeButton.h"

@interface YSSMsgTypeButton ()

@end

@implementation YSSMsgTypeButton

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    UIView *redDotView = [[UIView alloc] init];
    self.redDotView = redDotView;
    redDotView.backgroundColor = [UIColor redColor];
    [redDotView addCornerRadius:Value(2) borderColor:nil borderWidth:0];
    [self addSubview:redDotView];
    
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    [self.redDotView remakeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.titleLabel.right);
        make.centerY.equalTo(self.titleLabel.top);
        make.width.height.equalTo(Value(4));
    }];
}

@end
