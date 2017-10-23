//
//  YSSQRCodeView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/27.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSQRCodeView;
@protocol YSSQRCodeViewDelegate <NSObject>

- (void)yssQRCodeView:(YSSQRCodeView *)yssQRCodeView didClickShareWithImage:(UIImage *)image;

@end

@interface YSSQRCodeView : UIView

@property (nonatomic, weak) id<YSSQRCodeViewDelegate> delegate;

+ (instancetype)show;

@end
