//
//  YSSMessageViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMessageViewController.h"
#import "YSSMsgListViewController.h"

#import "YSSMessageBannerView.h"
#import "YSSMsgTypeButton.h"

#import "YSSMsgModel.h"

@interface YSSMessageViewController ()<YSSMessageBannerViewDelegate, UIScrollViewDelegate>
@property (nonatomic, strong) UIScrollView *mainScrollView;
@property (nonatomic, strong) YSSMessageBannerView *bannerView;

@property (nonatomic, strong) UIBarButtonItem *rightItem;
@property (nonatomic, strong) UIButton *rightBtn;

@property (nonatomic, assign) BOOL firstEdit;
@property (nonatomic, assign) BOOL secondEdit;
@property (nonatomic, assign) BOOL thirdEdit;
@end

@implementation YSSMessageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    _firstEdit = NO;
    _secondEdit = NO;
    _thirdEdit = NO;
    
    [self setupNav];
    
    [self setupUI];
}

- (void)setupNav
{
    UIButton *rightBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    self.rightBtn = rightBtn;
    [rightBtn setTitle:@"编辑" forState:0];
    rightBtn.titleLabel.font = YSSSystemFont(Value(14));
    [rightBtn addTarget:self action:@selector(edit) forControlEvents:UIControlEventTouchUpInside];
    [rightBtn setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
    [rightBtn sizeToFit];
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:rightBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
}

- (void)setupUI
{
    self.title = @"消息中心";
    
    YSSMessageBannerView *bannerView = [[YSSMessageBannerView alloc] init];
    self.bannerView = bannerView;
    bannerView.delegate = self;
    [self.view addSubview:bannerView];
    [bannerView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(@(Value(44)));
    }];
    
    JQFMDB *db = [JQFMDB shareDatabase];
    NSString *tableName = TableName([YSSUserModel sharedInstence].loginName);
    NSArray *serviceArr = [db jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where messageType = '%ld' and isRead = '0'", 2];
    YSSLog(@"%@", serviceArr);
    [db jq_updateTable:tableName dicOrModel:@{@"isRead" : @"1"} whereFormat:@"WHERE messageType = '2'"];
    
    NSArray *activityArr = [db jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where messageType = '%ld' and isRead = '0'", 1];
    YSSLog(@"%@", activityArr);
    ((YSSMsgTypeButton *)bannerView.subviews[1]).redDotView.hidden = activityArr.count == 0;
    
    NSArray *noticeArr = [db jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where messageType = '%ld' and isRead = '0'", 0];
    YSSLog(@"%@", noticeArr);
    ((YSSMsgTypeButton *)bannerView.subviews[2]).redDotView.hidden = noticeArr.count == 0;
    
    
    
    
    
    UIScrollView *mainScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 64 + Value(44), ScreenW, ScreenH - (64 + Value(44)))];
    self.mainScrollView = mainScrollView;
    mainScrollView.bounces = NO;
    [self.view addSubview:mainScrollView];
    
    YSSAlphaView * alphaView= [[YSSAlphaView alloc] initWithFrame:CGRectMake(0, 64 + Value(44), ScreenW, Value(10))];
    [self.view addSubview:alphaView];
    
    
    //添加子控制器
    NSArray *tempArr = @[@"YSSMsgListViewController",
                         @"YSSMsgListViewController",
                         @"YSSMsgListViewController"
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
    
    YSSLog(@"子控制器 ： %@", self.childViewControllers);
    
    [((YSSMsgListViewController *)self.childViewControllers[0]) loadData];
    
}

#pragma mark - action
- (void)edit
{
    NSInteger index = self.mainScrollView.contentOffset.x / ScreenW;
    NSString *notiName = @"";
    BOOL edit = 0;
    if (index == 0) {
        _firstEdit = !_firstEdit;
        edit = _firstEdit;
        notiName = @"SERVICEMSG_EDIT";
    }else if (index == 1)
    {
        _secondEdit = !_secondEdit;
        edit = _secondEdit;
        notiName = @"ACTIVITYMSG_EDIT";
    }else{
        _thirdEdit = !_thirdEdit;
        edit = _thirdEdit;
        notiName = @"NOTICE_EDIT";
    }
    
    [self.rightBtn setTitle:edit ? @"完成" : @"编辑" forState:0];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:notiName object:nil userInfo:nil];
}

#pragma mark - <UIScrollViewDelegate>
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    NSInteger index = scrollView.contentOffset.x / ScreenW;
//    BOOL edit = 0;
//    if (index == 0) {
//        edit = _firstEdit;
//    }else if (index == 1)
//    {
//        edit = _secondEdit;
//    }else{
//        edit = _thirdEdit;
//    }
//    [self.rightBtn setTitle:edit ? @"完成" : @"编辑" forState:0];
    
    [self.bannerView updateUIWithIndex:index];
//    [((YSSMsgListViewController *)self.childViewControllers[index]) loadData];
}



#pragma mark - <YSSMessageBannerViewDelegate>
- (void)ySSMessageBannerView:(YSSMessageBannerView *)ySSMessageBannerView didClickTypeBtnWithIndex:(NSInteger)index
{
    [self.mainScrollView setContentOffset:CGPointMake(ScreenW * index, 0) animated:YES];
    [((YSSMsgListViewController *)self.childViewControllers[index]) loadData];
    
    NSString *tableName = TableName([YSSUserModel sharedInstence].loginName);
    if (index == 1) {
        ((YSSMsgTypeButton *)self.bannerView.subviews[1]).redDotView.hidden = YES;
        [[JQFMDB shareDatabase] jq_updateTable:tableName dicOrModel:@{@"isRead" : @"1"} whereFormat:@"WHERE messageType = '1'"];
    }else if (index == 2)
    {
        ((YSSMsgTypeButton *)self.bannerView.subviews[2]).redDotView.hidden = YES;
        [[JQFMDB shareDatabase] jq_updateTable:tableName dicOrModel:@{@"isRead" : @"1"} whereFormat:@"WHERE messageType = '0'"];
    }
    
    BOOL edit = 0;
    if (index == 0) {
        edit = _firstEdit;
    }else if (index == 1)
    {
        edit = _secondEdit;
    }else{
        edit = _thirdEdit;
    }
    [self.rightBtn setTitle:edit ? @"完成" : @"编辑" forState:0];
    
}

@end
