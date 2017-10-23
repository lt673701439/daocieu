//
//  YSSCreatStoreVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSCreatStoreVC.h"
#import "YSSSubmitAptitudesVC.h"
#import "YSSChooseLocationVC.h"
#import "YSSCommonInputVC.h"
#import "YSSCategoryVC.h"
#import "YSSServiceContentVC.h"

#import "YSSStoreRegistHeaderView.h"
#import "YSSIdentifyCell.h"

#import "YSSMerChanModel.h"
#import "YSSServerContentModel.h"
#import "YSSSpotModel.h"
#import "YSSCityModel.h"

@interface YSSCreatStoreVC ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSCreatStoreVC

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        
        NSMutableString *content = [NSMutableString string];
        if ([YSSMerChanModel sharedInstence].dicts.count > 0) {
            for (NSDictionary *dict in [YSSMerChanModel sharedInstence].dicts) {
                [content appendFormat:@"%@,", dict[@"text"]];
            }
            content = [content substringToIndex:content.length - 1].mutableCopy;
        }
        
        _dataArr = @[
                     @[
                         @{
                             @"title" : @"店铺名称",
                             @"content" :  [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantShopname].length > 0 ? [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantShopname] : @"填写名称"
                             }.mutableCopy,
                         @{
                             @"title" : @"所在城市",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantCityName].length > 0 ? [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantCityName] : @"选择城市"
                             }.mutableCopy,
                         @{
                             @"title" : @"所在景区",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantScenicSpot].length > 0 ? [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantScenicSpot] : @"选择景区"
                             }.mutableCopy,
                         @{
                             @"title" : @"公司地址",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantAddress].length > 0 ? [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantAddress] : @"填写公司地址"
                             }.mutableCopy,
                         @{
                             @"title" : @"分类",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantTypeName].length > 0 ? [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantTypeName] : @"选择商户类型"
                             
                             }.mutableCopy,
                         ],
                     @[
                         @{
                             @"title" : @"联系人姓名",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantContactName].length > 0 ? [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantContactName] : @"填写姓名"
                             
                             }.mutableCopy,
                         @{
                             @"title" : @"联系电话",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantContactMobile].length > 0 ? [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantContactMobile] : @"填写电话"
                             
                             }.mutableCopy,
                         @{
                             @"title" : @"营业时间",
                             @"content" : ([YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantOperatingStart].length > 0 || [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantOperatingEnd].length > 0) ? [NSString stringWithFormat:@"%@-%@", [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantOperatingStart], [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantOperatingEnd]] : @"添加营业时间"
                             
                             }.mutableCopy,
                         @{
                             @"title" : @"服务内容",
                             @"content" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].merchantContentId].length > 0 ? content : @"添加服务内容"
                             
                             }.mutableCopy,
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

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self.navigationController setNavigationBarHidden:NO animated:YES];
}

