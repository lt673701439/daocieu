//
//  YSSSettingTFCell.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSIdentifyCell.h"

@interface YSSSettingTFCell : YSSIdentifyCell

@property (nonatomic, strong) UITextField *textField;

- (void)confiUIWithData:(NSDictionary *)data;

@end
