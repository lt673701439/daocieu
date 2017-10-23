//
//  YSSAddCardFinishVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSAddCardFinishVC.h"
#import "YSSManageCardViewController.h"

@interface YSSAddCardFinishVC ()

@end

@implementation YSSAddCardFinishVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupNav];
    
    [self setupUI];
}

- (void)setupNav
{
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithTitle:@"完成" style:UIBarButtonItemStylePlain target:self action:@selector(finish)];
    item.tintColor = [UIColor orangeColor];
    NSDictionary *dict = @{NSFontAttributeName : YSSSystemFont(Value(14))};
    [item setTitleTextAttributes:dict forState:UIControlStateNormal];
    self.navigationItem.rightBarButtonItem = item;
}

- (void)setupUI
{
    self.title = @"添加银行卡";
    
    self.view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    UIView *mainView = [[UIView alloc] init];
    mainView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:mainView];
    [mainView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(@(Value(240)));
    }];
    
    UIImageView *finishImgView = [[UIImageView alloc] init];
    finishImgView.image = [UIImage imageNamed:@"添加银行卡完成"];
//    finishImgView.backgroundColor = YSSRandomColor;
    [mainView addSubview:finishImgView];
    [finishImgView makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(mainView).offset(Value(50));
        make.centerX.equalTo(mainView);
        make.width.height.equalTo(@(Value(80)));
    }];
    
    UILabel *label = [[UILabel alloc] init];
    label.text = @"添加成功";
    label.font = YSSBoldSystemFont(Value(18));
    label.textColor = [UIColor orangeColor];
    [mainView addSubview:label];
    [label makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(mainView);
        make.top.equalTo(finishImgView.bottom).offset(Value(30));
    }];
}


#pragma mark - action
- (void)finish
{
    [self.navigationController popToViewController:self.navigationController.childViewControllers[1] animated:YES];
}

@end
