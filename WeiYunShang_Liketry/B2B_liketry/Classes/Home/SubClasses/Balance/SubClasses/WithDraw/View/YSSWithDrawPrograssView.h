//
//  YSSWithDrawPrograssView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSWithDrawPrograssView, YSSCardModel;

@protocol YSSWithDrawPrograssViewDelegate <NSObject>

- (void)yssWithDrawPrograssViewDidClickViewDetail:(YSSWithDrawPrograssView *)yssWithDrawPrograssView;

@end

@interface YSSWithDrawPrograssView : UIView

@property (nonatomic, weak) id<YSSWithDrawPrograssViewDelegate> delegate;

- (void)configUIWithModel:(YSSCardModel *)model recDisPrice:(NSString *)recDisPrice;

@end
