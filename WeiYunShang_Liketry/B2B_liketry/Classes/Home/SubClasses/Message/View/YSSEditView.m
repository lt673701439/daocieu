//
//  YSSEditView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSEditView.h"

@implementation YSSEditView

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
    
    UIButton *selectBtn = [UIButton buttonWithType: UIButtonTypeCustom];
    [self addSubview:selectBtn];
    [selectBtn addTarget:self action:@selector(fullSelect:) forControlEvents:UIControlEventTouchUpInside];
    [selectBtn setImage:[UIImage imageNamed:@"Globle_check_N"] forState:UIControlStateNormal];
    [selectBtn setImage:[UIImage imageNamed:@"Globle_check_H"] forState:UIControlStateSelected];
    [selectBtn setTitle:@"全选" forState:UIControlStateNormal];
    selectBtn.titleLabel.font = YSSSystemFont(Value(14));
    selectBtn.imageEdgeInsets = UIEdgeInsetsMake(0, 0, 0, Value(14));
    [selectBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.bottom.equalTo(self);
        make.width.equalTo(@(Value(95)));
    }];
    
    UIView *lineView = [[UIView alloc] init];
    lineView.backgroundColor = [UIColor whiteColor];
    [self addSubview:lineView];
    [lineView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(selectBtn.right).offset(Value(2));
        make.centerY.equalTo(self);
        make.width.equalTo(@0.5);
        make.height.equalTo(self).offset(- Value(20));
    }];
    
    UIButton *delBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [delBtn addTarget:self action:@selector(delete:) forControlEvents:UIControlEventTouchUpInside];
    [delBtn setImage:[UIImage imageNamed:@"垃圾桶"] forState:UIControlStateNormal];
    [self addSubview:delBtn];
    [delBtn makeConstraints:^(MASConstraintMaker *make) {
        make.top.right.bottom.equalTo(self);
        make.left.equalTo(selectBtn.right).offset(Value(5));
    }];
}

#pragma mark - action
- (void)fullSelect:(UIButton *)button
{
    button.selected = !button.isSelected;
    if ([self.delegate respondsToSelector:@selector(yssEditView:didClickFullSelectBtn:)]) {
        [self.delegate yssEditView:self didClickFullSelectBtn:button];
    }
}

- (void)delete:(UIButton *)button
{
    if ([self.delegate respondsToSelector:@selector(yssEditView:didClickDeleteBtn:)]) {
        [self.delegate yssEditView:self didClickDeleteBtn:button];
    }
}

@end
