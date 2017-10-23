//
//  YSSHomeListCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/5.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSHomeListCell.h"

#import <MLLinkLabel.h>

#import "YSSMsgModel.h"
#import "YSSMoneyLessonModel.h"

@interface YSSHomeListCell ()
@property (nonatomic, strong) UILabel *titleLable;
@property (nonatomic, strong) MLLinkLabel *contentLabel;

@property (nonatomic, strong) UIButton *selectBtn;

@end

@implementation YSSHomeListCell

+ (CGFloat)cellHeightWithData:(NSDictionary *)data
{
    NSString *content = data[@"content"];
    
    CGFloat contentH = [content StringOfSizeWithSize:CGSizeMake(ScreenW - Value(14) * 4, 1000) attributes:[self contentAttribute]].height;
    if (contentH < Value(25)) {
        contentH -= Value(8);
    }
    return Value(10) + Value(14) + Value(15) + Value(14) + contentH + Value(14);
}

+ (CGFloat)cellHeightWithModel:(YSSMsgModel *)model
{
    NSString *content = model.messageContent;
    
    CGFloat contentH = [content StringOfSizeWithSize:CGSizeMake(ScreenW - Value(14) * 4, 1000) attributes:[self contentAttribute]].height;
    if (contentH < Value(25)) {
        contentH -= Value(8);
    }
    return Value(10) + Value(14) + Value(15) + Value(14) + contentH + Value(14);
}

+ (CGFloat)cellHeightWithMoneyLessonModel:(YSSMoneyLessonModel *)model
{
    NSString *content = model.name;
    
    CGFloat contentH = [content StringOfSizeWithSize:CGSizeMake(ScreenW - Value(14) * 4, 1000) attributes:[self contentAttribute]].height;
    if (contentH < Value(25)) {
        contentH -= Value(8);
    }
    return Value(10) + Value(14) + Value(15) + Value(14) + contentH + Value(14);
}

+ (NSDictionary *)contentAttribute
{
    NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
    paragraphStyle.lineSpacing = Value(8);
    
    NSDictionary * diccc = @{NSFontAttributeName:[UIFont systemFontOfSize:Value(12)],
                             NSForegroundColorAttributeName : [UIColor colorWithHexString:@"3b2313"],
                             NSParagraphStyleAttributeName : paragraphStyle,};
    
    return diccc;
}

- (void)setupUI
{
    [super setupUI];
    self.backgroundColor = [UIColor colorWithHexString:YSSBGColor];
//    self.selectionStyle = UITableViewCellSelectionStyleDefault;
    
    [self.shadowMainView remakeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.contentView).offset(Value(10));
        make.left.equalTo(self.contentView).offset(Value(14));
        make.right.equalTo(self.contentView).offset(- Value(14));
        make.bottom.equalTo(self.contentView).offset(- Value(1));
    }];
    
    UIImageView *imgView = [[UIImageView alloc] init];
    imgView.image = [UIImage imageNamed:@"文本"];
    [self.contentView addSubview:imgView];
    [imgView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(self.shadowMainView).offset(Value(14));
        make.width.equalTo(Value(17.0 / 2));
        make.height.equalTo(Value(21.0 / 2));
    }];
    
    UILabel *titleLable = [[UILabel alloc] init];
    self.titleLable = titleLable;
    titleLable.font = YSSSystemFont(Value(10));
    titleLable.textColor = [UIColor lightGrayColor];
    [self.contentView addSubview:titleLable];
    [titleLable makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(imgView.right).offset(Value(5));
        make.centerY.equalTo(imgView);
    }];
    
    MLLinkLabel *contentLabel = [[MLLinkLabel alloc] init];
    self.contentLabel = contentLabel;
    contentLabel.font = YSSSystemFont(Value(12));
    contentLabel.numberOfLines = 0;
    [self.contentView addSubview:contentLabel];
    [contentLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(imgView);
        make.top.equalTo(imgView.bottom).offset(Value(14));
        make.right.equalTo(self.shadowMainView.right).offset(- Value(14));
    }];
    contentLabel.dataDetectorTypes = MLDataDetectorTypeURL;
