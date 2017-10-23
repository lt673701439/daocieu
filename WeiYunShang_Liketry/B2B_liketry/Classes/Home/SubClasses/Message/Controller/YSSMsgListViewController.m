//
//  YSSMsgListViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMsgListViewController.h"
#import "YSSActivityDetailVC.h"

#import "YSSHomeListCell.h"
#import "YSSActivityListCell.h"
#import "YSSEditView.h"

#import "YSSMsgModel.h"

@interface YSSMsgListViewController ()<UITableViewDataSource, UITableViewDelegate, YSSEditViewDelegate, YSSHomeListCellDelegate, YSSActivityListCellDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSMutableArray *dataArr;
@property (nonatomic, strong) NSMutableArray *deleteArr;

@property (nonatomic, strong) NSMutableArray *modelArr;

@property (nonatomic, assign) BOOL canEdit;
@property (nonatomic, assign) BOOL editTableView;

@property (nonatomic, strong) YSSEditView *editView;

@property (nonatomic, strong) UILabel *placeholderLabel;

@end

@implementation YSSMsgListViewController

- (NSMutableArray *)modelArr
{
    if (_modelArr == nil) {
        _modelArr = [NSMutableArray array];
    }
    return _modelArr;
}

- (NSMutableArray *)deleteArr
{
    if (_deleteArr == nil) {
        _deleteArr = [NSMutableArray array];
    }
    return _deleteArr;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    _canEdit = NO;
    _editTableView = NO;
    
    [self setupUI];
    
    [self addObserver];
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)addObserver
{
    NSString *notiName = @"";
    if (self.categoryId == 0) {
        notiName = @"SERVICEMSG_EDIT";
    }else if (self.categoryId == 1)
    {
        notiName = @"ACTIVITYMSG_EDIT";
    }else{
        notiName = @"NOTICE_EDIT";
    }
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(edit) name:notiName object:nil];
}

- (void)setupUI
{
    UITableView *tableView = [[UITableView alloc] init];
    self.tableView = tableView;
    tableView.backgroundColor = [UIColor colorWithHexString:YSSBGColor];
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.separatorStyle = 0;
    tableView.contentInset = UIEdgeInsetsMake(0, 0, Value(10), 0);
    [self.view addSubview:tableView];
    [tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    [tableView registerClass:[YSSHomeListCell class] forCellReuseIdentifier:@"YSSHomeListCell"];
    [tableView registerClass:[YSSActivityListCell class] forCellReuseIdentifier:@"YSSActivityListCell"];
    [tableView registerClass:[UITableViewCell class] forCellReuseIdentifier:@"UITableViewCell"];
    
    YSSEditView *editView = [[YSSEditView alloc] init];
    editView.delegate = self;
    self.editView = editView;
    [self.view addSubview:editView];
    [editView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.height.equalTo(Value(50));
    }];
    editView.transform = CGAffineTransformMakeTranslation(0, Value(50));
    
    UILabel *placeholderLabel = [[UILabel alloc] init];
    self.placeholderLabel = placeholderLabel;
    placeholderLabel.hidden = YES;
    placeholderLabel.text = @"暂无新消息";
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
    
    NSString *tableName = TableName([YSSUserModel sharedInstence].loginName);
    self.modelArr = [[JQFMDB shareDatabase] jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where messageType = '%ld' and isDelete = '0'", 2 - self.categoryId].mutableCopy;
    self.modelArr = (NSMutableArray *)[[self.modelArr reverseObjectEnumerator] allObjects];
    
//#warning TEST
//    if (self.categoryId == 1) {
//        YSSMsgModel *model = self.modelArr[0];
//        model.messageTitle = @"活动规则已改变活动规则已改变活动规则已改变活动规则已改变活动规则已改变活动规则已改变";
//        model.messageContent = @"活动规则已改变 \n详情见：http://www.liketry.com";
//    }
    
    
    YSSLog(@"%@", self.modelArr);
    self.placeholderLabel.hidden = self.modelArr.count > 0;
    [self.tableView reloadData];
}

- (void)showEditView:(BOOL)isShow
{
    if (isShow) {
        _tableView.contentInset = UIEdgeInsetsMake(0, 0, Value(60), 0);
        [UIView animateWithDuration:0.25 animations:^{
            self.editView.transform = CGAffineTransformIdentity;
        }];
    }else{
        [UIView animateWithDuration:0.25 animations:^{
            _tableView.contentInset = UIEdgeInsetsMake(0, 0, Value(10), 0);
           self.editView.transform = CGAffineTransformMakeTranslation(0, Value(50));
        }];
    }
}

#pragma mark - action
- (void)edit
{
    if (!self.editTableView) {
        for (YSSMsgModel *model in self.modelArr) {
            model.isSelect = 0;
        }
    }
    
    _canEdit = YES;
    self.editTableView = !self.editTableView;
    [self showEditView:self.editTableView];
    [self.tableView reloadData];
    
}

#pragma mark - <UITableViewDataSource>
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.modelArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (self.categoryId == 0 || self.categoryId == 2) {
        YSSHomeListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSHomeListCell"];
        cell.canEdit = self.canEdit;
        cell.edit = self.editTableView;
        cell.model = self.modelArr[indexPath.row];
        cell.delegate = self;
        return cell;
    }else{
        YSSActivityListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"YSSActivityListCell"];
        cell.canEdit = self.canEdit;
        cell.edit = self.editTableView;
        cell.model = self.modelArr[indexPath.row];
        cell.delegate = self;
        return cell;
    }
    
}

