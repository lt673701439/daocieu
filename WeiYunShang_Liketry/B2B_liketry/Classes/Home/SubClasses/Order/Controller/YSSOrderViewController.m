//
//  YSSOrderViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSOrderViewController.h"
#import "YSSChooseDateVC.h"

#import "YSSOrderListCell.h"
#import "YSSOrderListBannerView.h"

#import "YSSOrderListModel.h"

@interface YSSOrderViewController ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) YSSOrderListBannerView *bannerView;

@property (nonatomic, strong) UILabel *placeholderLabel;
@end

@implementation YSSOrderViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupNav];
    
    if (!self.orderlistModelArr) {
        [self loadData];
    }else{
        [self setupUI];
    }
}

- (void)setupNav
{
    UIButton *rightBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [rightBtn setTitle:@"选择时间" forState:0];
    [rightBtn setTitleColor:[UIColor orangeColor] forState:0];
    rightBtn.titleLabel.font = YSSSystemFont(Value(14));
    [rightBtn sizeToFit];
    [rightBtn addTarget:self action:@selector(chooseDate) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:rightBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
}

- (void)loadData
{
    YSSLog(@"id === %@", [YSSMerChanModel sharedInstence].id);
    [self getTodayOrder];
}

/** 获取今日成单 */
- (void)getTodayOrder
{
    
    self.orderlistModelArr = [NSMutableArray array];
    NSDictionary *param = @{
                            @"pagination" : @{
                                    @"number" : @1000,
                                    @"numberOfPages" : @1,
                                    @"start" : @0,
                                    @"totalItemCount" : @2
                                    },
                            @"search": @{
                                    @"orMerchantId" : [YSSMerChanModel sharedInstence].id ? : @"",
                                    },
                            @"sort": @{
                                    @"predicate" : @"create_time",
                                    @"reverse": @(false)
                                    },
                            @"startSearch" : [NSString stringWithFormat:@"%@ 00:00:00", [YSSCommonTool getTodayDate:YSSDateFormatterTypeLine]],
                            @"endSearch" : [NSString stringWithFormat:@"%@ 23:59:59", [YSSCommonTool getTodayDate:YSSDateFormatterTypeLine]],
                            };
    [YSSHttpTool post:GET_ORDERLIST params:param isJsonSerializer:YES success:^(id json) {
        
        NSArray *dictArr = json[@"result"][@"records"];
        for (NSDictionary *dict in dictArr) {
            YSSOrderListModel *model = [YSSOrderListModel mj_objectWithKeyValues:dict];
            [self.orderlistModelArr addObject:model];
        }
        
        self.extra = json[@"extra"];
        
        [self setupUI];
        
    } failure:^(NSError *error) {
        
    }];
}

- (void)setupUI
{
    self.title = @"订账单";
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.backgroundColor = [UIColor colorWithHexString:YSSBGColor];
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.rowHeight = Value(140);
    tableView.separatorStyle = 0;
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    [tableView registerClass:[YSSOrderListCell class] forCellReuseIdentifier:@"YSSOrderListCell"];
    
    tableView.contentInset = UIEdgeInsetsMake(Value(50), 0, 0, 0);
    [tableView setContentOffset:CGPointMake(0, - Value(50)) animated:NO];
    
    YSSOrderListBannerView *bannerView = [[YSSOrderListBannerView alloc] init];
    self.bannerView = bannerView;
    [self.view addSubview:bannerView];
    [bannerView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(@(Value(50)));
    }];
    [bannerView configUIWithPrice:self.extra];
    
    YSSAlphaView * alphaView= [[YSSAlphaView alloc] initWithFrame:CGRectMake(0, Value(50) + 64, ScreenW, Value(10))];
    [self.view addSubview:alphaView];
    
    UILabel *placeholderLabel = [[UILabel alloc] init];
    self.placeholderLabel = placeholderLabel;
    placeholderLabel.hidden = self.orderlistModelArr.count > 0;
    placeholderLabel.text = @"暂无账单信息";
    placeholderLabel.textColor = [UIColor lightGrayColor];
    placeholderLabel.font = YSSSystemFont(Value(12));
    [tableView addSubview:placeholderLabel];
    [placeholderLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(tableView).offset(Value(80));
        make.centerX.equalTo(tableView);
    }];
    
}

- (void)loadDataWithStartDate:(NSDate *)startDate endDate:(NSDate *)endDate;
{
    [self.orderlistModelArr removeAllObjects];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = @"yyyy-MM-dd";
    
    
    NSDictionary *param = @{
                            @"pagination" : @{
                                    @"number" : @1000,
                                    @"numberOfPages" : @1,
                                    @"start" : @0,
                                    @"totalItemCount" : @2
                                    },
                            @"search": @{
                                    @"orMerchantId" : [YSSMerChanModel sharedInstence].id,
                                    },
                            @"sort": @{
                                    @"predicate" : @"create_time",
                                    @"reverse": @(false)
                                    },
                            @"startSearch" : [NSString stringWithFormat:@"%@ 00:00:00", [formatter stringFromDate:startDate]],
                            @"endSearch" : [NSString stringWithFormat:@"%@ 23:59:59", [formatter stringFromDate:endDate]],
                            
                            
                            };
    [YSSHttpTool post:GET_ORDERLIST params:param isJsonSerializer:YES success:^(id json) {
        
        NSArray *dictArr = json[@"result"][@"records"];
        for (NSDictionary *dict in dictArr) {
            YSSOrderListModel *model = [YSSOrderListModel mj_objectWithKeyValues:dict];
            [self.orderlistModelArr addObject:model];
        }
        self.placeholderLabel.hidden = self.orderlistModelArr.count > 0;
        [self.bannerView configUIWithPrice:json[@"extra"]];
        [self.tableView reloadData];
        
    } failure:^(NSError *error) {
        
    }];
}


#pragma mark - action
- (void)chooseDate
{
    YSSLog(@"选择时间");
    YSSChooseDateVC *tempVC = [[YSSChooseDateVC alloc] init];
    tempVC.block = ^(NSDate *startDate, NSDate *endDate) {
        YSSLog(@"%@  --  %@", startDate, endDate);
        [self loadDataWithStartDate:startDate endDate:endDate];
        [self.bannerView configUIWithStartDate:startDate endDate:endDate];
    };
    [self.navigationController pushViewController:tempVC animated:YES];
}


#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.orderlistModelArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSOrderListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSOrderListCell"];
    cell.model = self.orderlistModelArr[indexPath.row];
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
}

@end
