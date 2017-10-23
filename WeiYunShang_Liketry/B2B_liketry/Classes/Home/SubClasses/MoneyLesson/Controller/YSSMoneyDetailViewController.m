//
//  YSSMoneyDetailViewController.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/10/11.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSMoneyDetailViewController.h"

@interface YSSMoneyDetailViewController ()<UIWebViewDelegate>
@property (nonatomic, strong) UIWebView *webView;
@property (nonatomic, strong) UILabel *placeHolderLabel;
@end

@implementation YSSMoneyDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    [self loadData];
}

- (void)setupUI
{
    self.title = @"赚钱课堂";
    
    UIWebView *webView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 0, ScreenW, ScreenH)];
    self.webView = webView;
    webView.delegate = self;
    webView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:webView];
    
    UILabel *placeHolderLabel = [[UILabel alloc] init];
    self.placeHolderLabel = placeHolderLabel;
    placeHolderLabel.text = @"加载中...";
    placeHolderLabel.font = YSSSystemFont(Value(14));
    [self.view addSubview:placeHolderLabel];
    [placeHolderLabel makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.centerY.equalTo(self.view);
    }];
}

- (void)loadData
{

    
    NSDictionary *param = @{};
    NSMutableString *url = MAKE_MONEY_DETAIL.mutableCopy;
    [url appendString:self.detailId];
    
    [YSSHttpTool get:url params:param isBase64:NO success:^(id json) {
        YSSLog(@"%@", json[@"result"][@"produce"]);
        
        NSString *htmlString = [NSString stringWithFormat:@"<html> \n"
                                "<head> \n"
                                "<style type=\"text/css\"> \n"
                                //                                "body {font-size:30px;}\n"
                                "</style> \n"
                                "</head> \n"
                                "<body>"
                                "<script type='text/javascript'>"
                                "window.onload = function(){\n"
                                "var $img = document.getElementsByTagName('img');\n"
                                "for(var p in  $img){\n"
                                " $img[p].style.width = '100%%';\n"
                                "$img[p].style.height ='auto'\n"
                                "}\n"
                                "}"
                                "</script>%@"
                                "</body>"
                                "</html>",json[@"result"][@"produce"]];
        
        
        [self.webView loadHTMLString:htmlString baseURL:nil];
    } responseDataError:^(id json) {
        self.placeHolderLabel.text = @"加载失败，请稍后重试";
    } failure:^(NSError *error) {
        self.placeHolderLabel.text = @"加载失败，请稍后重试";
    }];
}

#pragma mark - <UIWebViewDelegate>
- (void)webViewDidFinishLoad:(UIWebView *)webView
{
    self.placeHolderLabel.hidden = YES;
}

@end
