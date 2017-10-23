//
//  YSSChooseLocationVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSChooseLocationVC.h"

#import <AMapFoundationKit/AMapFoundationKit.h>
#import <AMapLocationKit/AMapLocationKit.h>

#import <ChineseString.h>
#import "BMChineseSort.h"

#import "YSSChooseLocationCell.h"
#import "YSSChooseLocationHeaderView.h"

#import "YSSSpotModel.h"
#import "YSSCityModel.h"

@interface YSSChooseLocationVC ()<UITableViewDataSource, UITableViewDelegate, YSSChooseLocationHeaderViewDelegate>

@property (nonatomic, strong) NSMutableArray *indexArray; /**< 中文排序索引数组 */
@property (nonatomic, strong) NSMutableArray *letterResultArr; /**< 中文排序结果名称数组 */

@property (nonatomic, strong) NSMutableArray *modelArr;
@property (nonatomic, strong) NSMutableArray *cityNameArrM;
@property (nonatomic, strong) NSMutableArray *spotNameArr;

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) YSSChooseLocationHeaderView *headerView;

@property (nonatomic, strong) AMapLocationManager *locationManager; /**< 定位manager */
@property (nonatomic, strong) AMapLocationReGeocode *regeocode;
@end

@implementation YSSChooseLocationVC

- (NSMutableArray *)modelArr
{
    if (_modelArr == nil) {
        _modelArr = [NSMutableArray array];
    }
    return _modelArr;
}

- (NSMutableArray *)cityNameArrM
{
    if (_cityNameArrM == nil) {
        _cityNameArrM = [NSMutableArray array];
    }
    return _cityNameArrM;
}

- (NSMutableArray *)spotNameArr
{
    if (_spotNameArr == nil) {
        _spotNameArr = [NSMutableArray array];
    }
    return _spotNameArr;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    [self getLocation];
}

/** 获取定位 */
- (void)getLocation
{
    [SVProgressHUD show];
    [AMapServices sharedServices].apiKey = YSSMapAPPKey;
    
    self.locationManager = [[AMapLocationManager alloc] init];
    // 带逆地理信息的一次定位（返回坐标和地址信息）
    [self.locationManager setDesiredAccuracy:kCLLocationAccuracyHundredMeters];
    //   定位超时时间，最低2s，此处设置为2s
    self.locationManager.locationTimeout =2;
    //   逆地理请求超时时间，最低2s，此处设置为2s
    self.locationManager.reGeocodeTimeout = 2;
    
    // 带逆地理（返回坐标和地址信息）。将下面代码中的 YES 改成 NO ，则不会返回地址信息。
    [self.locationManager requestLocationWithReGeocode:YES completionBlock:^(CLLocation *location, AMapLocationReGeocode *regeocode, NSError *error) {
        
        if (error)
        {
            YSSLog(@"locError:{%ld - %@};", (long)error.code, error.localizedDescription);
            [SVProgressHUD dismiss];
            
            if (error.code == AMapLocationErrorLocateFailed)
            {
                return;
            }
        }
        
        YSSLog(@"location:%@", location);
        
        if (regeocode)
        {
            YSSLog(@"reGeocode:%@", regeocode);
            self.regeocode = regeocode;
            [self loadData:location];
            if (self.index == 0) {
                [self.headerView configUIWithData:regeocode.city ? regeocode.city : regeocode.province];
            }
        }
    }];
}

- (void)setupUI
{
    self.title = self.navTitle;
    
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.rowHeight = Value(40);
    tableView.tableFooterView = [UIView new];
    //更改索引的背景颜色
    tableView.sectionIndexBackgroundColor = [UIColor clearColor];
    //更改索引的文字颜色:
    tableView.sectionIndexColor = [UIColor lightGrayColor];
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    [tableView registerClass:[YSSChooseLocationCell class] forCellReuseIdentifier:@"YSSChooseLocationCell"];
    
    YSSChooseLocationHeaderView *headerView = [[YSSChooseLocationHeaderView alloc] initWithFrame:CGRectMake(0, 0, ScreenW, Value(100))];
    headerView.delegate = self;
    self.headerView = headerView;
    self.tableView.tableHeaderView = headerView;
    
}

