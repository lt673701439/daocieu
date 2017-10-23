//
//  YSSSafeManageVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSSafeManageVC.h"
#import "YSSChangePswVC.h"

#import "YSSSettingTFCell.h"

@interface YSSSafeManageVC ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@end

@implementation YSSSafeManageVC

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
                     @[@"提现密码"],
                     @[@"登录密码"],
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
    self.title = @"安全管理";
    
    UITableView *tableView = [[UITableView alloc] init];
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.tableFooterView = [UIView new];
    tableView.rowHeight = Value(50);
    tableView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    [tableView registerClass:[YSSIdentifyCell class] forCellReuseIdentifier:@"YSSIdentifyCell"];
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
    [cell confiUIWithTitle:self.dataArr[indexPath.section][indexPath.row]];
    return cell;
}


#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section == 0) {
        YSSChangePswVC *tempVC = [[YSSChangePswVC alloc] init];
        tempVC.navTitle = @"修改提现密码";
        [self.navigationController pushViewController:tempVC animated:YES];
    }else{
        YSSChangePswVC *tempVC = [[YSSChangePswVC alloc] init];
        tempVC.navTitle = @"修改登录密码";
        [self.navigationController pushViewController:tempVC animated:YES];
    }
}

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

@end
