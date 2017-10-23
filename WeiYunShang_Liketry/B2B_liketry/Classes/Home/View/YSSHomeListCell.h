//
//  YSSHomeListCell.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseShadowCell.h"
@class YSSMsgModel, YSSHomeListCell, YSSMoneyLessonModel;

@protocol YSSHomeListCellDelegate <NSObject>

- (void)yssHomeListCell:(YSSHomeListCell *)yssHomeListCell didClickLink:(NSString *)linkText;

@end

@interface YSSHomeListCell : YSSBaseShadowCell

@property (nonatomic, strong) YSSMsgModel *model;

@property (nonatomic, strong) YSSMoneyLessonModel *moneyLessonModel;

- (void)configData:(NSDictionary *)data;

+ (CGFloat)cellHeightWithData:(NSDictionary *)data;

+ (CGFloat)cellHeightWithModel:(YSSMsgModel *)model;

+ (CGFloat)cellHeightWithMoneyLessonModel:(YSSMoneyLessonModel *)model;

@property (nonatomic, weak) id<YSSHomeListCellDelegate> delegate;

@end
