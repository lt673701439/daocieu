//
//  YSSStoreRegistHeaderView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSStoreRegistHeaderView.h"

@interface YSSStoreRegistHeaderView ()
@property (nonatomic, strong) UIImageView *leftIconView;
@property (nonatomic, strong) UIImageView *midIconView;
@property (nonatomic, strong) UIImageView *rightIconView;

@property (nonatomic, strong) UIImageView *firstDotView;
@property (nonatomic, strong) UIImageView *secondDotView;
@property (nonatomic, strong) UIImageView *thirdDotView;
@property (nonatomic, strong) UIImageView *fourthDotView;
@end

@implementation YSSStoreRegistHeaderView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    self.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    UIImageView *midIconView = [[UIImageView alloc] init];
    self.midIconView = midIconView;
    midIconView.image = [UIImage imageNamed:@"注册二步透明"];
    [self addSubview:midIconView];
    [midIconView makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(self).offset(Value(43));
    }];
    
    UILabel *midLabel = [[UILabel alloc] init];
    midLabel.text = @"创建门店";
    midLabel.font = YSSBoldSystemFont(Value(14));
    [self addSubview:midLabel];
    [midLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(midIconView);
        make.top.equalTo(midIconView.bottom).offset(Value(20));
    }];
    
    UIImageView *secondDotView = [[UIImageView alloc] init];
    self.secondDotView = secondDotView;
    secondDotView.image = [UIImage imageNamed:@"透明"];
    [self addSubview:secondDotView];
    [secondDotView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(midIconView.left).offset(- Value(14));
        make.centerY.equalTo(midIconView);
        make.width.height.equalTo(@(Value(8)));
    }];
    
    UIImageView *firstDotView = [[UIImageView alloc] init];
    self.firstDotView = firstDotView;
    firstDotView.image = [UIImage imageNamed:@"透明"];
    [self addSubview:firstDotView];
    [firstDotView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(secondDotView.left).offset(- Value(14));
        make.centerY.equalTo(midIconView);
        make.width.height.equalTo(@(Value(8)));
    }];
    
    UIImageView *thirdDotView = [[UIImageView alloc] init];
    self.thirdDotView = thirdDotView;
    thirdDotView.image = [UIImage imageNamed:@"透明"];
    [self addSubview:thirdDotView];
    [thirdDotView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(midIconView.right).offset(Value(14));
        make.centerY.equalTo(midIconView);
        make.width.height.equalTo(@(Value(8)));
    }];
    
    UIImageView *fourthDotView = [[UIImageView alloc] init];
    self.fourthDotView = fourthDotView;
    fourthDotView.image = [UIImage imageNamed:@"透明"];
    [self addSubview:fourthDotView];
    [fourthDotView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(thirdDotView.right).offset(Value(14));
        make.centerY.equalTo(midIconView);
        make.width.height.equalTo(@(Value(8)));
    }];
    
    UIImageView *leftIconView = [[UIImageView alloc] init];
    self.leftIconView = leftIconView;
    leftIconView.image = [UIImage imageNamed:@"注册一步"];
    [self addSubview:leftIconView];
    [leftIconView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(midIconView.left).offset(- Value(60));
        make.top.equalTo(midIconView);
    }];
    
    UILabel *leftLabel = [[UILabel alloc] init];
    leftLabel.text = @"商户注册";
    leftLabel.font = YSSBoldSystemFont(Value(14));
    [self addSubview:leftLabel];
    [leftLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(leftIconView);
        make.top.equalTo(leftIconView.bottom).offset(Value(20));
    }];
    
    UIImageView *rightIconView = [[UIImageView alloc] init];
    self.rightIconView = rightIconView;
    rightIconView.image = [UIImage imageNamed:@"注册第三步透明"];
    [self addSubview:rightIconView];
    [rightIconView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(midIconView.right).offset(Value(60));
        make.top.equalTo(midIconView);
    }];
    
    UILabel *rightLabel = [[UILabel alloc] init];
    rightLabel.text = @"提交资质";
    rightLabel.font = YSSBoldSystemFont(Value(14));
    [self addSubview:rightLabel];
    [rightLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(rightIconView);
        make.top.equalTo(rightIconView.bottom).offset(Value(20));
    }];
}

- (void)configUIWithStep:(NSInteger)index
{
    if (index == 0) {
        
    }else if (index == 1)
    {
        self.midIconView.image = [UIImage imageNamed:@"注册二步"];
        self.firstDotView.image = [UIImage imageNamed:@"已勾选"];
        self.secondDotView.image = [UIImage imageNamed:@"已勾选"];
        
    }else{
        self.midIconView.image = [UIImage imageNamed:@"注册二步"];
        self.firstDotView.image = [UIImage imageNamed:@"已勾选"];
        self.secondDotView.image = [UIImage imageNamed:@"已勾选"];
        
        self.rightIconView.image = [UIImage  imageNamed:@"注册第三步"];
        self.thirdDotView.image = [UIImage imageNamed:@"已勾选"];
        self.fourthDotView.image = [UIImage imageNamed:@"已勾选"];
    }
}

@end
