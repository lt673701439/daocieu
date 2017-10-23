//
//  YSSAddCardFirstStepVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/10.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSAddCardFirstStepVC.h"
#import "YSSAddCardSecondStepVC.h"

#import "YSSTextFieldCell.h"

@interface YSSAddCardFirstStepVC ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSAddCardFirstStepVC

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
                     @{
                         @"title" : @"持卡人",
                         @"subTitle" : @"",
                         @"placeholder" : @"持卡人姓名",
                         @"isShow" : @0
                         
                         },
                     @{
                         @"title" : @"身份证",
                         @"subTitle" : @"",
                         @"placeholder" : @"身份证号",
                         @"isShow" : @0
                         
                         },
                     @{
                         @"title" : @"卡号",
                         @"subTitle" : @"",
                         @"placeholder" : @"银行卡号",
                         @"isShow" : @0
                         
                         },
                     
                     
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
    self.title = @"添加银行卡";
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    tableView.tableFooterView = [UIView new];
    tableView.rowHeight = Value(50);
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    [tableView registerClass:[YSSTextFieldCell class] forCellReuseIdentifier:@"YSSTextFieldCell"];
    
    UIButton *nextStepBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [nextStepBtn addTarget:self action:@selector(nextStep) forControlEvents:UIControlEventTouchUpInside];
    [nextStepBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:0];
    [nextStepBtn setTitle:@"下一步" forState:0];
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
    NSString *bcUserName = ((YSSTextFieldCell *)[self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]]).textField.text;
    NSString *bcIdNumber = ((YSSTextFieldCell *)[self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:1 inSection:0]]).textField.text;
    NSString *bcBankNumber = ((YSSTextFieldCell *)[self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:2 inSection:0]]).textField.text;
    
    if (bcUserName.length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写持卡人姓名"];
        return;
    }
    
    if (bcIdNumber.length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写身份证号码"];
        return;
    }
    
    if ([YSSCommonTool returnBankName:bcBankNumber].length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写正确的卡号"];
        return;
    }
    
    NSMutableDictionary *cardInfo = @{
                                      @"bcUserName" : bcUserName,
                                      @"bcIdNumber" : bcIdNumber,
                                      @"bcBankNumber" : bcBankNumber,
                                      }.mutableCopy;
    
    YSSLog(@"%@", cardInfo);
    
    YSSAddCardSecondStepVC *tempVC = [[YSSAddCardSecondStepVC alloc] init];
    tempVC.cardInfo = cardInfo;
    [self.navigationController pushViewController:tempVC animated:YES];
}


#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSTextFieldCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSTextFieldCell"];
    [cell configUIWithData:self.dataArr[indexPath.row]];
    if (indexPath.row == 0) {
        [cell.textField becomeFirstResponder];
    }
    return cell;
}


#pragma mark - <UITableViewDelegate>
- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    [self.view endEditing:YES];
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, ScreenW, Value(50))];
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(Value(14), Value(10), ScreenW, Value(30))];
    label.text = @"请绑定持卡本人的银行卡";
    label.font = YSSSystemFont(Value(12));
    label.textColor = [UIColor lightGrayColor];
    [view addSubview:label];
    
    return view;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return Value(50);
}


@end
