//
//  YSSMessageBannerView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMessageBannerView.h"

#import "YSSMsgTypeButton.h"

@interface YSSMessageBannerView ()
@property (nonatomic, strong) YSSMsgTypeButton *preselectBtn;
@property (nonatomic, strong) UIImageView *selideView;
@end

@implementation YSSMessageBannerView

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
    
    YSSMsgTypeButton *serviceBtn = [YSSMsgTypeButton buttonWithType:UIButtonTypeCustom];
    self.preselectBtn = serviceBtn;
    serviceBtn.redDotView.hidden = YES;
    [serviceBtn addTarget:self action:@selector(typeBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [serviceBtn setTitle:@"服务消息" forState:0];
    [serviceBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    serviceBtn.titleLabel.font = YSSBoldSystemFont(Value(12));
    [self addSubview:serviceBtn];
    [serviceBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(44));
        make.centerY.equalTo(self);
    }];
    
    YSSMsgTypeButton *activityBtn = [YSSMsgTypeButton buttonWithType:UIButtonTypeCustom];
    [activityBtn addTarget:self action:@selector(typeBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [activityBtn setTitle:@"活动消息" forState:0];
    [activityBtn setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    activityBtn.titleLabel.font = YSSBoldSystemFont(Value(12));
    [self addSubview:activityBtn];
    [activityBtn makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.centerY.equalTo(self);
    }];
    
    YSSMsgTypeButton *noticeBtn = [YSSMsgTypeButton buttonWithType:UIButtonTypeCustom];
    [noticeBtn addTarget:self action:@selector(typeBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [noticeBtn setTitle:@"公告消息" forState:0];
    [noticeBtn setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    noticeBtn.titleLabel.font = YSSBoldSystemFont(Value(12));
    [self addSubview:noticeBtn];
    [noticeBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(- Value(44));
        make.centerY.equalTo(self);
    }];
    
    UIImageView *selideView = [[UIImageView alloc] init];
    self.selideView = selideView;
    selideView.image = [UIImage imageNamed:@"选择"];
    [self addSubview:selideView];
    [selideView makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self);
        make.centerX.equalTo(serviceBtn);
    }];
}

#pragma mark - action
- (void)typeBtnClick:(YSSMsgTypeButton *)button
{
    [_selideView remakeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self);
        make.centerX.equalTo(button);
    }];
    
    [UIView animateWithDuration:0.25 animations:^{
        [self layoutIfNeeded];
    }];
    
    [self.preselectBtn setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    [button setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    self.preselectBtn = button;
    
    NSInteger index = [self.subviews indexOfObject:button];
//    YSSLog(@"index ===  %ld", index);
    if ([self.delegate respondsToSelector:@selector(ySSMessageBannerView:didClickTypeBtnWithIndex:)]) {
        [self.delegate ySSMessageBannerView:self didClickTypeBtnWithIndex:index];
    }
}

- (void)updateUIWithIndex:(NSInteger)index
{
    [self typeBtnClick:self.subviews[index]];
}

@end
