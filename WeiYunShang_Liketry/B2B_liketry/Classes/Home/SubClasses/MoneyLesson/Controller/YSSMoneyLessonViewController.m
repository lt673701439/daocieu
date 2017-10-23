//
//  YSSMoneyLessonViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMoneyLessonViewController.h"
#import "YSSMoneyDetailViewController.h"

#import "YSSHomeListCell.h"

#import "YSSMoneyLessonModel.h"

@interface YSSMoneyLessonViewController ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@property (nonatomic, strong) NSMutableArray *modelArr;
@property (nonatomic, strong) UITableView *tableView;
@end

@implementation YSSMoneyLessonViewController

- (NSMutableArray *)modelArr
{
    if (_modelArr == nil) {
        _modelArr = [NSMutableArray array];
    }
    return _modelArr;
}

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
                     @{
                         @"time" : @"2017年8月21",
                         @"content" : @"如何赚到第一桶金"
                         
                         },
                     @{
                         @"time" : @"2017年8月25",
                         @"content" : @"如何提现与余额管理"
                         
                         },
                     @{
                         @"time" : @"2017年8月29",
                         @"content" : @"注册与审核注意事项"
                         
                         },
                     @{
                         @"time" : @"2017年8月20",
                         @"content" : @"如何利用我们做店铺营销"
                         
                         },
                     @{
                         @"time" : @"2017年8月26",
                         @"content" : @"如何获得渠道优惠"
                         
                         },
                     ];
    }
    return _dataArr;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    [self loadData];
}

- (void)setupUI
{
    self.title = @"赚钱课堂";
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.separatorStyle = 0;
    tableView.backgroundColor = [UIColor colorWithHexString:YSSBGColor];
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    [tableView registerClass:[YSSHomeListCell class] forCellReuseIdentifier:@"YSSHomeListCell"];
}

- (void)loadData
{
    NSDictionary *param = @{
                            @"pagination" : @{
                                    @"number" : @1000,
                                    @"numberOfPages" : @1,
                                    @"start" : @0,
                                    @"totalItemCount" : @2
                                    },
                            @"search": @{
//                                    @"orMerchantId" : [YSSMerChanModel sharedInstence].id ? : @"",
                                    },
                            @"sort": @{
                                    @"predicate" : @"showDate",
                                    @"reverse": @(false)
                                    },
                            
                            };
    [YSSHttpTool post:MAKE_MONEY_LIST params:param isJsonSerializer:YES success:^(id json) {
        NSArray *dictArr = json[@"result"][@"records"];
        for (NSDictionary *dict in dictArr) {
            YSSMoneyLessonModel *model = [YSSMoneyLessonModel mj_objectWithKeyValues:dict];
            [self.modelArr addObject:model];
        }
        [self.tableView reloadData];
        
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:json[@"msg"]];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
//    return self.dataArr.count;
    return self.modelArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSHomeListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSHomeListCell"];
//    [cell configData:self.dataArr[indexPath.row]];
    cell.moneyLessonModel = self.modelArr[indexPath.row];
    return cell;
}


#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSMoneyDetailViewController *tempVC = [[YSSMoneyDetailViewController alloc] init];
    tempVC.detailId = ((YSSMoneyLessonModel *)self.modelArr[indexPath.row]).id;
    [self.navigationController pushViewController:tempVC animated:YES];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return [YSSHomeListCell cellHeightWithMoneyLessonModel:self.modelArr[indexPath.row]];
}

@end
