//
//  YSSBaseShadowCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseShadowCell.h"

@implementation YSSBaseShadowCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    
    UIView *shadowMainView = [[UIView alloc] init];
    self.shadowMainView = shadowMainView;
    shadowMainView.backgroundColor = [UIColor whiteColor];
    [self.contentView addSubview:shadowMainView];
    [shadowMainView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(Value(14));
        make.right.equalTo(self.contentView).offset(- Value(14));
        make.top.equalTo(self.contentView).offset(Value(5));
        make.bottom.equalTo(self.contentView).offset(- Value(1));
    }];
    
    shadowMainView.layer.cornerRadius = 5.0;
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    [self.shadowMainView addShadow:[UIColor lightGrayColor] shadowOffset:CGSizeMake(0, 0) shadowOpacity:0.1 shadowRadius:2];
}

@end
