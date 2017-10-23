//
//  YSSIdentifyCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSIdentifyCell.h"

@interface YSSIdentifyCell ()

@end

@implementation YSSIdentifyCell

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
    self.separatorInset = UIEdgeInsetsZero;
    self.selectionStyle = 0;
    
    UILabel *leftLabel = [[UILabel alloc] init];
    self.leftLabel = leftLabel;
    leftLabel.font = YSSBoldSystemFont(Value(12));
    [self.contentView addSubview:leftLabel];
    [leftLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.centerY.equalTo(self);
    }];
    
    UIImageView *rightImgView = [[UIImageView alloc] init];
    self.rightImgView = rightImgView;
    rightImgView.image = [UIImage imageNamed:@"Globle_ArrowR"];
    [self.contentView addSubview:rightImgView];
    [rightImgView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(- Value(14));
        make.centerY.equalTo(self);
    }];
    
    UILabel *rightLabel = [[UILabel alloc] init];
    self.rightLabel = rightLabel;
    rightLabel.font = YSSSystemFont(Value(12));
    rightLabel.textColor = [UIColor lightGrayColor];
    [self.contentView addSubview:rightLabel];
    [rightLabel makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(rightImgView.left).offset(- Value(14));
        make.centerY.equalTo(self);
        make.width.lessThanOrEqualTo(Value(260));
    }];
}

- (void)confiUIWithData:(NSDictionary *)data
{
    self.leftLabel.text = data[@"title"];
    self.rightLabel.text = data[@"content"];
    self.rightImgView.hidden = [data[@"isHidden"] integerValue];
    if ([data[@"isHidden"] integerValue]) {
        [self.rightLabel remakeConstraints:^(MASConstraintMaker *make) {
            make.right.equalTo(self).offset(- Value(14));
            make.centerY.equalTo(self);
            make.width.lessThanOrEqualTo(Value(260));
        }];
    }
}

- (void)confiUIWithTitle:(NSString *)str
{
    self.leftLabel.text = str;
}

@end