- (void)loadData:(CLLocation *)location
{
    if (self.index == 0) {
        NSMutableArray *cityNameArrM = [NSMutableArray array];
        self.cityNameArrM = cityNameArrM;
        NSDictionary *param = @{
                                @"id" : @"7765323da47e4da8a970e3159836580d"
                                };
        [YSSHttpTool get:GET_CITYLIST params:param isBase64:NO success:^(id json) {
            NSArray *dictArr = json[@"result"];
            for (NSDictionary *dict in dictArr) {
                YSSCityModel *model = [YSSCityModel mj_objectWithKeyValues:dict];
                [self.modelArr addObject:model];
                [cityNameArrM addObject:dict[@"text"]];
            }
            
            self.indexArray = [BMChineseSort IndexArray:cityNameArrM];
            self.letterResultArr = [BMChineseSort LetterSortArray:cityNameArrM];
            [SVProgressHUD dismiss];
            [self.tableView reloadData];
            
        } responseDataError:^(id json) {
            [SVProgressHUD dismiss];
        } failure:^(NSError *error) {
            [YSSCommonTool showInfoWithStatus:@"网络错误"];
        }];

    }else{
        NSMutableArray *spotNameArr = [NSMutableArray array];
        self.spotNameArr = spotNameArr;
//        NSString *url = @"http://192.168.1.222:8090/lti-benison/spot_api/getSpotAll";
        NSString *url = @"";
        if ([YSSHeadUrl isEqualToString:@"https://bapi.daocieu.com"]) {
            url = @"https://api.daocieu.com/lti-benison/spot_api/getSpotAll";
        }else{
            url = @"http://211.159.151.156:8090/lti-benison/spot_api/getSpotAll";
        }
        
        NSDictionary *param = @{};
        [YSSHttpTool get:url params:param isBase64:NO success:^(id json) {
            CGFloat minDistance = CGFLOAT_MAX;
            YSSSpotModel *minDistanceModel = nil;
            
            for (NSDictionary *dict in json[@"data"]) {
                YSSSpotModel *model = [YSSSpotModel mj_objectWithKeyValues:dict];
                [spotNameArr addObject:model.spotName];
                [self.modelArr addObject:model];
                
                //最近景区
                if (model.screens.count > 0) {
                    CLLocation *templocation = [[CLLocation alloc] initWithLatitude:[model.screens[0][@"screenDimension"] doubleValue] longitude:[model.screens[0][@"screenLongitude"] doubleValue]];
                    CLLocationDistance kilometers = [location distanceFromLocation:templocation] / 1000.0;
                    YSSLog(@"距离:%.2fkm", kilometers);
                    if (kilometers < minDistance) {
                        minDistance = kilometers;
                        minDistanceModel = model;
                    }
                    
                }
            }
            
            self.indexArray = [BMChineseSort IndexArray:spotNameArr];
            self.letterResultArr = [BMChineseSort LetterSortArray:spotNameArr];
            [SVProgressHUD dismiss];
            [self.tableView reloadData];
            
            YSSLog(@"最小距离：%.2fkm, 屏幕：%@", minDistance, minDistanceModel.spotName);
            [self.headerView configUIWithData:minDistanceModel.spotName];
            
        } responseDataError:^(id json) {
            [SVProgressHUD dismiss];
        } failure:^(NSError *error) {
            [YSSCommonTool showInfoWithStatus:@"网络错误"];
        }];
    }
    
}


#pragma mark - <UITableViewDataSource>

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return self.letterResultArr.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.letterResultArr[section] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YSSChooseLocationCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSChooseLocationCell"];
    [cell configUIWithTitle:self.letterResultArr[indexPath.section][indexPath.row] charater:self.indexArray[indexPath.section] isFirst:indexPath.row == 0];
    return cell;
}

#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSString *title = self.letterResultArr[indexPath.section][indexPath.row];
    id targetModel = nil;
    if (self.index == 0) {
        for (YSSCityModel *model in self.modelArr) {
            if ([model.text isEqualToString:title]) {
                targetModel = model;
            }
        }
    }else{
        for (YSSSpotModel *model in self.modelArr) {
            if ([model.spotName isEqualToString:title]) {
                targetModel = model;
            }
        }
    }
    
    if (self.block) {
        self.block(title, targetModel);
    }
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    [self.view endEditing:YES];
}


/** 索引目录 */
-(NSArray *)sectionIndexTitlesForTableView:(UITableView *)tableView
{
    return @[@"#", @"A", @"B", @"C", @"D", @"E", @"F", @"G", @"H", @"I", @"J", @"K", @"L", @"M", @"N", @"O", @"P", @"Q", @"R", @"S", @"T", @"U", @"V", @"W", @"X", @"Y", @"Z"];
}

/** 索引点击  */
- (NSInteger)tableView:(UITableView *)tableView sectionForSectionIndexTitle:(NSString *)title atIndex:(NSInteger)index
{
    return [self.indexArray indexOfObject:title];
}

#pragma mark - <YSSChooseLocationHeaderViewDelegate>
- (void)yssChooseLocationHeaderView:(YSSChooseLocationHeaderView *)yssChooseLocationHeaderView searchDidChanged:(NSString *)searchText
{
    NSMutableArray *searchArrM = self.index == 0 ? self.cityNameArrM : self.spotNameArr;
    
    
    //还原
    if (searchText.length == 0) {
        self.indexArray = [ChineseString IndexArray:searchArrM];
        self.letterResultArr = [ChineseString LetterSortArray:searchArrM];
        [self.tableView reloadData];
        return;
    }
    
    
    //过滤
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"self CONTAINS[c] %@",searchText];
    NSMutableArray *tempArrM = [NSMutableArray arrayWithArray:[searchArrM filteredArrayUsingPredicate:predicate]];
    self.indexArray = [BMChineseSort IndexArray:tempArrM];
    self.letterResultArr = [BMChineseSort LetterSortArray:tempArrM];
    [self.tableView reloadData];
}

/** 点击地理位置 */
- (void)yssChooseLocationHeaderView:(YSSChooseLocationHeaderView *)yssChooseLocationHeaderView didClickLabel:(NSString *)text
{
    YSSLog(@"%@", text);
    if (self.index == 0) {
        YSSCityModel *cityModel = [[YSSCityModel alloc] init];
        cityModel.text = text;
        if (self.block) {
            self.block(text, cityModel);
        }
        
        
    }else{
        YSSSpotModel *spotModel = [[YSSSpotModel alloc] init];
        spotModel.spotName = text;
        if (self.block) {
            self.block(text, spotModel);
        }
    }
    
    [self.navigationController popViewControllerAnimated:YES];
}
@end
