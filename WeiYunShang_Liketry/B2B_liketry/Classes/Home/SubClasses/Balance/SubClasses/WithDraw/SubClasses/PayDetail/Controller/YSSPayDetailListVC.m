//
//  YSSPayDetailListVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSPayDetailListVC.h"
#import "YSSPayResultVC.h"

#import "YSSPayDetailListCell.h"

#import "YSSPayListModel.h"

@interface YSSPayDetailListVC ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) NSMutableArray *modelArr;
@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) UILabel *placeholderLabel;
@end

@implementation YSSPayDetailListVC

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
}

- (void)setupUI
{
    self.view.backgroundColor = YSSRandomColor;
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.rowHeight = Value(80);
    tableView.tableFooterView = [UIView new];
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    [tableView registerClass:[YSSPayDetailListCell class] forCellReuseIdentifier:@"YSSPayDetailListCell"];
    
    UILabel *placeholderLabel = [[UILabel alloc] init];
    self.placeholderLabel = placeholderLabel;
    placeholderLabel.hidden = YES;
    placeholderLabel.text = @"暂无数据";
    placeholderLabel.textColor = [UIColor lightGrayColor];
    placeholderLabel.font = YSSSystemFont(Value(12));
    [tableView addSubview:placeholderLabel];
    [placeholderLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(tableView).offset(Value(80));
        make.centerX.equalTo(tableView);
    }];
}

- (void)loadData
{
    if (self.modelArr.count > 0) {
        return;
    }
    
    NSDictionary *listParam = @{
                                @"pagination" : @{
                                        @"number" : @100000,
                                        @"numberOfPages" : @1,
                                        @"start" : @0,
                                        @"totalItemCount" : @2
                                        },
                                @"search": @{
                                        @"commdityId" : [YSSMerChanModel sharedInstence].id
                                        }.mutableCopy,
                                @"sort": @{
                                        @"predicate" : @"createTime",
                                        @"reverse": @(false)
                                        }
                                };
    if (self.categoryId == 1) {
        listParam[@"search"][@"recDisType"] = @"0";
    }else if (self.categoryId == 2)
    {
        listParam[@"search"][@"recDisType"] = @"0";
    }else if (self.categoryId == 3)
    {
        listParam[@"search"][@"recDisType"] = @"1";
    }
    
    [YSSHttpTool post:GET_EXPENSELIST params:listParam isJsonSerializer:YES success:^(id json) {
        NSArray *dictArr = json[@"result"][@"records"];
        for (NSDictionary *dict in dictArr) {
            YSSPayListModel *model = [YSSPayListModel mj_objectWithKeyValues:dict];
            
            if (self.categoryId == 1) {
                
                if (model.recDisPrice > 0) {
                    [self.modelArr addObject:model];
                }
                
            }else if (self.categoryId == 2)
            {
                if (model.recDisPrice < 0) {
                    [self.modelArr addObject:model];
                }
            }else{
                [self.modelArr addObject:model];
            }
        }
        self.placeholderLabel.hidden = self.modelArr.count > 0;
        [self.tableView reloadData];
        
    } failure:^(NSError *error) {
        
    }];
}


#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.modelArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSPayDetailListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSPayDetailListCell"];
    cell.model = self.modelArr[indexPath.row];
    return cell;
}


#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSLog(@"%ld", indexPath.row);
    YSSPayResultVC *tempVC = [[YSSPayResultVC alloc] init];
    tempVC.model = self.modelArr[indexPath.row];
    [self.navigationController pushViewController:tempVC animated:YES];
}

@end
