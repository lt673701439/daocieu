//
//  YSSStoreRegistBottomView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSStoreRegistBottomView;

@protocol YSSStoreRegistBottomViewDelegate <NSObject>

/** 下一步 */
- (void)yssStoreRegistBottomViewDidClickNextStep:(YSSStoreRegistBottomView *)yssStoreRegistBottomView;

@end

@interface YSSStoreRegistBottomView : UIView

@property (nonatomic, weak) id<YSSStoreRegistBottomViewDelegate> delegate;

@end
