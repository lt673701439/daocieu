//
//  YSSBalanceViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBalanceViewController.h"
#import "YSSWithDrawViewController.h"
#import "YSSManageCardViewController.h"
#import "YSSPayDetailVC.h"

#import "YSSBalanceInfoView.h"

@interface YSSBalanceViewController ()<YSSBalanceInfoViewDelegate>

@end

@implementation YSSBalanceViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupNav];
    
    [self setupUI];
}

- (void)setupNav
{
    UIButton *rightBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [rightBtn setTitle:@"收支明细" forState:0];
    [rightBtn setTitleColor:[UIColor orangeColor] forState:0];
    rightBtn.titleLabel.font = YSSSystemFont(Value(14));
    [rightBtn sizeToFit];
    [rightBtn addTarget:self action:@selector(nextStep) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:rightBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
}

- (void)setupUI
{
    self.title = @"账户结算";
    
    YSSLog(@"---%@  W = %lf H = %lf", self.navigationController.navigationBar, ScreenW, ScreenH);
    
    YSSBalanceInfoView *infoView = [[YSSBalanceInfoView alloc] init];
    infoView.delegate = self;
    [self.view addSubview:infoView];
    [infoView makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view).offset(Value(10) + 64);
        make.left.equalTo(self.view).offset(Value(14));
        make.right.equalTo(self.view).offset(- Value(14));
        make.height.equalTo(@(Value(350)));
    }];
    
    [infoView addShadow:[UIColor blackColor] shadowOffset:CGSizeMake(0, 0) shadowOpacity:0.1 shadowRadius:10];
    infoView.layer.cornerRadius = 5;
}

#pragma mark - action
- (void)nextStep
{
    YSSLog(@"收支明细");
    YSSPayDetailVC *tempVC = [[YSSPayDetailVC alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

#pragma mark - <YSSBalanceInfoViewDelegate>
/** 点击提现 */
- (void)yssBalanceInfoViewDidClickWithdraw:(YSSBalanceInfoView *)yssBalanceInfoView
{
    YSSWithDrawViewController *tempVC = [[YSSWithDrawViewController alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

/** 点击我的银行卡 */
- (void)yssBalanceInfoViewDidClickCard:(YSSBalanceInfoView *)yssBalanceInfoView
{
    YSSManageCardViewController *tempVC = [[YSSManageCardViewController alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

@end
