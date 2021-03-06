//
//  YSSInputWithDrawPswView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSInputWithDrawPswView.h"

#import "YSSPswDotView.h"

@interface YSSInputWithDrawPswView ()<UITextFieldDelegate>
@property (nonatomic, strong) NSMutableArray *dotViewArr;
@end

@implementation YSSInputWithDrawPswView

- (NSMutableArray *)dotViewArr
{
    if (_dotViewArr == nil) {
        _dotViewArr = [NSMutableArray array];
    }
    return _dotViewArr;
}

+ (instancetype)showWithMoney:(NSString *)money
{
    YSSInputWithDrawPswView *view = [[YSSInputWithDrawPswView alloc] initWithFrame:MainScreenBounds money:money];
    [[UIApplication sharedApplication].keyWindow addSubview:view];
    return view;
}

- (instancetype)initWithFrame:(CGRect)frame money:(NSString *)money
{
    if (self = [super initWithFrame:frame]) {
        [self setupUIWithMoney:money];
    }
    return self;
}

- (instancetype)initWithFrame:(CGRect)frame
{
    return [self initWithFrame:MainScreenBounds money:nil];
}

- (void)setupUIWithMoney:(NSString *)money
{
    UIView *bgView = [[UIView alloc] init];
    bgView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.15];
    [self addSubview:bgView];
    [bgView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self);
    }];
    [bgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(dismiss)]];
    
    UIView *mainView = [[UIView alloc] init];
    [mainView addCornerRadius:5 borderColor:nil borderWidth:0];
    mainView.backgroundColor = [UIColor whiteColor];
    [self addSubview:mainView];
    [mainView makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self).offset(Value(160));
        make.left.equalTo(self).offset(Value(14));
        make.right.equalTo(self).offset(- Value(14));
        make.height.equalTo(@(Value(230)));
    }];
    
    [YSSCommonTool alertZoomIn:mainView andAnimationDuration:0.8];
    
    UITextField *tempTF = [[UITextField alloc] init];
    [tempTF addTarget:self action:@selector(inputChanged:) forControlEvents:UIControlEventEditingChanged];
    tempTF.delegate = self;
    tempTF.keyboardType = UIKeyboardTypeNumberPad;
    [self addSubview:tempTF];
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.25 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [tempTF becomeFirstResponder];
    });
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.text = @"请输入提现密码";
    titleLabel.textColor = [UIColor blackColor];
    titleLabel.font = YSSBoldSystemFont(Value(14));
    [mainView addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(mainView);
        make.top.equalTo(mainView).offset(Value(14));
    }];
    
    UIButton *closeBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [closeBtn addTarget:self action:@selector(dismiss) forControlEvents:UIControlEventTouchUpInside];
    [closeBtn setImage:[UIImage imageNamed:@"关闭"] forState:UIControlStateNormal];
    [mainView addSubview:closeBtn];
    [closeBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(mainView).offset(- Value(20));
        make.centerY.equalTo(titleLabel);
    }];
    
    UIView *line = [[UIView alloc] init];
    line.backgroundColor = [UIColor colorWithHexString:YSSLineDarkColor];
    [mainView addSubview:line];
    [line makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(mainView);
        make.top.equalTo(titleLabel.bottom).offset(Value(20));
        make.height.equalTo(@0.5);
    }];
    
    UILabel *desrcLabel = [[UILabel alloc] init];
    desrcLabel.text = @"转出金额";
    desrcLabel.textColor = [UIColor lightGrayColor];
    desrcLabel.font = YSSSystemFont(Value(16));
    [mainView addSubview:desrcLabel];
    [desrcLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(mainView);
        make.top.equalTo(line.bottom).offset(Value(20));
    }];
    
    UILabel *moneyLabel = [[UILabel alloc] init];
    moneyLabel.text = [NSString stringWithFormat:@"￥%@", money];
    moneyLabel.textColor = [UIColor blackColor];
    moneyLabel.font = YSSBoldSystemFont(Value(26));
    [mainView addSubview:moneyLabel];
    [moneyLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(mainView);
        make.top.equalTo(desrcLabel.bottom).offset(Value(20));
    }];
    
    YSSPswDotView *preView = nil;
    for (int i = 0; i < 6; i++) {
        YSSPswDotView *view = [[YSSPswDotView alloc] init];
        [mainView addSubview:view];
        [view makeConstraints:^(MASConstraintMaker *make) {
            make.width.height.equalTo(@(Value(50)));
            make.bottom.equalTo(mainView.bottom).offset(- Value(14));
            if (preView) {
                make.left.equalTo(preView.right).offset(Value(5));
            }else{
                make.left.equalTo(mainView).offset(Value(10));
            }
        }];
        
        preView = view;
        [self.dotViewArr addObject:view];
    }
    
}

#pragma mark - action
- (void)inputChanged:(UITextField *)textField
{
    BOOL hasOne = textField.text.length >= 1;
    BOOL hasTwo = textField.text.length >= 2;
    BOOL hasThree = textField.text.length >= 3;
    BOOL hasFour = textField.text.length >= 4;
    BOOL hasFive = textField.text.length >= 5;
    BOOL hasSix = textField.text.length >= 6;
    
    ((YSSPswDotView *)self.dotViewArr[0]).circleView.hidden = !hasOne;
    ((YSSPswDotView *)self.dotViewArr[1]).circleView.hidden = !hasTwo;
    ((YSSPswDotView *)self.dotViewArr[2]).circleView.hidden = !hasThree;
    ((YSSPswDotView *)self.dotViewArr[3]).circleView.hidden = !hasFour;
    ((YSSPswDotView *)self.dotViewArr[4]).circleView.hidden = !hasFive;
    ((YSSPswDotView *)self.dotViewArr[5]).circleView.hidden = !hasSix;
    
    if (hasSix) {
        if ([self.delegate respondsToSelector:@selector(yssInputWithDrawPswView:DidFinishInputWithText:)]) {
            [self.delegate yssInputWithDrawPswView:self DidFinishInputWithText:textField.text];
        }
        
        [self dismiss];
    }
}

- (void)dismiss
{
    [self removeFromSuperview];
}

#pragma mark - <UITextFieldDelegate>
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    if (textField.text.length >= 6 && range.length == 0) {
        return NO;
    }
    return YES;
}


@end
