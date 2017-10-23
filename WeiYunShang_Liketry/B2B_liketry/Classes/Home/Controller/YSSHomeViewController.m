//
//  YSSHomeViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSHomeViewController.h"
#import "YSSMessageViewController.h"
#import "YSSBalanceViewController.h"
#import "YSSOrderViewController.h"
#import "YSSMoneyLessonViewController.h"
#import "YSSMyStoreViewController.h"
#import "YSSSettingViewController.h"
#import "YSSActivityDetailVC.h"

#import "YSSHomeListCell.h"
#import "YSSHomeHeaderView.h"
#import "YSSQRCodeView.h"

#import "YSSMsgModel.h"
#import "YSSOrderListModel.h"

@interface YSSHomeViewController ()<UITableViewDataSource, UITableViewDelegate, YSSHomeHeaderViewDelegate, YSSHomeListCellDelegate, YSSQRCodeViewDelegate>
@property (nonatomic, strong) UIImageView *kNavigationBar;
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UILabel *placeholderLabel;
@property (nonatomic, strong) YSSHomeHeaderView *headerView;
@property (nonatomic, strong) UIView *hangView;
@property (nonatomic, strong) UIView *messageDot;

@property (nonatomic, assign) BOOL isHang;

@property (nonatomic, strong) NSMutableArray *modelArr;
@property (nonatomic, strong) NSMutableArray *pageModelArr;
@property (nonatomic, strong) NSMutableArray *orderListModelArr;
@property (nonatomic, assign) NSInteger page;

@property (nonatomic, strong) NSString *extra;

@end

@implementation YSSHomeViewController

- (NSMutableArray *)modelArr
{
    if (_modelArr == nil) {
        _modelArr = [NSMutableArray array];
    }
    return _modelArr;
}

- (NSMutableArray *)pageModelArr
{
    if (_pageModelArr == nil) {
        _pageModelArr = [NSMutableArray array];
    }
    return _pageModelArr;
}

- (NSMutableArray *)orderListModelArr
{
    if (_orderListModelArr == nil) {
        _orderListModelArr = [NSMutableArray array];
    }
    return _orderListModelArr;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    _isHang = NO;
    _page = 1;
    
    NSString *headerUrl = [[NSUserDefaults standardUserDefaults] objectForKey:@"YSSHeadUrl"];
    if (headerUrl.length > 0) {
        YSSHeadUrl = [[NSUserDefaults standardUserDefaults] objectForKey:@"YSSHeadUrl"];
    }
    
    //获取商户信息
    [self getMerchanInfoDict];
    
    [self creatNavigationBar];
    
    [self setupUI];
    
    //获取今日成单
    [self getTodayOrder];
    
    //获取消息
    [self getMessage];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self.navigationController setNavigationBarHidden:YES animated:YES];
    [self adjustMsgRedDot];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [self.navigationController setNavigationBarHidden:NO animated:YES];
}

- (void)creatNavigationBar {
    _kNavigationBar = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, ScreenW, 64)];
//    _kNavigationBar.backgroundColor = [UIColor orangeColor];
    _kNavigationBar.image = [UIImage imageNamed:@"导航栏"];
    _kNavigationBar.userInteractionEnabled = YES;
    [self.view addSubview:_kNavigationBar];
    
    UIButton *logoBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [logoBtn addTarget:self action:@selector(logoBtnClick) forControlEvents:1<<6];
    [logoBtn setImage:[UIImage imageNamed:@"logo"] forState:UIControlStateNormal];
    [_kNavigationBar addSubview:logoBtn];
    [logoBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(@5);
        make.top.equalTo(@20);
        make.height.equalTo(@44);
        make.width.equalTo(@100);
    }];
    
    
    UIButton * messageBtn = [UIButton buttonWithType:0];
    [messageBtn addTarget:self action:@selector(message) forControlEvents:1<<6];
    [messageBtn setImage:[UIImage imageNamed:@"消息"] forState:0];
