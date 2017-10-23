//
//  YSSAddTagView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/20.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSAddTagView.h"

#import "GBTagListView.h"

#import "YSSServerContentModel.h"

@interface YSSAddTagView ()
@property (nonatomic, strong) NSMutableArray *dataArr;
@property (nonatomic, strong) NSMutableArray *modelArr;
@property (nonatomic, strong) NSArray *selectTagsArr;
@property (nonatomic, strong) NSMutableArray *selectArr;

@property (nonatomic, strong) GBTagListView *tagList;

@end

@implementation YSSAddTagView

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

+ (instancetype)show
{
    YSSAddTagView *view = [[YSSAddTagView alloc] initWithFrame:MainScreenBounds];
    [[UIApplication sharedApplication].keyWindow addSubview:view];
    return view;
}

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        
        [self loadData];
    }
    return self;
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

- (void)setupUI
{
    UIView *bgView = [[UIView alloc] init];
    [bgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(dismiss)]];
    bgView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.15];
    [self addSubview:bgView];
    [bgView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self);
    }];
    
    UIView *mainview = [[UIView alloc] init];
    mainview.backgroundColor = [UIColor whiteColor];
    [mainview addCornerRadius:5 borderColor:nil borderWidth:0];
    [self addSubview:mainview];
    [mainview makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.right.equalTo(self).offset(- Value(14));
//        make.top.equalTo(self).offset(Value(128));
        make.centerY.equalTo(self);
        make.height.equalTo(Value(200));
    }];
    
    [YSSCommonTool alertZoomIn:mainview andAnimationDuration:0.8];
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.text = @"添加标签";
    titleLabel.font = YSSBoldSystemFont(Value(16));
    titleLabel.textColor = [UIColor blackColor];
    [mainview addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(mainview).offset(Value(20));
        make.centerX.equalTo(mainview);
    }];
    
    UIButton *addBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [addBtn setTitle:@"添加" forState:0];
    [addBtn addTarget:self action:@selector(addTags) forControlEvents:UIControlEventTouchUpInside];
    [addBtn setTitleColor:[UIColor orangeColor] forState:0];
    addBtn.titleLabel.font = YSSSystemFont(Value(14));
    [mainview addSubview:addBtn];
    [addBtn makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(titleLabel);
        make.right.equalTo(mainview).offset(- Value(14));
    }];
    
    UIView *line = [[UIView alloc] init];
    line.backgroundColor = [UIColor colorWithHexString:YSSLineDarkColor];
    [mainview addSubview:line];
    [line makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(titleLabel.bottom).offset(Value(20));
        make.left.right.equalTo(mainview);
        make.height.equalTo(@0.5);
    }];
    
    GBTagListView *tagList = [[GBTagListView alloc] initWithFrame:CGRectMake(Value(0), 84, ScreenW - Value(28), 0)];
    self.tagList = tagList;
    tagList.canTouch = YES;
    tagList.isSingleSelect = NO;
    tagList.tagHight = Value(30);
    tagList.cornerRadius = Value(15);
    tagList.tagFontSize = Value(12);
    tagList.GBbackgroundColor = [UIColor clearColor];
    tagList.signalTagColor = [UIColor colorWithHexString:YSSYellowBGColor];
    tagList.selectTagColor = [UIColor colorWithHexString:YSSYellowBGColor];
    tagList.nomalBordColor = [UIColor clearColor];
    tagList.selectBordColor = [UIColor redColor];
    tagList.titleTagNomalColor = [UIColor blackColor];
    tagList.titleTagHighColor = [UIColor redColor];
    [mainview addSubview:tagList];
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

#pragma mark - action
- (void)dismiss
{
    [self removeFromSuperview];
}

- (void)addTags
{
    for (int i = 0; i < self.selectTagsArr.count; i++) {
        for (YSSServerContentModel *model in self.modelArr) {
            if ([_selectTagsArr[i] isEqualToString:model.text]) {
                [self.selectArr addObject:model];
            }
        }
    }
    YSSLog(@"选中的tag = %@", self.selectArr);
    if (!self.selectArr || self.selectArr.count == 0) {
        return ;
    }
    NSMutableString *title = [NSMutableString string];
    NSMutableString *serviceId = [NSMutableString string];
    for (YSSServerContentModel *model in self.selectArr) {
        [title appendFormat:@"%@ ", model.text];
        [serviceId appendFormat:@"%@,", model.id];
    }
    
    title = [title substringToIndex:title.length - 1].mutableCopy;
    serviceId = [serviceId substringToIndex:serviceId.length - 1].mutableCopy;
    
    [YSSMerChanModel sharedInstence].merchantContentId = serviceId;
    
    
    YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
    
    NSDictionary *param = @{
                            @"merchantId" : [YSSCommonTool parseString:model.id],
                            @"type" : @"3",
                            @"value" : [YSSCommonTool parseString:model.merchantContentId],
                            };
    
    [YSSHttpTool post:CHANGE_MERCHANT params:param isJsonSerializer:NO success:^(id json) {
        [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:json[@"result"]] forKey:@"merchanInfoDict"];
        
        [YSSMerChanModel sharedInstence].dicts = json[@"result"][@"dicts"];
        
        if (self.block) {
            self.block([YSSMerChanModel sharedInstence].dicts);
        }
        
    } failure:^(NSError *error) {
        
    }];
    
    [self dismiss];
    
    
}

@end
