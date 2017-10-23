//
//  YSSTimePickerView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSTimePickerView.h"

@interface YSSTimePickerView ()<UIPickerViewDataSource, UIPickerViewDelegate>
@property (nonatomic, strong) NSArray *dataArr;

@property (nonatomic, assign) NSInteger firstIndex;
@property (nonatomic, assign) NSInteger secondIndex;
@end

@implementation YSSTimePickerView

- (NSArray *)dataArr
{
    if (_dataArr == nil) {
        _dataArr = @[
                     @[@"00:00", @"01:00", @"02:00", @"03:00", @"04:00", @"05:00", @"06:00", @"07:00", @"08:00", @"09:00", @"10:00", @"11:00", @"12:00", @"13:00", @"14:00", @"15:00", @"16:00", @"17:00", @"18:00", @"19:00", @"20:00", @"21:00", @"22:00", @"23:00"],
                     @[@"00:00", @"01:00", @"02:00", @"03:00", @"04:00", @"05:00", @"06:00", @"07:00", @"08:00", @"09:00", @"10:00", @"11:00", @"12:00", @"13:00", @"14:00", @"15:00", @"16:00", @"17:00", @"18:00", @"19:00", @"20:00", @"21:00", @"22:00", @"23:00"],
                     
                     ];
    }
    return _dataArr;
}

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    _firstIndex = 0;
    _secondIndex = 0;
    
    self.backgroundColor = [UIColor whiteColor];
    self.dataSource = self;
    self.delegate = self;
}


#pragma mark - <UIPickerViewDataSource>
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 2;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    return 24;
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{
    //设置分割线的颜色
    for(UIView *singleLine in pickerView.subviews)
    {
        if (singleLine.frame.size.height < 1)
        {
            singleLine.backgroundColor = [UIColor colorWithHexString:YSSLineDarkColor];
        }
    }
    
    UILabel *titleLabel = (UILabel *)view;
    if (!titleLabel)
    {
        titleLabel = [self labelForPickerView];
    }
    titleLabel.text = [self titleForComponent:component row:row];
    return titleLabel;
}


#pragma mark - <UIPickerViewDelegate>
//选择指定列、指定列表项时激发该方法
- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (component == 0) {
        
        _firstIndex = row;
        
        if (_firstIndex >= _secondIndex) {
            NSInteger index = _firstIndex + 1 == [self.dataArr[0] count] ? _firstIndex : _firstIndex + 1;
            [pickerView selectRow:index inComponent:1 animated:YES];
            _secondIndex = index;
        }
        
    }else{
        
        _secondIndex = row;
        if (_secondIndex <= _firstIndex) {
            NSInteger index = _secondIndex == 0 ? _secondIndex : _secondIndex - 1;
            [pickerView selectRow:index inComponent:0 animated:YES];
            _firstIndex = index;
        }
    }
    
    YSSLog(@"%@  -  %@", self.dataArr[0][_firstIndex], self.dataArr[1][_secondIndex]);
    if ([self.selectDelegate respondsToSelector:@selector(yssTimePickerView:didSelectFirstTime:secondTime:)]) {
        [self.selectDelegate yssTimePickerView:self didSelectFirstTime:self.dataArr[0][_firstIndex] secondTime:self.dataArr[1][_secondIndex]];
    }
}


//列宽
- (CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    return Value(100);
}

//行高
- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component
{
    return Value(54);
}


#pragma mark - custom
- (UILabel *)labelForPickerView
{
    UILabel *label = [[UILabel alloc] init];
    label.textColor = [UIColor colorWithRed:85/255 green:85/255 blue:85/255 alpha:1];
    label.textAlignment = NSTextAlignmentCenter;
    label.adjustsFontSizeToFitWidth = YES;
    label.font = [UIFont systemFontOfSize:14];
    return label;
}

- (NSString *)titleForComponent:(NSInteger)component row:(NSInteger)row;
{
    return self.dataArr[component][row];
}

@end
