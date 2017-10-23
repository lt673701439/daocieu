//
//  YSSActivityListCell.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/6.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSActivityListCell.h"

#import <MLLinkLabel.h>

#import "YSSMsgModel.h"

@interface YSSActivityListCell ()

@property (nonatomic, strong) UIButton *selectBtn;

@property (nonatomic, strong) UILabel *titleLable;
@property (nonatomic, strong) MLLinkLabel *subTitleLabel;
@property (nonatomic, strong) UIImageView *picView;
@property (nonatomic, strong) MLLinkLabel *contentLabel;


@end

@implementation YSSActivityListCell

+ (CGFloat)cellHeightWithModel:(YSSMsgModel *)model
{
    NSString *title = model.messageTitle;
    CGFloat titleH = [title StringOfSizeWithSize:CGSizeMake(ScreenW - Value(14) * 4, 1000) attributes:[self contentAttribute:[UIColor blackColor]]].height;
    if (titleH < Value(25)) {
        titleH -= Value(8);
    }
    
    NSString *content = model.messageContent;
    CGFloat contentH = [content StringOfSizeWithSize:CGSizeMake(ScreenW - Value(14) * 4, 1000) attributes:[self contentAttribute:[UIColor blackColor]]].height;
//    if (contentH < Value(25)) {
//        contentH -= Value(8);
//    }
    return Value(10) + Value(14) + Value(15) + Value(14) + titleH + Value(14) + (ScreenW - Value(58)) * (300.0 / 634) + Value(14) + contentH;
    
//    return Value(265);
}

+ (NSDictionary *)contentAttribute:(UIColor *)color
{
    NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
    paragraphStyle.lineSpacing = Value(8);
    
    NSDictionary * diccc = @{NSFontAttributeName:[UIFont systemFontOfSize:Value(12)],
                             NSForegroundColorAttributeName : color,
                             NSParagraphStyleAttributeName : paragraphStyle,};
    
    return diccc;
}

- (void)setupUI
{
    [super setupUI];
    self.backgroundColor = [UIColor colorWithHexString:YSSBGColor];
    
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
    
    MLLinkLabel *subTitleLabel = [[MLLinkLabel alloc] init];
    self.subTitleLabel = subTitleLabel;
    subTitleLabel.font = YSSSystemFont(Value(12));
    subTitleLabel.numberOfLines = 0;
    [self.contentView addSubview:subTitleLabel];
    [subTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(imgView);
        make.top.equalTo(imgView.bottom).offset(Value(14));
        make.right.equalTo(self.shadowMainView.right).offset(- Value(14));
    }];
    subTitleLabel.dataDetectorTypes = MLDataDetectorTypeURL;
    //    contentLabel.linkTextAttributes = @{NSForegroundColorAttributeName : [UIColor blackColor]};
    subTitleLabel.activeLinkTextAttributes = @{NSForegroundColorAttributeName : [UIColor blackColor]};
    subTitleLabel.didClickLinkBlock = ^(MLLink *link, NSString *linkText, MLLinkLabel *label) {
        YSSLog(@"%@", linkText);
        if ([self.delegate respondsToSelector:@selector(yssActivityListCell:didClickLink:)]) {
            [self.delegate yssActivityListCell:self didClickLink:linkText];
        }
    };
    
    UIImageView *picView = [[UIImageView alloc] init];
    self.picView = picView;
    picView.image = [UIImage imageNamed:@"消息站位"];
    [self.contentView addSubview:picView];
    [picView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.shadowMainView).offset(Value(15));
        make.right.equalTo(self.shadowMainView).offset(- Value(15));
        make.top.equalTo(subTitleLabel.bottom).offset(Value(10));
        make.height.equalTo(picView.width).multipliedBy(300.0 / 634);
    }];
    
    MLLinkLabel *contentLabel = [[MLLinkLabel alloc] init];
    self.contentLabel = contentLabel;
    contentLabel.textColor = [UIColor lightGrayColor];
    contentLabel.font = YSSSystemFont(Value(12));
    contentLabel.numberOfLines = 2;
    [self.contentView addSubview:contentLabel];
    [contentLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(picView.bottom).offset(Value(10));
        make.left.right.equalTo(picView);
    }];
    contentLabel.dataDetectorTypes = MLDataDetectorTypeURL;
    //    contentLabel.linkTextAttributes = @{NSForegroundColorAttributeName : [UIColor blackColor]};
    contentLabel.activeLinkTextAttributes = @{NSForegroundColorAttributeName : [UIColor blackColor]};
    contentLabel.didClickLinkBlock = ^(MLLink *link, NSString *linkText, MLLinkLabel *label) {
        YSSLog(@"%@", linkText);
        if ([self.delegate respondsToSelector:@selector(yssActivityListCell:didClickLink:)]) {
            [self.delegate yssActivityListCell:self didClickLink:linkText];
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
    
    self.titleLable.text = model.createTime;
//    self.subTitleLabel.text = model.messageTitle;
    self.subTitleLabel.attributedText = [[NSAttributedString alloc] initWithString:model.messageTitle attributes:[YSSActivityListCell contentAttribute:[UIColor colorWithHexString:@"#3b2312"]]];
    self.contentLabel.attributedText = [[NSAttributedString alloc] initWithString:model.messageContent attributes:[YSSActivityListCell contentAttribute:[UIColor lightGrayColor]]];
    
    [self.picView sd_setImageWithURL:YSSAppendImgURL(model.messageImg) placeholderImage:YSSPlaceholderImage completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
        self.picView.contentMode = UIViewContentModeScaleAspectFit;
    }];
    
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

- (void)configData:(NSDictionary *)data
{
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
