//
//  YSSChooseDateVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/12.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSChooseDateVC.h"
#import <FSCalendar.h>

#import "YSSChooseDateHeaderView.h"
#import "RangePickerCell.h"

@interface YSSChooseDateVC ()<FSCalendarDataSource, FSCalendarDelegate>

@property (nonatomic, strong) YSSChooseDateHeaderView *headerView;
@property (nonatomic, strong) FSCalendar *calendar;

@property (weak, nonatomic) UILabel *eventLabel;
@property (strong, nonatomic) NSCalendar *gregorian;
@property (strong, nonatomic) NSDateFormatter *dateFormatter;


// The start date of the range
@property (strong, nonatomic) NSDate *date1;
// The end date of the range
@property (strong, nonatomic) NSDate *date2;

@property (nonatomic, strong) NSDate *startDate;
@property (nonatomic, strong) NSDate *endDate;

@end

@implementation YSSChooseDateVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupNav];
    
    [self setupUI];
}

- (void)setupNav
{
    UIButton *rightBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [rightBtn setTitle:@"完成" forState:0];
    [rightBtn setTitleColor:[UIColor orangeColor] forState:0];
    rightBtn.titleLabel.font = YSSSystemFont(Value(14));
    [rightBtn sizeToFit];
    [rightBtn addTarget:self action:@selector(finish) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:rightBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
}

- (void)setupUI
{
    self.title = @"选择时间";
    self.view.backgroundColor = [UIColor whiteColor];
    
    YSSChooseDateHeaderView *headerView = [[YSSChooseDateHeaderView alloc] init];
    self.headerView = headerView;
    [self.view addSubview:headerView];
    [headerView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(Value(140));
    }];
    
    
    FSCalendar *calendar = [[FSCalendar alloc] initWithFrame:CGRectMake(0, 64 + Value(140), ScreenW, ScreenH - (64 + Value(140)))];
    calendar.dataSource = self;
    calendar.delegate = self;
    calendar.pagingEnabled = NO;
    calendar.scrollDirection = FSCalendarScrollDirectionVertical;
    calendar.allowsMultipleSelection = YES;
    calendar.rowHeight = Value(53);
    calendar.placeholderType = FSCalendarPlaceholderTypeNone;
    [self.view addSubview:calendar];
    self.calendar = calendar;

    calendar.appearance.headerDateFormat = @"MM月";
    
    calendar.appearance.headerTitleColor = [UIColor orangeColor];
    calendar.appearance.headerTitleFont = YSSBoldSystemFont(Value(18));
//    calendar.appearance.titleDefaultColor = [UIColor orangeColor];
    calendar.appearance.titleFont = YSSBoldSystemFont(Value(14));
    calendar.weekdayHeight = 0;
    
    calendar.swipeToChooseGesture.enabled = YES;
    
    calendar.today = nil; // Hide the today circle
    [calendar registerClass:[RangePickerCell class] forCellReuseIdentifier:@"cell"];
    
    self.gregorian = [NSCalendar calendarWithIdentifier:NSCalendarIdentifierGregorian];
    self.gregorian.locale = [[NSLocale alloc] initWithLocaleIdentifier:@"zh_CN"];
    self.dateFormatter = [[NSDateFormatter alloc] init];
    self.dateFormatter.dateFormat = @"yyyy-MM-dd";
    
    // Uncomment this to perform an 'initial-week-scope'
    // self.calendar.scope = FSCalendarScopeWeek;
    
    // For UITest
    self.calendar.accessibilityIdentifier = @"calendar";
    
    
    UIView *alphaView = [[UIView alloc] init];
    alphaView.frame = CGRectMake(0, ScreenH - Value(80), ScreenW, Value(80));
    [self.view addSubview:alphaView];
    
    CAGradientLayer *gradientLayer = [CAGradientLayer layer];
    gradientLayer.colors = @[
                             (__bridge id)[[UIColor whiteColor] colorWithAlphaComponent:1.0].CGColor,
                             (__bridge id)[[UIColor whiteColor] colorWithAlphaComponent:0.6].CGColor,
                             (__bridge id)[[UIColor whiteColor] colorWithAlphaComponent:0.0].CGColor
                             ];
    gradientLayer.locations = @[@0.0, @0.4, @1.0];
    gradientLayer.startPoint = CGPointMake(0, 1);
    gradientLayer.endPoint = CGPointMake(0, 0);
    gradientLayer.frame = alphaView.bounds;
    [alphaView.layer addSublayer:gradientLayer];
    
}

