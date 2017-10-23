//
//  YSSInputWithDrawPswView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSInputWithDrawPswView;

@protocol YSSInputWithDrawPswViewDelegate <NSObject>
/** 6位密码输入完成 */
- (void)yssInputWithDrawPswView:(YSSInputWithDrawPswView *)yssInputWithDrawPswView DidFinishInputWithText:(NSString *)text;

@end

@interface YSSInputWithDrawPswView : UIView

@property (nonatomic, weak) id<YSSInputWithDrawPswViewDelegate> delegate;

+ (instancetype)showWithMoney:(NSString *)money;

@end
