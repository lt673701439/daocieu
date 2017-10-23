//
//  YSSMyInfoView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@class YSSMyInfoView, GBTagListView;

@protocol YSSMyInfoViewDelegate <NSObject>

/** 点击了服务内容标签 */
- (void)yssMyInfoView:(YSSMyInfoView *)yssMyInfoView didClickTag:(NSArray *)tagArr;

/** 点击了修改手机号 */
- (void)yssMyInfoView:(YSSMyInfoView *)yssMyInfoView didClickChangePhone:(UILabel *)phoneLabel;

/** 点击了修改地址 */
- (void)yssMyInfoView:(YSSMyInfoView *)yssMyInfoView didClickChangeLocation:(UILabel *)locationLabel;

@end

@interface YSSMyInfoView : UIView

@property (nonatomic, weak) id<YSSMyInfoViewDelegate> delegate;

- (void)resetTagView:(NSArray *)tagArr;

@end
