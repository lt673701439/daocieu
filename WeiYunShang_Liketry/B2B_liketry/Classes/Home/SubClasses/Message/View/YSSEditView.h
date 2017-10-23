//
//  YSSEditView.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>
@class YSSEditView;

@protocol YSSEditViewDelegate <NSObject>
/** 全选 */
- (void)yssEditView:(YSSEditView *)yssEditView didClickFullSelectBtn:(UIButton *)fullSelectBtn;
/** 删除 */
- (void)yssEditView:(YSSEditView *)yssEditView didClickDeleteBtn:(UIButton *)deleteBtn;

@end

@interface YSSEditView : UIView

@property (nonatomic, weak) id<YSSEditViewDelegate> delegate;

@end
