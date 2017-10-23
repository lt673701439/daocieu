//
//  YSSSettingViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSSettingViewController.h"
#import "YSSNavigationController.h"
#import "YSSLoginViewController.h"
#import "YSSSafeManageVC.h"
#import "YSSRecommendVC.h"
#import "YSSAboutUSViewController.h"

#import "YSSTempViewController.h"
#import "YSSStoreRegistVC.h"

#import "YSSIdentifyCell.h"
#import "YSSSwitchCell.h"

@interface YSSSettingViewController ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@end

@implementation YSSSettingViewController

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
                     @[@"安全管理", @"消息通知"],
                     @[@"联系我们", @"关于我们"],
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
    self.title = @"设置";
    
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
    [tableView registerClass:[YSSSwitchCell class] forCellReuseIdentifier:@"YSSSwitchCell"];
    
    UIButton *logoutBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [logoutBtn addTarget:self action:@selector(logout) forControlEvents:UIControlEventTouchUpInside];
    logoutBtn.backgroundColor = [UIColor colorWithHexString:@"e3e3e3"];
    [logoutBtn setTitle:@"注销账户" forState:UIControlStateNormal];
    [logoutBtn setTitleColor:YSSColorHexString(@"#9b9b9b") forState:0];
    logoutBtn.titleLabel.font = YSSSystemFont(Value(12));
    [self.view addSubview:logoutBtn];
    [logoutBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self.view);
        make.height.equalTo(@(Value(50)));
    }];
}

#pragma mark - action
- (void)logout
{
    UIAlertController *alertVC = [UIAlertController alertControllerWithTitle:@"确认退出登录?" message:nil preferredStyle:UIAlertControllerStyleActionSheet];
    UIAlertAction *queenAction = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDestructive handler:^(UIAlertAction * _Nonnull action) {
        
        [SVProgressHUD show];
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.5 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [SVProgressHUD dismiss];
            [[NSUserDefaults standardUserDefaults] setObject:@0 forKey:@"isLogin"];
            YSSNavigationController *navVC = [[YSSNavigationController alloc] initWithRootViewController:[[YSSLoginViewController alloc] init]];
            [UIApplication sharedApplication].keyWindow.rootViewController = navVC;
        });
        
    }];
    
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil];
    [alertVC addAction:queenAction];
    [alertVC addAction:cancelAction];
    [self presentViewController:alertVC animated:YES completion:nil];
    
    
    
    
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
    if (indexPath.section == 0 && indexPath.row == 1) {
        YSSSwitchCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSSwitchCell"];
        [cell confiUIWithTitle:self.dataArr[indexPath.section][indexPath.row]];
        return cell;
    }
    
    
    YSSIdentifyCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSIdentifyCell"];
    [cell confiUIWithTitle:self.dataArr[indexPath.section][indexPath.row]];
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            
            YSSSafeManageVC *tempVC = [[YSSSafeManageVC alloc] init];
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (indexPath.row == 1)
        {
            
            
            
        }else if (indexPath.row == 2)
        {
            YSSRecommendVC *tempVC = [[YSSRecommendVC alloc] init];
            [self.navigationController pushViewController:tempVC animated:YES];
        }
    }else{
        if (indexPath.row == 0) {
            
            //批量生成商户
//            YSSTempViewController *tempVC= [[YSSTempViewController alloc] init];
//            [self.navigationController pushViewController:tempVC animated:YES];
//            return;
            
//            YSSStoreRegistVC *tempVC = [[YSSStoreRegistVC alloc] init];
//            [self.navigationController pushViewController:tempVC animated:YES];
//            return
            
            UIAlertController *alertVC = [UIAlertController alertControllerWithTitle:@"确认呼叫客服?" message:@"021-65686809" preferredStyle:UIAlertControllerStyleActionSheet];
            UIAlertAction *queenAction = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                UIWebView * callWebview = [[UIWebView alloc] init];
                [callWebview loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"tel:021-65686809"]]];
                [[UIApplication sharedApplication].keyWindow addSubview:callWebview];
            }];
            
            UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil];
            [alertVC addAction:queenAction];
            [alertVC addAction:cancelAction];
            [self presentViewController:alertVC animated:YES completion:nil];
            
        }else if (indexPath.row == 1)
        {
            YSSAboutUSViewController *tempVC = [[YSSAboutUSViewController alloc] init];
            [self.navigationController pushViewController:tempVC animated:YES];
        }
    }
}


- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] init];
    view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    if (section == 1) {
        UILabel *label = [[UILabel alloc] init];
        label.text = @"要开启或停用，您可以在设置>通知中心>微商云中手动设置";
        label.font = YSSSystemFont(Value(12));
        label.textColor = [UIColor lightGrayColor];
        label.frame = CGRectMake(Value(14), Value(5), ScreenW, Value(30));
        [view addSubview:label];
    }
    
    return view;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    if (section == 1) {
        return Value(40);
    }

    return 0.01f;
}

@end
