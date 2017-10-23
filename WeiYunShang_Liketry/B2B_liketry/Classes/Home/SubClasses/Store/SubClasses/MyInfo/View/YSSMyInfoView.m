//
//  YSSMyInfoView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/7.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMyInfoView.h"

#import "GBTagListView.h"

@interface YSSMyInfoView ()
@property (nonatomic, strong) GBTagListView *tagList;
@property (nonatomic, strong) UIView *bottomView;

@property (nonatomic, strong) UILabel *phoneLabel;
@property (nonatomic, strong) UILabel *locationLabel;
@end

@implementation YSSMyInfoView

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
    [self addSubview:topView];
    [topView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self);
    }];
    
    UIView *bgView = [[UIView alloc] init];
    [bgView addCornerRadius:5 borderColor:nil borderWidth:0];
    bgView.backgroundColor = [UIColor colorWithHexString:YSSYellowColor];
    [topView addSubview:bgView];
    [bgView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(topView);
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
    titleLabel.textColor = [UIColor blackColor];
    titleLabel.font = YSSBoldSystemFont(Value(18));
    titleLabel.textColor = [UIColor whiteColor];
    [topView addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(iconView.right).offset(Value(14));
        make.top.equalTo(iconView.top).offset(Value(10));
    }];
    
    UIButton *chatBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [chatBtn setImage:[UIImage imageNamed:@"微信"] forState:UIControlStateNormal];
    [topView addSubview:chatBtn];
    [chatBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(titleLabel.right).offset(Value(5));
        make.centerY.equalTo(titleLabel);
        make.width.height.equalTo(@(Value(20)));
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
    
    [bgView makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(iconView).offset(Value(24));
    }];
    
    UIView *tempView = [[UIView alloc] init];
    tempView.backgroundColor = [UIColor whiteColor];
    [topView addSubview:tempView];
    [tempView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(bgView);
        make.top.equalTo(bgView.bottom).offset(- Value(5));
        make.height.equalTo(Value(10));
    }];
    
    UIView *verticalline = [[UIView alloc] init];
    verticalline.backgroundColor = [UIColor colorWithHexString:YSSLineDarkColor];
    [topView addSubview:verticalline];
    [verticalline makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(topView);
        make.top.equalTo(bgView.bottom).offset(Value(20));
        make.width.equalTo(@1);
        make.height.equalTo(@(Value(48)));
    }];
    
    
    //店铺状态
    UIView *topLeftView = [[UIView alloc] init];
    [topView addSubview:topLeftView];
    [topLeftView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(topView);
        make.right.equalTo(verticalline.left);
        make.top.equalTo(verticalline);
        make.height.equalTo(verticalline);
    }];
    
    UILabel *stateTitleLabel = [[UILabel alloc] init];
    stateTitleLabel.text = @"店铺状态";
    stateTitleLabel.textColor = [UIColor colorWithHexString:@"646464"];
    stateTitleLabel.font = YSSSystemFont(Value(10));
    [topLeftView addSubview:stateTitleLabel];
    [stateTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(topLeftView);
        make.top.equalTo(topLeftView).offset(Value(5));
    }];
    
    NSString *status = @"";
    switch ([YSSMerChanModel sharedInstence].merchantCensorStatus) {
        case 1:
        {
            status = @"审核通过";
        }
            break;
        case 2:
        {
            status = @"提交";
        }
            break;
        case 3:
        {
            status = @"审核不通过";
        }
            break;
        case 4:
        {
            status = @"停止合作";
        }
            break;
        case 5:
        {
            status = @"违规";
        }
            break;
        case 6:
        {
            status = @"违法";
        }
            break;
            
        default:
            break;
    }
    
    UILabel *stateLabel = [[UILabel alloc] init];
    stateLabel.text = status;
    stateLabel.font = YSSBoldSystemFont(Value(12));
    [topLeftView addSubview:stateLabel];
    [stateLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(topLeftView);
        make.top.equalTo(stateTitleLabel.bottom).offset(Value(5));
    }];
    
    UIButton *complainBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    complainBtn.hidden = !([YSSMerChanModel sharedInstence].merchantCensorStatus == 5 || [YSSMerChanModel sharedInstence].merchantCensorStatus == 6);
    [complainBtn setTitle:@"申诉" forState:UIControlStateNormal];
    [complainBtn setTitleColor:[UIColor colorWithHexString:YSSBlueColor] forState:UIControlStateNormal];
    complainBtn.titleLabel.font = YSSSystemFont(Value(11));
    [topLeftView addSubview:complainBtn];
    [complainBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(stateLabel.right).offset(Value(5));
        make.centerY.equalTo(stateLabel);
    }];
    
    
    //加入时间
    UIView *topRightView = [[UIView alloc] init];
    [topView addSubview:topRightView];
    [topRightView makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(topView);
        make.left.equalTo(verticalline.right);
        make.top.equalTo(verticalline);
        make.height.equalTo(verticalline);
    }];
    
    UILabel *timeTitleLabel = [[UILabel alloc] init];
    timeTitleLabel.text = @"加入时间";
    timeTitleLabel.textColor = [UIColor colorWithHexString:@"646464"];
    timeTitleLabel.font = YSSSystemFont(Value(10));
    [topRightView addSubview:timeTitleLabel];
    [timeTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(topRightView);
        make.top.equalTo(topRightView).offset(Value(5));
    }];

    UILabel *timeLabel = [[UILabel alloc] init];
    timeLabel.text = [[[YSSMerChanModel sharedInstence].createTime componentsSeparatedByString:@" "] firstObject];
    timeLabel.font = YSSBoldSystemFont(Value(12));
    [topRightView addSubview:timeLabel];
    [timeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(topRightView);
        make.top.equalTo(timeTitleLabel.bottom).offset(Value(5));
    }];
    
    
    //手机号
    UIImageView *phoneImgView = [[UIImageView alloc] init];
    phoneImgView.image = [UIImage imageNamed:@"手机黄"];
    [topView addSubview:phoneImgView];
    [phoneImgView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(topView).offset(Value(20));
        make.top.equalTo(verticalline.bottom).offset(Value(40));
    }];
    
    UILabel *phoneLabel = [[UILabel alloc] init];
    self.phoneLabel = phoneLabel;
    phoneLabel.text = [YSSMerChanModel sharedInstence].merchantContactMobile.length > 0 ? [YSSMerChanModel sharedInstence].merchantContactMobile : @"未填写手机号";
    phoneLabel.textColor = [UIColor lightGrayColor];
    phoneLabel.font = YSSSystemFont(Value(12));
    [topView addSubview:phoneLabel];
    [phoneLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(phoneImgView.right).offset(Value(14));
        make.centerY.equalTo(phoneImgView);
    }];
    
    UIButton *phoneChangeBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [phoneChangeBtn addTarget:self action:@selector(changePhone) forControlEvents:UIControlEventTouchUpInside];
    [phoneChangeBtn setTitle:@"修改" forState:0];
    [phoneChangeBtn setTitleColor:[UIColor colorWithHexString:YSSBlueColor] forState:0];
    phoneChangeBtn.titleLabel.font = YSSSystemFont(Value(10));
    [topView addSubview:phoneChangeBtn];
    [phoneChangeBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(phoneLabel.right).offset(Value(10));
        make.centerY.equalTo(phoneLabel).offset(Value(0.6));
    }];
    
    
    //地址
    UIImageView *locationImgView = [[UIImageView alloc] init];
    locationImgView.image = [UIImage imageNamed:@"地址黄"];
    [topView addSubview:locationImgView];
    [locationImgView makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(phoneImgView);
        make.top.equalTo(phoneImgView.bottom).offset(Value(30));
    }];
    
    UILabel *locationLabel = [[UILabel alloc] init];
    self.locationLabel = locationLabel;
    locationLabel.text = [YSSMerChanModel sharedInstence].merchantAddress;
    locationLabel.textColor = [UIColor lightGrayColor];
    locationLabel.font = YSSSystemFont(Value(12));
    [topView addSubview:locationLabel];
    [locationLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(locationImgView.right).offset(Value(14));
        make.centerY.equalTo(locationImgView);
    }];
    
    UIButton *locationChangeBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [locationChangeBtn addTarget:self action:@selector(changeLocation) forControlEvents:UIControlEventTouchUpInside];
    [locationChangeBtn setTitle:@"修改" forState:0];
    [locationChangeBtn setTitleColor:[UIColor colorWithHexString:YSSBlueColor] forState:0];
    locationChangeBtn.titleLabel.font = YSSSystemFont(Value(10));
    [topView addSubview:locationChangeBtn];
    [locationChangeBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(locationLabel.right).offset(Value(10));
        make.centerY.equalTo(locationLabel).offset(Value(0.6));
    }];
    
    [topView makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(locationLabel).offset(Value(20));
    }];
    
    UIView *bottomView = [[UIView alloc] init];
    self.bottomView = bottomView;
    [self addSubview:bottomView];
    [bottomView makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self);
        make.top.equalTo(topView.bottom);
    }];
    
    UIView *topLineView = [[UIView alloc] init];
    topLineView.backgroundColor = [UIColor colorWithHexString:@"FAF8F4"];
    [bottomView addSubview:topLineView];
    [topLineView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(bottomView);
        make.height.equalTo(@(Value(7.5)));
    }];
    
    NSString *typeText = [NSString stringWithFormat:@"商户类型:     %@", [YSSMerChanModel sharedInstence].merchantTypeName];
    NSMutableAttributedString *typeAttr = [[NSMutableAttributedString alloc] initWithString:typeText];
    [typeAttr addAttribute:NSForegroundColorAttributeName value:[UIColor lightGrayColor] range:[typeText rangeOfString:@"商户类型:"]];
    UILabel *storeTypeLabel = [[UILabel alloc] init];
    storeTypeLabel.textColor = [UIColor blackColor];
    storeTypeLabel.font = YSSSystemFont(Value(12));
    storeTypeLabel.attributedText = typeAttr;
    [bottomView addSubview:storeTypeLabel];
    [storeTypeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomView).offset(Value(18));
        make.top.equalTo(topLineView.bottom).offset(Value(14));
    }];
    
    NSString *brandText = [NSString stringWithFormat:@"商户品牌:     %@", [YSSMerChanModel sharedInstence].merchantShopname];
    NSMutableAttributedString *brandattr = [[NSMutableAttributedString alloc] initWithString:brandText];
    [brandattr addAttribute:NSForegroundColorAttributeName value:[UIColor lightGrayColor] range:[brandText rangeOfString:@"商户品牌:"]];
    UILabel *storeBrandLabel = [[UILabel alloc] init];
    storeBrandLabel.textColor = [UIColor blackColor];
    storeBrandLabel.font = YSSSystemFont(Value(12));
    storeBrandLabel.attributedText = brandattr;
    [bottomView addSubview:storeBrandLabel];
    [storeBrandLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(storeTypeLabel);
        make.top.equalTo(storeTypeLabel.bottom).offset(Value(14));
    }];
    
    UILabel *contentLabel = [[UILabel alloc] init];
    contentLabel.text = @"服务内容:";
    contentLabel.textColor = [UIColor lightGrayColor];
    contentLabel.font = YSSSystemFont(Value(12));
    [bottomView addSubview:contentLabel];
    [contentLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(storeTypeLabel);
        make.top.equalTo(storeBrandLabel.bottom).offset(Value(14));
    }];
    
    NSMutableArray *tagArrM = [NSMutableArray array];
    for (NSDictionary *dict in [YSSMerChanModel sharedInstence].dicts) {
        [tagArrM addObject:dict[@"text"]];
    }
    
    [tagArrM addObject:@" + "];
    
    GBTagListView *tagList = [[GBTagListView alloc] initWithFrame:CGRectMake(Value(71), Value(76), ScreenW - Value(14 * 2) - Value(71), 0)];
    self.tagList = tagList;
    tagList.canTouch = YES;
    tagList.canTouchNum = 0;
    tagList.isSingleSelect = YES;
    tagList.cornerRadius = Value(10);
    tagList.tagHight = Value(20);
    tagList.tagFontSize = Value(10);
    tagList.GBbackgroundColor = [UIColor clearColor];
    tagList.signalTagColor = [UIColor colorWithHexString:@"0xfaf8f4"];
    tagList.selectTagColor = [UIColor colorWithHexString:@"0xfaf8f4"];
    tagList.titleTagNomalColor = [UIColor blackColor];
    tagList.titleTagHighColor = [UIColor blackColor];
    [bottomView addSubview:tagList];
    [tagList setTagWithTagArray:tagArrM];
    [tagList setDidselectItemBlock:^(NSArray *arr) {
        YSSLog(@"选中的标签%@",arr);
        if ([self.delegate respondsToSelector:@selector(yssMyInfoView:didClickTag:)]) {
            [self.delegate yssMyInfoView:self didClickTag:tagArrM];
        }
    }];
    
    UIButton *addBtn = [tagList.subviews lastObject];
    addBtn.backgroundColor = [UIColor whiteColor];
    [addBtn setTitle:@"" forState:0];
    [addBtn setImage:[UIImage imageNamed:@"添加"] forState:0];
    
    [self makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(tagList.bottom).offset(Value(20));
    }];
}

