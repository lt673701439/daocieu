//
//  YSSWithDrawViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSWithDrawViewController.h"
#import "YSSWithDrawPrograssVC.h"
#import "YSSManageCardViewController.h"

#import "YSSWithDrawView.h"
#import "YSSInputWithDrawPswView.h"

#import "YSSCardModel.h"

@interface YSSWithDrawViewController ()<YSSInputWithDrawPswViewDelegate, YSSWithDrawViewDelegate>
@property (nonatomic, strong) YSSWithDrawView *withDrawView;

@property (nonatomic, strong) NSString *allRecDisPrice;
@property (nonatomic, strong) YSSCardModel *cardModel;
@end

@implementation YSSWithDrawViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    [self loadData];
}

- (void)setupUI
{
    self.title = @"提现";
    
    YSSWithDrawView *withDrawView = [[YSSWithDrawView alloc] init];
    self.withDrawView = withDrawView;
    withDrawView.delegate = self;
    [self.view addSubview:withDrawView];
    [withDrawView makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view).offset(64 + Value(14));
        make.left.equalTo(self.view).offset(Value(14));
        make.right.equalTo(self.view).offset(- Value(14));
        make.height.equalTo(@(Value(315)));
    }];
    
    [withDrawView addShadow:[UIColor blackColor] shadowOffset:CGSizeMake(0, 0) shadowOpacity:0.1 shadowRadius:10];
    withDrawView.layer.cornerRadius = 5;
    
    UIButton *nextStepBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [nextStepBtn addTarget:self action:@selector(nextStep) forControlEvents:UIControlEventTouchUpInside];
    [nextStepBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:0];
    [nextStepBtn setTitle:@"确认提现" forState:0];
    nextStepBtn.titleLabel.font = YSSBoldSystemFont(Value(16));
    [self.view addSubview:nextStepBtn];
    [nextStepBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self.view);
        make.height.equalTo(@(Value(50)));
    }];
    
}

- (void)loadData
{
    NSDictionary *param = @{
                            @"commdityId" : [YSSMerChanModel sharedInstence].id
                            };
    [YSSHttpTool get:GET_CASHBALANCE params:param isBase64:NO success:^(id json) {
        [self.withDrawView configUIWithData:json[@"result"]];
        NSDictionary *bankCardInfo = json[@"result"][@"bankCard"];
        YSSCardModel *model = [YSSCardModel mj_objectWithKeyValues:bankCardInfo];
        self.cardModel = model;
//        self.bankCardID = [YSSCommonTool parseDictionary:bankCardInfo][@"id"];
        self.allRecDisPrice = json[@"result"][@"price"];
        
    } responseDataError:^(id json) {
        
    } failure:^(NSError *error) {
        
    }];
}


#pragma mark - action
- (void)nextStep
{
    if (self.withDrawView.inputTF.text.length == 0) {
        [YSSCommonTool showInfoWithStatus:@"请输入提现金额"];
        return;
    }
    
    if (!self.cardModel) {
        [YSSCommonTool showInfoWithStatus:@"请选择银行卡"];
        return;
    }
    
    if ([self.withDrawView.inputTF.text floatValue] > [self.allRecDisPrice floatValue]) {
        [YSSCommonTool showInfoWithStatus:@"超出可转出金额"];
        return;
    }
    
    YSSInputWithDrawPswView *view =[YSSInputWithDrawPswView showWithMoney:self.withDrawView.inputTF.text];
    view.delegate = self;
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}

#pragma mark - <YSSInputWithDrawPswViewDelegate>
/** 6位密码输入完成 */
- (void)yssInputWithDrawPswView:(YSSInputWithDrawPswView *)yssInputWithDrawPswView DidFinishInputWithText:(NSString *)text
{
    if (!self.cardModel) {
        [YSSCommonTool showInfoWithStatus:@"请选择银行卡"];
        return;
    }
    
    NSDictionary *param = @{
                            @"id" : [YSSMerChanModel sharedInstence].merchantUserId,
                            @"commodityId" : [YSSMerChanModel sharedInstence].id,
                            @"recDisPrice" : self.withDrawView.inputTF.text,
                            @"allRecDisPrice" : self.allRecDisPrice,
                            @"cashPwd" : text,
                            @"bankCardId" : self.cardModel.id
                            };
    [YSSHttpTool post:GET_APPCASH params:param isJsonSerializer:YES success:^(id json) {
        YSSWithDrawPrograssVC *tempVC = [[YSSWithDrawPrograssVC alloc] init];
        tempVC.cardModel = self.cardModel;
        tempVC.recDisPrice = self.withDrawView.inputTF.text;
        [self.navigationController pushViewController:tempVC animated:YES];
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:json[@"msg"]];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

#pragma mark - <YSSWithDrawViewDelegate>
/** 选择银行卡 */
- (void)yssWithDrawViewDidClickChooseCard:(YSSWithDrawView *)yssWithDrawView
{
    YSSManageCardViewController *tempVC = [[YSSManageCardViewController alloc] init];
    tempVC.block = ^(YSSCardModel *model) {
        self.withDrawView.cardModel = model;
//        self.bankCardID = model.id;
        self.cardModel = model;
    };
    [self.navigationController pushViewController:tempVC animated:YES];
}
@end