//    [messageBtn setImageEdgeInsets:UIEdgeInsetsMake(0, 10, 0, -10)];
//    messageBtn.backgroundColor = YSSRandomColor;
    [_kNavigationBar addSubview:messageBtn];
    [messageBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(@(- 5));
        make.top.equalTo(@20);
        make.height.equalTo(@44);
        make.width.equalTo(@44);
    }];
    
    UIView *messageDot = [[UIView alloc] init];
    self.messageDot = messageDot;
    messageDot.hidden = YES;
    [messageDot addCornerRadius:Value(2) borderColor:nil borderWidth:0];
    messageDot.backgroundColor = [UIColor redColor];
    [_kNavigationBar addSubview:messageDot];
    [messageDot makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(messageBtn.right).offset(- Value(12));
        make.top.equalTo(messageBtn).offset(Value(12));
        make.width.height.equalTo(Value(4));
    }];
    
    UIButton * settingBtn = [UIButton buttonWithType:0];
    [settingBtn addTarget:self action:@selector(setting) forControlEvents:1<<6];
    [settingBtn setImage:[UIImage imageNamed:@"设置"] forState:0];
//    [settingBtn setImageEdgeInsets:UIEdgeInsetsMake(0, 5, 0, -5)];
//    settingBtn.backgroundColor = YSSRandomColor;
    [_kNavigationBar addSubview:settingBtn];
    [settingBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(messageBtn.left).offset(@(- Value(5)));
        make.top.equalTo(@20);
        make.height.equalTo(@44);
        make.width.equalTo(@44);
    }];
}

- (void)setupUI
{
    self.automaticallyAdjustsScrollViewInsets = NO;
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    if (@available(iOS 11.0, *)) {
        tableView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentNever;
    } else {
        // Fallback on earlier versions
    }
    tableView.backgroundColor = [UIColor clearColor];
    tableView.delegate = self;
    tableView.dataSource = self;
    tableView.contentInset = UIEdgeInsetsMake(64, 0, 0, 0);
    [tableView setContentOffset:CGPointMake(0, - 64) animated:NO];
    tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view insertSubview:tableView atIndex:0];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    [tableView registerClass:[YSSHomeListCell class] forCellReuseIdentifier:@"YSSHomeListCell"];
    
    UILabel *placeholderLabel = [[UILabel alloc] init];
    self.placeholderLabel = placeholderLabel;
    placeholderLabel.hidden = YES;
    placeholderLabel.text = @"暂无新消息";
    placeholderLabel.textColor = [UIColor lightGrayColor];
    placeholderLabel.font = YSSSystemFont(Value(12));
    [tableView addSubview:placeholderLabel];
    [placeholderLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(tableView).offset(Value(350));
        make.centerX.equalTo(tableView);
    }];
    
    
    //HeaderView
    YSSHomeHeaderView *headerView = [[YSSHomeHeaderView alloc] initWithFrame:CGRectMake(0, 0, ScreenW, Value(176) + Value(90))];
    headerView.delegate = self;
    self.headerView = headerView;
    self.hangView = headerView.bottomView;
    tableView.tableHeaderView = headerView;
    
    //Data
    @weakify(self);
    [tableView addRefreshHeader:^{
        @strongify(self);
        [self loadData:YES];
    }];
    
    [tableView addRefreshFooter:^{
        @strongify(self);
        [self loadData:NO];
    }];
    
    self.tableView.mj_footer.automaticallyHidden = YES;
    
    UIView *bgView = [[UIView alloc] init];
    bgView.backgroundColor = [UIColor colorWithHexString:@"0xffbb18"];
    [self.view insertSubview:bgView atIndex:0];
    [bgView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.equalTo(@(Value(200)));
    }];
    
}

- (void)getMerchanInfoDict
{
    NSDictionary *dict = [[NSUserDefaults standardUserDefaults] objectForKey:@"merchanInfoDict"];
    YSSLog(@"商户信息 ==  %@", dict);
    YSSMerChanModel *model = [YSSMerChanModel mj_objectWithKeyValues:dict];
    model.infoDict = [dict mutableCopy];
    [YSSMerChanModel sharedInstenceWithModel:model isReset:YES];
    
//    [self.headerView configBalanceWithData:dict];
    
    YSSLog(@"id ====== %@", [YSSMerChanModel sharedInstence].id);
    
    NSDictionary *userDict = [[NSUserDefaults standardUserDefaults] objectForKey:@"userInfoDict"];
    YSSUserModel *userModel = [YSSUserModel mj_objectWithKeyValues:userDict];
    [YSSUserModel sharedInstenceWithModel:userModel isReset:YES];
    
}

