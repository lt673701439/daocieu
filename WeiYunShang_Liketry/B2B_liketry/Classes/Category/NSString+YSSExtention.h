//
//  NSString+YSSExtention.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NSString (YSSExtention)

-(CGSize)StringOfSizeWithFontOfFloat:(CGFloat)afloat;

-(CGSize)StringOfSizeWithSize:(CGSize)asize withFontOfFloat:(CGFloat)afloat;

-(CGSize)StringOfSizeWithSize:(CGSize)asize attributes:(NSDictionary *)attributes;

@end
