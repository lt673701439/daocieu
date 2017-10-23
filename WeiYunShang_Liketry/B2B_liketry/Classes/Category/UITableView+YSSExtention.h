//
//  UITableView+YSSExtention.h
//  Blessings_liketry
//
//  Created by GentleZ on 2017/7/26.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^RefreshBlock)(void);

@interface UITableView (YSSExtention)
/** 添加刷新头视图 */
- (void)addRefreshHeader:(MJRefreshComponentRefreshingBlock)refreshingBlock;
/** 添加刷新尾视图 */
- (void)addRefreshFooter:(MJRefreshComponentRefreshingBlock)refreshingBlock;
/** 改变尾视图刷新状态 */
- (void)changeFooterStateWithDictArr:(NSArray *)dictArr isRefresh:(BOOL)isRefresh refreshBlock:(RefreshBlock)refreshBlock loadMoreBlock:(RefreshBlock)loadMoreBlock;

@end
