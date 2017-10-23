//
//  RangePickerCell.m
//  FSCalendar
//
//  Created by dingwenchao on 02/11/2016.
//  Copyright © 2016 Wenchao Ding. All rights reserved.
//

#import "RangePickerCell.h"
#import "FSCalendarExtensions.h"

@implementation RangePickerCell

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
        CALayer *selectionLayer = [[CALayer alloc] init];
        selectionLayer.backgroundColor = [UIColor orangeColor].CGColor;
        selectionLayer.actions = @{@"hidden":[NSNull null]}; // Remove hiding animation
        [self.contentView.layer insertSublayer:selectionLayer below:self.titleLabel.layer];
//        self.selectionLayer = selectionLayer;
        
        CALayer *middleLayer = [[CALayer alloc] init];
        middleLayer.backgroundColor = [[UIColor orangeColor] colorWithAlphaComponent:0.3].CGColor;
        middleLayer.actions = @{@"hidden":[NSNull null]}; // Remove hiding animation
        [self.contentView.layer insertSublayer:middleLayer below:self.titleLabel.layer];
//        self.middleLayer = middleLayer;
        
        // Hide the default selection layer
        self.shapeLayer.hidden = YES;
        
        UIImageView *selectionImg = [[UIImageView alloc] init];
        self.selectionImg = selectionImg;
        selectionImg.image = [UIImage imageNamed:@"日期"];
        selectionImg.hidden = YES;
        [self.contentView insertSubview:selectionImg atIndex:0];
        [selectionImg makeConstraints:^(MASConstraintMaker *make) {
//            make.edges.equalTo(self);
            make.centerX.equalTo(self);
            make.centerY.equalTo(self);
            make.width.height.equalTo(Value(35));
        }];
        
        UIImageView *middleImg = [[UIImageView alloc] init];
        self.middleImg = middleImg;
        middleImg.hidden = YES;
        middleImg.image = [UIImage imageNamed:@"日期背景"];
        [self.contentView insertSubview:middleImg atIndex:0];
        [middleImg makeConstraints:^(MASConstraintMaker *make) {
//            make.top.bottom.equalTo(self);
            make.centerY.equalTo(self);
            make.height.equalTo(Value(35));
            make.left.equalTo(self).offset(- Value(0));
            make.right.equalTo(self).offset(Value(1));
        }];
        
        UIImageView *rightImg = [[UIImageView alloc] init];
        self.rightImg = rightImg;
        rightImg.image = [UIImage imageNamed:@"日期背景"];
        [self.contentView insertSubview:rightImg atIndex:0];
        [rightImg makeConstraints:^(MASConstraintMaker *make) {
//            make.top.bottom.equalTo(self);
            make.centerY.equalTo(self);
            make.height.equalTo(Value(35));
            make.left.equalTo(self.centerX);
            make.right.equalTo(self).offset(Value(1));
        }];
        
        UIImageView *leftImg = [[UIImageView alloc] init];
        self.leftImg = leftImg;
        leftImg.image = [UIImage imageNamed:@"日期背景"];
        [self.contentView insertSubview:leftImg atIndex:0];
        [leftImg makeConstraints:^(MASConstraintMaker *make) {
//            make.top.bottom.equalTo(self);
            make.centerY.equalTo(self);
            make.height.equalTo(Value(35));
            make.right.equalTo(self.centerX);
            make.left.equalTo(self).offset(- Value(1));
        }];
        
    }
    return self;
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    self.titleLabel.frame = self.contentView.bounds;
}

- (void)layoutSublayersOfLayer:(CALayer *)layer
{
    [super layoutSublayersOfLayer:layer];
    self.selectionLayer.frame = self.contentView.bounds;
    self.middleLayer.frame = self.contentView.bounds;
}

@end
