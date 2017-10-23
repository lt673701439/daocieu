//
//  YSSLoginViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/4.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSLoginViewController.h"
#import "YSSRegistViewController.h"
#import "YSSNavigationController.h"
#import "YSSHomeViewController.h"
#import "YSSStoreRegistVC.h"
#import "YSSCreatStoreVC.h"
#import "YSSSubmitSuccessVC.h"

#import "YSSMerChanModel.h"

@interface YSSLoginViewController ()<UITextFieldDelegate>
@property (nonatomic, strong) UITextField *phoneNumTF;
@property (nonatomic, strong) UITextField *verifyTF;

@property (nonatomic, strong) UIButton *loginBtn;
@property (nonatomic, strong) YSSBaseButton *closeBtn;
@property (nonatomic, strong) YSSBaseButton *eyeBtn;

@property (nonatomic, assign) CGFloat loginBtnMaxY;
@property (nonatomic, strong) NSArray *httpUrlArr;
@property (nonatomic, strong) NSArray *infoUrlArr;
@end

@implementation YSSLoginViewController

- (NSArray *)httpUrlArr
{
    if (_httpUrlArr == nil) {
        _httpUrlArr = @[YSSTencentUrl, YSSAliUrl, YSSHaiXiaoUrl, YSSYangYangUrl];
    }
    return _httpUrlArr;
}

- (NSArray *)infoUrlArr
{
    if (_infoUrlArr == nil) {
        _infoUrlArr = @[@"切换至腾讯云服务器(测试)", @"切换至阿里云服务器(线上)", @"连接至海晓机器", @"连接至洋洋机器"];
    }
    return _infoUrlArr;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    NSString *headerUrl = [[NSUserDefaults standardUserDefaults] objectForKey:@"YSSHeadUrl"];
    if (headerUrl.length > 0) {
        YSSHeadUrl = [[NSUserDefaults standardUserDefaults] objectForKey:@"YSSHeadUrl"];
    }
    
    [self setupUI];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyBoardchange:) name:UIKeyboardWillChangeFrameNotification object:nil];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self.navigationController setNavigationBarHidden:YES animated:YES];
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)setupUI
{
    self.view.backgroundColor = [UIColor whiteColor];
    
    UIImageView *logoView = [[UIImageView alloc] init];
    logoView.image = [UIImage imageNamed:@"Login_logo"];
    logoView.backgroundColor = YSSRandomColor;
    [self.view addSubview:logoView];
    [logoView makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(self.view).offset(@(Value(100)));
        make.width.height.equalTo(@(Value(140)));
    }];
    
    UITextField *phoneNumTF = [[UITextField alloc] init];
    if ([YSSUserModel sharedInstence].loginName && [YSSUserModel sharedInstence].loginName.length > 0) {
        phoneNumTF.text = [YSSUserModel sharedInstence].loginName;
    }
    self.phoneNumTF = phoneNumTF;
    [phoneNumTF addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
    phoneNumTF.delegate = self;
    phoneNumTF.tintColor = [UIColor orangeColor];
    phoneNumTF.keyboardType = UIKeyboardTypeNumberPad;
    phoneNumTF.placeholder = @"请输入手机号";
    phoneNumTF.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:phoneNumTF];
    [phoneNumTF makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(logoView.bottom).offset(@(Value(20)));
        make.centerX.equalTo(self.view);
        make.left.equalTo(self.view).offset(Value(90));
        make.right.equalTo(self.view).offset(- Value(90));
        make.height.equalTo(@(Value(44)));
    }];
    
    
    UIView *line = [[UIView alloc] init];
    line.backgroundColor = [UIColor blackColor];
    [self.view addSubview:line];
    [line makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(Value(60));
        make.right.equalTo(self.view).offset(- Value(60));
        make.top.equalTo(phoneNumTF.bottom).offset(Value(14));
        make.height.equalTo(@(Value(2)));
    }];
    
    YSSBaseButton *closeBtn = [[YSSBaseButton alloc] init];
    self.closeBtn = closeBtn;
    closeBtn.hidden = YES;
    [closeBtn addTarget:self action:@selector(clearPhoneNum:) forControlEvents:UIControlEventTouchUpInside];
    [closeBtn setImage:[UIImage imageNamed:@"关闭"] forState:0];
    [self.view addSubview:closeBtn];
    [closeBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(line).offset(- Value(14));
        make.centerY.equalTo(phoneNumTF);
    }];
    
    UITextField *verifyTF = [[UITextField alloc] init];
    self.verifyTF = verifyTF;
    verifyTF.secureTextEntry = YES;
    [verifyTF addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
    verifyTF.delegate = self;
    verifyTF.tintColor = [UIColor orangeColor];
    verifyTF.placeholder = @"请输入密码";
    verifyTF.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:verifyTF];
    [verifyTF makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(line.bottom).offset(@(Value(14)));
        make.centerX.equalTo(self.view);
        make.left.equalTo(self.view).offset(Value(90));
        make.right.equalTo(self.view).offset(- Value(90));
        make.height.equalTo(@(Value(44)));
    }];
    
    YSSBaseButton *eyeBtn = [[YSSBaseButton alloc] init];
    self.eyeBtn = eyeBtn;
    [eyeBtn addTarget:self action:@selector(showPwd:) forControlEvents:UIControlEventTouchUpInside];
    [eyeBtn setImage:[UIImage imageNamed:@"显示密码"] forState:0];
    [eyeBtn setImage:[UIImage imageNamed:@"眼睛"] forState:UIControlStateSelected];
    [self.view addSubview:eyeBtn];
    [eyeBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(line).offset(- Value(14));
        make.centerY.equalTo(verifyTF);
    }];
    
    UIButton *weChatBtn = [[UIButton alloc] init];
    [weChatBtn addTarget:self action:@selector(weChatLogin) forControlEvents:UIControlEventTouchUpInside];
    [weChatBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:UIControlStateNormal];
    [weChatBtn setImage:[UIImage imageNamed:@"登录微信"] forState:UIControlStateNormal];
    weChatBtn.adjustsImageWhenHighlighted = NO;
    [self.view addSubview:weChatBtn];
    [weChatBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self.view);
        make.height.equalTo(@(Value(50)));
    }];
    
    UIButton *registBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [registBtn setTitle:@"快速注册" forState:UIControlStateNormal];
    [registBtn addTarget:self action:@selector(regist) forControlEvents:UIControlEventTouchUpInside];
    [registBtn setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
    registBtn.titleLabel.font = YSSBoldSystemFont(Value(13));
    [self.view addSubview:registBtn];
    [registBtn makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weChatBtn.top).offset(- Value(50));
        make.right.equalTo(self.view.centerX).offset(- Value(30));
    }];
    
    UIButton *forgetPswBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [forgetPswBtn setTitle:@"忘记密码" forState:UIControlStateNormal];
    [forgetPswBtn addTarget:self action:@selector(forgetPwd) forControlEvents:UIControlEventTouchUpInside];
    [forgetPswBtn setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    forgetPswBtn.titleLabel.font = YSSBoldSystemFont(Value(13));
    [self.view addSubview:forgetPswBtn];
    [forgetPswBtn makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weChatBtn.top).offset(- Value(50));
        make.left.equalTo(self.view.centerX).offset(Value(30));
    }];
    
