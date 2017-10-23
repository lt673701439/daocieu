//
//  YSSPayDetailListCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSPayDetailListCell.h"

#import "YSSPayListModel.h"

@interface YSSPayDetailListCell ()
@property (nonatomic, strong) UILabel *payTypeLable;
@property (nonatomic, strong) UILabel *timeLable;
@property (nonatomic, strong) UILabel *priceLabel;
@property (nonatomic, strong) UILabel *subPriceLabel;
@end

@implementation YSSPayDetailListCell

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
    self.separatorInset = UIEdgeInsetsZero;
    
    UILabel *payTypeLable = [[UILabel alloc] init];
    self.payTypeLable = payTypeLable;
    payTypeLable.font = YSSBoldSystemFont(Value(12));
    [self.contentView addSubview:payTypeLable];
    [payTypeLable makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.top.equalTo(self).offset(Value(20));
    }];
    
    UILabel *timeLable = [[UILabel alloc] init];
    self.timeLable = timeLable;
    timeLable.font = YSSSystemFont(Value(12));
    timeLable.textColor = [UIColor lightGrayColor];
    [self.contentView addSubview:timeLable];
    [timeLable makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(payTypeLable);
        make.top.equalTo(payTypeLable.bottom).offset(Value(10));
    }];
    
    UILabel *priceLabel = [[UILabel alloc] init];
    self.priceLabel = priceLabel;
    priceLabel.font = YSSBoldSystemFont(Value(17));
    priceLabel.textColor = [UIColor redColor];
    [self.contentView addSubview:priceLabel];
    [priceLabel makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(- Value(14));
        make.bottom.equalTo(payTypeLable).offset(Value(2));
    }];
    
    UILabel *subPriceLabel = [[UILabel alloc] init];
    self.subPriceLabel = subPriceLabel;
    subPriceLabel.textColor = [UIColor lightGrayColor];
    subPriceLabel.font = YSSSystemFont(Value(12));
    [self.contentView addSubview:subPriceLabel];
    [subPriceLabel makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(priceLabel);
        make.top.equalTo(timeLable);
    }];
}

- (void)setModel:(YSSPayListModel *)model
{
    _model = model;
    
    self.timeLable.text = model.effectTime;
    self.payTypeLable.text = [model.recDisType isEqualToString:@"0"] ? (model.recDisPrice >= 0 ? @"转入" : @"转出" ) : @"提现";
    self.priceLabel.text = [NSString stringWithFormat:@"%.2f", model.recDisPrice];
    self.priceLabel.textColor = model.recDisPrice >= 0 ? [UIColor redColor] : [UIColor greenColor];
    self.subPriceLabel.text = [NSString stringWithFormat:@"余额%.2f", model.recDisBalance];
}

@end
