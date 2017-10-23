//
//  YSSChooseLocationCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSChooseLocationCell.h"

@interface YSSChooseLocationCell ()
@property (nonatomic, strong) UILabel *charaterlabel;
@property (nonatomic, strong) UILabel *titlelabel;
@end

@implementation YSSChooseLocationCell

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
    self.selectionStyle = 0;
    self.separatorInset = UIEdgeInsetsMake(0, Value(40), 0, Value(14));
    
    UILabel *charaterlabel = [[UILabel alloc] init];
    self.charaterlabel = charaterlabel;
    charaterlabel.font = YSSSystemFont(Value(14));
    [self.contentView addSubview:charaterlabel];
    [charaterlabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(21));
        make.centerY.equalTo(self);
    }];
    
    UILabel *titlelabel = [[UILabel alloc] init];
    self.titlelabel = titlelabel;
    titlelabel.font = YSSSystemFont(Value(14));
    [self.contentView addSubview:titlelabel];
    [titlelabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(40));
        make.centerY.equalTo(self);
    }];
}

- (void)configUIWithTitle:(NSString *)title charater:(NSString *)charater isFirst:(BOOL)isFirst
{
    self.titlelabel.text = title;
    self.charaterlabel.text = charater;
    self.charaterlabel.hidden = !isFirst;
}

@end
