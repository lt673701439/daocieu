//
//  YSSHomeHeaderView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSHomeHeaderView.h"

#import "YSSHeaderInfoView.h"

@interface YSSHomeHeaderView ()
@property (nonatomic, strong) YSSHeaderInfoView *priceView;
@property (nonatomic, strong) YSSHeaderInfoView *orderView;

@property (nonatomic, strong) YSSVerticalButton *leftBtn;
@property (nonatomic, strong) YSSVerticalButton *rightBtn;
@property (nonatomic, strong) YSSVerticalButton *midBtn;


@property (nonatomic, strong) UILabel *shopNameLabel;
@end

@implementation YSSHomeHeaderView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    UIView *topView = [[UIView alloc] init];
//    topView.backgroundColor = [UIColor orangeColor];
    [self addSubview:topView];
    [topView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self);
        make.height.equalTo(@(Value(176)));
    }];
    
    UIImageView *bgImgView = [[UIImageView alloc] init];
    bgImgView.image = [UIImage imageNamed:@"背景图"];
    [topView addSubview:bgImgView];
    [bgImgView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(topView);
    }];
    
    UIView *merchantInfoView = [[UIView alloc] init];
    [topView addSubview:merchantInfoView];
    [merchantInfoView makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(topView);
        make.bottom.equalTo(topView.centerY).offset(- Value(60));
    }];
    
    UILabel *shopNameLabel = [[UILabel alloc] init];
    self.shopNameLabel = shopNameLabel;
    shopNameLabel.text = [YSSMerChanModel sharedInstence].merchantShopname;
    shopNameLabel.textColor = [UIColor whiteColor];
    shopNameLabel.font = YSSBoldSystemFont(Value(20));
    [merchantInfoView addSubview:shopNameLabel];
    [shopNameLabel makeConstraints:^(MASConstraintMaker *make) {
//        make.right.equalTo(topView.centerX);
//        make.bottom.equalTo(topView.centerY).offset(- Value(60));
        make.left.equalTo(merchantInfoView);
        make.centerY.equalTo(merchantInfoView);
    }];
    
    UIButton *levelBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [levelBtn setBackgroundImage:[UIImage imageNamed:@"等级"] forState:UIControlStateNormal];
    [levelBtn setTitle:[NSString stringWithFormat:@"LV%@", [YSSMerChanModel sharedInstence].merchantLevel] forState:UIControlStateNormal];
    [levelBtn setTitleColor:[UIColor colorWithHexString:YSSYellowBGColor] forState:UIControlStateNormal];
    levelBtn.titleLabel.font = YSSSystemFont(Value(10));
    levelBtn.titleEdgeInsets = UIEdgeInsetsMake(Value(6), Value(15), 0, 0);
    [merchantInfoView addSubview:levelBtn];
    [levelBtn makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(shopNameLabel.right).offset(Value(14));
//        make.centerY.equalTo(shopNameLabel);
        make.left.equalTo(shopNameLabel.right).offset(Value(10));
        make.top.right.bottom.equalTo(merchantInfoView);
    }];
    
    
    
    UIView *verticalLine = [[UIView alloc] init];
    verticalLine.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    [topView addSubview:verticalLine];
    [verticalLine makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(topView);
        make.centerY.equalTo(topView).offset(- Value(10));
        make.width.equalTo(@1);
        make.height.equalTo(@(Value(60)));
    }];
    
    YSSHeaderInfoView *priceView = [[YSSHeaderInfoView alloc] init];
    self.priceView = priceView;
    NSDictionary *dict = @{
                           
                           @"num" : @([YSSMerChanModel sharedInstence].merchantBalance),
                           @"title" : @"余额(元)"
                           };
    [priceView configUIWithData:dict];
    
    [priceView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(price)]];
    [topView addSubview:priceView];
    [priceView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(verticalLine.left).offset(- Value(30));
        make.centerY.equalTo(verticalLine);
        make.width.equalTo(Value(127));
        make.height.equalTo(verticalLine);
    }];
    
    YSSHeaderInfoView *orderView = [[YSSHeaderInfoView alloc] init];
    self.orderView = orderView;
    [orderView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(order)]];
    [topView addSubview:orderView];
    [orderView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(verticalLine.right).offset(Value(30));
        make.centerY.equalTo(verticalLine);
        make.width.equalTo(Value(127));
        make.height.equalTo(verticalLine);
    }];
    
    
    UIView *bottomView = [[UIView alloc] init];
    self.bottomView = bottomView;
    bottomView.backgroundColor = [UIColor whiteColor];
    [self addSubview:bottomView];
    [bottomView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self);
        make.top.equalTo(topView.bottom);
        make.height.equalTo(@(Value(90)));
    }];
    
    YSSVerticalButton *midBtn = [YSSVerticalButton buttonWithType:UIButtonTypeCustom];
    self.midBtn = midBtn;
    midBtn.image_topOffset = 10;
    midBtn.title_topOffset = 10;
    [midBtn addTarget:self action:@selector(midBtnClick) forControlEvents:UIControlEventTouchUpInside];
    [midBtn setImage:[UIImage imageNamed:@"二维码"] forState:UIControlStateNormal];
    [midBtn setTitle:@"我的二维码" forState:UIControlStateNormal];
    [midBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    midBtn.titleLabel.font = YSSSystemFont(Value(12));
    [bottomView addSubview:midBtn];
    [midBtn makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(bottomView);
        make.centerY.equalTo(bottomView);
        make.width.equalTo(@(Value(85)));
        make.height.equalTo(@(Value(75)));
    }];
    
    YSSVerticalButton *leftBtn = [YSSVerticalButton buttonWithType:UIButtonTypeCustom];
    self.leftBtn = leftBtn;
    leftBtn.image_topOffset = 10;
    leftBtn.title_topOffset = 10;
    [leftBtn addTarget:self action:@selector(leftBtnClick) forControlEvents:UIControlEventTouchUpInside];
    [leftBtn setImage:[UIImage imageNamed:@"店铺"] forState:UIControlStateNormal];
    [leftBtn setTitle:@"我的店铺" forState:UIControlStateNormal];
    [leftBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    leftBtn.titleLabel.font = YSSSystemFont(Value(12));
    [bottomView addSubview:leftBtn];
    [leftBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomView).offset(Value(30));
        make.centerY.equalTo(bottomView);
        make.width.equalTo(@(Value(85)));
        make.height.equalTo(@(Value(75)));
    }];
    
    YSSVerticalButton *rightBtn = [YSSVerticalButton buttonWithType:UIButtonTypeCustom];
    self.rightBtn = rightBtn;
    rightBtn.image_topOffset = 10;
    rightBtn.title_topOffset = 10;
    [rightBtn addTarget:self action:@selector(rightBtnClick) forControlEvents:UIControlEventTouchUpInside];
    [rightBtn setImage:[UIImage imageNamed:@"赚钱课堂"] forState:UIControlStateNormal];
    [rightBtn setTitle:@"赚钱课堂" forState:UIControlStateNormal];
    [rightBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    rightBtn.titleLabel.font = YSSSystemFont(Value(12));
    [bottomView addSubview:rightBtn];
    [rightBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(bottomView).offset(- Value(30));
        make.centerY.equalTo(bottomView);
        make.width.equalTo(@(Value(85)));
        make.height.equalTo(@(Value(75)));
    }];
    
    
    YSSAlphaView * alphaView= [[YSSAlphaView alloc] initWithFrame:CGRectMake(0, Value(90), ScreenW, Value(10))];
    [bottomView addSubview:alphaView];
}

