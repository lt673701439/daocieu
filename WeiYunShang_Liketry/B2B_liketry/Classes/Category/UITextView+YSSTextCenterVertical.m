//
//  UITextView+YSSTextCenterVertical.m
//  Blessings_liketry
//
//  Created by GentleZ on 2017/6/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "UITextView+YSSTextCenterVertical.h"

@implementation UITextView (YSSTextCenterVertical)

- (void)contentSizeToFit
{
    YSSLog(@"%@ -- %lf -- %lf", self.text, self.contentSize.height, self.yss_height);
    //先判断一下有没有文字（没文字就没必要设置居中了）
    if(self.text.length > 0)
    {
        //textView的contentSize属性
        CGSize contentSize = self.contentSize;
        //textView的内边距属性
        UIEdgeInsets offset = UIEdgeInsetsZero;
        CGSize newSize = contentSize;
        
        
        
        //如果文字内容高度没有超过textView的高度
        if(contentSize.height <= Value(170))
        {
            //textView的高度减去文字高度除以2就是Y方向的偏移量，也就是textView的上内边距
            CGFloat offsetY = (Value(170) - contentSize.height) / 2.0;
            offset = UIEdgeInsetsMake(offsetY, 0, 0, 0);
        }
        
        //根据前面计算设置textView的ContentSize和Y方向偏移量
        [self setContentSize:newSize];
        [self setContentInset:offset];
        
    }
}

@end