#pragma mark - action
- (void)finish
{
    if (!self.date1) {
        [self.navigationController popViewControllerAnimated:YES];
        return;
    }
    
    if (self.block) {
        if (self.startDate && self.date2) {
            self.block(self.startDate, self.endDate);
        }else{
            self.block(self.date1, self.date1);
        }
    }
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - <FSCalendarDataSource>
- (NSDate *)minimumDateForCalendar:(FSCalendar *)calendar
{
    return [self.dateFormatter dateFromString:@"2017-06-01"];
}

- (NSDate *)maximumDateForCalendar:(FSCalendar *)calendar
{
    return [self.gregorian dateByAddingUnit:NSCalendarUnitMonth value:1 toDate:[NSDate date] options:0];
//    return [self.dateFormatter dateFromString:[self getMonthEnd]];
}

- (NSString *)calendar:(FSCalendar *)calendar titleForDate:(NSDate *)date
{
    if ([self.gregorian isDateInToday:date]) {
        return @"今";
    }
    return nil;
}

- (FSCalendarCell *)calendar:(FSCalendar *)calendar cellForDate:(NSDate *)date atMonthPosition:(FSCalendarMonthPosition)monthPosition
{
    RangePickerCell *cell = [calendar dequeueReusableCellWithIdentifier:@"cell" forDate:date atMonthPosition:monthPosition];
    return cell;
}

- (void)calendar:(FSCalendar *)calendar willDisplayCell:(FSCalendarCell *)cell forDate:(NSDate *)date atMonthPosition: (FSCalendarMonthPosition)monthPosition
{
    [self configureCell:cell forDate:date atMonthPosition:monthPosition];
}

#pragma mark - <FSCalendarDelegate>


- (BOOL)calendar:(FSCalendar *)calendar shouldSelectDate:(NSDate *)date atMonthPosition:(FSCalendarMonthPosition)monthPosition
{
    return monthPosition == FSCalendarMonthPositionCurrent;
}

- (BOOL)calendar:(FSCalendar *)calendar shouldDeselectDate:(NSDate *)date atMonthPosition:(FSCalendarMonthPosition)monthPosition
{
    return NO;
}

- (void)calendar:(FSCalendar *)calendar didSelectDate:(NSDate *)date atMonthPosition:(FSCalendarMonthPosition)monthPosition
{
    
    if (calendar.swipeToChooseGesture.state == UIGestureRecognizerStateChanged) {
        // If the selection is caused by swipe gestures
        if (!self.date1) {
            self.date1 = date;
        } else {
            if (self.date2) {
                [calendar deselectDate:self.date2];
            }
            self.date2 = date;
        }
    } else {
        if (self.date2) {
            [calendar deselectDate:self.date1];
            [calendar deselectDate:self.date2];
            self.date1 = date;
            self.date2 = nil;
        } else if (!self.date1) {
            self.date1 = date;
        } else {
            self.date2 = date;
        }
    }
    
    YSSLog(@"%@---%@", self.date1, self.date2);
    if (self.date1 && self.date2) {
        YSSLog(@"%ld", [self.date1 compare:self.date2]);
        if ([self.date1 compare:self.date2] == -1) {
            [self.headerView configUIWithBeginDate:self.date1 endDate:self.date2];
            self.startDate = self.date1;
            self.endDate = self.date2;
        }else{
            [self.headerView configUIWithBeginDate:self.date2 endDate:self.date1];
            self.startDate = self.date2;
            self.endDate = self.date1;
        }
    }
    
    [self configureVisibleCells];
}

- (void)calendar:(FSCalendar *)calendar didDeselectDate:(NSDate *)date atMonthPosition:(FSCalendarMonthPosition)monthPosition
{
    NSLog(@"did deselect date %@",[self.dateFormatter stringFromDate:date]);
    [self configureVisibleCells];
}

- (NSArray<UIColor *> *)calendar:(FSCalendar *)calendar appearance:(FSCalendarAppearance *)appearance eventDefaultColorsForDate:(NSDate *)date
{
    if ([self.gregorian isDateInToday:date]) {
        return @[[UIColor orangeColor]];
    }
    return @[appearance.eventDefaultColor];
}

#pragma mark - Private methods

- (void)configureVisibleCells
{
    [self.calendar.visibleCells enumerateObjectsUsingBlock:^(__kindof FSCalendarCell * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        NSDate *date = [self.calendar dateForCell:obj];
        FSCalendarMonthPosition position = [self.calendar monthPositionForCell:obj];
        [self configureCell:obj forDate:date atMonthPosition:position];
    }];
}

- (void)configureCell:(__kindof FSCalendarCell *)cell forDate:(NSDate *)date atMonthPosition:(FSCalendarMonthPosition)position
{
    RangePickerCell *rangeCell = cell;
    if (position != FSCalendarMonthPositionCurrent) {
        rangeCell.middleLayer.hidden = YES;
        rangeCell.selectionLayer.hidden = YES;
        return;
    }
    
    if (self.date1 && self.date2) {
        // The date is in the middle of the range
        BOOL isMiddle = [date compare:self.date1] != [date compare:self.date2];
        rangeCell.middleLayer.hidden = !isMiddle;
//        rangeCell.middleImg.hidden = !isMiddle;
        rangeCell.middleImg.hidden = (date == self.date1 || date == self.date2) ? YES : !isMiddle;
        rangeCell.titleLabel.textColor = isMiddle ? [UIColor whiteColor] : ([self.gregorian isDateInToday:date] ? [UIColor redColor] : [UIColor orangeColor]);
    } else {
        rangeCell.middleLayer.hidden = YES;
        rangeCell.middleImg.hidden = YES;
        rangeCell.titleLabel.textColor = date == self.date1 ? [UIColor whiteColor] : ([self.gregorian isDateInToday:date] ? [UIColor redColor] : [UIColor orangeColor]);
    }
    BOOL isSelected = NO;
    isSelected |= self.date1 && [self.gregorian isDate:date inSameDayAsDate:self.date1];
    isSelected |= self.date2 && [self.gregorian isDate:date inSameDayAsDate:self.date2];
    rangeCell.selectionLayer.hidden = !isSelected;
    
    rangeCell.selectionImg.hidden = isSelected ? NO : YES;
    
    NSDate *minDate;
    NSDate *maxDate;
    if ([self.date1 compare:self.date2] == -1) {
        maxDate = self.date2;
        minDate = self.date1;
    }else{
        maxDate = self.date1;
        minDate = self.date2;
    }
    
    rangeCell.rightImg.hidden = (self.date1 && self.date2) ? ((isSelected && date == minDate) ? NO : YES) : YES;
    rangeCell.leftImg.hidden = (self.date1 && self.date2) ? ((isSelected && date == maxDate) ? NO : YES) : YES;
}


//获得月底日期
- (NSString *)getMonthEnd
{
    NSDate *now = [NSDate date];
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *comp = [calendar components:NSCalendarUnitYear | NSCalendarUnitMonth | NSCalendarUnitDay|NSCalendarUnitWeekday | NSCalendarUnitDay fromDate:now];
    // 得到星期几
    // 1(星期天) 2(星期一) 3(星期二) 4(星期三) 5(星期四) 6(星期五) 7(星期六)
    //            NSInteger weekDay = [comp weekday];
    // 得到几号
    NSInteger day = [comp day];
    
    NSInteger month = [comp month];
    YSSLog(@"month:%ld   day:%ld",month,day);
    
    NSDateComponents *firstDayComp = [calendar components:NSCalendarUnitYear | NSCalendarUnitMonth | NSCalendarUnitDay  fromDate:now];
    [firstDayComp setDay:1];
    NSDate *firstDayOfWeek= [calendar dateFromComponents:firstDayComp];
    
    NSRange range = [calendar rangeOfUnit:NSCalendarUnitDay inUnit:NSCalendarUnitMonth forDate:now];
    NSDateComponents *lastDayComp = [calendar components:NSCalendarUnitYear | NSCalendarUnitMonth | NSCalendarUnitDay fromDate:now];
    [lastDayComp setDay:range.length];
    NSDate *lastDayOfWeek= [calendar dateFromComponents:lastDayComp];
    
    NSDateFormatter *formater = [[NSDateFormatter alloc] init];
    //            [formater setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    [formater setDateFormat:@"yyyy-MM-dd"];
    
    NSString  *startdate = [formater stringFromDate:firstDayOfWeek];
    NSString  *enddate = [formater stringFromDate:lastDayOfWeek];
    YSSLog(@"%@  %@", startdate, enddate);
    return enddate;
}


@end