/** 余额 */
- (void)configBalanceWithData:(NSDictionary *)data
{
    
    NSDictionary *dict = @{
                           @"num" : [NSString stringWithFormat:@"%.2lf", [data[@"merchantBalance"] doubleValue] - [data[@"disPrice"] doubleValue]],
                           @"title" : @"余额(元)"
                           };
    [self.priceView configUIWithData:dict];
    
}

/** 订单数 */
- (void)configOrderNumWithData:(NSDictionary *)data
{
    NSArray *orderArr = data[@"records"];
    if (orderArr) {
        NSDictionary *dict = @{
                               @"num" : [NSString stringWithFormat:@"%ld", orderArr.count],
                               @"title" : @"今日成单(个)"
                               };
        [self.orderView configUIWithData:dict];
    }
}

#pragma mark - action
- (UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event
{
    if (self.isHidden == NO) {
        CGPoint newP = [self convertPoint:point toView:self.bottomView];
        if ([self.bottomView pointInside:newP withEvent:event]) {
            CGPoint leftP = [self convertPoint:point toView:self.leftBtn];
            if ([self.leftBtn pointInside:leftP withEvent:event]) {
                return self.leftBtn;
            }
            
            CGPoint rightP = [self convertPoint:point toView:self.rightBtn];
            if ([self.rightBtn pointInside:rightP withEvent:event]) {
                return self.rightBtn;
            }
            
            CGPoint midP = [self convertPoint:point toView:self.midBtn];
            if ([self.midBtn pointInside:midP withEvent:event]) {
                return self.midBtn;
            }
            
            return self.bottomView;
            
        }else{
            return [super hitTest:point withEvent:event];
        }
        
    }else{
        return [super hitTest:point withEvent:event];
    }
}


- (void)price
{
    if ([self.delegate respondsToSelector:@selector(ySSHomeHeaderView:didClickPrice:)]) {
        [self.delegate ySSHomeHeaderView:self didClickPrice:@"1000"];
    }
}

- (void)order
{
    if ([self.delegate respondsToSelector:@selector(ySSHomeHeaderView:didClickOrder:)]) {
        [self.delegate ySSHomeHeaderView:self didClickOrder:@"100"];
    }
}

- (void)leftBtnClick
{
    if ([self.delegate respondsToSelector:@selector(ySSHomeHeaderViewDidClickStore:)]) {
        [self.delegate ySSHomeHeaderViewDidClickStore:self];
    }
}

- (void)midBtnClick
{
    if ([self.delegate respondsToSelector:@selector(ySSHomeHeaderViewDidClickQRCode:)]) {
        [self.delegate ySSHomeHeaderViewDidClickQRCode:self];
    }
}

- (void)rightBtnClick
{
    if ([self.delegate respondsToSelector:@selector(ySSHomeHeaderViewDidClickMoneyLesson:)]) {
        [self.delegate ySSHomeHeaderViewDidClickMoneyLesson:self];
    }
}

@end