- (void)resetTagView:(NSArray *)tagArr
{
    [self.tagList removeFromSuperview];
    
    NSMutableArray *tagArrM = [NSMutableArray array];
    for (NSDictionary *dict in [YSSMerChanModel sharedInstence].dicts) {
        [tagArrM addObject:dict[@"text"]];
    }
    
    [tagArrM addObject:@" + "];
    
    GBTagListView *tagList = [[GBTagListView alloc] initWithFrame:CGRectMake(Value(71), Value(78), ScreenW - Value(14 * 2) - Value(71), 0)];
    self.tagList = tagList;
    tagList.canTouch = YES;
    tagList.canTouchNum = 0;
    tagList.isSingleSelect = YES;
    tagList.cornerRadius = Value(10);
    tagList.tagHight = Value(20);
    tagList.tagFontSize = Value(10);
    tagList.GBbackgroundColor = [UIColor clearColor];
    tagList.signalTagColor = [UIColor colorWithHexString:@"0xfaf8f4"];
    tagList.selectTagColor = [UIColor colorWithHexString:@"0xfaf8f4"];
    tagList.titleTagNomalColor = [UIColor blackColor];
    tagList.titleTagHighColor = [UIColor blackColor];
    [self.bottomView addSubview:tagList];
    [tagList setTagWithTagArray:tagArrM];
    [tagList setDidselectItemBlock:^(NSArray *arr) {
        YSSLog(@"选中的标签%@",arr);
        if ([self.delegate respondsToSelector:@selector(yssMyInfoView:didClickTag:)]) {
            [self.delegate yssMyInfoView:self didClickTag:tagArrM];
        }
    }];
    
    UIButton *addBtn = [tagList.subviews lastObject];
    addBtn.backgroundColor = [UIColor whiteColor];
    [addBtn setTitle:@"" forState:0];
    [addBtn setImage:[UIImage imageNamed:@"添加"] forState:0];
    
    [self makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(tagList.bottom).offset(Value(20));
    }];
}

#pragma mark - action
- (void)changePhone
{
    if ([self.delegate respondsToSelector:@selector(yssMyInfoView:didClickChangePhone:)]) {
        [self.delegate yssMyInfoView:self didClickChangePhone:self.phoneLabel];
    }
}

- (void)changeLocation
{
    if ([self.delegate respondsToSelector:@selector(yssMyInfoView:didClickChangeLocation:)]) {
        [self.delegate yssMyInfoView:self didClickChangeLocation:self.locationLabel];
    }
}

@end
