//
//  YSSCategoryCell.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/15.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>
@class YSSServerContentModel;
@interface YSSCategoryCell : UICollectionViewCell

@property (nonatomic, strong) YSSServerContentModel *model;

- (void)configUIWithData:(NSDictionary *)data;

@end
