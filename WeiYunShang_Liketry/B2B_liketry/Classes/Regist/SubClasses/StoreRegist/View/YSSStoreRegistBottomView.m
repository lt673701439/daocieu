//
//  YSSStoreRegistBottomView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSStoreRegistBottomView.h"

@implementation YSSStoreRegistBottomView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    UIImageView *bgImgView = [[UIImageView alloc] init];
    bgImgView.image = [UIImage imageNamed:@"按钮"];
    [self addSubview:bgImgView];
    [bgImgView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self);
    }];
    
    UIButton *selectBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [selectBtn setBackgroundImage:[UIImage imageNamed:@"全选"] forState:0];
    [selectBtn setImage:[UIImage imageNamed:@"全选1"] forState:0];
    [self addSubview:selectBtn];
    [selectBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.centerY.equalTo(self);
    }];
    
    NSString *text = @"我同意并遵守《商家服务协议》";
    NSMutableAttributedString *attr = [[NSMutableAttributedString alloc] initWithString:text];
    [attr addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithHexString:YSSBlueColor] range:[text rangeOfString:@"《商家服务协议》"]];
    
    UILabel *label = [[UILabel alloc] init];
    label.font = YSSSystemFont(Value(14));
    label.textColor = [UIColor whiteColor];
    label.attributedText = attr;
    [self addSubview:label];
    [label makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(self);
        make.left.equalTo(selectBtn.right).offset(Value(10));
    }];
    
    UIButton *nextStepBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [nextStepBtn addTarget:self action:@selector(nextStep) forControlEvents:UIControlEventTouchUpInside];
    [nextStepBtn setTitle:@"下一步" forState:UIControlStateNormal];
    nextStepBtn.titleLabel.font = YSSBoldSystemFont(Value(16));
    [self addSubview:nextStepBtn];
    [nextStepBtn makeConstraints:^(MASConstraintMaker *make) {
        make.top.right.bottom.equalTo(self);
        make.width.equalTo(@(Value(100)));
    }];
}

- (void)nextStep
{
    if ([self.delegate respondsToSelector:@selector(yssStoreRegistBottomViewDidClickNextStep:)]) {
        [self.delegate yssStoreRegistBottomViewDidClickNextStep:self];
    }
}

@end
