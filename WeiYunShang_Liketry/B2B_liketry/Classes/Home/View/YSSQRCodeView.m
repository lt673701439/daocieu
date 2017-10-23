//
//  YSSQRCodeView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/27.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSQRCodeView.h"

#import <YBPopupMenu.h>

@interface YSSQRCodeView ()<YBPopupMenuDelegate>

@property (nonatomic, strong) UIView *mainView;
@property (nonatomic, strong) UIButton *moreButton;

@end

@implementation YSSQRCodeView

+ (instancetype)show
{
    YSSQRCodeView *view = [[YSSQRCodeView alloc] initWithFrame:MainScreenBounds];
    [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:view];
    return view;
}

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        [self setupUI];
    }
    return self;
}

- (void)setupUI
{
    UIView *bgView = [[UIView alloc] init];
    bgView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.15];
    [bgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(dismiss)]];
    [self addSubview:bgView];
    [bgView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self);
    }];
    
    UIView *mainView = [[UIView alloc] init];
    self.mainView = mainView;
    mainView.backgroundColor = [UIColor whiteColor];
    [mainView addCornerRadius:5 borderColor:nil borderWidth:0];
    [self addSubview:mainView];
    [mainView makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.centerY.equalTo(self);
        make.width.equalTo(self.width).offset(- Value(48));
        make.height.equalTo(Value(443));
    }];
    [YSSCommonTool alertZoomIn:mainView andAnimationDuration:0.8];
    
//    UIImageView *topImgView = [[UIImageView alloc] init];
//    topImgView.image = [UIImage imageNamed:@"二维码背景"];
//    [mainView addSubview:topImgView];
//    [topImgView makeConstraints:^(MASConstraintMaker *make) {
//        make.left.top.right.equalTo(mainView);
//        make.height.equalTo(Value(59));
//    }];
    
    UIImageView *iconView = [[UIImageView alloc] init];
    iconView.image = [UIImage imageNamed:@"默认头像"];
    [mainView addSubview:iconView];
    [iconView makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(mainView).offset(Value(18));
        make.width.height.equalTo(@(Value(62.5)));
    }];
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.text = [YSSMerChanModel sharedInstence].merchantShopname;
    titleLabel.textColor = [UIColor colorWithHexString:YSSTextBlackColor];
    titleLabel.font = YSSBoldSystemFont(Value(18));
    [mainView addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(iconView.right).offset(Value(10));
        make.centerY.equalTo(iconView);
    }];
    
    UIButton *moreButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.moreButton = moreButton;
    [moreButton setTitle:@"..." forState:UIControlStateNormal];
    [moreButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [moreButton addTarget:self action:@selector(moreBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [mainView addSubview:moreButton];
    [moreButton makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(iconView).offset(- Value(3));
        make.right.equalTo(mainView).offset(- Value(18));
    }];
    
    
    UIImageView *qrCodeImgView = [[UIImageView alloc] init];
    [qrCodeImgView setHttpImageWithURL:YSSAppendImgURL([YSSMerChanModel sharedInstence].merchantQrCode)];
    YSSLog(@"%@", [NSString stringWithFormat:@"%@%@", YSSHttpImagePreStr, [YSSMerChanModel sharedInstence].merchantQrCode]);
    [mainView addSubview:qrCodeImgView];
    [qrCodeImgView makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.top.equalTo(iconView.bottom).offset(Value(14));
//        make.width.height.equalTo(@(Value(235)));
        make.left.equalTo(iconView.left);
        make.right.equalTo(mainView).offset(- Value(18));
        make.bottom.equalTo(mainView).offset(- Value(18));
    }];
    
//    UILabel *label = [[UILabel alloc] init];
//    label.text = @"微信扫一扫，点播送祝福";
//    label.textColor = [UIColor colorWithHexString:YSSTextBlackColor];
//    label.font = YSSSystemFont(Value(13));
//    [mainView addSubview:label];
//    [label makeConstraints:^(MASConstraintMaker *make) {
//        make.centerX.equalTo(mainView);
//        make.top.equalTo(qrCodeImgView.bottom).offset(Value(14));
//    }];
}

#pragma mark - action
- (void)moreBtnClick:(UIButton *)sender
{
    YSSLog(@"...");
    NSArray *titleArr = @[@"分享", @"保存图片"];
//    [YBPopupMenu showRelyOnView:sender titles:titleArr icons:nil menuWidth:Value(100) delegate:self];
    [YBPopupMenu showRelyOnView:sender titles:titleArr icons:nil menuWidth:Value(100) otherSettings:^(YBPopupMenu *popupMenu) {
        popupMenu.delegate = self;
    }];
}

- (void)dismiss
{
    [self removeFromSuperview];
}

#pragma mark - <YBPopupMenuDelegate>
- (void)ybPopupMenuDidSelectedAtIndex:(NSInteger)index ybPopupMenu:(YBPopupMenu *)ybPopupMenu
{
    self.moreButton.hidden = YES;
    UIImage *image = [YSSCommonTool captureScreen:self.mainView];
    self.moreButton.hidden = NO;
    if (index == 0) {
        YSSLog(@"分享");
        if ([self.delegate respondsToSelector:@selector(yssQRCodeView:didClickShareWithImage:)]) {
            [self.delegate yssQRCodeView:self didClickShareWithImage:image];
        }

    }else{
        UIImageWriteToSavedPhotosAlbum(image, self, @selector(image:didFinishSavingWithError:contextInfo:), (__bridge void *)self);
    }
}

- (void)image:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo
{
    YSSLog(@"image = %@, error = %@, contextInfo = %@", image, error, contextInfo);
    
    [YSSCommonTool showSuccessWithStatus:@"保存成功"];
}


@end
