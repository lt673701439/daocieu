//
//  YSSOrderListCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSOrderListCell.h"

#import "YSSGradientLabel.h"

#import "YSSOrderListModel.h"

@interface YSSOrderListCell ()
@property (nonatomic, strong) UIImageView *iconView;
@property (nonatomic, strong) UILabel *payStateLabel;
@property (nonatomic, strong) YSSGradientLabel *priceLabel;
@property (nonatomic, strong) UILabel *phoneLabel;
@property (nonatomic, strong) UILabel *serviceTypeLabel;
@property (nonatomic, strong) UILabel *orderCodeLabel;
@property (nonatomic, strong) UILabel *balanceLabel;
@end

@implementation YSSOrderListCell

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
    
    UIImageView *iconView = [[UIImageView alloc] init];
    self.iconView = iconView;
    [iconView addCornerRadius:5.0 borderColor:[UIColor colorWithHexString:YSSLineLightColor] borderWidth:0.5];
    [self.contentView addSubview:iconView];
    [iconView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(self).offset(Value(14));
        make.width.height.equalTo(@(Value(60)));
    }];
    
    UIView *vertivalLine = [[UIView alloc] init];
    vertivalLine.backgroundColor = [UIColor blackColor];
    [self.contentView addSubview:vertivalLine];
    [vertivalLine makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(iconView.right).offset(Value(20));
        make.top.equalTo(iconView);
        make.width.equalTo(@(Value(2)));
        make.height.equalTo(@(Value(11)));
    }];
    
    UILabel *payStateLabel = [[UILabel alloc] init];
    self.payStateLabel = payStateLabel;
    payStateLabel.font = YSSSystemFont(Value(12));
    [self.contentView addSubview:payStateLabel];
    [payStateLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(vertivalLine.right).offset(Value(5));
        make.centerY.equalTo(vertivalLine);
    }];
    
    YSSGradientLabel *priceLabel = [[YSSGradientLabel alloc] initWithFrame:CGRectMake(ScreenW - Value(114), Value(5), Value(100), Value(30))];
    self.priceLabel = priceLabel;
    priceLabel.textAlignment = NSTextAlignmentRight;
    priceLabel.font = YSSSystemFont(Value(14));
    [self.contentView addSubview:priceLabel];
    priceLabel.colors = @[(id)[UIColor yellowColor].CGColor, (id)[UIColor orangeColor].CGColor];
    
    UILabel *balanceLabel = [[UILabel alloc] init];
    self.balanceLabel = balanceLabel;
    balanceLabel.text = @"余额￥300";
    balanceLabel.textAlignment = NSTextAlignmentRight;
    balanceLabel.textColor = [UIColor lightGrayColor];
    balanceLabel.font = YSSSystemFont(Value(10));
    [self.contentView addSubview:balanceLabel];
    [balanceLabel makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(- Value(14));
        make.top.equalTo(payStateLabel.bottom).offset(Value(5));
    }];
    
    
    UILabel *phoneLabel = [[UILabel alloc] init];
    self.phoneLabel = phoneLabel;
    phoneLabel.font = YSSSystemFont(Value(12));
    phoneLabel.textColor = [UIColor lightGrayColor];
    [self.contentView addSubview:phoneLabel];
    [phoneLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(vertivalLine);
        make.bottom.equalTo(iconView);
    }];
    
    UILabel *serviceTypeLabel = [[UILabel alloc] init];
    self.serviceTypeLabel = serviceTypeLabel;
    serviceTypeLabel.font = YSSSystemFont(Value(12));
    serviceTypeLabel.textColor = [UIColor lightGrayColor];
    [self.contentView addSubview:serviceTypeLabel];
    [serviceTypeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(phoneLabel);
        make.bottom.equalTo(phoneLabel.top).offset(- Value(5));
    }];
    
    UIView *line = [[UIView alloc] init];
    line.backgroundColor = [UIColor colorWithHexString:YSSLineLightColor];
    [self.contentView addSubview:line];
    [line makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.right.equalTo(self).offset(- Value(14));
        make.top.equalTo(iconView.bottom).offset(Value(14));
        make.height.equalTo(@0.5);
    }];
    
    UILabel *orderCodeLabel = [[UILabel alloc] init];
    self.orderCodeLabel = orderCodeLabel;
    orderCodeLabel.font = YSSSystemFont(Value(12));
    orderCodeLabel.textColor = [UIColor lightGrayColor];
    [self.contentView addSubview:orderCodeLabel];
    [orderCodeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(line);
        make.right.equalTo(line);
        make.top.equalTo(line.bottom).offset(Value(14));
    }];
    
    UIView *bottomLineView = [[UIView alloc] init];
    bottomLineView.backgroundColor = [UIColor colorWithHexString:YSSBGColor];
    [self.contentView addSubview:bottomLineView];
    [bottomLineView makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self);
        make.height.equalTo(@(Value(10)));
    }];
}

- (void)setModel:(YSSOrderListModel *)model
{
    _model = model;
    self.priceLabel.text = [YSSCommonTool parseString:[NSString stringWithFormat:@"￥%.2lf", model.orPayPrice]];
    self.balanceLabel.text = [NSString stringWithFormat:@"余额￥%.2lf", model.orBalance];
    self.orderCodeLabel.text = [NSString stringWithFormat:@"订单成交时间:%@", model.orOrderTime];
    self.phoneLabel.text = [NSString stringWithFormat:@"联系电话: %@", model.orMobile.length > 0 ? [YSSCommonTool parseString:model.orMobile] : @"无"];
    
    [self.iconView setHttpImageWithURL:[NSURL URLWithString:model.orUserIcon]];
//    [self.iconView sd_setImageWithURL:[NSURL URLWithString:model.orUserIcon] placeholderImage:YSSPlaceholderImage completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
//        _iconView.contentMode = UIViewContentModeScaleAspectFit;
//    }];
    
    //0-草稿，1-待支付，2-已支付，3-已播放，4-已取消，5-已退单，6-已退款
    NSString *payStatye = @"";
    switch (model.orStatus) {
        case 1:
            payStatye = @"待支付";
            break;
        case 2:
            payStatye = @"已支付";
            break;
        case 3:
            payStatye = @"已播放";
            break;
        case 4:
            payStatye = @"已取消";
            break;
        case 5:
            payStatye = @"已退单";
            break;
        case 6:
            payStatye = @"已退款";
            break;
            
        default:
            break;
    }
    self.payStateLabel.text = payStatye;
    
    //类型：0-普通，1-自制，2-DIY，3-定制
    NSString *orderType = @"";
    switch (model.orType) {
        case 0:
            orderType = @"服务类型: 普通模板";
            break;
        case 1:
            orderType = @"服务类型: 自制下单";
            break;
        case 2:
            orderType = @"服务类型: DIY下单";
            break;
        case 3:
            orderType = @"服务类型: 定制下单";
            break;
        default:
            orderType = @"服务类型:";
            break;
    }
    self.serviceTypeLabel.text = orderType;
    
}

@end
