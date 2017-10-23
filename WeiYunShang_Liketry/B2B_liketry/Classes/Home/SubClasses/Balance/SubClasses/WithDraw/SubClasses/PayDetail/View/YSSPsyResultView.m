//
//  YSSPsyResultView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/25.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSPsyResultView.h"

#import "YSSPayListModel.h"

@interface YSSPsyResultView ()
@property (weak, nonatomic) IBOutlet UILabel *priceTitle;
@property (weak, nonatomic) IBOutlet UILabel *priceLabel;
@property (weak, nonatomic) IBOutlet UILabel *typeLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;
@property (weak, nonatomic) IBOutlet UILabel *orderCodeLabel;
@property (weak, nonatomic) IBOutlet UILabel *balanceLabel;



@end

@implementation YSSPsyResultView

+ (instancetype)payResultView
{
    return [[[NSBundle mainBundle] loadNibNamed:@"YSSPsyResultView" owner:nil options:nil] firstObject];
}

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    
}

- (void)setModel:(YSSPayListModel *)model
{
    _model = model;
    
    self.priceTitle.text = [model.recDisType isEqualToString:@"0"] ? (model.recDisPrice >= 0 ? @"转入金额" : @"转出金额" ) : @"提现金额";;
    self.priceLabel.text = [NSString stringWithFormat:@"%.2f", model.recDisPrice];
    self.typeLabel.text = [model.recDisType isEqualToString:@"0"] ? (model.recDisPrice >= 0 ? @"转入" : @"转出" ) : @"提现";;
    self.timeLabel.text = model.createTime;
    self.orderCodeLabel.text = model.recIdsCode;
    self.balanceLabel.text = [NSString stringWithFormat:@"%.2f", model.recDisBalance];
    
}

@end
