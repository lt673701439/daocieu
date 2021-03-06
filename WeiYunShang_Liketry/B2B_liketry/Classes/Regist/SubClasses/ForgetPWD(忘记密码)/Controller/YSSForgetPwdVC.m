//
//  YSSForgetPwdVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/10/10.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSForgetPwdVC.h"

#import "YSSSettingTFCell.h"

@interface YSSForgetPwdVC ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSForgetPwdVC

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
                     @{@"title" : @"新登录密码", @"placeHolder" : @"输入新登录密码" , @"isHidden" : @"1"},
                     @{@"title" : @"确认登录密码", @"placeHolder" : @"再次输入新登录密码" , @"isHidden" : @"1"}
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
    self.title = @"忘记密码";
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.tableFooterView = [UIView new];
    tableView.rowHeight = Value(50);
    tableView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    [tableView registerClass:[YSSSettingTFCell class] forCellReuseIdentifier:@"YSSSettingTFCell"];
    
    UIButton *changeBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [changeBtn addTarget:self action:@selector(change) forControlEvents:UIControlEventTouchUpInside];
    [changeBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:0];
    [changeBtn setTitle:@"提交修改" forState:UIControlStateNormal];
    changeBtn.titleLabel.font = YSSBoldSystemFont(Value(16));
    [self.view addSubview:changeBtn];
    [changeBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self.view);
        make.height.equalTo(@(Value(50)));
    }];
}

#pragma mark - action
- (void)change
{
    NSString *newPwd = ((YSSSettingTFCell *)[self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]]).textField.text;
    NSString *queenPwd = ((YSSSettingTFCell *)[self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:1 inSection:0]]).textField.text;
    
    if (![newPwd isEqualToString:queenPwd]) {
        [YSSCommonTool showInfoWithStatus:@"请保证新密码输入一致"];
        return;
    }
    
    
    NSDictionary *param = @{
                            @"pwd" : @"",
                            @"id" : @"",
                            @"newPwd" : newPwd,
                            @"phone" : self.phoneNum,
                            @"type" : @(2)  //0-登陆/1-提现
                            };
    [YSSHttpTool post:CHANGE_PWD params:param isJsonSerializer:YES success:^(id json) {
        [self.navigationController popToRootViewControllerAnimated:YES];
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:json[@"msg"]];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSSettingTFCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSSettingTFCell"];
//    [cell confiUIWithTitle:self.dataArr[indexPath.row]];
    [cell confiUIWithData:self.dataArr[indexPath.row]];
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    [self.view endEditing:YES];
}

@end
