//
//  YSSIdentifyCell.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface YSSIdentifyCell : UITableViewCell

@property (nonatomic, strong) UILabel *leftLabel;
@property (nonatomic, strong) UILabel *rightLabel;
@property (nonatomic, strong) UIImageView *rightImgView;

- (void)setupUI;

- (void)confiUIWithData:(NSDictionary *)data;

- (void)confiUIWithTitle:(NSString *)str;

@end
