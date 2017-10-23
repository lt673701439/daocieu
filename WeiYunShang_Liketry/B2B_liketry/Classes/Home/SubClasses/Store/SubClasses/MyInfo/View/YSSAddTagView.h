//
//  YSSAddTagView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/20.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^AddTagBlock)(NSArray *tagArr);

@interface YSSAddTagView : UIView

@property (nonatomic, copy) AddTagBlock block;

+ (instancetype)show;

@end