- (void)setupUI
{
    self.title = @"商户资料";
    
    YSSStoreRegistHeaderView *headerView = [[YSSStoreRegistHeaderView alloc] init];
    [headerView configUIWithStep:1];
    [self.view addSubview:headerView];
    [headerView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(@(Value(180)));
    }];
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.rowHeight = Value(50);
    tableView.tableFooterView = [UIView new];
    tableView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    tableView.contentInset = UIEdgeInsetsMake(0, 0, Value(50), 0);
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.top.equalTo(headerView.bottom);
    }];
    
    [tableView registerClass:[YSSIdentifyCell class] forCellReuseIdentifier:@"YSSIdentifyCell"];
    
    UIButton *nextStepBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [nextStepBtn addTarget:self action:@selector(nextStep) forControlEvents:UIControlEventTouchUpInside];
    [nextStepBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:UIControlStateNormal];
    [nextStepBtn setTitle:@"下一步" forState:UIControlStateNormal];
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
    YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
    
    if (![YSSCommonTool parseString:model.merchantShopname] || [YSSCommonTool parseString:model.merchantShopname].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写店铺名称"];
        return;
    }
    
    if (![YSSCommonTool parseString:model.merchantCity] || [YSSCommonTool parseString:model.merchantCity].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写所在城市"];
        return;
    }
    
    if (![YSSCommonTool parseString:model.merchantScenicSpot] || [YSSCommonTool parseString:model.merchantScenicSpot].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写所在景区"];
        return;
    }
    
    if (![YSSCommonTool parseString:model.merchantAddress] ||  [YSSCommonTool parseString:model.merchantAddress].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写公司地址"];
        return;
    }
    
    if (![YSSCommonTool parseString:model.merchantTypeId] || [YSSCommonTool parseString:model.merchantTypeId].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写商户分类"];
        return;
    }
    
    if (![YSSCommonTool parseString:model.merchantContactName] || [YSSCommonTool parseString:model.merchantContactName].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写联系人姓名"];
        return;
    }
    
    if (![YSSCommonTool parseString:model.merchantContactMobile] || [YSSCommonTool parseString:model.merchantContactMobile].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写联系电话"];
        return;
    }
    
    if (![YSSCommonTool parseString:model.merchantOperatingStart] || [YSSCommonTool parseString:model.merchantOperatingStart].length == 0 || [YSSCommonTool parseString:model.merchantOperatingEnd].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写营业时间"];
        return;
    }
    
    if (![YSSCommonTool parseString:model.merchantContentId] || [YSSCommonTool parseString:model.merchantContentId].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写服务内容"];
        return;
    }
    
    
    NSMutableDictionary *infoDict = model.infoDict;
    infoDict[@"merchantShopname"] = [YSSCommonTool parseString:model.merchantShopname];
    infoDict[@"merchantCity"] = [YSSCommonTool parseString:model.merchantCity];
    infoDict[@"merchantScenicSpot"] = [YSSCommonTool parseString:model.merchantScenicSpot];
    infoDict[@"merchantAddress"] = [YSSCommonTool parseString:model.merchantAddress];
    infoDict[@"merchantTypeId"] = [YSSCommonTool parseString:model.merchantTypeId];
    infoDict[@"merchantContactName"] = [YSSCommonTool parseString:model.merchantContactName];
    infoDict[@"merchantContactMobile"] = [YSSCommonTool parseString:model.merchantContactMobile];
    infoDict[@"merchantOperatingStart"] = [YSSCommonTool parseString:model.merchantOperatingStart];
    infoDict[@"merchantOperatingEnd"] = [YSSCommonTool parseString:model.merchantOperatingEnd];
    infoDict[@"merchantContentId"] = [YSSCommonTool parseString:model.merchantContentId];
    infoDict[@"dicts"] = @[];
    infoDict[@"isProof"] = @"0";
    
    [YSSHttpTool post:SAVE_MERCHANT params:infoDict isJsonSerializer:NO success:^(id json) {
        YSSSubmitAptitudesVC *tempVC = [[YSSSubmitAptitudesVC alloc] init];
        [self.navigationController pushViewController:tempVC animated:YES];
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:@"请检查您的信息输入无误"];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}


