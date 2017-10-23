//
//  YSSOrderListBannerView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/12.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSOrderListBannerView.h"

@interface YSSOrderListBannerView ()

@property (nonatomic, strong) UILabel *timeLabel;
@property (nonatomic, strong) UILabel *priceLabel;

@end

@implementation YSSOrderListBannerView

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
    
    UIImageView *clockView = [[UIImageView alloc] init];
    clockView.image = [UIImage imageNamed:@"选择时间"];
    [self addSubview:clockView];
    [clockView makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(self);
        make.left.equalTo(self).offset(Value(20));
//        make.width.height.equalTo(@(Value(20)));
    }];
    
    UILabel *timeLabel = [[UILabel alloc] init];
    self.timeLabel = timeLabel;
    timeLabel.textColor = [UIColor lightGrayColor];
    timeLabel.font = YSSSystemFont(Value(14));
    timeLabel.text = [YSSCommonTool getTodayDate:YSSDateFormatterTypeDot];
    [self addSubview:timeLabel];
    [timeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(clockView.right).offset(Value(10));
        make.centerY.equalTo(self);
    }];
    
    UILabel *priceLabel = [[UILabel alloc] init];
    self.priceLabel = priceLabel;
    priceLabel.text = @"收入金额  ￥0";
    priceLabel.textColor = [UIColor blackColor];
    priceLabel.font = YSSSystemFont(Value(14));
    [self addSubview:priceLabel];
    [priceLabel makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(- Value(20));
        make.centerY.equalTo(self);
    }];
    
    UIImageView *moneyView = [[UIImageView alloc] init];
    moneyView.image = [UIImage imageNamed:@"账单钱"];
    [self addSubview:moneyView];
    [moneyView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(priceLabel.left).offset(- Value(10));
        make.centerY.equalTo(self.centerY);
//        make.width.height.equalTo(@(Value(20)));
    }];
}

- (void)configUIWithPrice:(NSString *)price
{
    NSString *text = [NSString stringWithFormat:@"收入金额  ￥%@", price];
    NSMutableAttributedString *attr = [[NSMutableAttributedString alloc] initWithString:text];
    [attr addAttribute:NSForegroundColorAttributeName value:[UIColor lightGrayColor] range:[text rangeOfString:@"收入金额"]];
    self.priceLabel.attributedText = attr;
}

- (void)configUIWithStartDate:(NSDate *)startDate endDate:(NSDate *)endDate
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = @"yyyy.MM.dd";
    
    self.timeLabel.text = [startDate isEqualToDate:endDate] ? [NSString stringWithFormat:@"%@", [formatter stringFromDate:startDate]] : [NSString stringWithFormat:@"%@ - %@", [formatter stringFromDate:startDate], [formatter stringFromDate:endDate]];
}

@end
