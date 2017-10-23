//
//  YSSDataMoreCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSDataMoreCell.h"

@implementation YSSDataMoreCell

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
    
    UILabel *titlelabel = [[UILabel alloc] init];
    titlelabel.text = @"敬请期待";
    titlelabel.font = YSSSystemFont(Value(14));
    [self.contentView addSubview:titlelabel];
    [titlelabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(self).offset(Value(30));
    }];
    
    UILabel *subTitlelabel = [[UILabel alloc] init];
    subTitlelabel.text = @"COMING SOON";
    subTitlelabel.textColor = [UIColor lightGrayColor];
    subTitlelabel.font = YSSSystemFont(Value(9));
    [self.contentView addSubview:subTitlelabel];
    [subTitlelabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(titlelabel.bottom).offset(Value(5));
    }];
}

@end
