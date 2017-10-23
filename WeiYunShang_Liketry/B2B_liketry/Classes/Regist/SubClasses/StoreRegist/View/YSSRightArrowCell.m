//
//  YSSRightArrowCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSRightArrowCell.h"

@interface YSSRightArrowCell ()
@property (nonatomic, strong) UILabel *leftLabel;
@property (nonatomic, strong) UIImageView *imgView;
@end

@implementation YSSRightArrowCell

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
    leftLabel.font = YSSBoldSystemFont(Value(14));
    [self.contentView addSubview:leftLabel];
    [leftLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.centerY.equalTo(self);
        make.width.equalTo(@(Value(80)));
    }];
    
    UIImageView *imgView = [[UIImageView alloc] init];
    self.imgView = imgView;
    imgView.image = [UIImage imageNamed:@"Globle_ArrowR"];
    [self.contentView addSubview:imgView];
    [imgView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(- Value(14));
        make.centerY.equalTo(self);
    }];
}

- (void)configUIWithData:(NSDictionary *)data
{
    self.leftLabel.text = data[@"title"];
    self.imgView.hidden = ![data[@"isShow"] boolValue];
}

@end
