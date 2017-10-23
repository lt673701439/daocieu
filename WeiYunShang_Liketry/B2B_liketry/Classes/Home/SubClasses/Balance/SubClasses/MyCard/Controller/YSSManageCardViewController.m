//
//  YSSManageCardViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSManageCardViewController.h"
#import "YSSAddCardFirstStepVC.h"

#import "YSSManagerCardFooterView.h"
#import "YSSManagerCardListCell.h"
#import "YSSSetWithDrawPswView.h"

#import "YSSCardModel.h"

@interface YSSManageCardViewController ()<UITableViewDataSource, UITableViewDelegate, YSSSetWithDrawPswViewDelegate>
@property (nonatomic, strong) NSMutableArray *modelArr;
@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSManageCardViewController

- (NSMutableArray *)modelArr
{
    if (_modelArr == nil) {
        _modelArr = [NSMutableArray array];
    }
    return _modelArr;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    [self loadData];
}

- (void)setupUI
{
    self.title = @"我的银行卡";
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.rowHeight = Value(105);
    [self.view addSubview:tableView];
    tableView.separatorStyle = 0;
    tableView.contentInset = UIEdgeInsetsMake(Value(10), 0, 0, 0);
    [tableView setContentOffset:CGPointMake(0, - Value(10)) animated:NO];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    [tableView registerClass:[YSSManagerCardListCell class] forCellReuseIdentifier:@"YSSManagerCardListCell"];
    
    YSSManagerCardFooterView *footerView = [[YSSManagerCardFooterView alloc] initWithFrame:CGRectMake(0, 0, ScreenW, Value(60))];
    [footerView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(addCard)]];
    tableView.tableFooterView = footerView;
}

- (void)loadData
{
    YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
    
    NSDictionary *param = @{
                            @"merchantId" : model.id
                            };
    [YSSHttpTool post:GET_BANKCARD params:param isJsonSerializer:NO success:^(id json) {
        
        for (NSDictionary *dict in json[@"result"]) {
            YSSCardModel *model = [YSSCardModel mj_objectWithKeyValues:dict];
            [self.modelArr addObject:model];
        }
        
        [self.tableView reloadData];
        
    } failure:^(NSError *error) {
        
    }];
    
    
}

#pragma mark - action
- (void)addCard
{
    YSSLog(@"%@", [YSSUserModel sharedInstence].cashPwd);
    if ([YSSUserModel sharedInstence].cashPwd.length == 0) {
        YSSSetWithDrawPswView *view =[YSSSetWithDrawPswView show];
        view.delegate = self;
    }else{
        YSSAddCardFirstStepVC *tempVC = [[YSSAddCardFirstStepVC alloc] init];
        [self.navigationController pushViewController:tempVC animated:YES];
    }
    
    
}

#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.modelArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSManagerCardListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSManagerCardListCell"];
    cell.model = self.modelArr[indexPath.row];
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (self.block) {
        self.block(self.modelArr[indexPath.row]);
        [self.navigationController popViewControllerAnimated:YES];
    }
}


#pragma mark - <YSSSetWithDrawPswViewDelegate>
/** 6位密码输入完成并校验通过 */
- (void)yssSetWithDrawPswViewDidFinishInput:(YSSSetWithDrawPswView *)yssSetWithDrawPswView
{
    YSSAddCardFirstStepVC *tempVC = [[YSSAddCardFirstStepVC alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

@end
