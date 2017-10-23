//
//  YSSPayDetailVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSPayDetailVC.h"
#import "YSSPayDetailListVC.h"

#import "YSSPayDetailHeaderView.h"

@interface YSSPayDetailVC ()<UIScrollViewDelegate, YSSPayDetailHeaderViewDelegate>
@property (nonatomic, strong) UIScrollView *mainScrollView;
@property (nonatomic, strong) YSSPayDetailHeaderView *headerView;
@end

@implementation YSSPayDetailVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    [self loadData];
}

- (void)setupUI
{
    self.title = @"收支明细";
    
    YSSPayDetailHeaderView *headerView = [[YSSPayDetailHeaderView alloc] init];
    self.headerView = headerView;
    headerView.delegate = self;
    headerView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    [self.view addSubview:headerView];
    [headerView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(@(Value(180)));
    }];
    
    
    UIScrollView *mainScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 64 + Value(180), ScreenW, ScreenH - (64 + Value(180)))];
    self.mainScrollView = mainScrollView;
    [self.view addSubview:mainScrollView];
    
    YSSAlphaView * alphaView= [[YSSAlphaView alloc] initWithFrame:CGRectMake(0, 64 + Value(180), ScreenW, Value(10))];
    [self.view addSubview:alphaView];
    
    //添加子控制器
    NSArray *tempArr = @[@"YSSPayDetailListVC",
                         @"YSSPayDetailListVC",
                         @"YSSPayDetailListVC",
                         @"YSSPayDetailListVC"
                         ];
    
    CGFloat scrollViewH = ScreenH - (64 + Value(180));
    
    for (int i = 0; i < tempArr.count; i++) {
        YSSBaseViewController *tempVC = [[NSClassFromString(tempArr[i]) alloc] init];
        tempVC.categoryId = i;
        tempVC.view.frame = CGRectMake(i * ScreenW, 0, ScreenW, scrollViewH);
        [self addChildViewController:tempVC];
        [mainScrollView addSubview:tempVC.view];
    }
    mainScrollView.delegate = self;
    mainScrollView.pagingEnabled = YES;
    mainScrollView.showsHorizontalScrollIndicator = NO;
    mainScrollView.showsVerticalScrollIndicator = NO;
    mainScrollView.contentSize = CGSizeMake(ScreenW * tempArr.count, 0);
    
    YSSLog(@"子控制器 ： %@", self.childViewControllers);
    
    [((YSSPayDetailListVC *)self.childViewControllers[0]) loadData];
}

- (void)loadData
{
    NSDictionary *param = @{
                            @"commdityId" : [YSSMerChanModel sharedInstence].id
                            };
    [YSSHttpTool get:GET_RECANDDISPRICE params:param isBase64:NO success:^(id json) {
        [self.headerView configUIWithData:json[@"result"]];
    } responseDataError:^(id json) {

    } failure:^(NSError *error) {

    }];
}


#pragma mark - <UIScrollViewDelegate>
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    NSInteger index = scrollView.contentOffset.x / ScreenW;
    [self.headerView updateUIWithIndex:index];
}

#pragma mark - <YSSPayDetailHeaderViewDelegate>
- (void)yssPayDetailHeaderView:(YSSPayDetailHeaderView *)yssPayDetailHeaderView DidClickTypeWithIndex:(NSInteger)index
{
    [self.mainScrollView setContentOffset:CGPointMake(index * ScreenW, 0) animated:YES];
    
    [((YSSPayDetailListVC *)self.childViewControllers[index]) loadData];
}


@end
