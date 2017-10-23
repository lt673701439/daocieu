//
//  YSSRegistViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSRegistViewController.h"
#import "YSSStoreRegistVC.h"
#import "YSSForgetPwdVC.h"

#import "YSSInputCodeView.h"

@interface YSSRegistViewController ()<UITextFieldDelegate>
@property (nonatomic, strong) UITextField *phoneNumTF;
@property (nonatomic, strong) UIButton *getCodeBtn;
@property (nonatomic, strong) UIView *line;
@property (nonatomic, strong) UITextField *tempTF;
@property (nonatomic, strong) YSSInputCodeView *inpuCodeView;

@property (nonatomic, strong) NSTimer *timer;
@property (nonatomic, assign) int timeout;

@property (nonatomic, strong) YSSBaseButton *closeBtn;

@end

@implementation YSSRegistViewController

- (UITextField *)tempTF
{
    if (_tempTF == nil) {
        _tempTF = [[UITextField alloc] init];
        [_tempTF addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
        _tempTF.delegate = self;
        _tempTF.keyboardType = UIKeyboardTypeNumberPad;
        [self.view addSubview:_tempTF];
    }
    return _tempTF;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyBoardchange:) name:UIKeyboardWillChangeFrameNotification object:nil];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self.navigationController setNavigationBarHidden:NO animated:YES];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [self.timer invalidate];
    self.timer = nil;
    _timeout = 59;
    self.getCodeBtn.userInteractionEnabled = YES;
    [self.getCodeBtn setTitle:@"获取验证码" forState:UIControlStateNormal];
    [self.view endEditing:YES];
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)setupUI
{
    switch (self.type) {
        case registType:
            self.title = @"注册";
            break;
        case forgetPWDType:
            self.title = @"忘记密码";
            break;
        case bindPhoneNumType:
            self.title = @"绑定手机号";
            break;
            
        default:
            break;
    }
    
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
    self.phoneNumTF = phoneNumTF;
    if (self.phoneNum) {
        phoneNumTF.text = self.phoneNum;
    }
    phoneNumTF.delegate = self;
    [phoneNumTF addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
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
    self.line = line;
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
        make.centerY.equalTo(phoneNumTF).offset(- Value(0));
    }];
    
    UIButton *getCodeBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    self.getCodeBtn = getCodeBtn;
    getCodeBtn.userInteractionEnabled = NO;
    [getCodeBtn addTarget:self action:@selector(getCodeClick) forControlEvents:UIControlEventTouchUpInside];
    [getCodeBtn setTitle:@"获取验证码" forState:UIControlStateNormal];
    [getCodeBtn setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    [self.view addSubview:getCodeBtn];
    [getCodeBtn makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(line.bottom).offset(Value(14));
    }];
    
    //测试用
    [self textFieldChanged:self.phoneNumTF];
    
}

#pragma mark - action
- (void)textFieldChanged:(UITextField *)textField
{
    if (textField == self.phoneNumTF) {
        //手机号
        self.closeBtn.hidden = textField.text.length == 0;
        
        BOOL canTouch = textField.text.length >= 11;
        [self.getCodeBtn setTitleColor:canTouch ? [UIColor orangeColor] : [UIColor lightGrayColor] forState:UIControlStateNormal];
        self.getCodeBtn.userInteractionEnabled = canTouch ? YES : NO;
        
    }else{
        [self.inpuCodeView updataNumWithString:textField.text];
        if (textField.text.length == 4) {
            YSSLog(@"验证码输入完成");
            switch (self.type) {
                case registType:
                    [self registeUser];
                    break;
                case forgetPWDType:
                    [self forgetPWD];
                    break;
                case bindPhoneNumType:
                    [self bindPhone];
                    break;
                    
                default:
                    break;
            }
            
        }
    }
}

- (void)clearPhoneNum:(UIButton *)closeBtn
{
    self.phoneNumTF.text = @"";
    closeBtn.hidden = YES;
    [self.getCodeBtn setTitleColor:[UIColor lightGrayColor] forState:0];
    self.getCodeBtn.userInteractionEnabled = NO;
}

