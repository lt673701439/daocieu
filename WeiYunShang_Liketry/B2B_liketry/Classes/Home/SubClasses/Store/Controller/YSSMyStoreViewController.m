//
//  YSSMyStoreViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMyStoreViewController.h"

#import "YSSStoreBannerView.h"

@interface YSSMyStoreViewController ()<UIScrollViewDelegate, YSSStoreBannerViewDelegate>
@property (nonatomic, strong) UIScrollView *mainScrollView;
@property (nonatomic, strong) YSSStoreBannerView *bannerView;
@end

@implementation YSSMyStoreViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    YSSLog(@"city ====== %@", [YSSMerChanModel sharedInstence].merchantCityName);
}

- (void)setupUI
{
    self.title = @"我的店铺";
    YSSLog(@"%@", [YSSMerChanModel sharedInstence].infoDict);
    
    YSSStoreBannerView *bannerView = [[YSSStoreBannerView alloc] init];
    self.bannerView = bannerView;
    bannerView.delegate = self;
    [self.view addSubview:bannerView];
    [bannerView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(@(Value(44)));
    }];
    
    UIScrollView *mainScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 64 + Value(44), ScreenW, ScreenH - (64 + Value(44)))];
    self.mainScrollView = mainScrollView;
    [self.view addSubview:mainScrollView];
    
    YSSAlphaView * alphaView= [[YSSAlphaView alloc] initWithFrame:CGRectMake(0, 64 + Value(44), ScreenW, Value(10))];
    [self.view addSubview:alphaView];
    
    //添加子控制器
    NSArray *tempArr = @[@"YSSMyCardViewController",
                         @"YSSMyInfoViewController",
                         @"YSSIdentifyViewController"
                         ];
    
    CGFloat scrollViewH = ScreenH - (64 + Value(44));
    
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
    mainScrollView.contentSize = CGSizeMake(ScreenW * 3, 0);
}


#pragma mark - <UIScrollViewDelegate>
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    NSInteger index = scrollView.contentOffset.x / ScreenW;
    [self.bannerView updateUIWithIndex:index];
}

#pragma mark - <YSSStoreBannerViewDelegate>
- (void)ySSStoreBannerView:(YSSStoreBannerView *)ySSStoreBannerView didClickTypeBtnWithIndex:(NSInteger)index
{
    [self.mainScrollView setContentOffset:CGPointMake(ScreenW * index, 0) animated:YES];
}


@end
