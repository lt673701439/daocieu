//
//  YSSTimePickerView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSTimePickerView;

@protocol YSSTimePickerViewDelegate <NSObject>

- (void)yssTimePickerView:(YSSTimePickerView *)yssTimePickerView didSelectFirstTime:(NSString *)firstTime secondTime:(NSString *)secondTime;

@end

@interface YSSTimePickerView : UIPickerView

@property (nonatomic, weak) id<YSSTimePickerViewDelegate> selectDelegate;

@end