/** 获取消息 */
- (void)getMessage
{
    YSSLog(@"沙盒路径 : %@", [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) lastObject]);
    JQFMDB *db = [JQFMDB shareDatabase:@"Message.sqlite" path:[NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) lastObject]];
    NSString *tableName = TableName([YSSUserModel sharedInstence].loginName);
    if (![db jq_isExistTable:tableName]) {
        [db jq_createTable:tableName dicOrModel:[YSSMsgModel class]];
    }
    
    NSDictionary *param = @{
                            @"merchantId" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].id]
                            };
    [YSSHttpTool post:GET_MSGLIST params:param isJsonSerializer:NO success:^(id json) {
        NSArray *dictArr = json[@"result"];
        
        [self.modelArr removeAllObjects];
        
        for (NSDictionary *dict in dictArr) {
            YSSMsgModel *model = [YSSMsgModel mj_objectWithKeyValues:dict];
            
            
            NSArray *arr = [db jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where id = '%@'", model.id];
            if (arr.count == 0) {
                //新消息插入数据库
                model.isSelect = 0;
                model.isRead = 0;
                model.isDelete = 0;
                
                [db jq_inDatabase:^{
                    [db jq_insertTable:tableName dicOrModel:model];
                }];
            }else{
                //旧消息更新数据
                YSSMsgModel *tempModel = arr[0];
                model.isSelect = tempModel.isSelect;
                model.isRead = tempModel.isRead;
                model.isDelete = tempModel.isDelete;
                [db jq_inDatabase:^{
                    [db jq_updateTable:tableName dicOrModel:model whereFormat:@"where id = '%@'", model.id];
                }];
            }
        }
        
        //首页红点显示判断
        [self adjustMsgRedDot];
        
        //首页显示非活动消息
        self.modelArr = [db jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where messageType != '1'"].mutableCopy;
        self.modelArr = (NSMutableArray *)[[self.modelArr reverseObjectEnumerator] allObjects];
        
        if (self.modelArr.count >= 10) {
            self.pageModelArr = [self.modelArr subarrayWithRange:NSMakeRange(0, 10)].mutableCopy;
        }else{
            self.pageModelArr = [self.modelArr subarrayWithRange:NSMakeRange(0, self.modelArr.count)].mutableCopy;
            [self.tableView.mj_footer endRefreshingWithNoMoreData];
        }
        
        self.placeholderLabel.hidden = self.modelArr.count > 0;
        [self.tableView reloadData];
        
    } dataError:^(id json) {
        
    } failure:^(NSError *error) {
        
    }];
}

- (void)getMerchantWithUserId:(NSString *)userId
{
    NSDictionary *param = @{
                            @"userId" : userId
                            };
    [YSSHttpTool post:GET_MERCHANT params:param isJsonSerializer:NO success:^(id json) {
        //审核通过1 提交2  审核不通过3  停止合作4  违规5  违法6
        
        YSSMerChanModel *model = [YSSMerChanModel mj_objectWithKeyValues:json[@"result"]];
        [YSSMerChanModel sharedInstenceWithModel:model isReset:YES];
        
        NSDictionary *info = json[@"result"];
        YSSLog(@"%@", [YSSCommonTool processDictionaryIsNSNull:info]);
        [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:info] forKey:@"merchanInfoDict"];
        
        [YSSMerChanModel sharedInstence].infoDict = [[YSSCommonTool processDictionaryIsNSNull:info] mutableCopy];
        [self.headerView configBalanceWithData:json[@"result"]];
        
    } failure:^(NSError *error) {
        
    }];
}


/** 获取今日成单 */
- (void)getTodayOrder
{
    
    [self.orderListModelArr removeAllObjects];
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
        [self.headerView configOrderNumWithData:json[@"result"]];
        
        NSArray *dictArr = json[@"result"][@"records"];
        for (NSDictionary *dict in dictArr) {
            YSSOrderListModel *model = [YSSOrderListModel mj_objectWithKeyValues:dict];
            [self.orderListModelArr addObject:model];
        }
        
        self.extra = json[@"extra"];
        
    } failure:^(NSError *error) {
        
    }];
}

