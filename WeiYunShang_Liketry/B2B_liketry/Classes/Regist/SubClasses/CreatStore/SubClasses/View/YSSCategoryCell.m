//
//  YSSCategoryCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSCategoryCell.h"

#import "YSSServerContentModel.h"

@interface YSSCategoryCell ()
@property (nonatomic, strong) UIImageView *iconView;
@property (nonatomic, strong) UILabel *label;
@end

@implementation YSSCategoryCell

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
    [self addCornerRadius:0 borderColor:[UIColor colorWithHexString:YSSLineDarkColor] borderWidth:0.25];
    
    UIImageView *iconView = [[UIImageView alloc] init];
    self.iconView = iconView;
    iconView.image = [UIImage imageNamed:@"餐饮"];
    [self.contentView addSubview:iconView];
    [iconView makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(self).offset(Value(25));
    }];
    
    UILabel *label = [[UILabel alloc] init];
    self.label = label;
    label.text = @"餐饮";
    label.font = YSSSystemFont(Value(14));
    [self.contentView addSubview:label];
    [label makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(iconView.bottom).offset(Value(10));
    }];
}

- (void)configUIWithData:(NSDictionary *)data
{
    self.iconView.image = [UIImage imageNamed:data[@"icon"]];
    self.label.text = data[@"title"];
}

- (void)setModel:(YSSServerContentModel *)model
{
    _model = model;
    
    self.iconView.image = [UIImage imageNamed:model.iconName];
    self.label.text = model.text;
}

@end
