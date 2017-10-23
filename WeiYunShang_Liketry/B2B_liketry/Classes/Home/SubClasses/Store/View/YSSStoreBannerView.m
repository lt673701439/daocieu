//
//  YSSStoreBannerView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSStoreBannerView.h"

@interface YSSStoreBannerView ()
@property (nonatomic, strong) UIButton *preselectBtn;
@property (nonatomic, strong) UIImageView *selideView;
@end

@implementation YSSStoreBannerView

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
    
    UIButton *cardBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    self.preselectBtn = cardBtn;
//    self.preselectBtn = serviceBtn;
    [cardBtn addTarget:self action:@selector(typeBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [cardBtn setTitle:@"我的名片" forState:0];
    [cardBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    cardBtn.titleLabel.font = YSSBoldSystemFont(Value(12));
    [self addSubview:cardBtn];
    [cardBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(44));
        make.centerY.equalTo(self);
    }];
    
    UIButton *infoBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [infoBtn addTarget:self action:@selector(typeBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [infoBtn setTitle:@"我的资料" forState:0];
    [infoBtn setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    infoBtn.titleLabel.font = YSSBoldSystemFont(Value(12));
    [self addSubview:infoBtn];
    [infoBtn makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.centerY.equalTo(self);
    }];
    
    UIButton *identityBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [identityBtn addTarget:self action:@selector(typeBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [identityBtn setTitle:@"认证审核" forState:0];
    [identityBtn setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    identityBtn.titleLabel.font = YSSBoldSystemFont(Value(12));
    [self addSubview:identityBtn];
    [identityBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(- Value(44));
        make.centerY.equalTo(self);
    }];
    
    UIImageView *selideView = [[UIImageView alloc] init];
    self.selideView = selideView;
    selideView.image = [UIImage imageNamed:@"选择"];
    [self addSubview:selideView];
    [selideView makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self);
        make.centerX.equalTo(cardBtn);
    }];
    
}

#pragma mark - action
- (void)typeBtnClick:(UIButton *)button
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
    
    if ([self.delegate respondsToSelector:@selector(ySSStoreBannerView:didClickTypeBtnWithIndex:)]) {
        [self.delegate ySSStoreBannerView:self didClickTypeBtnWithIndex:index];
    }
}

- (void)updateUIWithIndex:(NSInteger)index
{
    [self typeBtnClick:self.subviews[index]];
}

@end
