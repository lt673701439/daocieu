//
//  NSString+YSSExtention.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "NSString+YSSExtention.h"

@implementation NSString (YSSExtention)

-(CGSize)StringOfSizeWithFontOfFloat:(CGFloat)afloat
{
    UIFont *font=[UIFont systemFontOfSize:afloat];
    CGSize size=[self sizeWithAttributes:@{NSFontAttributeName:font}];
    
    return size;
    
}
-(CGSize)StringOfSizeWithSize:(CGSize)asize withFontOfFloat:(CGFloat)afloat
{
    if(!self || self.length ==0 ) return CGSizeZero;
    
    UIFont *font=[UIFont systemFontOfSize:afloat];
    NSAttributedString *attrstr=[[NSAttributedString alloc]initWithString:self attributes:@{NSFontAttributeName:font}];
    
    CGRect rect=[attrstr boundingRectWithSize:asize options:NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading context:nil];
    
    
    return rect.size;
}

-(CGSize)StringOfSizeWithSize:(CGSize)asize attributes:(NSDictionary *)attributes
{
    NSAttributedString *attrstr=[[NSAttributedString alloc]initWithString:self attributes:attributes?:@{}];
    
    CGRect rect=[attrstr boundingRectWithSize:asize options:NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading context:nil];
    
    return rect.size;
}

@end
