//
//  YSSTempViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/29.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSTempViewController.h"

@interface YSSTempViewController ()

@property (nonatomic, strong) UITextField *phoneTF;
@property (nonatomic, strong) UITextField *numTF;

@property (nonatomic, assign) NSInteger currentPhone;

@end

@implementation YSSTempViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}

- (void)setupUI
{
    self.title = @"批量生成商户";
    
    UITextField *phoneTF = [[UITextField alloc] init];
    self.phoneTF = phoneTF;
    phoneTF.borderStyle = UITextBorderStyleRoundedRect;
    phoneTF.placeholder = @"起始号码";
    [self.view addSubview:phoneTF];
    [phoneTF makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(self.view).offset(64 + Value(30));
        make.width.equalTo(self.view).offset(- Value(150));
    }];
    
    UITextField *numTF = [[UITextField alloc] init];
    self.numTF = numTF;
    numTF.borderStyle = UITextBorderStyleRoundedRect;
    numTF.placeholder = @"创建个数";
    [self.view addSubview:numTF];
    [numTF makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(phoneTF.bottom).offset(Value(30));
        make.width.equalTo(self.view).offset(- Value(150));
    }];
    
    UIButton *startBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [startBtn addTarget:self action:@selector(createUser) forControlEvents:UIControlEventTouchUpInside];
    [startBtn setTitle:@"START" forState:0];
    [startBtn setTitleColor:[UIColor blackColor] forState:0];
    [self.view addSubview:startBtn];
    [startBtn makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(numTF.bottom).offset(Value(30));
    }];
}

- (void)createUser
{
    NSInteger phoneNum = [self.phoneTF.text integerValue];
    NSInteger num = [self.numTF.text integerValue];
    
    __block NSInteger i = phoneNum;
    [NSTimer scheduledTimerWithTimeInterval:2 repeats:YES block:^(NSTimer * _Nonnull timer) {
        if (i == phoneNum + num - 1) {
            [timer invalidate];
            timer = nil;
        }
        
        self.currentPhone = i;
        
        YSSLog(@"%ld", self.currentPhone);
        [self loadData];
        
        i++;
    }];
    
}


- (void)loadData
{
    YSSLog(@"%------ld", self.currentPhone);
    
    [self registeUser];
    
}

- (void)registeUser
{
    NSDictionary *param = @{
                            @"phone" : [NSString stringWithFormat:@"%ld", self.currentPhone],
                            @"code" : @"1234",
                            @"type" : @"0",
                            };
    [YSSHttpTool post:USER_REGIST params:param isJsonSerializer:YES success:^(id json) {
        
        [self getMerchantWithUserId:json[@"result"][@"id"] ];
        
    } dataError:^(id json) {
        
        [YSSCommonTool showInfoWithStatus:json[@"msg"]];
        
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
        if ([json[@"result"] isKindOfClass:[NSNull class]]) {
            return;
        }
        
        YSSMerChanModel *model = [YSSMerChanModel mj_objectWithKeyValues:json[@"result"]];
        [YSSMerChanModel sharedInstenceWithModel:model isReset:YES];
        
        YSSLog(@"%@", [YSSMerChanModel sharedInstence].id);
        
        NSDictionary *info = json[@"result"];
        YSSLog(@"%@", [YSSCommonTool processDictionaryIsNSNull:info]);
//        [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:info] forKey:@"merchanInfoDict"];
        
        [YSSMerChanModel sharedInstence].infoDict = [[YSSCommonTool processDictionaryIsNSNull:info] mutableCopy];
        
        [self setUserPwd];
        
        
    } failure:^(NSError *error) {
        
    }];
}

- (void)setUserPwd
{
    NSDictionary *param = @{
                            @"id" : [YSSMerChanModel sharedInstence].merchantUserId,
                            @"userPwd" : @"123",
                            };
    
    [YSSHttpTool put:USER_API params:param success:^(id json) {
        
        [self uploadInfo];
        
    } failure:^(NSError *error) {
        
    }];
}

- (void)uploadInfo
{
    YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
    NSMutableDictionary *infoDict = model.infoDict;
    
    NSString *text = [NSString stringWithFormat:@"%ld", self.currentPhone];
    YSSLog(@"%@", [text substringFromIndex:text.length - 2]);
    
    infoDict[@"merchantShopname"] = [NSString stringWithFormat:@"景区宣传片%@", [text substringFromIndex:text.length - 2]];
    infoDict[@"merchantCity"] = @"24670";
    infoDict[@"merchantScenicSpot"] = @"白帝城景区";
    infoDict[@"merchantAddress"] = @"北京";
    infoDict[@"merchantTypeId"] = @"6c974f04da4041a9ba48dff9ccf41f8c";
    infoDict[@"merchantContactName"] = @"Admin";
    infoDict[@"merchantContactMobile"] = [NSString stringWithFormat:@"%ld", self.currentPhone];
    infoDict[@"merchantOperatingStart"] = @"09:00";
    infoDict[@"merchantOperatingEnd"] = @"17:00";
    infoDict[@"merchantContentId"] = @"df02dd4d90cc4414b3ba4d1db1335fd0,698c760b665f4d75a6c397c3dd27146a";
    infoDict[@"merchantIdCardUp"] = @"idUp/2017-09-22/4c4ede2092584744b42016ba52017e7c.png";
    infoDict[@"merchantIdCardDown"] = @"idDown/2017-09-22/931a92b2b7e94f2cb3f5f34c32a279aa.png";
    infoDict[@"merchantBusinessLicence"] = @"license/2017-09-22/9232122a77e247fba70ff98468ddba2b.png";
    infoDict[@"isProof"] = @"1";
    infoDict[@"dicts"] = @[];
    
    YSSLog(@"%@", infoDict);
    
    [YSSHttpTool post:SAVE_MERCHANT params:infoDict isJsonSerializer:NO success:^(id json) {
        
        YSSLog(@"========成功");
        
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:@"请检查您的信息填写无误"];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

@end
