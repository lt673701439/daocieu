//
//  YSSTextFieldCell.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface YSSTextFieldCell : UITableViewCell

@property (nonatomic, strong) UITextField *textField;
@property (nonatomic, strong) NSString *phoneNum;

- (void)configUIWithData:(NSDictionary *)data;

@end
