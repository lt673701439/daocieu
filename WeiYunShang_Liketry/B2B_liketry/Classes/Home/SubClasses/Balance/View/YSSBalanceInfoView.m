//
//  YSSBalanceInfoView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBalanceInfoView.h"

#import "DPScrollNumberLabel.h"
#import "UICountingLabel.h"

@implementation YSSBalanceInfoView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    self.backgroundColor = [UIColor whiteColor];
    
    UIImageView *coinView = [[UIImageView alloc] init];
    coinView.image = [YSSMerChanModel sharedInstence].merchantBalance == 0 ? [UIImage imageNamed:@"灰色金币"] :  [UIImage imageNamed:@"钱"];
    [self addSubview:coinView];
    [coinView makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(self).offset(Value(30));
        make.height.width.equalTo(@(Value(50)));
    }];
    
//    NSArray *familyNames = [UIFont familyNames];
//    for( NSString *familyName in familyNames )
//    {
//        printf( "Family: %s \n", [familyName UTF8String]);
//        
//        NSArray *fontNames = [UIFont fontNamesForFamilyName:familyName];
//        for( NSString *fontName in fontNames )
//        {
//            printf( "\tFont: %s \n", [fontName UTF8String] );
//        }
//    }
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.text = @"账户余额";
    titleLabel.textColor = [UIColor lightGrayColor];
    titleLabel.font = YSSSystemFont(Value(14));
    [self addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(coinView.bottom).offset(Value(20));
        make.centerX.equalTo(self);
    }];
    
    
    UICountingLabel *myLabel = [[UICountingLabel alloc] initWithFrame:CGRectMake(0, Value(130), ScreenW - Value(28), 45)];
    myLabel.textAlignment = NSTextAlignmentCenter;
//    myLabel.font = [UIFont fontWithName:@"Avenir Next" size:Value(36)];
    myLabel.font = YSSBoldSystemFont(Value(30));
    myLabel.textColor = [UIColor blackColor];
    [self addSubview:myLabel];
    //设置格式
    myLabel.format = @"%.2f";
    //设置变化范围及动画时间
    CGFloat number = [YSSMerChanModel sharedInstence].merchantBalance;
    [myLabel countFrom:0.0 to:number withDuration:0.8f];
    myLabel.formatBlock = ^NSString *(CGFloat value) {
        return [NSString stringWithFormat:@"￥%.2lf", value];
    };

    
    UIButton *withdrawBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [withdrawBtn addTarget:self action:@selector(withdrawBtn) forControlEvents:UIControlEventTouchUpInside];
    [withdrawBtn setTitle:@"提现" forState:UIControlStateNormal];
    withdrawBtn.titleLabel.font = YSSBoldSystemFont(Value(15));
    if ([YSSMerChanModel sharedInstence].merchantBalance == 0) {
        withdrawBtn.backgroundColor = [UIColor lightGrayColor];
    }else{
        [withdrawBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:UIControlStateNormal];
    }
    [self addSubview:withdrawBtn];
    [withdrawBtn makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(myLabel.bottom).offset(Value(50));
        make.centerX.equalTo(self);
        make.width.equalTo(@(Value(200)));
        make.height.equalTo(@(Value(50)));
    }];
    
    UIButton *myCardBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [myCardBtn addTarget:self action:@selector(myCardBtn) forControlEvents:UIControlEventTouchUpInside];
    [myCardBtn setTitle:@"我的银行卡 >" forState:UIControlStateNormal];
    [myCardBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    myCardBtn.titleLabel.font = YSSSystemFont(Value(14));
    [self addSubview:myCardBtn];
    [myCardBtn makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(withdrawBtn.bottom).offset(Value(14));
        make.centerX.equalTo(self);
    }];
}

/** 点击提现 */
- (void)withdrawBtn
{
    if ([YSSMerChanModel sharedInstence].merchantBalance < 10) {
        [YSSCommonTool showInfoWithStatus:@"余额小于10元无法提现"];
        return;
    }
    
    if ([self.delegate respondsToSelector:@selector(yssBalanceInfoViewDidClickWithdraw:)]) {
        [self.delegate yssBalanceInfoViewDidClickWithdraw:self];
    }
}

/** 点击银行卡 */
- (void)myCardBtn
{
    if ([self.delegate respondsToSelector:@selector(yssBalanceInfoViewDidClickCard:)]) {
        [self.delegate yssBalanceInfoViewDidClickCard:self];
    }
}

@end
