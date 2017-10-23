//
//  YSSIdentifyViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSIdentifyViewController.h"
#import "YSSChooseLocationVC.h"
#import "YSSCommonInputVC.h"
#import "YSSCategoryVC.h"

#import "YSSIdentifyCell.h"

#import "YSSSpotModel.h"
#import "YSSServerContentModel.h"

@interface YSSIdentifyViewController ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSIdentifyViewController

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
                     @[
                         @{
                             @"title" : @"所在城市",
                             @"content" : [YSSMerChanModel sharedInstence].merchantCityName.length > 0 ? [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantCityName] :   [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantCity],
                             @"isHidden" : @"1"
                             },
                         @{
                             @"title" : @"所在景区",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantScenicSpot],
                             @"isHidden" : @"1"
                             },
                         @{
                             @"title" : @"商户名称",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantShopname],
                             @"isHidden" : @"1"
                             },
                         @{
                             @"title" : @"公司地址",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantAddress],
                             @"isHidden" : @"1"
                             },
                         @{
                             @"title" : @"商户分类",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantTypeName],
                             @"isHidden" : @"1"
                             },
                         ],
                     @[
                         @{
                             @"title" : @"联系人姓名",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantContactName],
                             @"isHidden" : @"1"
                             },
                         @{
                             @"title" : @"联系电话",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantContactMobile],
                             @"isHidden" : @"1"
                             },
                         @{
                             @"title" : @"营业执照",
                             @"content" : @"审核已经通过",
                             @"isHidden" : @"1"
                             },
                         ],
                     ];
    }
    return _dataArr;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)setupUI
{
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.rowHeight = Value(50);
    tableView.tableFooterView = [UIView new];
    tableView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    [tableView registerClass:[YSSIdentifyCell class] forCellReuseIdentifier:@"YSSIdentifyCell"];
    
//    UIButton *nextStepBtn = [UIButton buttonWithType:UIButtonTypeCustom];
//    [nextStepBtn addTarget:self action:@selector(nextStep) forControlEvents:UIControlEventTouchUpInside];
//    [nextStepBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:UIControlStateNormal];
//    [nextStepBtn setTitle:@"提交审核" forState:UIControlStateNormal];
//    nextStepBtn.titleLabel.font = YSSBoldSystemFont(Value(16));
//    [self.view addSubview:nextStepBtn];
//    [nextStepBtn makeConstraints:^(MASConstraintMaker *make) {
//        make.left.bottom.right.equalTo(self.view);
//        make.height.equalTo(@(Value(50)));
//    }];
}

#pragma mark - action
- (void)nextStep
{
    YSSLog(@"提交审核");
    YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
    NSMutableDictionary *infoDict = model.infoDict;
    infoDict[@"merchantCity"] = [YSSCommonTool parseString:model.merchantCity];
    infoDict[@"merchantScenicSpot"] = [YSSCommonTool parseString:model.merchantScenicSpot];
    infoDict[@"merchantShopname"] = [YSSCommonTool parseString:model.merchantShopname];
    infoDict[@"merchantAddress"] = [YSSCommonTool parseString:model.merchantAddress];
    infoDict[@"merchantTypeId"] = [YSSCommonTool parseString:model.merchantTypeId];
    infoDict[@"merchantContactName"] = [YSSCommonTool parseString:model.merchantContactName];
    infoDict[@"merchantContactMobile"] = [YSSCommonTool parseString:model.merchantContactMobile];
    infoDict[@"isProof"] = @"1";
    
    [YSSHttpTool post:SAVE_MERCHANT params:infoDict isJsonSerializer:NO success:^(id json) {
        
    } failure:^(NSError *error) {
        
    }];
}

#pragma mark - <UITableViewDataSource>
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (section == 0) {
        return [self.dataArr[0] count];
    }else{
        return [self.dataArr[1] count];
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSIdentifyCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSIdentifyCell"];
    [cell confiUIWithData:self.dataArr[indexPath.section][indexPath.row]];
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] init];
    view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    return view;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    if (section == 1) {
        return Value(10);
    }
    
    return 0.01f;
}


//- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    if (indexPath.section == 0) {
//        if (indexPath.row == 0) {
//
//            YSSChooseLocationVC *tempVC = [[YSSChooseLocationVC alloc] init];
//            tempVC.navTitle = @"选择城市";
//            tempVC.index = 0;
//            tempVC.block = ^(NSString *title, id model) {
//                if (title.length == 0) {
//                    return ;
//                }
////                self.dataArr[indexPath.section][indexPath.row][@"content"] = title;
//                [self.tableView reloadData];
//            };
//            [self.navigationController pushViewController:tempVC animated:YES];
//
//        }else if (indexPath.row == 1)
//        {
//
//            YSSChooseLocationVC *tempVC = [[YSSChooseLocationVC alloc] init];
//            tempVC.navTitle = @"选择景区";
//            tempVC.index = 1;
//            tempVC.block = ^(NSString *title, id model) {
//                if (title.length == 0) {
//                    return ;
//                }
//
////                self.dataArr[indexPath.section][indexPath.row][@"content"] = title;
//                [self.tableView reloadData];
//
//                YSSSpotModel *spotModel = model;
//                [YSSMerChanModel sharedInstence].merchantScenicSpot = spotModel.spotId;
//                YSSLog(@"%@", [YSSMerChanModel sharedInstence].merchantScenicSpot);
//            };
//            [self.navigationController pushViewController:tempVC animated:YES];
//
//        }else if (indexPath.row == 2)
//        {
//
//            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
//            tempVC.navTitle = @"店铺名称";
//            tempVC.index = 4;
//            tempVC.block = ^(NSString *str) {
//                if (str.length == 0) {
//                    return ;
//                }
//                self.dataArr[indexPath.section][indexPath.row][@"content"] = str;
//                [self.tableView reloadData];
//                [YSSMerChanModel sharedInstence].merchantShopname = str;
//            };
//            [self.navigationController pushViewController:tempVC animated:YES];
//
//        }else if (indexPath.row == 3)
//        {
//
//            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
//            tempVC.navTitle = @"公司地址";
//            tempVC.index = 0;
//            tempVC.block = ^(NSString *str) {
//                if (str.length == 0) {
//                    return;
//                }
//                self.dataArr[indexPath.section][indexPath.row][@"content"] = str;
//                [self.tableView reloadData];
//                [YSSMerChanModel sharedInstence].merchantAddress = str;
//            };
//            [self.navigationController pushViewController:tempVC animated:YES];
//
//        }else{
//
//            YSSCategoryVC *tempVC = [[YSSCategoryVC alloc] init];
//            tempVC.block = ^(NSString *title, YSSServerContentModel *model) {
//                if (title.length == 0) {
//                    return;
//                }
//                self.dataArr[indexPath.section][indexPath.row][@"content"] = title;
//                [self.tableView reloadData];
//                [YSSMerChanModel sharedInstence].merchantTypeId = model.id;
//                YSSLog(@"分类id = %@", [YSSMerChanModel sharedInstence].merchantTypeId);
//
//            };
//            [self.navigationController pushViewController:tempVC animated:YES];
//
//        }
//    }else{
//        if (indexPath.row == 0) {
//
//            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
//            tempVC.navTitle = @"联系人姓名";
//            tempVC.index = 1;
//            tempVC.block = ^(NSString *str) {
//                if (str.length == 0) {
//                    return;
//                }
//                self.dataArr[indexPath.section][indexPath.row][@"content"] = str;
//                [self.tableView reloadData];
//                [YSSMerChanModel sharedInstence].merchantContactName = str;
//            };
//            [self.navigationController pushViewController:tempVC animated:YES];
//
//        }else if (indexPath.row == 1)
//        {
//
//            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
//            tempVC.navTitle = @"联系电话";
//            tempVC.index = 2;
//            tempVC.block = ^(NSString *str) {
//                if (str.length == 0) {
//                    return;
//                }
//                self.dataArr[indexPath.section][indexPath.row][@"content"] = str;
//                [self.tableView reloadData];
//                [YSSMerChanModel sharedInstence].merchantContactMobile = str;
//            };
//            [self.navigationController pushViewController:tempVC animated:YES];
//
//        }else{
//
//
//
//        }
//    }
//}

@end
