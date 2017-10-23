//
//  UITableView+YSSExtention.m
//  Blessings_liketry
//
//  Created by GentleZ on 2017/7/26.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "UITableView+YSSExtention.h"

@implementation UITableView (YSSExtention)

- (void)addRefreshHeader:(MJRefreshComponentRefreshingBlock)refreshingBlock
{
    MJRefreshNormalHeader *header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{
        if (refreshingBlock) {
            refreshingBlock();
        }
    }];
    header.lastUpdatedTimeLabel.hidden = YES;
    header.stateLabel.textColor = [UIColor colorWithHexString:@"0x272635"];
    header.stateLabel.font = YSSSystemFont(Value(12));
    [header setTitle:@"下拉刷新" forState:MJRefreshStateIdle];
    [header setTitle:@"松开加载" forState:MJRefreshStatePulling];
    [header setTitle:@"加载中..." forState:MJRefreshStateRefreshing];
    header.arrowView.image = [UIImage imageNamed:@"refreshDown"];
    header.labelLeftInset = Value(12);
    self.mj_header = header;
}

- (void)addRefreshFooter:(MJRefreshComponentRefreshingBlock)refreshingBlock
{
    MJRefreshAutoStateFooter *footer = [MJRefreshAutoStateFooter footerWithRefreshingBlock:^{
        if (refreshingBlock) {
            refreshingBlock();
        }
    }];
    footer.stateLabel.textColor = [UIColor colorWithHexString:@"0x272635"];
    footer.stateLabel.font = YSSSystemFont(Value(12));
    [footer setTitle:@"上拉加载" forState:MJRefreshStateIdle];
    [footer setTitle:@"松开加载" forState:MJRefreshStatePulling];
    [footer setTitle:@"加载中..." forState:MJRefreshStateRefreshing];
    [footer setTitle:@"- END -" forState:MJRefreshStateNoMoreData];
//    footer.arrowView.image = [UIImage imageNamed:@"refreshDown"];
    footer.labelLeftInset = Value(12);
    self.mj_footer = footer;
}

- (void)changeFooterStateWithDictArr:(NSArray *)dictArr isRefresh:(BOOL)isRefresh refreshBlock:(RefreshBlock)refreshBlock loadMoreBlock:(RefreshBlock)loadMoreBlock
{
    if (isRefresh) {
        if (refreshBlock) {
            refreshBlock();
        }
        
        [self.mj_header endRefreshing];
        
        if (dictArr.count < 10) {
//            MJRefreshAutoStateFooter *footer = (MJRefreshAutoStateFooter *)self.mj_footer;
//            footer.arrowView.image = [UIImage new];
            [self.mj_footer endRefreshingWithNoMoreData];
        }else{
//            MJRefreshAutoStateFooter *footer = (MJRefreshAutoStateFooter *)self.mj_footer;
//            footer.arrowView.image = [UIImage imageNamed:@"refreshDown"];
            [self.mj_footer resetNoMoreData];
        }
        
    }else{
        if (loadMoreBlock) {
            loadMoreBlock();
        }
        
        [self.mj_footer endRefreshing];
        
        if (dictArr.count < 10) {
//            MJRefreshAutoStateFooter *footer = (MJRefreshAutoStateFooter *)self.mj_footer;
//            footer.arrowView.image = [UIImage new];
            [self.mj_footer endRefreshingWithNoMoreData];
        }
    }
}

@end
