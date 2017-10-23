//
//  YSSAddCardSecondStepVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSAddCardSecondStepVC.h"
#import "YSSAddCardThirdStepVC.h"

#import "YSSTextFieldCell.h"

@interface YSSAddCardSecondStepVC ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSAddCardSecondStepVC

- (NSArray *)dataArr
{
    
    if (_dataArr == nil) {
        _dataArr = @[
                     @{
                         @"title" : @"类型",
                         @"subTitle" : [[((NSString *)[YSSCommonTool returnBankName:self.cardInfo[@"bcBankNumber"]]) componentsSeparatedByString:@"·"] firstObject],
                         @"placeholder" : @"",
                         @"isShow" : @0
                         
                         },
                     @{
                         @"title" : @"手机号",
                         @"subTitle" : @"",
                         @"placeholder" : @"银行预留手机号",
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
    self.title = @"填写银行卡信息";
    
    
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
    NSString *bcBankName = ((YSSTextFieldCell *)[self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]]).textField.text;
    NSString *bcReservedTelephone = ((YSSTextFieldCell *)[self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:1 inSection:0]]).textField.text;
    
    if (bcReservedTelephone.length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请填写银行预留手机号"];
        return;
    }
    
    self.cardInfo[@"bcBankName"] = bcBankName;
    self.cardInfo[@"bcReservedTelephone"] = bcReservedTelephone;
    
    YSSLog(@"%@", self.cardInfo);
    
    YSSAddCardThirdStepVC *tempVC = [[YSSAddCardThirdStepVC alloc] init];
    tempVC.cardInfo = self.cardInfo;
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
        cell.textField.userInteractionEnabled = NO;
    }
    if (indexPath.row == 1) {
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
    
    NSString *text = @"同意《服务协议》";
    
    NSMutableAttributedString *attr = [[NSMutableAttributedString alloc] initWithString:text];
    [attr addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithHexString:YSSBlueColor] range:[text rangeOfString:@"《服务协议》"]];
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(Value(14), Value(10), ScreenW, Value(30))];
    label.font = YSSSystemFont(Value(12));
    label.textColor = [UIColor lightGrayColor];
    label.attributedText = attr;
    [view addSubview:label];
    
    return view;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return Value(50);
}

@end
