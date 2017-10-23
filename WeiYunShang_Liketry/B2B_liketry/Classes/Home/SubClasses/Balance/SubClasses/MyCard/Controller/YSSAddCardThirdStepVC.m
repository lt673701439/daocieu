//
//  YSSAddCardThirdStepVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSAddCardThirdStepVC.h"
#import "YSSAddCardFinishVC.h"

#import "YSSTextFieldCell.h"

@interface YSSAddCardThirdStepVC ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;

@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSAddCardThirdStepVC

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
                     @{
                         @"title" : @"验证码",
                         @"subTitle" : @"",
                         @"placeholder" : @"填写验证码",
                         @"isShow" : @1
                         
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
    self.view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, ScreenW, Value(60))];
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(Value(14), Value(14), ScreenW - Value(28), Value(40))];
    
    label.text = [NSString stringWithFormat:@"本次操作需要短信确认，请输入%@收到的短信验证码", self.cardInfo[@"bcReservedTelephone"]];
    label.textColor = [UIColor lightGrayColor];
    label.font = YSSSystemFont(Value(14));
    label.numberOfLines = 0;
    [headerView addSubview:label];
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.tableHeaderView = headerView;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.tableFooterView = [UIView new];
    tableView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
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
    YSSTextFieldCell *cell = [self.tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]];
    
    
    YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
    NSString *bankName = self.cardInfo[@"bcBankName"];
//    招商银行·银联IC普卡
    NSDictionary *param = @{
                            @"bcMerchantId" : model.id,
                            @"bcUserName" : self.cardInfo[@"bcUserName"],
                            @"bcBankType" : [bankName containsString:@"信用"] ? @"信用卡" : @"储蓄卡",
                            @"bcBankName" : [[self.cardInfo[@"bcBankName"] componentsSeparatedByString:@"·"] firstObject],
                            @"bcBankNumber" : self.cardInfo[@"bcBankNumber"],
                            @"bcReservedTelephone" : self.cardInfo[@"bcReservedTelephone"],
                            @"bcIdNumber" : self.cardInfo[@"bcIdNumber"],
                            @"userMobile" : [YSSUserModel sharedInstence].loginName,
                            @"code" : cell.textField.text,
                            };
    [YSSHttpTool post:ADD_BANKCARD params:param isJsonSerializer:NO success:^(id json) {
        YSSAddCardFinishVC *tempVC = [[YSSAddCardFinishVC alloc] init];
        [self.navigationController pushViewController:tempVC animated:YES];
    } dataError:^(id json) {
        
        [YSSCommonTool showInfoWithStatus:@"请检查您的信息填写无误"];
        
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSTextFieldCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSTextFieldCell"];
    cell.phoneNum = self.cardInfo[@"bcReservedTelephone"];
    [cell configUIWithData:self.dataArr[indexPath.row]];
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    [self.view endEditing:YES];
}

@end
