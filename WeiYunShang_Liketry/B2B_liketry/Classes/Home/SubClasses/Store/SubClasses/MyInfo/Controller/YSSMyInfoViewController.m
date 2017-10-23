//
//  YSSMyInfoViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMyInfoViewController.h"
#import "YSSCommonInputVC.h"

#import "YSSMyInfoView.h"

#import "YSSAddTagView.h"
#import "GBTagListView.h"

@interface YSSMyInfoViewController ()<YSSMyInfoViewDelegate>
@property (nonatomic, strong) YSSMyInfoView *infoView;
@end

@implementation YSSMyInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)setupUI
{
    YSSMyInfoView *infoView = [[YSSMyInfoView alloc] init];
    self.infoView = infoView;
    infoView.delegate = self;
    [self.view addSubview:infoView];
    [infoView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(Value(14));
        make.right.equalTo(self.view).offset(- Value(14));
        make.top.equalTo(self.view).offset(Value(14));
//        make.height.equalTo(@(Value(500)));
    }];
    
    [infoView addShadow:[UIColor blackColor] shadowOffset:CGSizeMake(0, 0) shadowOpacity:0.1 shadowRadius:10];
    infoView.layer.cornerRadius = 5;
}


#pragma mark - <YSSMyInfoViewDelegate>
/** 点击了服务内容标签 */
- (void)yssMyInfoView:(YSSMyInfoView *)yssMyInfoView didClickTag:(NSArray *)tagArr
{
    YSSAddTagView *tagView = [YSSAddTagView show];
    tagView.block = ^(NSArray *tagArr) {
        [self.infoView resetTagView:tagArr];
    };
}

/** 点击了修改手机号 */
- (void)yssMyInfoView:(YSSMyInfoView *)yssMyInfoView didClickChangePhone:(UILabel *)phoneLabel
{
    YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
    tempVC.navTitle = @"联系电话";
    tempVC.index = 2;
    tempVC.block = ^(NSString *str) {
        if (str.length == 0) {
            return;
        }
        
        YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
        NSDictionary *param = @{
                                @"merchantId" : [YSSCommonTool parseString:model.id],
                                @"type" : @"1",
                                @"value" : str,
                                };
        
        [YSSHttpTool post:CHANGE_MERCHANT params:param isJsonSerializer:NO success:^(id json) {
            [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:json[@"result"]] forKey:@"merchanInfoDict"];
            
            phoneLabel.text = str;
            [YSSMerChanModel sharedInstence].merchantContactMobile = str;
            
        } failure:^(NSError *error) {
            
        }];
        
    };
    [self.navigationController pushViewController:tempVC animated:YES];
}

/** 点击了修改地址 */
- (void)yssMyInfoView:(YSSMyInfoView *)yssMyInfoView didClickChangeLocation:(UILabel *)locationLabel
{
    YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
    tempVC.navTitle = @"公司地址";
    tempVC.index = 0;
    tempVC.block = ^(NSString *str) {
        if (str.length == 0) {
            return;
        }
        
        YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
        NSDictionary *param = @{
                                @"merchantId" : [YSSCommonTool parseString:model.id],
                                @"type" : @"2",
                                @"value" : str,
                                };
        
        [YSSHttpTool post:CHANGE_MERCHANT params:param isJsonSerializer:NO success:^(id json) {
            [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:json[@"result"]] forKey:@"merchanInfoDict"];
            
            locationLabel.text = str;
            [YSSMerChanModel sharedInstence].merchantAddress = str;
            
        } failure:^(NSError *error) {
            
        }];
        
    };
    [self.navigationController pushViewController:tempVC animated:YES];
}

@end
