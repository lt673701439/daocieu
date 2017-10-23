//
//  YSSTextFieldCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSTextFieldCell.h"

#import "YSSGradientLabel.h"

@interface YSSTextFieldCell ()
@property (nonatomic, strong) UILabel *leftLabel;

@property (nonatomic, strong) YSSGradientLabel *codeLabel;
@property (nonatomic, strong) UIView *rightView;

@property (nonatomic, strong) NSTimer *timer;
@property (nonatomic, assign) int timeout;
@end

@implementation YSSTextFieldCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    self.separatorInset = UIEdgeInsetsZero;
    self.selectionStyle = 0;
    
    UILabel *leftLabel = [[UILabel alloc] init];
    self.leftLabel = leftLabel;
    leftLabel.font = YSSBoldSystemFont(Value(14));
    [self.contentView addSubview:leftLabel];
    [leftLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.centerY.equalTo(self);
        make.width.equalTo(@(Value(80)));
    }];
    
    UITextField *textField = [[UITextField alloc] init];
    textField.tintColor = [UIColor orangeColor];
    self.textField = textField;
    textField.font = YSSSystemFont(Value(15));
    [self.contentView addSubview:textField];
    [textField makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(leftLabel.right).offset(Value(10));
        make.centerY.equalTo(self);
        make.height.equalTo(self);
        make.width.equalTo(@(Value(230)));
    }];
    
    UIView *rightView = [[UIView alloc] init];
    self.rightView = rightView;
    [rightView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(codeTap)]];
    [self.contentView addSubview:rightView];
    [rightView makeConstraints:^(MASConstraintMaker *make) {
        make.right.top.bottom.equalTo(self);
        make.width.equalTo(@(Value(100)));
    }];
    
    YSSGradientLabel *codeLabel = [[YSSGradientLabel alloc] initWithFrame:CGRectMake(- Value(20), Value(10), Value(100), Value(30))];
    self.codeLabel = codeLabel;
    codeLabel.text = @"获取验证码";
    codeLabel.textAlignment = NSTextAlignmentRight;
    codeLabel.font = YSSSystemFont(Value(14));
    [rightView addSubview:codeLabel];
    codeLabel.colors = @[(id)[UIColor yellowColor].CGColor, (id)[UIColor orangeColor].CGColor];
}

- (void)configUIWithData:(NSDictionary *)data
{
    self.leftLabel.text = data[@"title"];
    self.textField.text = data[@"subTitle"];
    self.textField.placeholder = data[@"placeholder"];
    self.codeLabel.hidden = ![data[@"isShow"] boolValue];
    self.rightView.hidden = ![data[@"isShow"] boolValue];
}

#pragma mark - action
- (void)codeTap
{
    //开启倒计时
    self.timeout = 59;
    [self beginCount];
    
    [self getCode];
}

/** 开启倒计时 */
- (void)beginCount
{
    self.codeLabel.text = @"重新获取(59)";
    self.codeLabel.userInteractionEnabled = NO;
    self.timer = [NSTimer scheduledTimerWithTimeInterval:1 repeats:YES block:^(NSTimer * _Nonnull timer) {
        _timeout--;
        if (_timeout == 0) {
            [self.timer invalidate];
            self.timer = nil;
            self.codeLabel.text = @"获取验证码";
            self.codeLabel.userInteractionEnabled = YES;
            return;
        }
        
        self.codeLabel.text = [NSString stringWithFormat:@"重新获取(%d)", _timeout];
    }];
}

- (void)getCode
{
    NSDictionary *param = @{
                            @"mobile" : self.phoneNum ? : [YSSUserModel sharedInstence].loginName,
                            @"type" : @"1"
                            };
    [YSSHttpTool post:GET_IDENTIFYCODE params:param isJsonSerializer:YES success:^(id json) {
        
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:@"验证码发送过于频繁，请稍后再试"];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

@end
