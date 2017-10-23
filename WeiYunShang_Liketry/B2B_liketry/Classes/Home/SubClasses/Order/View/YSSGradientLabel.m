//
//  YSSGradientLabel.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSGradientLabel.h"

@implementation YSSGradientLabel

- (void)setColors:(NSArray *)colors
{
    CAGradientLayer *layer = [CAGradientLayer layer];
    layer.frame = self.frame;
    [layer setColors:colors];
    [self.superview.layer addSublayer:layer];
    
    [layer setStartPoint:CGPointMake(0, 0)];
    [layer setEndPoint:CGPointMake(1, 0)];
    
    layer.mask = self.layer;
    self.frame = layer.bounds;
}

@end
