//
//  YSSMerChanModel.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/12.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMerChanModel.h"

@implementation YSSMerChanModel

+ (instancetype)sharedInstence
{
    return [self sharedInstenceWithModel:nil isReset:NO];
}

+ (instancetype)sharedInstenceWithModel:(YSSMerChanModel *)model isReset:(BOOL)isReset
{
    static dispatch_once_t once;
    static id instance;
    
    if (isReset) {
        instance = model;
    }else{
        dispatch_once(&once, ^{
            if (!instance) {
                instance = model;
            }
        });
    }
    
    return instance;
}

@end
