//
//  YSSCommonInputVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSCommonInputVC.h"

#import "YSSTimePickerView.h"

@interface YSSCommonInputVC ()<YSSTimePickerViewDelegate>
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UITextField *inputTF;

@property (nonatomic, strong) NSString *startTime;
@property (nonatomic, strong) NSString *endTime;
@end

@implementation YSSCommonInputVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    if (self.block) {
        self.block(self.inputTF.text);
    }
    
    if (self.timeBlock) {
        self.timeBlock(self.startTime, self.endTime);
    }
}

- (void)setupUI
{
    self.title = self.navTitle;
    self.view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    UIView *mainView = [[UIView alloc] init];
    mainView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:mainView];
    [mainView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(Value(64));
        make.height.equalTo(@(Value(50)));
    }];
    
    UILabel *titleLabel = [[UILabel alloc] init];
    self.titleLabel = titleLabel;
    titleLabel.font = YSSBoldSystemFont(Value(14));
    [mainView addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(mainView).offset(Value(14));
        make.centerY.equalTo(mainView);
        make.width.greaterThanOrEqualTo(@(Value(70)));
    }];
    
    UITextField *inputTF = [[UITextField alloc] init];
    inputTF.tintColor = [UIColor orangeColor];
    self.inputTF = inputTF;
    inputTF.font = YSSSystemFont(Value(14));
    [mainView addSubview:inputTF];
    [inputTF makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(titleLabel.right).offset(Value(14));
        make.centerY.equalTo(mainView).offset(Value(1));
        make.width.equalTo(@(ScreenW - Value(28 + 70)));
        make.height.equalTo(mainView);
    }];
    
    [self configUIWithData:_index];
}

- (void)configUIWithData:(NSInteger)index
{
    if (index == 0) {
        self.titleLabel.text = @"公司地址";
        self.inputTF.placeholder = @"详细地址，例：1号楼一党员101室";
        
    }else if (index == 1)
    {
        self.titleLabel.text = @"联系人姓名";
        self.inputTF.placeholder = @"请输入真实姓名 ";
        
    }else if (index == 2)
    {
        self.titleLabel.text = @"联系人电话";
        self.inputTF.placeholder = @"请输入联系人电话";
        self.inputTF.keyboardType = UIKeyboardTypeNumberPad;
        
    }else if (index == 3)
    {
        self.titleLabel.text = @"营业时间";
        self.inputTF.placeholder = @"添加营业时间";
        
        YSSTimePickerView *pickerView = [[YSSTimePickerView alloc] initWithFrame:CGRectMake(0, ScreenH - Value(240), ScreenW, Value(240))];
        pickerView.selectDelegate = self;
        
        self.inputTF.inputView = pickerView;
        
    }else if (index == 4)
    {
        self.titleLabel.text = @"店铺名称";
        self.inputTF.placeholder = @"添加店铺名称";
    }
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}

#pragma mark - <YSSTimePickerViewDelegate>
- (void)yssTimePickerView:(YSSTimePickerView *)yssTimePickerView didSelectFirstTime:(NSString *)firstTime secondTime:(NSString *)secondTime
{
    self.inputTF.text = [NSString stringWithFormat:@"%@-%@", firstTime, secondTime];
    self.startTime = firstTime;
    self.endTime = secondTime;
}

@end