#ifdef kDebugVesion
    [self addChangeBaseURLGesture];
#endif
    
}

#pragma mark - 环境切换
- (void)addChangeBaseURLGesture
{
    UITapGestureRecognizer * tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapChangeURLHandle:)];
    tap.numberOfTapsRequired = 2;
    [self.view addGestureRecognizer:tap];
}

- (void)tapChangeURLHandle:(UITapGestureRecognizer *)gesture{
    if (gesture.state == UIGestureRecognizerStateRecognized) {
        
        NSInteger index = [self.httpUrlArr indexOfObject:YSSHeadUrl];
        if (index == self.httpUrlArr.count - 1) {
            index = 0;
        }else{
            index ++;
        }
        
        YSSHeadUrl = [self.httpUrlArr objectAtIndex:index];
        [[NSUserDefaults standardUserDefaults] setObject:YSSHeadUrl forKey:@"YSSHeadUrl"];
        [[NSUserDefaults standardUserDefaults] synchronize];
        YSSLog(@"当前环境 ： %@", YSSHeadUrl);
        [SVProgressHUD showInfoWithStatus:self.infoUrlArr[index]];
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.5 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [SVProgressHUD dismiss];
        });
    }
}

#pragma mark - custom
- (void)addLoginBtn
{
    UIButton *loginBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    self.loginBtn = loginBtn;
    [loginBtn addTarget:self action:@selector(login) forControlEvents:UIControlEventTouchUpInside];
    [loginBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:UIControlStateNormal];
    [loginBtn setTitle:@"登录" forState:UIControlStateNormal];
    [self.view addSubview:loginBtn];
    [loginBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.bottom.equalTo(self.view.top).offset(_loginBtnMaxY + Value(30));
        make.height.equalTo(@(Value(50)));
    }];
    
    loginBtn.transform = CGAffineTransformMakeTranslation(ScreenW, 0);
}

- (void)showLoginBtn:(BOOL)isShow
{
    if (isShow) {
        [UIView animateWithDuration:0.25 animations:^{
            self.loginBtn.transform = CGAffineTransformIdentity;
        }];
        
    }else{
        [UIView animateWithDuration:0.25 animations:^{
            self.loginBtn.transform = CGAffineTransformMakeTranslation(ScreenW, 0);
        }];
    }
}

