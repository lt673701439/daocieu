//
//  YSSManagerCardFooterView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/10.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSManagerCardFooterView.h"

@implementation YSSManagerCardFooterView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    UIView *mainView = [[UIView alloc] init];
    mainView.backgroundColor = [UIColor whiteColor];
    [self addSubview:mainView];
    [mainView makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(self).offset(Value(14));
//        make.right.equalTo(self).offset(- Value(14));
        make.centerX.equalTo(self);
        make.width.equalTo(@(ScreenW - Value(28)));
        make.top.bottom.equalTo(self);
    }];
    
    [mainView addShadow:[UIColor blackColor] shadowOffset:CGSizeMake(0, 0) shadowOpacity:0.1 shadowRadius:10];
    mainView.layer.cornerRadius = 5;
    
    UIImageView *addImgView = [[UIImageView alloc] init];
    addImgView.image = [UIImage imageNamed:@"添加蓝色"];
    [mainView addSubview:addImgView];
    [addImgView makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(mainView);
        make.left.equalTo(mainView).offset(Value(14));
    }];
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.text = @"添加银行卡";
    titleLabel.textColor = [UIColor lightGrayColor];
    titleLabel.font = YSSSystemFont(Value(14));
    [mainView addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(addImgView.right).offset(Value(14));
        make.centerY.equalTo(mainView);;
    }];
    
}

@end