#pragma mark - <UITableViewDataSource>
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return self.dataArr.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.dataArr[section] count];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSIdentifyCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSIdentifyCell"];
    [cell confiUIWithData:self.dataArr[indexPath.section][indexPath.row]];
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] init];
    view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    return view;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    if (section == 0) {
        return 10.0f;
    }
    
    return 0.01f;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section == 0) {
        
        if (indexPath.row == 0) {
            
            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
            tempVC.navTitle = @"店铺名称";
            tempVC.index = 4;
            tempVC.block = ^(NSString *str) {
                if (str.length == 0) {
                    return ;
                }
                self.dataArr[indexPath.section][indexPath.row][@"content"] = str;
                [self.tableView reloadData];
                [YSSMerChanModel sharedInstence].merchantShopname = str;
            };
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (indexPath.row == 1) {
            
            YSSChooseLocationVC *tempVC = [[YSSChooseLocationVC alloc] init];
            tempVC.navTitle = @"选择城市";
            tempVC.index = 0;
            tempVC.block = ^(NSString *title, id model) {
                if (title.length == 0) {
                    return ;
                }
                self.dataArr[indexPath.section][indexPath.row][@"content"] = title;
                [self.tableView reloadData];
                
                YSSCityModel *citymodel = model;
                [YSSMerChanModel sharedInstence].merchantCity = citymodel.text;
                YSSLog(@"城市code%@", [YSSMerChanModel sharedInstence].merchantCity);
            };
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (indexPath.row == 2)
        {
            YSSChooseLocationVC *tempVC = [[YSSChooseLocationVC alloc] init];
            tempVC.navTitle = @"选择景区";
            tempVC.index = 1;
            tempVC.block = ^(NSString *title, id model) {
                if (title.length == 0) {
                    return ;
                }
                
                self.dataArr[indexPath.section][indexPath.row][@"content"] = title;
                [self.tableView reloadData];
                
                YSSSpotModel *spotModel = model;
                [YSSMerChanModel sharedInstence].merchantScenicSpot = spotModel.spotName;
                YSSLog(@"%@", [YSSMerChanModel sharedInstence].merchantScenicSpot);
            };
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (indexPath.row == 3)
        {
            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
            tempVC.navTitle = @"公司地址";
            tempVC.index = 0;
            tempVC.block = ^(NSString *str) {
                if (str.length == 0) {
                    return;
                }
                self.dataArr[indexPath.section][indexPath.row][@"content"] = str;
                [self.tableView reloadData];
                [YSSMerChanModel sharedInstence].merchantAddress = str;
            };
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (indexPath.row == 4)
        {
            YSSCategoryVC *tempVC = [[YSSCategoryVC alloc] init];
            tempVC.block = ^(NSString *title, YSSServerContentModel *model) {
                if (title.length == 0) {
                    return;
                }
                self.dataArr[indexPath.section][indexPath.row][@"content"] = title;
                [self.tableView reloadData];
                [YSSMerChanModel sharedInstence].merchantTypeId = model.id;
                YSSLog(@"分类id = %@", [YSSMerChanModel sharedInstence].merchantTypeId);
                
            };
            [self.navigationController pushViewController:tempVC animated:YES];
        }
        
    }else{
        
        if (indexPath.row == 0) {
            
            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
            tempVC.navTitle = @"联系人姓名";
            tempVC.index = 1;
            tempVC.block = ^(NSString *str) {
                if (str.length == 0) {
                    return;
                }
                self.dataArr[indexPath.section][indexPath.row][@"content"] = str;
                [self.tableView reloadData];
                [YSSMerChanModel sharedInstence].merchantContactName = str;
            };
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (indexPath.row == 1)
        {
            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
            tempVC.navTitle = @"联系电话";
            tempVC.index = 2;
            tempVC.block = ^(NSString *str) {
                if (str.length == 0) {
                    return;
                }
                self.dataArr[indexPath.section][indexPath.row][@"content"] = str;
                [self.tableView reloadData];
                [YSSMerChanModel sharedInstence].merchantContactMobile = str;
            };
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (indexPath.row == 2)
        {
            YSSCommonInputVC *tempVC = [[YSSCommonInputVC alloc] init];
            tempVC.navTitle = @"营业时间";
            tempVC.index = 3;
            tempVC.timeBlock = ^(NSString *startTime, NSString *endTime) {
                if (startTime.length == 0 || endTime.length == 0) {
                    return ;
                }
                self.dataArr[indexPath.section][indexPath.row][@"content"] = [NSString stringWithFormat:@"%@-%@", startTime, endTime];
                [self.tableView reloadData];
                [YSSMerChanModel sharedInstence].merchantOperatingStart = startTime;
                [YSSMerChanModel sharedInstence].merchantOperatingEnd = endTime;
                
            };
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (indexPath.row == 3)
        {
            YSSServiceContentVC *tempVC = [[YSSServiceContentVC alloc] init];
            tempVC.block = ^(NSMutableArray *selectArr) {
                if (!selectArr || selectArr.count == 0) {
                    return ;
                }
                NSMutableString *title = [NSMutableString string];
                NSMutableString *serviceId = [NSMutableString string];
                for (YSSServerContentModel *model in selectArr) {
                    [title appendFormat:@"%@,", model.text];
                    [serviceId appendFormat:@"%@,", model.id];
                }
                
                title = [title substringToIndex:title.length - 1].mutableCopy;
                serviceId = [serviceId substringToIndex:serviceId.length - 1].mutableCopy;
                self.dataArr[indexPath.section][indexPath.row][@"content"] = title;
                [self.tableView reloadData];
                [YSSMerChanModel sharedInstence].merchantContentId = serviceId;
            };
            [self.navigationController pushViewController:tempVC animated:YES];
        }
        
    }
}

@end
