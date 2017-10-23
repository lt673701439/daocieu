//
//  UIView+YSSExtention.m
//  Blessings_liketry
//
//  Created by GentleZ on 2017/5/26.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "UIView+YSSExtention.h"

@implementation UIView (YSSExtention)

- (void)setYss_size:(CGSize)yss_size
{
    CGRect frame = self.frame;
    frame.size = yss_size;
    self.frame = frame;
}

- (CGSize)yss_size
{
    return self.frame.size;
}

- (void)setYss_width:(CGFloat)yss_width
{
    CGRect frame = self.frame;
    frame.size.width = yss_width;
    self.frame = frame;
}

- (CGFloat)yss_width
{
    return self.frame.size.width;
}

- (void)setYss_height:(CGFloat)yss_height
{
    CGRect frame = self.frame;
    frame.size.height = yss_height;
    self.frame = frame;
}

- (CGFloat)yss_height
{
    return self.frame.size.height;
}

- (void)setYss_x:(CGFloat)yss_x
{
    CGRect frame = self.frame;
    frame.origin.x = yss_x;
    self.frame = frame;
}

- (CGFloat)yss_x
{
    return self.frame.origin.x;
}

- (void)setYss_y:(CGFloat)yss_y
{
    CGRect frame = self.frame;
    frame.origin.y = yss_y;
    self.frame = frame;
}

- (CGFloat)yss_y
{
    return self.frame.origin.y;
}

- (void)setYss_centerX:(CGFloat)yss_centerX
{
    CGPoint center = self.center;
    center.x = yss_centerX;
    self.center = center;
}

- (CGFloat)yss_centerX
{
    return self.center.x;
}

- (void)setYss_centerY:(CGFloat)yss_centerY
{
    CGPoint center = self.center;
    center.y = yss_centerY;
    self.center = center;
}

- (CGFloat)yss_centerY
{
    return self.center.y;
}

- (CGFloat)yss_maxX
{
    return self.yss_x + self.yss_width;
}

- (CGFloat)yss_maxY
{
    return self.yss_y + self.yss_height;
}

- (void)setYss_ttx:(CGFloat)yss_ttx
{
    CGAffineTransform  transform = self.transform;
    transform.tx = yss_ttx;
    self.transform = transform;
}

- (CGFloat)yss_ttx
{
    return self.transform.tx;
}

- (void)setYss_tty:(CGFloat)yss_tty
{
    CGAffineTransform  transform = self.transform;
    transform.ty = yss_tty;
    self.transform = transform;
}

- (CGFloat)yss_tty
{
    return self.transform.ty;
}


- (void)addShadow:(UIColor *)shadowColor shadowOffset:(CGSize)shadowOffset shadowOpacity:(CGFloat)shadowOpacity shadowRadius:(CGFloat)shadowRadius
{
    [self layoutIfNeeded];
    
    self.layer.shadowColor = shadowColor.CGColor;//shadowColor阴影颜色
    self.layer.shadowOffset = shadowOffset;//shadowOffset阴影偏移，默认(0, -3),这个跟shadowRadius配合使用
    self.layer.shadowOpacity = shadowOpacity;//阴影透明度，默认0
    self.layer.shadowRadius = shadowRadius;//阴影半径，默认3
    
    //路径阴影
    UIBezierPath *path = [UIBezierPath bezierPath];
    
    float width = self.bounds.size.width;
    float height = self.bounds.size.height;
    float x = self.bounds.origin.x;
    float y = self.bounds.origin.y;
    float addWH = 10;
    
    CGPoint topLeft = self.bounds.origin;
    CGPoint topMiddle = CGPointMake(x + (width / 2), y - addWH);
    CGPoint topRight = CGPointMake(x + width, y);
    
    CGPoint rightMiddle = CGPointMake(x + width + addWH, y + (height / 2));
    
    CGPoint bottomRight = CGPointMake(x + width, y + height);
    CGPoint bottomMiddle = CGPointMake(x + (width / 2), y + height + addWH);
    CGPoint bottomLeft = CGPointMake(x, y + height);
    
    
    CGPoint leftMiddle = CGPointMake(x - addWH,y + (height / 2));
    
    [path moveToPoint:topLeft];
    //添加四个二元曲线
    [path addQuadCurveToPoint:topRight
                 controlPoint:topMiddle];
    [path addQuadCurveToPoint:bottomRight
                 controlPoint:rightMiddle];
    [path addQuadCurveToPoint:bottomLeft
                 controlPoint:bottomMiddle];
    [path addQuadCurveToPoint:topLeft
                 controlPoint:leftMiddle];
    //设置阴影路径
    self.layer.shadowPath = path.CGPath;
}

- (void)setCornerRadius:(CGFloat)radius withShadow:(BOOL)shadow withOpacity:(CGFloat)opacity
{
    self.layer.cornerRadius = radius;
    if (shadow) {
        self.layer.shadowColor = [UIColor lightGrayColor].CGColor;
        self.layer.shadowOpacity = opacity;
        self.layer.shadowOffset = CGSizeMake(0, 0);
        self.layer.shadowRadius = 4;
        self.layer.shouldRasterize = NO;
        self.layer.shadowPath = [[UIBezierPath bezierPathWithRoundedRect:[self bounds] cornerRadius:radius] CGPath];
    }
    self.layer.masksToBounds = !shadow;
}


- (void)addCornerRadius:(CGFloat)cornerRadius borderColor:(UIColor *)borderColor borderWidth:(CGFloat)borderWidth
{
    self.layer.masksToBounds = YES;
    self.layer.cornerRadius = cornerRadius;
    self.layer.borderColor = borderColor.CGColor;
    self.layer.borderWidth = borderWidth;
}

- (CAShapeLayer *)addDashedBorderWithColor:(UIColor *)borderColor
{
    CAShapeLayer *border = [CAShapeLayer layer];
    
    border.strokeColor = borderColor.CGColor;
    
    border.fillColor = nil;
    
    border.path = [UIBezierPath bezierPathWithRect:self.bounds].CGPath;
    
    border.frame = self.bounds;
    
    border.lineWidth = 1.f;
    
    border.lineCap = @"square";
    
    border.lineDashPattern = @[@10, @10];
    
    [self.layer addSublayer:border];
    
    return border;

}









@end