- (void)keyBoardchange:(NSNotification *)note
{
    NSDictionary *userInfo = note.userInfo;
    CGRect endFrame = [userInfo[@"UIKeyboardFrameEndUserInfoKey"] CGRectValue];
    
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

- (void)getCodeClick
{
    [self.tempTF becomeFirstResponder];
    [UIView animateWithDuration:0.25 animations:^{
        CGAffineTransform scale = CGAffineTransformMakeScale(0.7, 0.7);
        self.phoneNumTF.transform = scale;
        self.phoneNumTF.transform = CGAffineTransformTranslate(scale, 0, - Value(30));
        
        self.closeBtn.transform = CGAffineTransformMakeTranslation(- Value(20), - Value(21));
    }];
    
    self.line.hidden = YES;
    
    //添加验证码输入View
    [self addInputCodeView];
    //开启倒计时
    self.timeout = 59;
    [self beginCount];
    
    [self getCode];
    
}

- (void)getCode
{
    NSString *type = @"0";
    switch (self.type) {
        case registType:
            type = @"0";
            break;
        case forgetPWDType:
            type = @"3";
            break;
        case bindPhoneNumType:
            type = @"2";
            break;
        
            
        default:
            break;
    }
    
    NSDictionary *param = @{
                            @"mobile" : self.phoneNumTF.text,
                            @"type" : type
                            };
    [YSSHttpTool post:GET_IDENTIFYCODE params:param isJsonSerializer:YES success:^(id json) {
        
    } dataError:^(id json) {
        
    } failure:^(NSError *error) {
        
    }];
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}

- (void)inpuCodeViewTap
{
    [self.tempTF becomeFirstResponder];
}

#pragma mark - custom

- (void)bindPhone
{
    NSDictionary *param = @{
                            @"phone" : self.phoneNumTF.text,
                            @"openId" : self.uid,
                            @"code" : self.tempTF.text,
                            @"type" : @"2"
                            
                            };
    [YSSHttpTool post:BIND_MOBILE params:param isJsonSerializer:YES success:^(id json) {
        [self.view endEditing:YES];
        [self.navigationController popViewControllerAnimated:YES];
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:json[@"msg"]];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

- (void)registeUser
{
//    YSSStoreRegistVC *tempVC = [[YSSStoreRegistVC alloc] init];
//    [self.view endEditing:YES];
//    [self.navigationController pushViewController:tempVC animated:YES];
//    return;
    
    
    NSDictionary *param = @{
                            @"phone" : self.phoneNumTF.text,
                            @"code" : self.tempTF.text,
                            @"type" : @"0",
                            };
    [YSSHttpTool post:USER_REGIST params:param isJsonSerializer:YES success:^(id json) {
        
        [self getMerchantWithUserId:json[@"result"][@"id"] ];
        
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:json[@"msg"]];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

- (void)forgetPWD
{
    NSDictionary *param = @{
                            @"phone" : self.phoneNumTF.text,
                            @"code" : self.tempTF.text,
                            @"type" : @"3",
                            };
    [YSSHttpTool post:FIND_PWD params:param isJsonSerializer:YES success:^(id json) {
        YSSForgetPwdVC *tempVC = [[YSSForgetPwdVC alloc] init];
        tempVC.phoneNum = self.phoneNumTF.text;
        [self.navigationController pushViewController:tempVC animated:YES];
        
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:json[@"msg"]];
        
        YSSForgetPwdVC *tempVC = [[YSSForgetPwdVC alloc] init];
        tempVC.phoneNum = self.phoneNumTF.text;
        [self.navigationController pushViewController:tempVC animated:YES];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
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
        [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:info] forKey:@"merchanInfoDict"];
        
        [YSSMerChanModel sharedInstence].infoDict = [[YSSCommonTool processDictionaryIsNSNull:info] mutableCopy];
        
        YSSStoreRegistVC *tempVC = [[YSSStoreRegistVC alloc] init];
        [self.view endEditing:YES];
        [self.navigationController pushViewController:tempVC animated:YES];
        
    } failure:^(NSError *error) {
        
    }];
}

/** 开启倒计时 */
- (void)beginCount
{
    [self.getCodeBtn setTitle:@"重新获取(59)" forState:UIControlStateNormal];
    self.getCodeBtn.userInteractionEnabled = NO;
    self.timer = [NSTimer scheduledTimerWithTimeInterval:1 repeats:YES block:^(NSTimer * _Nonnull timer) {
        _timeout--;
        if (_timeout == 0) {
            [self.timer invalidate];
            self.timer = nil;
            [self.getCodeBtn setTitle:@"获取验证码" forState:UIControlStateNormal];
            self.getCodeBtn.userInteractionEnabled = YES;
            return;
        }
        
        [self.getCodeBtn setTitle:[NSString stringWithFormat:@"重新获取(%d)", _timeout] forState:UIControlStateNormal];
    }];
}

/** 添加验证码输入View */
- (void)addInputCodeView
{
    if (!self.inpuCodeView) {
        YSSInputCodeView *inpuCodeView = [[YSSInputCodeView alloc] init];
        [inpuCodeView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(inpuCodeViewTap)]];
        self.inpuCodeView = inpuCodeView;
        [self.view addSubview:inpuCodeView];
        [inpuCodeView makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(self.view);
            make.width.equalTo(@(Value(Value(35) * 4 + Value(14) * 3)));
            make.bottom.equalTo(self.line);
            make.height.equalTo(@(Value(50)));
        }];
    }
}


#pragma mark - <UITextFieldDelegate>
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    if (textField == self.phoneNumTF) {
        if (textField.text.length >= 11 && range.length == 0) {
            return NO;
        }
        return YES;
    }else if(textField == self.tempTF){
        if (textField.text.length >= 4 && range.length == 0) {
            return NO;
        }
        return YES;
    }else{
        return YES;
    }
}


@end
