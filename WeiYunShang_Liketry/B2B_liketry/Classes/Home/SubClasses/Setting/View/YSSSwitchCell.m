//
//  YSSSwitchCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSSwitchCell.h"

@interface YSSSwitchCell ()
@property (nonatomic, strong) UILabel *leftLabel;
@end

@implementation YSSSwitchCell

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
    
//    UISwitch *msgSwitch = [[UISwitch alloc] init];
//    msgSwitch.onTintColor = [UIColor redColor];
//    [self.contentView addSubview:msgSwitch];
//    [msgSwitch makeConstraints:^(MASConstraintMaker *make) {
//        make.right.equalTo(self).offset(- Value(14));
//        make.centerY.equalTo(self);
//    }];
    
    UILabel *rightLabel = [[UILabel alloc] init];
    
    UIUserNotificationSettings *setting = [[UIApplication sharedApplication] currentUserNotificationSettings];
    if (setting.types == UIUserNotificationTypeNone) {
        //推送通知未打开
        rightLabel.text = @"未启用";
    }else{
        rightLabel.text = @"已启用";
    }
    
    
    rightLabel.textColor = [UIColor lightGrayColor];
    rightLabel.font = YSSSystemFont(Value(12));
    [self.contentView addSubview:rightLabel];
    [rightLabel makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(- Value(14));
        make.centerY.equalTo(self);
    }];
}

- (void)confiUIWithTitle:(NSString *)str
{
    self.leftLabel.text = str;
}

@end
