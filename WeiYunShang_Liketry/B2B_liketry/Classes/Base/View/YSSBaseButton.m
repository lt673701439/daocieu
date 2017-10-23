//
//  YSSBaseButton.m
//  Blessings_liketry
//
//  Created by GentleZ on 2017/6/1.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseButton.h"

@implementation YSSBaseButton

//扩大点击范围
- (BOOL)pointInside:(CGPoint)point withEvent:(UIEvent *)event
{
    CGRect bounds =self.bounds;
    
    CGFloat widthDelta = 44.0 - bounds.size.width;
    
    CGFloat heightDelta = 44.0 - bounds.size.height;
    
    bounds = CGRectInset(bounds, - 0.5 * widthDelta, - 0.5 * heightDelta);//注意这里是负数，扩大了之前的bounds的范围
    
    return CGRectContainsPoint(bounds, point);
}

@end
