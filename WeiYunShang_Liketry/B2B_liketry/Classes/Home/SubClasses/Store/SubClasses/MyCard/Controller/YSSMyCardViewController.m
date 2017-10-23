//
//  YSSMyCardViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMyCardViewController.h"

#import "YSSCardView.h"

@interface YSSMyCardViewController ()

@end

@implementation YSSMyCardViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)setupUI
{
    
    YSSCardView *cardView = [[YSSCardView alloc] init];
    [self.view addSubview:cardView];
    [cardView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(Value(14));
        make.right.equalTo(self.view).offset(- Value(14));
        make.top.equalTo(self.view).offset(Value(14));
        make.bottom.equalTo(self.view).offset(- Value(14));
    }];
    
    [cardView addShadow:[UIColor blackColor] shadowOffset:CGSizeMake(0, 0) shadowOpacity:0.1 shadowRadius:10];
    cardView.layer.cornerRadius = 5;
}



@end
