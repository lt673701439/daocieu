//
//  YSSWithDrawView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSWithDrawView.h"

#import "YSSCardModel.h"

@interface YSSWithDrawView ()
@property (nonatomic, strong) UILabel *topPriceLabel;
@property (nonatomic, strong) UIView *cardView;
@property (nonatomic, strong) UIImageView *cardBGImgView;

@property (nonatomic, strong) UILabel *cardNameLabel;
@property (nonatomic, strong) UILabel *typeLabel;
@end

@implementation YSSWithDrawView

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
    
    UIView *cardView = [[UIView alloc] init];
    self.cardView = cardView;
    [cardView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(cardViewTap)]];
//    cardView.backgroundColor = [UIColor colorWithHexString:@"e3e3e3"];
    [cardView addCornerRadius:5 borderColor:nil borderWidth:0];
    [self addSubview:cardView];
    [cardView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self);
        make.height.equalTo(@(Value(75)));
    }];
    
    UIImageView *cardBGImgView = [[UIImageView alloc] init];
    self.cardBGImgView = cardBGImgView;
    [cardView addSubview:cardBGImgView];
    [cardBGImgView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(cardView);
    }];
    
    UIView *mainView = [[UIView alloc] init];
    mainView.backgroundColor = [UIColor whiteColor];
    [self addSubview:mainView];
    [mainView makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(cardView.bottom).offset(- Value(5));
        make.bottom.equalTo(self).offset(- Value(5));
        make.left.right.equalTo(self);
    }];
    
    UILabel *topPriceTitleLabel = [[UILabel alloc] init];
    topPriceTitleLabel.text = @"可转出金额";
    topPriceTitleLabel.font = YSSSystemFont(Value(14));
    topPriceTitleLabel.textColor = [UIColor lightGrayColor];
    [mainView addSubview:topPriceTitleLabel];
    [topPriceTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(mainView).offset(Value(14));
        make.top.equalTo(mainView).offset(Value(20));
    }];
    
    UILabel *topPriceLabel = [[UILabel alloc] init];
    self.topPriceLabel = topPriceLabel;
    topPriceLabel.font = YSSBoldSystemFont(Value(28));
    [mainView addSubview:topPriceLabel];
    [topPriceLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(topPriceTitleLabel);
        make.top.equalTo(topPriceTitleLabel.bottom).offset(Value(16));
    }];
    
    UIView *topline = [[UIView alloc] init];
    topline.backgroundColor = [UIColor colorWithHexString:YSSLineLightColor];
    [mainView addSubview:topline];
    [topline makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(mainView);
        make.top.equalTo(mainView).offset(@(Value(100)));
        make.height.equalTo(@0.5);
    }];
    
    UILabel *bottomPriceTitleLabel = [[UILabel alloc] init];
    bottomPriceTitleLabel.text = @"转出金额";
    bottomPriceTitleLabel.font = YSSSystemFont(Value(14));
    bottomPriceTitleLabel.textColor = [UIColor lightGrayColor];
    [mainView addSubview:bottomPriceTitleLabel];
    [bottomPriceTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(mainView).offset(Value(14));
        make.top.equalTo(topline).offset(Value(20));
    }];
    
    UILabel *cn_MoneyLabel = [[UILabel alloc] init];
    cn_MoneyLabel.text = @"￥";
    cn_MoneyLabel.font = YSSBoldSystemFont(Value(28));
    [mainView addSubview:cn_MoneyLabel];
    [cn_MoneyLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomPriceTitleLabel);
        make.top.equalTo(bottomPriceTitleLabel.bottom).offset(Value(14));
        make.width.equalTo(@(Value(25)));
    }];
    
    UITextField *inputTF = [[UITextField alloc] init];
    self.inputTF = inputTF;
    inputTF.tintColor = [UIColor orangeColor];
    inputTF.font = YSSBoldSystemFont(Value(28));
    inputTF.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    [mainView addSubview:inputTF];
    [inputTF makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(cn_MoneyLabel.right).offset(Value(5));
        make.centerY.equalTo(cn_MoneyLabel);
        make.height.equalTo(@(Value(25)));
        make.right.equalTo(mainView).offset(- Value(14));
    }];
    
    UIView *bottomline = [[UIView alloc] init];
    bottomline.backgroundColor = [UIColor colorWithHexString:YSSLineLightColor];
    [mainView addSubview:bottomline];
    [bottomline makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(mainView);
        make.bottom.equalTo(mainView).offset(@(- Value(30)));
        make.height.equalTo(@0.5);
    }];
    
    UILabel *ruleLabel = [[UILabel alloc] init];
    ruleLabel.text = @"申请日起3个工作日到账";
    ruleLabel.font = YSSSystemFont(Value(10));
    ruleLabel.textColor = [UIColor lightGrayColor];
    [mainView addSubview:ruleLabel];
    [ruleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(mainView).offset(Value(14));
        make.top.equalTo(bottomline.bottom).offset(Value(10));
    }];
}

