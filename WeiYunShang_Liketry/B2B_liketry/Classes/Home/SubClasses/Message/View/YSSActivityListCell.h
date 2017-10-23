//
//  YSSActivityListCell.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSBaseShadowCell.h"
@class YSSMsgModel, YSSActivityListCell;

@protocol YSSActivityListCellDelegate <NSObject>

- (void)yssActivityListCell:(YSSActivityListCell *)yssActivityListCell didClickLink:(NSString *)linkText;

@end

@interface YSSActivityListCell : YSSBaseShadowCell

@property (nonatomic, strong) YSSMsgModel *model;

- (void)configData:(NSDictionary *)data;

+ (CGFloat)cellHeightWithModel:(YSSMsgModel *)model;

@property (nonatomic, weak) id<YSSActivityListCellDelegate> delegate;

@end