- (void)loadData:(BOOL)isRefresh
{
    if (isRefresh) {
        [self.tableView.mj_header endRefreshing];
        [self.tableView.mj_footer resetNoMoreData];
        [self getMessage];
        [self getMerchantWithUserId:[YSSMerChanModel sharedInstence].merchantUserId];
        [self getTodayOrder];
    }else{
        if (_page < 5) {
            _page ++;
        }
        
        if (self.modelArr.count >= 10 * _page) {
            self.pageModelArr = [self.modelArr subarrayWithRange:NSMakeRange(0, 10 * _page)].mutableCopy;
            if (_page == 5) {
                [self.tableView.mj_footer endRefreshingWithNoMoreData];
            }
            [self.tableView reloadData];
        }else{
            self.pageModelArr = [self.modelArr subarrayWithRange:NSMakeRange(0, self.modelArr.count)].mutableCopy;
            [self.tableView.mj_footer endRefreshingWithNoMoreData];
            [self.tableView reloadData];
        }
    }
}

#pragma mark - action
/** 点击logo */
- (void)logoBtnClick
{
    [self.tableView setContentOffset:CGPointMake(0, - 64) animated:YES];
    
//    NSDictionary *param = @{};
//    [YSSHttpTool get:TEST_PUSH params:param isBase64:NO success:^(id json) {
//
//    } responseDataError:^(id json) {
//
//    } failure:^(NSError *error) {
//
//    }];
}

