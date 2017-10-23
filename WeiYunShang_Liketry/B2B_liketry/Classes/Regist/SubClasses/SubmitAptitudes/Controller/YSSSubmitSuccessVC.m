//
//  YSSSubmitSuccessVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/21.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSSubmitSuccessVC.h"

@interface YSSSubmitSuccessVC ()

@end

@implementation YSSSubmitSuccessVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupNav];
    
    [self setupUI];
}

- (void)setupNav
{
    // 设置导航栏按钮
    UIButton *backBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    backBtn.adjustsImageWhenHighlighted = NO;
    [backBtn setImage:[UIImage imageNamed:@"返回"] forState:UIControlStateNormal];
    backBtn.frame = CGRectMake(0, Value(10), Value(40), Value(40));
    backBtn.imageEdgeInsets = UIEdgeInsetsMake(0, 0, 0, Value(30));
    [backBtn addTarget:self action:@selector(back) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:backBtn];
}

- (void)setupUI
{
    self.title = @"提交成功";
    
    UILabel *label = [[UILabel alloc] init];
    label.text = @"提交资料成功，请耐心等待审核";
    label.font = YSSBoldSystemFont(Value(18));
    label.textColor = [UIColor blackColor];
    [self.view addSubview:label];
    [label makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(self.view).offset(64 + Value(150));
    }];
}

- (void)back
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}


@end
