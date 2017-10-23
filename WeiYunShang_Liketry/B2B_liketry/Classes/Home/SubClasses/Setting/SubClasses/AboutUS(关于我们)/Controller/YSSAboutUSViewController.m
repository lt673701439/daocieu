//
//  YSSAboutUSViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/26.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSAboutUSViewController.h"

@interface YSSAboutUSViewController ()

@end

@implementation YSSAboutUSViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)setupUI
{
    self.title = @"关于我们";
    self.view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    NSString *text = @"在“五德循环”就是“五德”相生相克和周而复始的循环典故中衍生了德循实业（上海）有限公司；\n德循实业公司成立于上海，创始员是广告、渠道、旅游等刚也专家组成，权利打造旅游景区互动媒体，占领旅游互动营销新高地的思路，建设了到此一游、微商云、云播控等多个互联网载体平台。\n立足中国旅游景区的室内外大屏互动营销平台首创者。企业通过用户和商家的数据规整和深度挖掘实现了媒体互动、营销渠道闭环、引入用户消费、带动周边商家、引导品牌流入等方式让营销在互动中落地，结合当下全域旅游，打造品牌、打造渠道、服务旅游群体用户，提升营销商家品牌形象与价值。\n旅游是目前社会最为频繁发生的活动，在德循铺设的互动营销平台高地上，与景区屏幕、周边商户、旅游景区架设互动介质，这种形态的形成逐渐影响营销着传播和服务旅行者行为的交叉网络。\n在创新式互动营销平台上不断的植入各种合作形式，通过互联网+，实现IOT互联、LBS服务、o2o等形式逐渐形成一个旅游互动综合营销整合平台。\n计划未来1-2年内，覆盖全国340个5A级景区，通过5A辐射4A和更多特色旅游目的的互动服务营销网络，使其景区本身、互动合作商家、用户消费全流程融入到互联网+时代的创新性互动营销平台中。\n企业的核心在于团队，团队的核心在于人才，团队的灵魂在于精神，所以人才作为第一战略资源，将人才战略的核心定义为培养人、吸引人、使用人、发掘人。\n企业成功在于创新，创新的源泉在于不断改变，充分利用人才价值的同时充分发挥创新精神，通过积极思考和创新。把平台定位于服务，让服务变成价值实现。\n企业价值的核心在于“分享”，分享的成长、价值和资源，分享连接未来价值，创造人与人、人与企业、企业与企业的价值共赢";
    
    NSMutableAttributedString *attr = [[NSMutableAttributedString alloc] initWithString:text];
    NSMutableParagraphStyle *style = [[NSMutableParagraphStyle alloc] init];
    style.lineSpacing = Value(5);
    style.paragraphSpacing = Value(20);
    [attr addAttribute:NSParagraphStyleAttributeName value:style range:NSMakeRange(0, attr.length)];
    [attr addAttribute:NSForegroundColorAttributeName value:[UIColor lightGrayColor] range:NSMakeRange(0, attr.length)];
    
    UITextView *textView = [[UITextView alloc] init];
    textView.editable = NO;
    textView.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    textView.attributedText = attr;
    [self.view addSubview:textView];
    [textView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
    }];
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.text = @"五德循环";
    titleLabel.textColor = [UIColor lightGrayColor];
    titleLabel.font = YSSCustomFont(@"FZMHJW--GB1-0", Value(20));
    [textView addSubview:titleLabel];
    [titleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.bottom.equalTo(textView.top).offset(- Value(30));
    }];
    
    UILabel *subTitleLabel = [[UILabel alloc] init];
    subTitleLabel.text = @"FIVE CYCLE";
    subTitleLabel.textColor = [UIColor lightGrayColor];
    subTitleLabel.font = YSSSystemFont(Value(10));
    [textView addSubview:subTitleLabel];
    [subTitleLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.bottom.equalTo(textView.top).offset(- Value(10));
    }];
    
    
    
    YSSLog(@"%@", [[NSUserDefaults standardUserDefaults] objectForKey:@"fontFamily"]);
    
    NSMutableArray *fontArrM = [NSMutableArray array];
    
    NSArray *familyNames = [UIFont familyNames];
    for( NSString *familyName in familyNames )
    {
        NSArray *fontNames = [UIFont fontNamesForFamilyName:familyName];
        for( NSString *fontName in fontNames )
        {
            NSString *font = [NSString stringWithFormat:@"%s:%s", [familyName UTF8String], [fontName UTF8String]];
            [fontArrM addObject:font];
        }
    }
    
    YSSLog(@"%@--- %ld", fontArrM, fontArrM.count);
    [fontArrM removeObjectsInArray:[[NSUserDefaults standardUserDefaults] objectForKey:@"fontFamily"]];
    YSSLog(@"%@", fontArrM);
    
    
    
//    [[NSUserDefaults standardUserDefaults] setObject:fontArrM forKey:@"fontFamily"];
    
    if (@available(iOS 11.0, *)) {
        textView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentNever;
        textView.contentInset = UIEdgeInsetsMake(70, 0, 0, 0);
    } else {
        // Fallback on earlier versions
    }
    
    [textView setContentOffset:CGPointMake(0, - 200) animated:NO];
}


@end
