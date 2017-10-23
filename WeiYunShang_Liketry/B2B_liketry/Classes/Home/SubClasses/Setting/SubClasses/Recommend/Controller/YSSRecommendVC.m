//
//  YSSRecommendVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/19.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSRecommendVC.h"

@interface YSSRecommendVC ()

@end

@implementation YSSRecommendVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)setupUI
{
    self.title = @"邀请码推荐";
    
    UIImageView *bgView = [[UIImageView alloc] init];
    bgView.image = [UIImage imageNamed:@"二维码分享页"];
    [self.view addSubview:bgView];
    [bgView makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view).offset(64);
        make.left.right.bottom.equalTo(self.view);
    }];
    
    UIImageView *qrCodeImgVeiw = [[UIImageView alloc] init];
    qrCodeImgVeiw.backgroundColor = YSSRandomColor;
    [self.view addSubview:qrCodeImgVeiw];
    [qrCodeImgVeiw makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(Value(120));
        make.top.equalTo(self.view).offset(64 + Value(138));
        make.width.height.equalTo(Value(80));
    }];
    
    UILabel *ruleTitleLabel = [[UILabel alloc] init];
    ruleTitleLabel.text = @"规则说明";
    ruleTitleLabel.textColor = [UIColor blackColor];
    ruleTitleLabel.font = YSSBoldSystemFont(Value(16));
    [self.view addSubview:ruleTitleLabel];
    [ruleTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(self.view).offset(Value(475) + 64);
    }];
    
    UILabel *ruleContentLabel = [[UILabel alloc] init];
    ruleContentLabel.text = @"1.二维码x下载官方微商云APP\n2.通过邀请码，完成注册可以提升个人用户级别\n3.被邀请的人，每次被邀请都会获得5元奖励\n4.此邀请码唯一，可以重复使用";
    ruleContentLabel.textColor = [UIColor lightGrayColor];
    ruleContentLabel.font = YSSSystemFont(Value(14));
    ruleContentLabel.numberOfLines = 0;
    [self.view addSubview:ruleContentLabel];
    [ruleContentLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(ruleTitleLabel.bottom).offset(Value(14));
    }];
    
}


@end