- (void)getMerchantWithUserId:(NSString *)userId
{
    NSDictionary *param = @{
                            @"userId" : userId
                            };
    [YSSHttpTool post:GET_MERCHANT params:param isJsonSerializer:NO success:^(id json) {
        YSSMerChanModel *model = [YSSMerChanModel mj_objectWithKeyValues:json[@"result"]];
        [YSSMerChanModel sharedInstenceWithModel:model isReset:YES];
        
        NSDictionary *info = json[@"result"];
        YSSLog(@"%@", [YSSCommonTool processDictionaryIsNSNull:info]);
        [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:info] forKey:@"merchanInfoDict"];

        [YSSMerChanModel sharedInstence].infoDict = [[YSSCommonTool processDictionaryIsNSNull:info] mutableCopy];
        
        
        [UMessage setAlias:model.id type:@"COMMON" response:^(id responseObject, NSError *error) {
            YSSLog(@"绑定别名 %@  --  错误:%@", responseObject, error);
        }];
        
        //商户状态:草稿态0 审核通过1 提交2 审核不通过3 停止合作4  违规5  违法6
        NSInteger status = [YSSCommonTool parseInt:json[@"result"][@"merchantCensorStatus"]];
        [self.view endEditing:YES];
        [self showLoginBtn:NO];
        if (status == 1) {
            
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.5 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                [SVProgressHUD dismiss];
                [[NSUserDefaults standardUserDefaults] setObject:@1 forKey:@"isLogin"];
                YSSNavigationController *navVC = [[YSSNavigationController alloc] initWithRootViewController:[[YSSHomeViewController alloc] init]];
                [UIApplication sharedApplication].keyWindow.rootViewController = navVC;
            });
            
        }else if(status == 0 || status == 3){
            if (status == 3) {
                [YSSCommonTool showInfoWithStatus:@"审核不通过，请重新填写资料"];
            }
            [SVProgressHUD dismiss];
            YSSCreatStoreVC *tempVC = [[YSSCreatStoreVC alloc] init];
            [self showLoginBtn:NO];
            [self.view endEditing:YES];
            [self.navigationController pushViewController:tempVC animated:YES];
            
        }else if (status == 2)
        {
            [SVProgressHUD dismiss];
            YSSSubmitSuccessVC *tempVC = [[YSSSubmitSuccessVC alloc] init];
            [self.navigationController pushViewController:tempVC animated:YES];
        }else{
            [YSSCommonTool showInfoWithStatus:@"商户状态异常，无法登陆"];
        }
        
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

#pragma mark - 第三方平台数据
- (void)getUserInfoForPlatform:(UMSocialPlatformType)platformType
{
    [[UMSocialManager defaultManager] getUserInfoWithPlatform:platformType currentViewController:nil completion:^(id result, NSError *error) {
        if (error) {
            YSSLog(@"%@", error);
        } else {
            UMSocialUserInfoResponse *resp = result;
            
            // 授权信息
            YSSLog(@"Wechat uid: %@", resp.uid);
            YSSLog(@"Wechat openid: %@", resp.openid);
            YSSLog(@"Wechat unionid: %@", resp.unionId);
            YSSLog(@"Wechat accessToken: %@", resp.accessToken);
            YSSLog(@"Wechat refreshToken: %@", resp.refreshToken);
            YSSLog(@"Wechat expiration: %@", resp.expiration);
            
            // 用户信息
            YSSLog(@"Wechat name: %@", resp.name);
            YSSLog(@"Wechat iconurl: %@", resp.iconurl);
            YSSLog(@"Wechat gender: %@", resp.unionGender);
            
            // 第三方平台SDK源数据
            YSSLog(@"Wechat originalResponse: %@", resp.originalResponse);
            
            NSDictionary *param = @{
                                    @"openId" : resp.uid
                                    };
            [YSSHttpTool post:USER_WXLOGIN params:param isJsonSerializer:YES success:^(id json) {
                
                //已绑定 获得用户信息
                YSSUserModel *model = [YSSUserModel mj_objectWithKeyValues:json[@"result"]];
                [YSSUserModel sharedInstenceWithModel:model isReset:YES];
                
                NSDictionary *info = json[@"result"];
                YSSLog(@"%@", [YSSCommonTool processDictionaryIsNSNull:info]);
                [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:info] forKey:@"userInfoDict"];
                
                [self getMerchantWithUserId:[YSSCommonTool parseString:json[@"result"][@"id"]]];
                
            } dataError:^(id json) {
                
                //未绑定 跳转至绑定手机号
                [SVProgressHUD showInfoWithStatus:@"即将跳转至手机号绑定..."];
                dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.5 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                    [SVProgressHUD dismiss];
                    YSSRegistViewController *tempVC = [[YSSRegistViewController alloc] init];
                    tempVC.type = bindPhoneNumType;
                    tempVC.uid = resp.uid;
                    [self.navigationController pushViewController:tempVC animated:YES];
                });
                
            } failure:^(NSError *error) {
                [YSSCommonTool showInfoWithStatus:@"网络错误"];
            }];
            
            
        }
    }];
}

