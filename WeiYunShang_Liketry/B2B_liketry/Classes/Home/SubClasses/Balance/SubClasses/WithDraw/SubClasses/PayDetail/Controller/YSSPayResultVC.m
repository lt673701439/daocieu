//
//  YSSPayResultVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/25.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSPayResultVC.h"

#import "YSSPsyResultView.h"

#import "YSSPayListModel.h"

@interface YSSPayResultVC ()

@end

@implementation YSSPayResultVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)setupUI
{
    self.title = @"收支明细";
    
    YSSPsyResultView *resultView = [YSSPsyResultView payResultView];
    resultView.model = self.model;
    [self.view addSubview:resultView];
    [resultView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
    }];
}



@end