#pragma mark - <UITableViewDelegate>
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_editTableView) {
        YSSMsgModel *model = self.modelArr[indexPath.row];
        model.isSelect = !model.isSelect;
        
        if (model.isSelect) {
            [self.deleteArr addObject:model];
        }else{
            [self.deleteArr removeObject:model];
        }
        
        [self.tableView reloadData];
        YSSLog(@"%@", self.deleteArr);
    }else{
        YSSLog(@"----->");
        if(self.categoryId == 1){
            YSSMsgModel *model = self.modelArr[indexPath.row];
            YSSActivityDetailVC *tempVC = [[YSSActivityDetailVC alloc] init];
            tempVC.messagePromotionId = model.messagePromotionId;
            [self.navigationController pushViewController:tempVC animated:YES];
        }else{
#warning TEST
            YSSActivityDetailVC *tempVC = [[YSSActivityDetailVC alloc] init];
            tempVC.messagePromotionId = @"d126e2a339214a8fbf3c267958518564";
            [self.navigationController pushViewController:tempVC animated:YES];
        }
    }
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (self.categoryId == 0 || self.categoryId == 2) {
        return [YSSHomeListCell cellHeightWithModel:self.modelArr[indexPath.row]];
    }else{
        return [YSSActivityListCell cellHeightWithModel:self.modelArr[indexPath.row]];
    }
}

#pragma mark - <YSSEditViewDelegate>
/** 全选 */
- (void)yssEditView:(YSSEditView *)yssEditView didClickFullSelectBtn:(UIButton *)fullSelectBtn
{
    for (YSSMsgModel *model in self.modelArr) {
        model.isSelect = fullSelectBtn.isSelected ? 1 : 0;
    }
    
    if (fullSelectBtn.isSelected) {
        [self.deleteArr removeAllObjects];
        [self.deleteArr addObjectsFromArray:self.modelArr];
    }else{
        [self.deleteArr removeAllObjects];
    }
    
    [self.tableView reloadData];
    YSSLog(@"%@", self.deleteArr);
}

/** 删除 */
- (void)yssEditView:(YSSEditView *)yssEditView didClickDeleteBtn:(UIButton *)deleteBtn
{
    for (YSSMsgModel *model in self.deleteArr) {
        YSSLog(@"%@", model.id);
        [[JQFMDB shareDatabase] jq_updateTable:TableName([YSSUserModel sharedInstence].loginName) dicOrModel:@{@"isDelete" : @"1"} whereFormat:@"WHERE id = '%@'", model.id];
    }
    [self.modelArr removeObjectsInArray:self.deleteArr];
    self.placeholderLabel.hidden = self.modelArr.count > 0;
    [self.tableView reloadData];
}

#pragma mark - <YSSHomeListCellDelegate>
- (void)yssHomeListCell:(YSSHomeListCell *)yssHomeListCell didClickLink:(NSString *)linkText
{
    YSSLog(@"-----%@", linkText);
    YSSActivityDetailVC *tempVC = [[YSSActivityDetailVC alloc] init];
    tempVC.linkText = linkText;
    [self.navigationController pushViewController:tempVC animated:YES];
}

#pragma mark - <YSSActivityListCellDelegate>
- (void)yssActivityListCell:(YSSActivityListCell *)yssActivityListCell didClickLink:(NSString *)linkText
{
    YSSLog(@"-----%@", linkText);
    YSSActivityDetailVC *tempVC = [[YSSActivityDetailVC alloc] init];
    tempVC.linkText = linkText;
    [self.navigationController pushViewController:tempVC animated:YES];
}

@end