#pragma mark - action
- (void)login
{
    NSDictionary *param = @{
                            @"loginName" : self.phoneNumTF.text,
                            @"userPwd" : self.verifyTF.text,
                            @"loginSource" : @"1"
                            };
    
    [SVProgressHUD show];
    [YSSHttpTool post:USER_LOGIN params:param isJsonSerializer:YES success:^(id json) {
        
        YSSUserModel *model = [YSSUserModel mj_objectWithKeyValues:json[@"result"]];
        [YSSUserModel sharedInstenceWithModel:model isReset:YES];
        
        NSDictionary *info = json[@"result"];
        YSSLog(@"%@", [YSSCommonTool processDictionaryIsNSNull:info]);
        [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:info] forKey:@"userInfoDict"];
        
        [self getMerchantWithUserId:[YSSCommonTool parseString:json[@"result"][@"id"]]];
        
    } dataError:^(id json) {
        [SVProgressHUD dismiss];
        if ([json[@"code"] integerValue] == 10030) {
            [self.loginBtn setTitle:@"用户名或密码错误" forState:0];
            [self.loginBtn setBackgroundImage:[UIImage new] forState:0];
            self.loginBtn.backgroundColor = [UIColor colorWithHexString:@"d65b26"];
        }
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

- (void)regist
{
    YSSRegistViewController *tempVC = [[YSSRegistViewController alloc] init];
    tempVC.type = registType;
    if (self.phoneNumTF.text.length == 11) {
        tempVC.phoneNum = self.phoneNumTF.text;
    }
    [self.navigationController pushViewController:tempVC animated:YES];
}

- (void)forgetPwd
{
    YSSRegistViewController *tempVC = [[YSSRegistViewController alloc] init];
    tempVC.type = forgetPWDType;
    if (self.phoneNumTF.text.length == 11) {
        tempVC.phoneNum = self.phoneNumTF.text;
    }
    [self.navigationController pushViewController:tempVC animated:YES];
}

- (void)weChatLogin
{
    [self getUserInfoForPlatform:UMSocialPlatformType_WechatSession];
}

- (void)clearPhoneNum:(UIButton *)closeBtn
{
    self.phoneNumTF.text = @"";
    closeBtn.hidden = YES;
}

- (void)showPwd:(UIButton *)pwdBtn
{
    pwdBtn.selected = !pwdBtn.isSelected;
    self.verifyTF.secureTextEntry = !pwdBtn.selected;
}

- (void)keyBoardchange:(NSNotification *)note
{
    NSDictionary *userInfo = note.userInfo;
    YSSLog(@"键盘通知 %@", userInfo);
    CGRect endFrame = [userInfo[@"UIKeyboardFrameEndUserInfoKey"] CGRectValue];
    self.loginBtnMaxY = endFrame.origin.y;
    
    [self.loginBtn remakeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.bottom.equalTo(self.view.top).offset(_loginBtnMaxY + Value(30));
        make.height.equalTo(@(Value(50)));
    }];
    
    [UIView animateWithDuration:0.25 animations:^{
        [self.view layoutIfNeeded];
    }];
    
    if (endFrame.origin.y != ScreenH) {
        [UIView animateWithDuration:0.25 animations:^{
            self.view.transform = CGAffineTransformMakeTranslation(0, - Value(30));
        }];
    }else{
        [UIView animateWithDuration:0.25 animations:^{
            self.view.transform = CGAffineTransformIdentity;
        }];
    }
}

- (void)textFieldChanged:(UITextField *)textField
{
    if (textField == self.phoneNumTF) {
        //手机号
        self.closeBtn.hidden = textField.text.length == 0;
    }else{
        //密码
        if (!self.loginBtn) {
            [self addLoginBtn];
        }
        BOOL isShow = textField.text.length > 0;
        [self showLoginBtn:isShow];
        
        [_loginBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:UIControlStateNormal];
        [_loginBtn setTitle:@"登录" forState:UIControlStateNormal];
    }
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
    [self showLoginBtn:NO];
}

#pragma mark - <UITextFieldDelegate>
- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    if (textField == self.phoneNumTF) {
        
    }else{
        BOOL isShow = textField.text.length > 0;
        [self showLoginBtn:isShow];
    }
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    if (textField == self.phoneNumTF) {
        if (textField.text.length >= 11 && range.length == 0) {
            return NO;
        }
        return YES;
    }else{
        return YES;
    }
}


@end
