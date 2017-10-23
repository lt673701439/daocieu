//
//  YSSManagerCardListCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/10.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSManagerCardListCell.h"

#import "YSSCardModel.h"

@interface YSSManagerCardListCell ()
@property (nonatomic, strong) UIImageView *mainBgView;
@property (nonatomic, strong) UIImageView *iconView;
@property (nonatomic, strong) UILabel *cardNameLabel;
@property (nonatomic, strong) UILabel *cardTypeLabel;
@property (nonatomic, strong) UILabel *cardNumLabel;
@end

@implementation YSSManagerCardListCell

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
    
    UIImageView *mainBgView = [[UIImageView alloc] init];
    self.mainBgView = mainBgView;
    mainBgView.image = [UIImage imageNamed:@"招商银行"];
    mainBgView.backgroundColor = [UIColor redColor];
    [mainBgView addCornerRadius:5 borderColor:nil borderWidth:0];
    [self.contentView addSubview:mainBgView];
    [mainBgView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.top.equalTo(self);
        make.right.equalTo(self).offset(- Value(14));
        make.bottom.equalTo(self).offset(- Value(5));
    }];
    
    UIImageView *iconView = [[UIImageView alloc] init];
    self.iconView = iconView;
//    iconView.backgroundColor = YSSRandomColor;
    [self.contentView addSubview:iconView];
    [iconView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(28));
        make.top.equalTo(self).offset(Value(10));
        make.width.height.equalTo(@(Value(40)));
    }];
    
    UILabel *cardNameLabel = [[UILabel alloc] init];
    self.cardNameLabel = cardNameLabel;
    cardNameLabel.textColor = [UIColor whiteColor];
    cardNameLabel.font = YSSBoldSystemFont(Value(16));
    [self.contentView addSubview:cardNameLabel];
    [cardNameLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(iconView.right).offset(Value(10));
        make.top.equalTo(iconView).offset(Value(3));
    }];
    
    UILabel *cardTypeLabel = [[UILabel alloc] init];
    self.cardTypeLabel = cardTypeLabel;
    cardTypeLabel.textColor = [UIColor whiteColor];
    cardTypeLabel.font = YSSSystemFont(Value(14));
    [self.contentView addSubview:cardTypeLabel];
    [cardTypeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(cardNameLabel);
        make.top.equalTo(cardNameLabel.bottom).offset(Value(2));
    }];
    
    UILabel *cardNumLabel = [[UILabel alloc] init];
    self.cardNumLabel = cardNumLabel;
    cardNumLabel.textColor = [UIColor whiteColor];
    cardNumLabel.font = YSSBoldSystemFont(Value(26));
    [self.contentView addSubview:cardNumLabel];
    [cardNumLabel makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self).offset(- Value(16));
        make.right.equalTo(self).offset(- Value(35));
    }];
}

- (void)setModel:(YSSCardModel *)model
{
    _model = model;
    
    self.cardNameLabel.text = model.bcBankName;
    self.cardTypeLabel.text = model.bcBankType;
    self.cardNumLabel.text = [NSString stringWithFormat:@"%@", [model.bcBankNumber substringWithRange:NSMakeRange(model.bcBankNumber.length - 4, 4)]];
}

@end