/** 点击消息 */
- (void)message
{
    YSSLog(@"消息");
    YSSMessageViewController *tempVC = [[YSSMessageViewController alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

/** 点击设置 */
- (void)setting
{
    YSSLog(@"设置");
    YSSSettingViewController *tempVC = [[YSSSettingViewController alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}


#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.pageModelArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSHomeListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSHomeListCell"];
    cell.canEdit = NO;
//    [cell configData:self.dataArr[indexPath.row]];
    cell.model = self.pageModelArr[indexPath.row];
    cell.delegate = self;
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
//    return [YSSHomeListCell cellHeightWithData:self.dataArr[indexPath.row]];
    return [YSSHomeListCell cellHeightWithModel:self.modelArr[indexPath.row]];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    CGFloat offsetY = scrollView.contentOffset.y;
//    YSSLog(@"+++++%lf", self.hangView.yss_y);
    CGFloat changeValue = Value(176) - 64;

    if (offsetY >= changeValue) {
        if (!_isHang) {
            YSSLog(@"开始悬浮");
            _isHang = YES;
            [self convertHangView];
        }
    }else{
        if (_isHang) {
            YSSLog(@"结束悬浮");
            _isHang = NO;
            [self convertHangView];
        }
    }
}

#pragma mark - <YSSHomeHeaderViewDelegate>
/** 点击余额 */
- (void)ySSHomeHeaderView:(YSSHomeHeaderView *)ySSHomeHeaderView didClickPrice:(NSString *)price
{
    YSSLog(@"余额");
    YSSBalanceViewController *tempVC = [[YSSBalanceViewController alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

/** 点击成单 */
- (void)ySSHomeHeaderView:(YSSHomeHeaderView *)ySSHomeHeaderView didClickOrder:(NSString *)orderNum
{
    YSSLog(@"订单");
    YSSOrderViewController *tempVC = [[YSSOrderViewController alloc] init];
    tempVC.orderlistModelArr = [NSMutableArray arrayWithArray:self.orderListModelArr];
    tempVC.extra = self.extra;
    [self.navigationController pushViewController:tempVC animated:YES];
}

/** 点击店铺 */
- (void)ySSHomeHeaderViewDidClickStore:(YSSHomeHeaderView *)ySSHomeHeaderView
{
    YSSMyStoreViewController *tempVC = [[YSSMyStoreViewController alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

/** 点击二维码 */
- (void)ySSHomeHeaderViewDidClickQRCode:(YSSHomeHeaderView *)ySSHomeHeaderView
{
    YSSQRCodeView *qrcodeView = [YSSQRCodeView show];
    qrcodeView.delegate = self;
}

/** 点击赚钱课堂 */
- (void)ySSHomeHeaderViewDidClickMoneyLesson:(YSSHomeHeaderView *)ySSHomeHeaderView
{
    YSSMoneyLessonViewController *tempVC = [[YSSMoneyLessonViewController alloc] init];
    [self.navigationController pushViewController:tempVC animated:YES];
}

#pragma mark - <YSSHomeListCellDelegate>
- (void)yssHomeListCell:(YSSHomeListCell *)yssHomeListCell didClickLink:(NSString *)linkText
{
    YSSLog(@"-----%@", linkText);
    YSSActivityDetailVC *tempVC = [[YSSActivityDetailVC alloc] init];
    tempVC.linkText = linkText;
    [self.navigationController pushViewController:tempVC animated:YES];
}

#pragma mark - <YSSQRCodeViewDelegate>
- (void)yssQRCodeView:(YSSQRCodeView *)yssQRCodeView didClickShareWithImage:(UIImage *)image
{
    [UMSocialUIManager setPreDefinePlatforms:@[
                                               @(UMSocialPlatformType_WechatSession),
                                               @(UMSocialPlatformType_WechatTimeLine),
                                               @(UMSocialPlatformType_Sina),
                                               @(UMSocialPlatformType_QQ),
                                               ]];
    [UMSocialUIManager showShareMenuViewInWindowWithPlatformSelectionBlock:^(UMSocialPlatformType platformType, NSDictionary *userInfo) {
        // 根据获取的platformType确定所选平台进行下一步操作
        [self shareImageToPlatformType:platformType image:image];
    }];
}

#pragma mark - custom
- (void)shareImageToPlatformType:(UMSocialPlatformType)platformType image:(UIImage *)image
{
    //创建分享消息对象
    UMSocialMessageObject *messageObject = [UMSocialMessageObject messageObject];
    
    //创建图片内容对象
    UMShareImageObject *shareObject = [[UMShareImageObject alloc] init];
    //如果有缩略图，则设置缩略图
    shareObject.thumbImage = [UIImage imageNamed:@"icon"];
    [shareObject setShareImage:image];
    
    //分享消息对象设置分享内容对象
    messageObject.shareObject = shareObject;
    
    //调用分享接口
    [[UMSocialManager defaultManager] shareToPlatform:platformType messageObject:messageObject currentViewController:self completion:^(id data, NSError *error) {
        if (error) {
            NSLog(@"************Share fail with error %@*********",error);
        }else{
            NSLog(@"response data is %@",data);
        }
    }];
}


- (void)convertHangView
{
    if (_isHang) {
//        [self.hangView removeFromSuperview];
//        [self.view addSubview:_hangView];
//        [self.hangView willMoveToSuperview:self.view];
        [_hangView remakeConstraints:^(MASConstraintMaker *make) {
            make.left.right.equalTo(self.view);
            make.top.equalTo(self.view).offset(64);
            make.height.equalTo(@(Value(90)));
        }];
    }else{
//        [self.hangView removeFromSuperview];
//        [self.headerView addSubview:_hangView];
//        [self.hangView willMoveToSuperview:self.headerView];
        [_hangView remakeConstraints:^(MASConstraintMaker *make) {
            make.left.bottom.right.equalTo(self.headerView);
            make.height.equalTo(@(Value(90)));
        }];
    }
}

- (void)adjustMsgRedDot
{
    
    NSString *tableName = TableName([YSSUserModel sharedInstence].loginName);
    NSArray *noReadSecviceMsg = [[JQFMDB shareDatabase] jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where messageType = '%ld' and isRead = '0'", 2];
    NSArray *noReadActivityMsg = [[JQFMDB shareDatabase] jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where messageType = '%ld' and isRead = '0'", 1];
    NSArray *noReadNoticeMsg = [[JQFMDB shareDatabase] jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where messageType = '%ld' and isRead = '0'", 0];
    if (noReadSecviceMsg.count > 0 || noReadActivityMsg.count > 0 || noReadNoticeMsg.count > 0) {
        self.messageDot.hidden = NO;
    }else{
        self.messageDot.hidden = YES;
    }
}

@end
