//
//  YSSCardView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSCardView.h"

#import "GBTagListView.h"

@implementation YSSCardView

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    self.backgroundColor = [UIColor whiteColor];
    
    UIView *topView = [[UIView alloc] init];
    topView.backgroundColor = [UIColor colorWithHexString:YSSYellowColor];
    [topView addCornerRadius:5 borderColor:nil borderWidth:0];
    [self addSubview:topView];
    [topView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self);
//        make.height.equalTo(@(Value(170)));
    }];
    
    UIImageView *iconView = [[UIImageView alloc] init];
    iconView.image = [UIImage imageNamed:@"默认头像"];
    [topView addSubview:iconView];
    [iconView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(topView).offset(Value(14));;
        make.width.height.equalTo(@(Value(62.5)));
    }];
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.text = [YSSMerChanModel sharedInstence].merchantShopname;
    titleLabel.textColor = [UIColor whiteColor];
    titleLabel.font = YSSBoldSystemFont(Value(18));
    [topView addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(iconView.right).offset(Value(14));
        make.top.equalTo(iconView.top).offset(Value(10));
    }];
    
    UIButton *chatBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [chatBtn setImage:[UIImage imageNamed:@"微信"] forState:UIControlStateNormal];
    chatBtn.hidden = [YSSUserModel sharedInstence].openId.length == 0;
    [topView addSubview:chatBtn];
    [chatBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(titleLabel.right).offset(Value(5));
        make.centerY.equalTo(titleLabel);
        make.width.height.equalTo(@(Value(20)));
    }];
    
    UIButton *shareBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    shareBtn.hidden = YES;
    [shareBtn setImage:[UIImage imageNamed:@"分享"] forState:0];
    [topView addSubview:shareBtn];
    [shareBtn makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(iconView);
        make.right.equalTo(topView).offset(- Value(14));
    }];
    
    UIButton *levelBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [levelBtn setBackgroundImage:[UIImage imageNamed:@"等级"] forState:UIControlStateNormal];
    [levelBtn setTitle:[NSString stringWithFormat:@"LV%@", [YSSMerChanModel sharedInstence].merchantLevel] forState:UIControlStateNormal];
    [levelBtn setTitleColor:[UIColor colorWithHexString:YSSYellowBGColor] forState:UIControlStateNormal];
    levelBtn.titleLabel.font = YSSSystemFont(Value(10));
    levelBtn.titleEdgeInsets = UIEdgeInsetsMake(Value(6), Value(15), 0, 0);
    [topView addSubview:levelBtn];
    [levelBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(titleLabel);
        make.top.equalTo(titleLabel.bottom).offset(Value(5));
    }];
    
    
    NSMutableArray *tagArrM = [NSMutableArray array];
    for (NSDictionary *dict in [YSSMerChanModel sharedInstence].dicts) {
        [tagArrM addObject:dict[@"text"]];
    }
    
    GBTagListView *tagList = [[GBTagListView alloc] initWithFrame:CGRectMake(Value(0), Value(90), ScreenW - (Value(14 * 2)), 0)];
    tagList.canTouch = YES;
    tagList.canTouchNum = 0;
    
    tagList.horizontalPadding = 4.0;
    
    tagList.isSingleSelect = YES;
    tagList.cornerRadius = Value(10);
    tagList.tagHight = Value(20);
    tagList.tagFontSize = Value(10);
    tagList.GBbackgroundColor = [UIColor clearColor];
    tagList.signalTagColor = [UIColor colorWithHexString:@"0xF6D280"];
    tagList.selectTagColor = [UIColor colorWithHexString:@"0xF6D280"];
    tagList.titleTagNomalColor = [UIColor blackColor];
    tagList.titleTagHighColor = [UIColor blackColor];
    [topView addSubview:tagList];
    [tagList setTagWithTagArray:tagArrM];
    [tagList setDidselectItemBlock:^(NSArray *arr) {
        YSSLog(@"选中的标签%@",arr);
        
    }];
    
    YSSLog(@"%---lf", tagList.yss_height);
    [topView makeConstraints:^(MASConstraintMaker *make) {
        make.height.equalTo(Value(90) + Value(35 + Value(14)));
    }];
    
    UIView *bottomView = [[UIView alloc] init];
    bottomView.backgroundColor = [UIColor whiteColor];
    [self addSubview:bottomView];
    [bottomView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self);
        make.bottom.equalTo(self).offset(- Value(4));
        make.top.equalTo(topView.bottom).offset(- Value(4));
    }];
    
    UIImageView *locationImgView = [[UIImageView alloc] init];
    locationImgView.image = [UIImage imageNamed:@"地址黄"];
    [bottomView addSubview:locationImgView];
    [locationImgView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomView).offset(Value(20));
        make.top.equalTo(bottomView).offset(Value(20));
        make.width.equalTo(@(Value(9)));
        make.height.equalTo(@(Value(13)));
    }];
    
    UILabel *locationLabel = [[UILabel alloc] init];
    locationLabel.text = [YSSMerChanModel sharedInstence].merchantAddress;
    locationLabel.textColor = [UIColor lightGrayColor];
    locationLabel.font = YSSSystemFont(Value(12));
    [bottomView addSubview:locationLabel];
    [locationLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(locationImgView.right).offset(Value(14));
        make.centerY.equalTo(locationImgView);
    }];
    
    UIView *locationline = [[UIView alloc] init];
    locationline.backgroundColor = [UIColor colorWithHexString:YSSLineLightColor];
    [bottomView addSubview:locationline];
    [locationline makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(bottomView);
        make.top.equalTo(locationImgView.bottom).offset(Value(20));
        make.height.equalTo(@0.5);
    }];
    
    
    UIImageView *phoneImgView = [[UIImageView alloc] init];
    phoneImgView.image = [UIImage imageNamed:@"手机黄"];
    [bottomView addSubview:phoneImgView];
    [phoneImgView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomView).offset(Value(20));
        make.top.equalTo(locationline.bottom).offset(Value(20));
        make.width.equalTo(@(Value(9)));
        make.height.equalTo(@(Value(13)));
    }];
    
    UILabel *phoneLabel = [[UILabel alloc] init];
    phoneLabel.text = [YSSMerChanModel sharedInstence].merchantContactMobile.length > 0 ? [YSSMerChanModel sharedInstence].merchantContactMobile : @"未填写手机号";
    phoneLabel.textColor = [UIColor lightGrayColor];
    phoneLabel.font = YSSSystemFont(Value(12));
    [bottomView addSubview:phoneLabel];
    [phoneLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(phoneImgView.right).offset(Value(14));
        make.centerY.equalTo(phoneImgView);
    }];
    
    UIView *phomeline = [[UIView alloc] init];
    phomeline.backgroundColor = [UIColor colorWithHexString:YSSLineLightColor];
    [bottomView addSubview:phomeline];
    [phomeline makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(bottomView);
        make.top.equalTo(phoneImgView.bottom).offset(Value(20));
        make.height.equalTo(@0.5);
    }];
    
    UILabel *bottomLabel = [[UILabel alloc] init];
    bottomLabel.text = @"微信扫一扫，点播送祝福";
    bottomLabel.font = YSSSystemFont(Value(12));
    bottomLabel.textColor = [UIColor lightGrayColor];
    [bottomView addSubview:bottomLabel];
    [bottomLabel makeConstraints:^(MASConstraintMaker *make) {
//        make.top.equalTo(qRMainView.bottom).offset(Value(14));
        make.bottom.equalTo(bottomView).offset(- Value(10));
        make.centerX.equalTo(bottomView);
    }];
    
    
    UIView *qRMainView = [[UIView alloc] init];
    qRMainView.backgroundColor = [UIColor colorWithHexString:@"#FAF8F4"];
    [bottomView addSubview:qRMainView];
    [qRMainView makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(phomeline.bottom).offset(Value(10));
        make.centerX.equalTo(bottomView);
//        make.width.height.equalTo(@(Value(200)));
        make.bottom.equalTo(bottomView).offset(- Value(40));
        make.height.equalTo(qRMainView.width);
    }];
    
    UIImageView *qRImgView = [[UIImageView alloc] init];
    [qRImgView setHttpImageWithURL:YSSAppendImgURL([YSSMerChanModel sharedInstence].merchantQrCode)];
//    [qRImgView sd_setImageWithURL:YSSAppendImgURL([YSSMerChanModel sharedInstence].merchantQrCode) placeholderImage:YSSPlaceholderImage completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, NSURL *imageURL) {
//        qRImgView.contentMode = UIViewContentModeScaleAspectFit;
//    }];
    
    [qRMainView addSubview:qRImgView];
    [qRImgView makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.equalTo(qRMainView).offset(Value(5));
        make.right.bottom.equalTo(qRMainView).offset(- Value(5));
    }];
    
   
    
//    [self makeConstraints:^(MASConstraintMaker *make) {
//        make.bottom.equalTo(bottomLabel).offset(Value(20));
//    }];
}

@end
