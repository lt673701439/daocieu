//
//  YSSHeaderInfoView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSHeaderInfoView.h"

#import "UICountingLabel.h"

@interface YSSHeaderInfoView ()

@property (nonatomic, strong) UICountingLabel *titleLable;
@property (nonatomic, strong) UILabel *subTitleLabel;

@end

@implementation YSSHeaderInfoView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    UICountingLabel *titleLable = [[UICountingLabel alloc] init];
    titleLable.format = @"%.2f";
    self.titleLable = titleLable;
    titleLable.text = @"--";
    titleLable.textColor = [UIColor whiteColor];
    titleLable.font = YSSCustomFont(@"Avenir-Black", Value(30));
    titleLable.textAlignment = NSTextAlignmentCenter;
    [self addSubview:titleLable];
    [titleLable makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self).offset(Value(5));
        make.centerX.equalTo(self);
    }];
    
    UILabel *subTitleLabel = [[UILabel alloc] init];
    self.subTitleLabel = subTitleLabel;
    subTitleLabel.textAlignment = NSTextAlignmentCenter;
    subTitleLabel.font = YSSSystemFont(Value(14));
    [self addSubview:subTitleLabel];
    [subTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(titleLable.bottom).offset(Value(5));
        make.centerX.equalTo(titleLable);
    }];
    
    UIImageView *rightImgView = [[UIImageView alloc] init];
    rightImgView.image = [UIImage imageNamed:@"Globle_ArrowR白色"];
    [self addSubview:rightImgView];
    [rightImgView makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(titleLable);
        make.right.equalTo(titleLable).offset(Value(25));
    }];
}

- (void)configUIWithData:(NSDictionary *)data
{
    NSString *title = data[@"title"];
    self.subTitleLabel.text = title;
    if ([title containsString:@"余额"]) {
        self.titleLable.format = @"%.2f";
        [self.titleLable countFrom:0.0 to:[data[@"num"] floatValue] withDuration:0.8f];
    }else{
        self.titleLable.format = @"%ld";
        [self.titleLable countFrom:0 to:[data[@"num"] integerValue] withDuration:0.5f];
    }
}

@end
