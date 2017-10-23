//
//  YSSChooseDateHeaderView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/12.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSChooseDateHeaderView.h"

@interface YSSChooseDateHeaderView ()
@property (nonatomic, strong) UILabel *beginTimeLabel;
@property (nonatomic, strong) UILabel *endTimeLabel;
@end

@implementation YSSChooseDateHeaderView

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
    
    UILabel *beginTitle = [[UILabel alloc] init];
    beginTitle.text = @"开始日期";
    beginTitle.textColor = [UIColor lightGrayColor];
    beginTitle.font = YSSSystemFont(Value(12));
    [self addSubview:beginTitle];
    [beginTitle makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self).offset(Value(45));
        make.left.equalTo(self).offset(Value(60));
    }];
    
    UILabel *beginTimeLabel = [[UILabel alloc] init];
    self.beginTimeLabel = beginTimeLabel;
    beginTimeLabel.text = @"请选择日期";
    beginTimeLabel.font = YSSBoldSystemFont(Value(14));
    [self addSubview:beginTimeLabel];
    [beginTimeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(beginTitle.bottom).offset(Value(20));
        make.centerX.equalTo(beginTitle);
    }];
    
    UIView *verticalLine = [[UIView alloc] init];
    verticalLine.backgroundColor = [UIColor colorWithHexString:YSSLineDarkColor];
    [self addSubview:verticalLine];
    [verticalLine makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(self).offset(Value(32));
        make.width.equalTo(@0.5);
        make.height.equalTo(@(Value(70)));
    }];
    
    UILabel *endTitle = [[UILabel alloc] init];
    endTitle.text = @"结束日期";
    endTitle.textColor = [UIColor lightGrayColor];
    endTitle.font = YSSSystemFont(Value(12));
    [self addSubview:endTitle];
    [endTitle makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self).offset(Value(45));
        make.right.equalTo(self).offset(- Value(60));
    }];
    
    UILabel *endTimeLabel = [[UILabel alloc] init];
    self.endTimeLabel = endTimeLabel;
    endTimeLabel.text = @"请选择日期";
    endTimeLabel.font = YSSBoldSystemFont(Value(14));
    [self addSubview:endTimeLabel];
    [endTimeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(endTitle.bottom).offset(Value(20));
        make.centerX.equalTo(endTitle);
    }];
}

- (void)configUIWithBeginDate:(NSDate *)beginDate endDate:(NSDate *)endDate
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = @"yyyy-MM-dd";
    self.beginTimeLabel.text = ((NSString *)[formatter stringFromDate:beginDate]).length > 0 ? [formatter stringFromDate:beginDate] : @"请选择日期";
    self.endTimeLabel.text = ((NSString *)[formatter stringFromDate:endDate]).length > 0 ? [formatter stringFromDate:endDate] : @"请选择日期";
}

@end
