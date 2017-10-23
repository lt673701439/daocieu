//
//  UIView+YSSExtention.h
//  Blessings_liketry
//
//  Created by GentleZ on 2017/5/26.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (YSSExtention)

@property (nonatomic, assign) CGSize yss_size;
@property (nonatomic, assign) CGFloat yss_width;
@property (nonatomic, assign) CGFloat yss_height;
@property (nonatomic, assign) CGFloat yss_x;
@property (nonatomic, assign) CGFloat yss_y;
@property (nonatomic, assign) CGFloat yss_centerX;
@property (nonatomic, assign) CGFloat yss_centerY;
@property (nonatomic, assign, readonly) CGFloat yss_maxX;
@property (nonatomic, assign, readonly) CGFloat yss_maxY;
//添加手势用
@property(nonatomic,assign)CGFloat yss_ttx;
@property(nonatomic,assign)CGFloat yss_tty;

/** 
 View四周添加阴影 
 shadowColor : 阴影颜色
 shadowOffset : 阴影偏移
 shadowOpacity : 阴影透明度
 shadowRadius : 阴影半径
 */
- (void)addShadow:(UIColor *)shadowColor shadowOffset:(CGSize)shadowOffset shadowOpacity:(CGFloat)shadowOpacity shadowRadius:(CGFloat)shadowRadius;


- (void)setCornerRadius:(CGFloat)radius withShadow:(BOOL)shadow withOpacity:(CGFloat)opacity;


/** 添加圆角及边框 */
- (void)addCornerRadius:(CGFloat)cornerRadius borderColor:(UIColor *)borderColor borderWidth:(CGFloat)borderWidth;

/** 添加虚线边框 */
- (CAShapeLayer *)addDashedBorderWithColor:(UIColor *)borderColor;

@end
