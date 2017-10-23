//
//  YSSCategoryVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSCategoryVC.h"

#import "YSSCategoryCell.h"
#import "YSSDataMoreCell.h"

#import "YSSServerContentModel.h"

@interface YSSCategoryVC ()<UICollectionViewDataSource, UICollectionViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;
@property (nonatomic, strong) UICollectionView *collectionView;
@property (nonatomic, strong) NSMutableArray *modelArr;
@end

@implementation YSSCategoryVC

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
                         @"icon" : @"餐饮",
                         @"title" : @"餐饮"
                         },
                     @{
                         @"icon" : @"旅游",
                         @"title" : @"旅游"
                         },
                     @{
                         @"icon" : @"酒店",
                         @"title" : @"酒店"
                         },
                     @{
                         @"icon" : @"娱乐",
                         @"title" : @"休闲娱乐"
                         },
                     @{
                         @"icon" : @"丽人",
                         @"title" : @"丽人"
                         },
                     @{
                         @"icon" : @"汽车",
                         @"title" : @"爱车"
                         },
                     @{
                         @"icon" : @"购物",
                         @"title" : @"购物"
                         },
                     @{
                         @"icon" : @"生活",
                         @"title" : @"生活服务"
                         },
                     @{
                         @"icon" : @"医疗",
                         @"title" : @"医疗"
                         },
                     @{
                         @"icon" : @"宠物",
                         @"title" : @"宠物"
                         },
                     @{
                         @"icon" : @"其他",
                         @"title" : @"其他"
                         },
                     @{
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
    self.title = @"分类";
    
    [self.modelArr addObject:[YSSServerContentModel new]];
    
    UICollectionViewFlowLayout *flowlayout = [[UICollectionViewFlowLayout alloc] init];
    flowlayout.minimumLineSpacing = 0;
    flowlayout.minimumInteritemSpacing = 0;
    
    UICollectionView *collectionView = [[UICollectionView alloc] initWithFrame:self.view.bounds collectionViewLayout:flowlayout];
    self.collectionView = collectionView;
    collectionView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    collectionView.dataSource = self;
    collectionView.delegate = self;
    [self.view addSubview:collectionView];
    [collectionView registerClass:[YSSCategoryCell class] forCellWithReuseIdentifier:@"YSSCategoryCell"];
    [collectionView registerClass:[YSSDataMoreCell class] forCellWithReuseIdentifier:@"YSSDataMoreCell"];
    
}

- (void)loadData
{
    NSDictionary *param = @{
                            @"pagination" : @{
                                    @"number" : @20,
                                    @"numberOfPages" : @1,
                                    @"start" : @0,
                                    @"totalItemCount" : @2
                                    },
                            @"search": @{
                                    @"dictClassId" : @"7a4fdeaecafc4aec8a3b2e567132ce9d"
                                    },
                            @"sort": @{
                                    @"predicate" : @"createTime",
                                    @"reverse": @(true)
                                    }
                            };
    [YSSHttpTool post:GET_SERVICECONTENT params:param isJsonSerializer:YES success:^(id json) {
        NSArray *dictArr = json[@"result"][@"records"];
        for (NSDictionary *dict in dictArr) {
            
            YSSServerContentModel *model = [YSSServerContentModel mj_objectWithKeyValues:dict];
            for (NSDictionary *dict in self.self.dataArr) {
                if ([dict[@"title"] isEqualToString:model.text]) {
                    model.iconName = dict[@"icon"];
                }
            }
            [self.modelArr addObject:model];
        }
        
        [self.modelArr removeObjectAtIndex:0];
        [self.modelArr addObject:[YSSServerContentModel new]];
        
        [self.collectionView reloadData];
        
    } dataError:^(id json) {
        
    } failure:^(NSError *error) {
        
    }];
}

#pragma mark - <UICollectionViewDataSource>
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return self.modelArr.count;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == self.dataArr.count - 1) {
        YSSDataMoreCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"YSSDataMoreCell" forIndexPath:indexPath];
        return cell;
    }else{
        YSSCategoryCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"YSSCategoryCell" forIndexPath:indexPath];
//        [cell configUIWithData:self.dataArr[indexPath.row]];
        cell.model = self.modelArr[indexPath.row];
        return cell;
    }
}

#pragma mark - <UICollectionViewDelegate>
- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.item == self.dataArr.count - 1) {
        return;
    }
    
    YSSServerContentModel *model = self.modelArr[indexPath.row];
    
    if (self.block) {
        self.block(model.text, model);
    }
    
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - <UICollectionViewDelegateFlowLayout>
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath
{
    return CGSizeMake(ScreenW / 4.0, ScreenW / 4.0);
}

@end
