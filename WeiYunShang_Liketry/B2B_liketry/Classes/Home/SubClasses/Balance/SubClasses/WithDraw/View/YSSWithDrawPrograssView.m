//
//  YSSWithDrawPrograssView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSWithDrawPrograssView.h"

#import "YSSCardModel.h"

@interface YSSWithDrawPrograssView ()
@property (nonatomic, strong) UILabel *cardNameLabel;
@property (nonatomic, strong) UILabel *priceLabel;
@end

@implementation YSSWithDrawPrograssView

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
    
    UIImageView *topImgView = [[UIImageView alloc] init];
    topImgView.image = [UIImage imageNamed:@"进度条1"];
    [self addSubview:topImgView];
    [topImgView makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self).offset(Value(14));
        make.left.equalTo(self).offset(Value(14));
        make.width.equalTo(@(Value(25)));
        make.height.equalTo(@(Value(130)));
    }];
    
//    UIImageView *bottomImgView = [[UIImageView alloc] init];
//    bottomImgView.backgroundColor = YSSRandomColor;
//    [self addSubview:bottomImgView];
//    [bottomImgView makeConstraints:^(MASConstraintMaker *make) {
//        make.top.equalTo(topImgView.bottom);
//        make.left.equalTo(topImgView);
//        make.width.equalTo(@(Value(25)));
//        make.height.equalTo(@(Value(65)));
//    }];
    
    UILabel *topTitleLabel = [[UILabel alloc] init];
    topTitleLabel.text = @"提现申请已提交 - 等待银行处理";
    topTitleLabel.font = YSSSystemFont(Value(14));
    [self addSubview:topTitleLabel];
    [topTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(topImgView.right).offset(Value(14));
        make.top.equalTo(topImgView).offset(Value(5));
    }];
    
    UILabel *cardNameLabel = [[UILabel alloc] init];
    self.cardNameLabel = cardNameLabel;
    cardNameLabel.font = YSSSystemFont(Value(12));
    cardNameLabel.textColor = [UIColor lightGrayColor];
    [self addSubview:cardNameLabel];
    [cardNameLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(topTitleLabel);
        make.top.equalTo(topTitleLabel.bottom).offset(Value(5));
    }];
    
    UILabel *priceLabel = [[UILabel alloc] init];
    self.priceLabel = priceLabel;
    priceLabel.font = YSSBoldSystemFont(Value(14));
    [self addSubview:priceLabel];
    [priceLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(cardNameLabel).offset(- Value(2));
        make.top.equalTo(cardNameLabel.bottom).offset(Value(5));
    }];
    
    UILabel *bottomTitleLabel = [[UILabel alloc] init];
    bottomTitleLabel.text = @"提现成功";
    bottomTitleLabel.font = YSSSystemFont(Value(14));
    [self addSubview:bottomTitleLabel];
    [bottomTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(topTitleLabel);
        make.top.equalTo(topImgView.bottom).offset(- Value(20));
    }];
    
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = @"hh:mm";
    NSDate *today = [NSDate date];
    NSString *time = [formatter stringFromDate:[today dateByAddingTimeInterval:3600 * 2]];
    YSSLog(@"%@", time);
    
    UILabel *bottomSubTitleLabel = [[UILabel alloc] init];
    bottomSubTitleLabel.text = [NSString stringWithFormat:@"预计2小时内(%@前到账)", time];
    bottomSubTitleLabel.textColor = [UIColor lightGrayColor];
    bottomSubTitleLabel.font = YSSSystemFont(Value(12));
    [self addSubview:bottomSubTitleLabel];
    [bottomSubTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomTitleLabel);
        make.top.equalTo(bottomTitleLabel.bottom).offset(Value(5));
    }];
    
//    UIView *bottomLine = [[UIView alloc] init];
//    bottomLine.backgroundColor = [UIColor colorWithHexString:YSSLineDarkColor];
//    [self addSubview:bottomLine];
//    [bottomLine makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.equalTo(self);
//        make.bottom.equalTo(self).offset(- Value(30));
//        make.height.equalTo(@0.5);
//    }];
//    
//    UIButton *detailBtn = [UIButton buttonWithType:UIButtonTypeCustom];
//    [detailBtn addTarget:self action:@selector(viewDetail) forControlEvents:UIControlEventTouchUpInside];
//    [detailBtn setTitle:@"查看详细 >" forState:UIControlStateNormal];
//    detailBtn.titleLabel.font = YSSSystemFont(Value(11));
//    [detailBtn setTitleColor:[UIColor colorWithHexString:YSSBlueColor] forState:UIControlStateNormal];
//    [self addSubview:detailBtn];
//    [detailBtn makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.bottom.equalTo(self);
//        make.top.equalTo(bottomLine.bottom);
//    }];
}

- (void)configUIWithModel:(YSSCardModel *)model recDisPrice:(NSString *)recDisPrice
{
    self.cardNameLabel.text = [NSString stringWithFormat:@"招商银行(%@) 储蓄卡", [model.bcBankNumber substringWithRange:NSMakeRange(model.bcBankNumber.length - 4, 4)]];
    self.priceLabel.text = [NSString stringWithFormat:@"￥%@", recDisPrice];
}

#pragma mark - action
- (void)viewDetail
{
    YSSLog(@"查看详细");
    if ([self.delegate respondsToSelector:@selector(yssWithDrawPrograssViewDidClickViewDetail:)]) {
        [self.delegate yssWithDrawPrograssViewDidClickViewDetail:self];
    }
}

@end
