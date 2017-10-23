//
//  CALayer+YSSExtention.m
//  Blessings_liketry
//
//  Created by GentleZ on 2017/8/30.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "CALayer+YSSExtention.h"

@implementation CALayer (YSSExtention)

-(void)setBorderUIColor:(UIColor*)color
{
    self.borderColor = color.CGColor;
}

-(UIColor*)borderUIColor
{
    return [UIColor colorWithCGColor:self.borderColor];
}

@end
