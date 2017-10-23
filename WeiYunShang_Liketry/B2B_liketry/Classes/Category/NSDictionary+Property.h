//
//  NSDictionary+Property.h
//  YOUMENG
//
//  Created by GentleZ on 2017/3/29.
//  Copyright © 2017年 YSS. All rights reserved.
//  自动构建模型

#import <Foundation/Foundation.h>

@interface NSDictionary (Property)
/** 自动构建模型 */
- (void)createPropertyCode;

/** 拼接URL参数 */
- (NSString *)createHttpTest;

@end
