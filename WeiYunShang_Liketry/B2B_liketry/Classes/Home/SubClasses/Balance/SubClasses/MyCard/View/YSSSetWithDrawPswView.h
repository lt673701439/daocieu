//
//  YSSSetWithDrawPswView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSSetWithDrawPswView;

@protocol YSSSetWithDrawPswViewDelegate <NSObject>
/** 6位密码输入完成 */
- (void)yssSetWithDrawPswViewDidFinishInput:(YSSSetWithDrawPswView *)yssSetWithDrawPswView;

@end

@interface YSSSetWithDrawPswView : UIView


@property (nonatomic, weak) id<YSSSetWithDrawPswViewDelegate> delegate;

+ (instancetype)show;

@end
