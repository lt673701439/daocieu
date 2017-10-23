//
//  YSSServiceContentVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSServiceContentVC.h"

#import "GBTagListView.h"

#import "YSSServerContentModel.h"

@interface YSSServiceContentVC ()
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UITextField *inputTF;

@property (nonatomic, strong) NSMutableArray *dataArr;
@property (nonatomic, strong) NSMutableArray *modelArr;
@property (nonatomic, strong) NSArray *selectTagsArr;
@property (nonatomic, strong) NSMutableArray *selectArr;
@end

@implementation YSSServiceContentVC

- (NSMutableArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = [NSMutableArray array];
    }
    return _dataArr;
}

- (NSMutableArray *)modelArr
{
    if (_modelArr == nil) {
        _modelArr = [NSMutableArray array];
    }
    return _modelArr;
}

- (NSArray *)selectTagsArr
{
    if (_selectTagsArr == nil) {
        _selectTagsArr = [NSArray array];
    }
    return _selectTagsArr;
}

- (NSMutableArray *)selectArr
{
    if (_selectArr == nil) {
        _selectArr = [NSMutableArray array];
    }
    return _selectArr;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self loadData];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    for (int i = 0; i < self.selectTagsArr.count; i++) {
        for (YSSServerContentModel *model in self.modelArr) {
            if ([_selectTagsArr[i] isEqualToString:model.text]) {
                [self.selectArr addObject:model];
            }
        }
    }
    
    YSSLog(@"%@", self.selectArr);
    if (self.block) {
        self.block(self.selectArr);
    }
}

- (void)setupUI
{
    self.title = @"服务内容";
    self.view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
//    UIView *mainView = [[UIView alloc] init];
//    mainView.backgroundColor = [UIColor whiteColor];
//    [self.view addSubview:mainView];
//    [mainView makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.equalTo(self.view);
//        make.top.equalTo(self.view).offset(Value(64));
//        make.height.equalTo(@(Value(50)));
//    }];
//    
//    UILabel *titleLabel = [[UILabel alloc] init];
//    self.titleLabel = titleLabel;
//    titleLabel.text = @"服务内容";
//    titleLabel.font = YSSBoldSystemFont(Value(14));
//    [mainView addSubview:titleLabel];
//    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(mainView).offset(Value(14));
//        make.centerY.equalTo(mainView);
//        make.width.greaterThanOrEqualTo(@(Value(70)));
//    }];
//    
//    UITextField *inputTF = [[UITextField alloc] init];
//    inputTF.placeholder = @"填写服务内容";
//    inputTF.tintColor = [UIColor orangeColor];
//    self.inputTF = inputTF;
//    inputTF.font = YSSSystemFont(Value(14));
//    [mainView addSubview:inputTF];
//    [inputTF makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(titleLabel.right).offset(Value(14));
//        make.centerY.equalTo(mainView).offset(Value(1));
//        make.width.equalTo(@(ScreenW - Value(28 + 70)));
//        make.height.equalTo(mainView);
//    }];
    
    
//    NSArray *tagArr = @[@"酒店", @"餐饮", @"购物", @"生活", @"小商品", @"日用百货"];
    GBTagListView *tagList = [[GBTagListView alloc] initWithFrame:CGRectMake(Value(0), 84, ScreenW, 0)];
    tagList.canTouch = YES;
    tagList.isSingleSelect = NO;
    tagList.tagHight = Value(30);
    tagList.cornerRadius = Value(15);
    tagList.tagFontSize = Value(12);
    tagList.GBbackgroundColor = [UIColor clearColor];
    tagList.signalTagColor = [UIColor whiteColor];
    tagList.selectTagColor = [UIColor whiteColor];
    tagList.nomalBordColor = [UIColor clearColor];
    tagList.selectBordColor = [UIColor redColor];
    tagList.titleTagNomalColor = [UIColor blackColor];
    tagList.titleTagHighColor = [UIColor redColor];
    [self.view addSubview:tagList];
    [tagList setTagWithTagArray:self.dataArr];
    [tagList setDidselectItemBlock:^(NSArray *arr) {
        YSSLog(@"选中的标签%@",arr);
        self.selectTagsArr = arr;
    }];
    
    NSMutableArray *tempArrM = [NSMutableArray array];
    self.selectTagsArr = tempArrM;
    for (NSDictionary *dict in [YSSMerChanModel sharedInstence].dicts) {
        [tempArrM addObject:dict[@"text"]];
    }
    
    for (UIButton *button in tagList.subviews) {
        if ([tempArrM containsObject:button.titleLabel.text]) {
            button.selected = YES;
            button.layer.borderColor = [UIColor redColor].CGColor;
            button.layer.borderWidth = 1;
        }
    }
}

- (void)loadData
{
    NSDictionary *param = @{
                            @"pagination" : @{
                                    @"number" : @10,
                                    @"numberOfPages" : @1,
                                    @"start" : @0,
                                    @"totalItemCount" : @2
                                    },
                            @"search": @{
                                    @"dictClassId" : @"4a19dabe0a8d4a17acfd2a3ee3721b8a"
                                    },
                            @"sort": @{
                                    @"predicate" : @"createTime",
                                    @"reverse": @(true)
                                    }
                            };
    [YSSHttpTool post:GET_SERVICECONTENT params:param isJsonSerializer:YES success:^(id json) {
        NSArray *dictArr = json[@"result"][@"records"];
        for (NSDictionary *dict in dictArr) {
            [self.dataArr addObject:dict[@"text"]];
            YSSServerContentModel *model = [YSSServerContentModel mj_objectWithKeyValues:dict];
            [self.modelArr addObject:model];
        }
        
        [self setupUI];
    } dataError:^(id json) {
        
    } failure:^(NSError *error) {
        
    }];
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}

@end
