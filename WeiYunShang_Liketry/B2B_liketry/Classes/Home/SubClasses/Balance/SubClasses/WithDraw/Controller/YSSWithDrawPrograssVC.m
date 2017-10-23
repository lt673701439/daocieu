//
//  YSSWithDrawPrograssVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSWithDrawPrograssVC.h"
#import "YSSPayDetailVC.h"

#import "YSSWithDrawPrograssView.h"

@interface YSSWithDrawPrograssVC ()<YSSWithDrawPrograssViewDelegate>

@end

@implementation YSSWithDrawPrograssVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)setupUI
{
    self.title = @"结果详情";
    
    YSSWithDrawPrograssView *view = [[YSSWithDrawPrograssView alloc] init];
    [view configUIWithModel:self.cardModel recDisPrice:self.recDisPrice];
    view.delegate = self;
    [self.view addSubview:view];
    [view makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view).offset(64 + Value(14));
        make.left.equalTo(self.view).offset(Value(14));
        make.right.equalTo(self.view).offset(- Value(14));
        make.height.equalTo(@(Value(210)));
    }];
    
    [view addShadow:[UIColor blackColor] shadowOffset:CGSizeMake(0, 0) shadowOpacity:0.1 shadowRadius:10];
    view.layer.cornerRadius = 5;
    
    UIButton *nextStepBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [nextStepBtn addTarget:self action:@selector(nextStep) forControlEvents:UIControlEventTouchUpInside];
    [nextStepBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:0];
    [nextStepBtn setTitle:@"完成" forState:0];
    nextStepBtn.titleLabel.font = YSSBoldSystemFont(Value(16));
    [self.view addSubview:nextStepBtn];
    [nextStepBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self.view);
        make.height.equalTo(@(Value(50)));
    }];
    
}

#pragma mark - action
- (void)nextStep
{
    [self.navigationController popToViewController:self.navigationController.childViewControllers[1] animated:YES];
}

#pragma mark - <YSSWithDrawPrograssViewDelegate>
- (void)yssWithDrawPrograssViewDidClickViewDetail:(YSSWithDrawPrograssView *)yssWithDrawPrograssView
{
    YSSPayDetailVC *tempVC = [[YSSPayDetailVC alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

@end
