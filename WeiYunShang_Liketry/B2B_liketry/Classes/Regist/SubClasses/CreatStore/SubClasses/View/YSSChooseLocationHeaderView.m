//
//  YSSChooseLocationHeaderView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSChooseLocationHeaderView.h"

@interface YSSChooseLocationHeaderView ()<UITextFieldDelegate>
@property (nonatomic, strong) UILabel *locationLabel;
@end

@implementation YSSChooseLocationHeaderView

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
    
    UIView *tempView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, Value(30), Value(11))];
    UIImageView *leftView = [[UIImageView alloc] initWithFrame:CGRectMake(Value(15), - Value(1), Value(11), Value(11))];
    leftView.image = [UIImage imageNamed:@"搜索栏"];
    [tempView addSubview:leftView];
    
    
    UITextField *textField = [[UITextField alloc] init];
    [textField addTarget:self action:@selector(inputChanged:) forControlEvents:UIControlEventEditingChanged];
    textField.backgroundColor = [UIColor whiteColor];
    textField.tintColor = [UIColor orangeColor];
    [textField addCornerRadius:Value(15) borderColor:nil borderWidth:0];
    textField.leftView = tempView;
    textField.leftViewMode = UITextFieldViewModeAlways;
    textField.placeholder = @"请输入城市或拼音查询";
    textField.returnKeyType = UIReturnKeyDone;
    textField.delegate = self;
    textField.font = YSSSystemFont(Value(14));
    [self addSubview:textField];
    [textField makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(self).offset(Value(14));
//        make.right.equalTo(self).offset(- Value(14));
        make.centerX.equalTo(self);
        make.width.equalTo(ScreenW - Value(28));
        make.top.equalTo(self).offset(Value(7));
        make.height.equalTo(@(Value(30)));
    }];
    
    UIImageView *locationView = [[UIImageView alloc] init];
    locationView.image = [UIImage imageNamed:@"定点"];
    [self addSubview:locationView];
    [locationView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(textField).offset(Value(15));
        make.top.equalTo(textField.bottom).offset(Value(30));
    }];
    
    UILabel *label = [[UILabel alloc] init];
    label.text = @"GPS定位";
    label.font = YSSBoldSystemFont(Value(14));
    [self addSubview:label];
    [label makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(locationView.right).offset(Value(5));
        make.centerY.equalTo(locationView);
    }];
    
    UILabel *locationLabel = [[UILabel alloc] init];
    self.locationLabel = locationLabel;
    locationLabel.text = @"定位中...";
    locationLabel.font = YSSBoldSystemFont(Value(16));
    locationLabel.textColor = [UIColor orangeColor];
    locationLabel.userInteractionEnabled = YES;
    [locationLabel addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(locationTap:)]];
    [self addSubview:locationLabel];
    [locationLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(label.right).offset(Value(5));
        make.centerY.equalTo(locationView);
    }];
}

- (void)locationTap:(UITapGestureRecognizer *)tapGesture
{
    UILabel *label = (UILabel *)tapGesture.view;
    if ([self.delegate respondsToSelector:@selector(yssChooseLocationHeaderView:didClickLabel:)]) {
        [self.delegate yssChooseLocationHeaderView:self didClickLabel:label.text];
    }
}

- (void)configUIWithData:(NSString *)city
{
    self.locationLabel.text = city;
}

- (void)inputChanged:(UITextField *)textField
{
    if ([self.delegate respondsToSelector:@selector(yssChooseLocationHeaderView:searchDidChanged:)]) {
        [self.delegate yssChooseLocationHeaderView:self searchDidChanged:textField.text];
    }
}

#pragma mark - <UITextFieldDelegate>
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

@end
