//
//  YSSBaseViewController.m
//  Blessings_liketry
//
//  Created by GentleZ on 2017/5/27.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseViewController.h"

@interface YSSBaseViewController ()

@end

@implementation YSSBaseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor colorWithHexString:YSSBGColor];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)setTitle:(NSString *)title
{
    UILabel *navtitle = [[UILabel alloc] init];
    navtitle.text = title;
    navtitle.font = YSSCustomFont(@"MFYueHei_Noncommercial-Regular", Value(18));
    [navtitle sizeToFit];
    self.navigationItem.titleView = navtitle;
}


@end
