//
//  YSSPayDetailHeaderView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSPayDetailHeaderView.h"

#import "YSSGradientLabel.h"

@interface YSSPayDetailHeaderView ()
@property (nonatomic, strong) NSArray *titleArr;
@property (nonatomic, strong) UIButton *preSelectBtn;
@property (nonatomic, strong) UIView *bannerView;

@property (nonatomic, strong) YSSGradientLabel *leftGradientLabel;
@property (nonatomic, strong) YSSGradientLabel *rightGradientLabel;

@property (nonatomic, strong) UIImageView *selideView;
@end

@implementation YSSPayDetailHeaderView

- (NSArray *)titleArr
{
    if (_titleArr == nil) {
        _titleArr = @[@"全部", @"转入", @"转出", @"提现"];
    }
    return _titleArr;
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
    UIView *verticalLine = [[UIView alloc] init];
    verticalLine.backgroundColor = [UIColor colorWithHexString:YSSLineDarkColor];
    [self addSubview:verticalLine];
    [verticalLine makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(self).offset(Value(30));
        make.width.equalTo(@0.5);
        make.height.equalTo(@(Value(72.5)));
    }];
    
    YSSGradientLabel *leftGradientLabel = [[YSSGradientLabel alloc] initWithFrame:CGRectMake(Value(15), Value(30), Value(150), Value(40))];
    self.leftGradientLabel = leftGradientLabel;
    leftGradientLabel.textAlignment = NSTextAlignmentCenter;
    leftGradientLabel.font = YSSBoldSystemFont(Value(36));
    [self addSubview:leftGradientLabel];
    leftGradientLabel.colors = @[(id)[UIColor yellowColor].CGColor, (id)[UIColor orangeColor].CGColor];
    
    UILabel *leftTitle = [[UILabel alloc] initWithFrame:CGRectMake(Value(60), Value(70), Value(100), Value(40))];
    leftTitle.text = @"收入金额";
    leftTitle.font = YSSBoldSystemFont(Value(15));
    [self addSubview:leftTitle];
    
    YSSGradientLabel *rightGradientLabel = [[YSSGradientLabel alloc] initWithFrame:CGRectMake(Value(205), Value(30), Value(150), Value(40))];
    self.rightGradientLabel = rightGradientLabel;
    rightGradientLabel.textAlignment = NSTextAlignmentCenter;
    rightGradientLabel.font = YSSBoldSystemFont(Value(36));
    [self addSubview:rightGradientLabel];
    rightGradientLabel.colors = @[(id)[UIColor yellowColor].CGColor, (id)[UIColor orangeColor].CGColor];
    
    UILabel *rightTitle = [[UILabel alloc] initWithFrame:CGRectMake(Value(250), Value(70), Value(100), Value(40))];
    rightTitle.text = @"支出金额";
    rightTitle.font = YSSBoldSystemFont(Value(15));
    [self addSubview:rightTitle];
    
    
    UIView *bannerView = [[UIView alloc] init];
    self.bannerView = bannerView;
    bannerView.backgroundColor = [UIColor whiteColor];
    [self addSubview:bannerView];
    [bannerView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self);
        make.height.equalTo(@(Value(40)));
    }];
    
    CGFloat width = ScreenW * 1.0 / self.titleArr.count;
    
    for (int i = 0; i < self.titleArr.count; i++) {
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        if (i == 0) {
            self.preSelectBtn = button;
        }
        [button addTarget:self action:@selector(typeClick:) forControlEvents:UIControlEventTouchUpInside];
        [button setTitleColor:i == 0 ? [UIColor blackColor] : [UIColor lightGrayColor] forState:0];
        button.titleLabel.font = YSSBoldSystemFont(Value(12));
        [button setTitle:self.titleArr[i] forState:UIControlStateNormal];
        [bannerView addSubview:button];
        [button makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(width * i);
            make.top.equalTo(bannerView);
            make.width.equalTo(@(width));
            make.height.equalTo(bannerView);
        }];
    }
    
    UIImageView *selideView = [[UIImageView alloc] init];
    self.selideView = selideView;
    selideView.image = [UIImage imageNamed:@"选择"];
    [self addSubview:selideView];
    [selideView makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self);
        make.centerX.equalTo(bannerView.subviews[0]);
    }];
}

- (void)typeClick:(UIButton *)button
{
    [_selideView remakeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self);
        make.centerX.equalTo(button);
    }];
    
    [UIView animateWithDuration:0.25 animations:^{
        [self layoutIfNeeded];
    }];
    
    [self.preSelectBtn setTitleColor:[UIColor lightGrayColor] forState:0];
    [button setTitleColor:[UIColor blackColor] forState:0];
    self.preSelectBtn = button;
    NSInteger index = [self.bannerView.subviews indexOfObject:button];
    YSSLog(@"--- %ld", index);
    
    if ([self.delegate respondsToSelector:@selector(yssPayDetailHeaderView:DidClickTypeWithIndex:)]) {
        [self.delegate yssPayDetailHeaderView:self DidClickTypeWithIndex:index];
    }
}

- (void)updateUIWithIndex:(NSInteger)index
{
    [self typeClick:self.bannerView.subviews[index]];
}

- (void)configUIWithData:(NSDictionary *)data
{
    self.leftGradientLabel.text = [NSString stringWithFormat:@"%.2f", [data[@"recPrice"] floatValue]];
    self.rightGradientLabel.text = [NSString stringWithFormat:@"%.2f", [data[@"disPrice"] floatValue]];
}

@end