- (void)configUIWithData:(NSDictionary *)data
{
    self.topPriceLabel.text = [YSSCommonTool parseString:[data[@"price"] stringValue]];
    self.inputTF.placeholder = [NSString stringWithFormat:@"%@", [YSSCommonTool parseString:[data[@"price"] stringValue]]];
    
    NSDictionary *cardInfo = [YSSCommonTool parseDictionary:data[@"bankCard"]];
    if (cardInfo) {
        _cardView.backgroundColor = [UIColor colorWithHexString:@"b85758"];
        
        UIImageView *logoView = [[UIImageView alloc] init];
        logoView.image = [UIImage imageNamed:@"招商"];
        [_cardView addSubview:logoView];
        [logoView makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(_cardView).offset(Value(14));
            make.centerY.equalTo(_cardView);
        }];
        
        UILabel *cardNameLabel = [[UILabel alloc] init];
        self.cardNameLabel = cardNameLabel;
        NSString *str = [NSString stringWithFormat:@"%@(%@)", cardInfo[@"bcBankName"], [((NSString*)cardInfo[@"bcBankNumber"]) substringWithRange:NSMakeRange(((NSString*)cardInfo[@"bcBankNumber"]).length - 4, 4)]];
        cardNameLabel.text = str;
        cardNameLabel.textColor = [UIColor whiteColor];
        cardNameLabel.font = YSSBoldSystemFont(Value(18));
        [_cardView addSubview:cardNameLabel];
        [cardNameLabel makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(_cardView).offset(Value(65));
            make.top.equalTo(_cardView).offset(Value(18));
        }];
        
        UILabel *typeLabel = [[UILabel alloc] init];
        self.typeLabel = typeLabel;
        typeLabel.text = cardInfo[@"bcBankType"];
        typeLabel.textColor = [UIColor whiteColor];
        typeLabel.font = YSSSystemFont(Value(12));
        [_cardView addSubview:typeLabel];
        [typeLabel makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(cardNameLabel);
            make.top.equalTo(cardNameLabel.bottom).offset(Value(5));
        }];
        
        UIImageView *rightImgView = [[UIImageView alloc] init];
        rightImgView.image = [UIImage imageNamed:@"Globle_ArrowR白色"];
        [_cardView addSubview:rightImgView];
        [rightImgView makeConstraints:^(MASConstraintMaker *make) {
            make.right.equalTo(_cardView).offset(- Value(14));
            make.centerY.equalTo(_cardView);
        }];
        
        
    }else{
        _cardView.backgroundColor = [UIColor colorWithHexString:@"e3e3e3"];
        
        UIImageView *logoView = [[UIImageView alloc] init];
        logoView.image = [UIImage imageNamed:@"添加蓝色"];
        [_cardView addSubview:logoView];
        [logoView makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(_cardView).offset(Value(14));
            make.centerY.equalTo(_cardView);
        }];
        
        UILabel *cardNameLabel = [[UILabel alloc] init];
        cardNameLabel.text = @"添加银行卡";
        cardNameLabel.textColor = [UIColor whiteColor];
        cardNameLabel.font = YSSBoldSystemFont(Value(18));
        [_cardView addSubview:cardNameLabel];
        [cardNameLabel makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(_cardView).offset(Value(40));
            make.centerY.equalTo(_cardView);
        }];
    }
}

- (void)setCardModel:(YSSCardModel *)cardModel
{
    _cardModel = cardModel;
    
    [_cardView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
    
    _cardView.backgroundColor = [UIColor colorWithHexString:@"b85758"];
    
    UIImageView *logoView = [[UIImageView alloc] init];
    logoView.image = [UIImage imageNamed:@"招商"];
    [_cardView addSubview:logoView];
    [logoView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_cardView).offset(Value(14));
        make.centerY.equalTo(_cardView);
    }];
    
    UILabel *cardNameLabel = [[UILabel alloc] init];
    self.cardNameLabel = cardNameLabel;
    NSString *str = [NSString stringWithFormat:@"%@(%@)", cardModel.bcBankName, [cardModel.bcBankNumber substringWithRange:NSMakeRange(cardModel.bcBankNumber.length - 4, 4)]];
    cardNameLabel.text = str;
    cardNameLabel.textColor = [UIColor whiteColor];
    cardNameLabel.font = YSSBoldSystemFont(Value(18));
    [_cardView addSubview:cardNameLabel];
    [cardNameLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_cardView).offset(Value(65));
        make.top.equalTo(_cardView).offset(Value(18));
    }];
    
    UILabel *typeLabel = [[UILabel alloc] init];
    self.typeLabel = typeLabel;
    typeLabel.text = cardModel.bcBankType;
    typeLabel.textColor = [UIColor whiteColor];
    typeLabel.font = YSSSystemFont(Value(12));
    [_cardView addSubview:typeLabel];
    [typeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(cardNameLabel);
        make.top.equalTo(cardNameLabel.bottom).offset(Value(5));
    }];
    
    UIImageView *rightImgView = [[UIImageView alloc] init];
    rightImgView.image = [UIImage imageNamed:@"Globle_ArrowR白色"];
    [_cardView addSubview:rightImgView];
    [rightImgView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(_cardView).offset(- Value(14));
        make.centerY.equalTo(_cardView);
    }];
}

#pragma mark - action
- (void)cardViewTap
{
    if ([self.delegate respondsToSelector:@selector(yssWithDrawViewDidClickChooseCard:)]) {
        [self.delegate yssWithDrawViewDidClickChooseCard:self];
    }
}

@end
