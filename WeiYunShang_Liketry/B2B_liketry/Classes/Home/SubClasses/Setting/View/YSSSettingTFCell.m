//
//  YSSSettingTFCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/13.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSSettingTFCell.h"

@implementation YSSSettingTFCell

- (void)setupUI
{
    [super setupUI];
    
    UITextField *textField = [[UITextField alloc] init];
    self.textField = textField;
    textField.font = YSSSystemFont(Value(12));
    textField.tintColor = [UIColor orangeColor];
    [self.contentView addSubview:textField];
    [textField makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(120));
        make.right.equalTo(self).offset(- Value(60));
        make.centerY.equalTo(self);
        make.height.equalTo(self);
    }];
}

- (void)confiUIWithData:(NSDictionary *)data
{
    self.leftLabel.text = data[@"title"];
    self.textField.placeholder = data[@"placeHolder"];
    self.rightImgView.hidden = [data[@"isHidden"] integerValue];
    if ([data[@"isHidden"] integerValue]) {
        [self.rightLabel remakeConstraints:^(MASConstraintMaker *make) {
            make.right.equalTo(self).offset(- Value(14));
            make.centerY.equalTo(self);
            make.width.lessThanOrEqualTo(Value(260));
        }];
    }
}

@end
