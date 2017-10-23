//
//  YSSOrderListBannerView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/12.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface YSSOrderListBannerView : UIView

- (void)configUIWithPrice:(NSString *)price;

- (void)configUIWithStartDate:(NSDate *)startDate endDate:(NSDate *)endDate;

@end