//    contentLabel.linkTextAttributes = @{NSForegroundColorAttributeName : [UIColor blackColor]};
    contentLabel.activeLinkTextAttributes = @{NSForegroundColorAttributeName : [UIColor blackColor]};
    contentLabel.didClickLinkBlock = ^(MLLink *link, NSString *linkText, MLLinkLabel *label) {
        YSSLog(@"%@", linkText);
        if ([self.delegate respondsToSelector:@selector(yssHomeListCell:didClickLink:)]) {
            [self.delegate yssHomeListCell:self didClickLink:linkText];
        }
    };
    
    UIButton *selectBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    self.selectBtn = selectBtn;
    selectBtn.userInteractionEnabled = NO;
    selectBtn.alpha = 0;
    [selectBtn setImage:[UIImage imageNamed:@"Globle_check_N"] forState:UIControlStateNormal];
    [selectBtn setImage:[UIImage imageNamed:@"Globle_check_H"] forState:UIControlStateSelected];
    [self.contentView addSubview:selectBtn];
    [selectBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(Value(14));
        make.centerY.equalTo(self);
        make.width.height.equalTo(@(Value(18)));
    }];
}

- (void)setModel:(YSSMsgModel *)model
{
    _model = model;
    self.titleLable.text = model.messagePublishTime;
    self.contentLabel.attributedText = [[NSAttributedString alloc] initWithString:model.messageContent attributes:[YSSHomeListCell contentAttribute]];
    
    if (!self.canEdit) {
        return;
    }
    
    if (self.edit) {
        [self.shadowMainView remakeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView).offset(Value(10));
            make.left.equalTo(self.contentView).offset(Value(14 + 40));
            make.right.equalTo(self.contentView).offset(- Value(14 - 40));
            make.bottom.equalTo(self.contentView).offset(- Value(1));
        }];
        
        self.selectBtn.alpha = 1;
        self.selectBtn.selected = model.isSelect;
        
        [UIView animateWithDuration:0.25 animations:^{
            [self layoutIfNeeded];
        }];
        
    }else{
        [self.shadowMainView remakeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView).offset(Value(10));
            make.left.equalTo(self.contentView).offset(Value(14));
            make.right.equalTo(self.contentView).offset(- Value(14));
            make.bottom.equalTo(self.contentView).offset(- Value(1));
        }];
        
        self.selectBtn.alpha = 0;
        
        [UIView animateWithDuration:0.25 animations:^{
            [self layoutIfNeeded];
        }];
    }
}

- (void)setMoneyLessonModel:(YSSMoneyLessonModel *)moneyLessonModel
{
    _moneyLessonModel = moneyLessonModel;
    
    self.titleLable.text = moneyLessonModel.showDate;
    self.contentLabel.attributedText = [[NSAttributedString alloc] initWithString:moneyLessonModel.name attributes:[YSSHomeListCell contentAttribute]];
}

- (void)configData:(NSDictionary *)data
{
    self.titleLable.text = data[@"time"];
    self.contentLabel.attributedText = [[NSAttributedString alloc] initWithString:data[@"content"] attributes:[YSSHomeListCell contentAttribute]];
    
    if (!self.canEdit) {
        return;
    }
    
    if (self.edit) {
        [self.shadowMainView remakeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView).offset(Value(10));
            make.left.equalTo(self.contentView).offset(Value(14 + 40));
            make.right.equalTo(self.contentView).offset(- Value(14 - 40));
            make.bottom.equalTo(self.contentView).offset(- Value(1));
        }];
        
        self.selectBtn.alpha = 1;
        self.selectBtn.selected = [data[@"isSelect"] boolValue];
        
        [UIView animateWithDuration:0.25 animations:^{
            [self layoutIfNeeded];
        }];
        
    }else{
        [self.shadowMainView remakeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView).offset(Value(10));
            make.left.equalTo(self.contentView).offset(Value(14));
            make.right.equalTo(self.contentView).offset(- Value(14));
            make.bottom.equalTo(self.contentView).offset(- Value(1));
        }];
        
        self.selectBtn.alpha = 0;
        
        [UIView animateWithDuration:0.25 animations:^{
            [self layoutIfNeeded];
        }];
    }
}

@end
