//
//  YSSStoreRegistVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSStoreRegistVC.h"
#import "YSSCreatStoreVC.h"

#import "YSSStoreRegistHeaderView.h"
#import "YSSTextFieldCell.h"
#import "YSSRightArrowCell.h"
#import "YSSStoreRegistBottomView.h"

#import "YSSMerChanModel.h"

@interface YSSStoreRegistVC ()<UITableViewDataSource, UITableViewDelegate, YSSStoreRegistBottomViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSStoreRegistVC

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
//                     @{
//                         @"title" : @"手机号码",
//                         @"placeholder" : @"请填写手机号码",
//                         @"isShow" : @0
//                         },
//                     @{
//                         @"title" : @"验证码",
//                         @"placeholder" : @"填写验证码",
//                         @"isShow" : @1
//                         },
                     @{
                         @"title" : @"创建密码",
                         @"placeholder" : @"请输入密码",
                         @"isShow" : @0
                         },
                     @{
                         @"title" : @"确认密码",
                         @"placeholder" : @"请确认密码",
                         @"isShow" : @0
                         },
//                     @{
//                         @"title" : @"所在景区",
//                         @"isShow" : @1
//                         },
                     
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
    self.title = @"商家资料";
    
    YSSStoreRegistHeaderView *headerView = [[YSSStoreRegistHeaderView alloc] init];
    [self.view addSubview:headerView];
    [headerView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(@(Value(180)));
    }];
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    [tableView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self.view action:@selector(endEditing:)]];
    tableView.scrollEnabled = NO;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.rowHeight = Value(50);
    tableView.tableFooterView = [UIView new];
    tableView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.top.equalTo(headerView.bottom);
    }];
    
    [tableView registerClass:[YSSTextFieldCell class] forCellReuseIdentifier:@"YSSTextFieldCell"];
    [tableView registerClass:[YSSRightArrowCell class] forCellReuseIdentifier:@"YSSRightArrowCell"];
    
    YSSStoreRegistBottomView *bottomView = [[YSSStoreRegistBottomView alloc] init];
    bottomView.delegate = self;
    [self.view addSubview:bottomView];
    [bottomView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.height.equalTo(@(Value(50)));
    }];
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}


#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row < 4) {
        YSSTextFieldCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSTextFieldCell"];
        cell.textField.secureTextEntry = YES;
        [cell configUIWithData:self.dataArr[indexPath.row]];
        if (indexPath.row == 0) {
            [cell.textField becomeFirstResponder];
        }
        return cell;
    }else{
        YSSRightArrowCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSRightArrowCell"];
        [cell configUIWithData:self.dataArr[indexPath.row]];
        return cell;
    }
}

#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 4) {
        YSSLog(@"所在景区");
    }
}


- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    [self.view endEditing:YES];
}

#pragma mark - <YSSStoreRegistBottomViewDelegate>
/** 下一步 */
- (void)yssStoreRegistBottomViewDidClickNextStep:(YSSStoreRegistBottomView *)yssStoreRegistBottomView
{
    YSSLog(@"商户id =  %@", [YSSMerChanModel sharedInstence].id);
    
    YSSTextFieldCell *pwdCell = [self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]];
    YSSTextFieldCell *queenCell = [self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:1 inSection:0]];
    if (![pwdCell.textField.text isEqualToString:queenCell.textField.text]) {
        [YSSCommonTool showInfoWithStatus:@"请确保两次输入的密码相同"];
        return;
    }
    
    NSDictionary *param = @{
                            @"id" : [YSSMerChanModel sharedInstence].merchantUserId ? : @"",
                            @"userPwd" : pwdCell.textField.text,
                            };
    
    [YSSHttpTool put:USER_API params:param success:^(id json) {
        YSSCreatStoreVC *tempVC = [[YSSCreatStoreVC alloc] init];
        [self.navigationController pushViewController:tempVC animated:YES];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
    
    
}

@end
