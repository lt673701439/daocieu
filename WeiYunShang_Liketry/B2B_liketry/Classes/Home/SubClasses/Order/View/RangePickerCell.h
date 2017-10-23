//
//  RangePickerCell.h
//  FSCalendar
//
//  Created by dingwenchao on 02/11/2016.
//  Copyright © 2016 Wenchao Ding. All rights reserved.
//

#import <FSCalendar/FSCalendar.h>

@interface RangePickerCell : FSCalendarCell

// The start/end of the range
@property (weak, nonatomic) CALayer *selectionLayer;

// The middle of the range
@property (weak, nonatomic) CALayer *middleLayer;


@property (nonatomic, strong) UIImageView *selectionImg;
@property (nonatomic, strong) UIImageView *middleImg;

@property (nonatomic, strong) UIImageView *rightImg;
@property (nonatomic, strong) UIImageView *leftImg;

@end
