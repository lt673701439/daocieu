//
//  YSSInputCodeView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSInputCodeView.h"

@interface YSSInputCodeView ()
@property (nonatomic, strong) UILabel *firstLabel;
@property (nonatomic, strong) UILabel *secondLabel;
@property (nonatomic, strong) UILabel *thirdLabel;
@property (nonatomic, strong) UILabel *fourthLabel;
@end

@implementation YSSInputCodeView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    UILabel *firstLabel = [[UILabel alloc] init];
    self.firstLabel = firstLabel;
    firstLabel.textAlignment = NSTextAlignmentCenter;
    firstLabel.font = YSSBoldSystemFont(Value(16));
    [self addSubview:firstLabel];
    [firstLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(self);
        make.width.equalTo(Value(Value(35)));
        make.bottom.equalTo(self).offset(- Value(2));
    }];
    
    UIView *firstLine = [[UIView alloc] init];
    firstLine.backgroundColor = [UIColor blackColor];
    [self addSubview:firstLine];
    [firstLine makeConstraints:^(MASConstraintMaker *make) {
        make.width.equalTo(firstLabel);
        make.centerX.equalTo(firstLabel);
        make.bottom.equalTo(self);
        make.height.equalTo(@(Value(2)));
    }];
    
    
    UILabel *secondLabel = [[UILabel alloc] init];
    self.secondLabel = secondLabel;
    secondLabel.textAlignment = NSTextAlignmentCenter;
    secondLabel.font = YSSBoldSystemFont(Value(16));
    [self addSubview:secondLabel];
    [secondLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self);
        make.left.equalTo(firstLabel.right).offset(Value(14));;
        make.width.equalTo(Value(Value(35)));
        make.bottom.equalTo(self).offset(- Value(2));
    }];
    
    UIView *secondLine = [[UIView alloc] init];
    secondLine.backgroundColor = [UIColor blackColor];
    [self addSubview:secondLine];
    [secondLine makeConstraints:^(MASConstraintMaker *make) {
        make.width.equalTo(secondLabel);
        make.centerX.equalTo(secondLabel);
        make.bottom.equalTo(self);
        make.height.equalTo(@(Value(2)));
    }];
    
    UILabel *thirdLabel = [[UILabel alloc] init];
    self.thirdLabel = thirdLabel;
    thirdLabel.textAlignment = NSTextAlignmentCenter;
    thirdLabel.font = YSSBoldSystemFont(Value(16));
    [self addSubview:thirdLabel];
    [thirdLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self);
        make.left.equalTo(secondLabel.right).offset(Value(14));;
        make.width.equalTo(Value(Value(35)));
        make.bottom.equalTo(self).offset(- Value(2));
    }];
    
    UIView *thirdLine = [[UIView alloc] init];
    thirdLine.backgroundColor = [UIColor blackColor];
    [self addSubview:thirdLine];
    [thirdLine makeConstraints:^(MASConstraintMaker *make) {
        make.width.equalTo(thirdLabel);
        make.centerX.equalTo(thirdLabel);
        make.bottom.equalTo(self);
        make.height.equalTo(@(Value(2)));
    }];
    
    UILabel *fourthLabel = [[UILabel alloc] init];
    self.fourthLabel = fourthLabel;
    fourthLabel.textAlignment = NSTextAlignmentCenter;
    fourthLabel.font = YSSBoldSystemFont(Value(16));
    [self addSubview:fourthLabel];
    [fourthLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.right.equalTo(self);
        make.width.equalTo(Value(Value(35)));
        make.bottom.equalTo(self).offset(- Value(2));
    }];
    
    UIView *fourthLine = [[UIView alloc] init];
    fourthLine.backgroundColor = [UIColor blackColor];
    [self addSubview:fourthLine];
    [fourthLine makeConstraints:^(MASConstraintMaker *make) {
        make.width.equalTo(fourthLabel);
        make.centerX.equalTo(fourthLabel);
        make.bottom.equalTo(self);
        make.height.equalTo(@(Value(2)));
    }];
}

- (void)updataNumWithString:(NSString *)str
{
    BOOL hasOne = str.length >= 1;
    BOOL hasTwo = str.length >= 2;
    BOOL hasThree = str.length >= 3;
    BOOL hasFour = str.length >= 4;
    
    self.firstLabel.text = hasOne ? [str substringToIndex:1] : @"";
    self.secondLabel.text = hasTwo ? [str substringWithRange:NSMakeRange(1, 1)] : @"";
    self.thirdLabel.text = hasThree ? [str substringWithRange:NSMakeRange(2, 1)] : @"";
    self.fourthLabel.text = hasFour ? [str substringWithRange:NSMakeRange(3, 1)] : @"";
}

@end
