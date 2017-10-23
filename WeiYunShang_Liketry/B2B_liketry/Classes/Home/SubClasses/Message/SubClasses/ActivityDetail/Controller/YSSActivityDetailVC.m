//
//  YSSActivityDetailVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/28.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSActivityDetailVC.h"

@interface YSSActivityDetailVC ()<IMYWebViewDelegate>
@property (nonatomic, strong) IMYWebView *webView;
@property (nonatomic, strong) UILabel *placeHolderLabel;
@property (nonatomic, strong) UIProgressView *progressView;

@end

@implementation YSSActivityDetailVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
    
    [self loadData];
}

- (void)setupUI
{
    
    self.title = self.linkText ? @"详情" : @"活动详情";
    
    IMYWebView *webView = [[IMYWebView alloc] initWithFrame:CGRectMake(0, 0, ScreenW, ScreenH)];
    self.webView = webView;
    webView.backgroundColor = [UIColor whiteColor];
    webView.delegate = self;
    [webView addObserver:self forKeyPath:@"estimatedProgress" options:NSKeyValueObservingOptionNew context:nil];
    [self.view addSubview:webView];
    
    UIProgressView *progressView = [[UIProgressView alloc] initWithFrame:CGRectMake(0, 64, ScreenW, Value(3))];
    self.progressView = progressView;
    progressView.tintColor = [UIColor orangeColor];
    progressView.trackTintColor = [UIColor whiteColor];
    [self.view addSubview:progressView];
    
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
    
    if (self.linkText) {
        [self.webView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:self.linkText]]];
    }else{
        NSString *urlStr = [NSString stringWithFormat:@"http://192.168.1.125:8050/travelB-admin-out/api/getPromotionDetail?id=%@", self.messagePromotionId];
        [self.webView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:urlStr]]];
    }
}

// 进度条监听
- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context {
    if (object == self.webView && [keyPath isEqualToString:@"estimatedProgress"]) {
        CGFloat newprogress = [[change objectForKey:NSKeyValueChangeNewKey] doubleValue];
        if (newprogress == 1) {
            [self.progressView setProgress:1 animated:YES];
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.6 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                self.progressView.hidden = YES;
            });
            
        }else {
            self.progressView.hidden = NO;
            [self.progressView setProgress:newprogress animated:YES];
        }
    }
}

// 取消监听
- (void)dealloc {
    [self.webView removeObserver:self forKeyPath:@"estimatedProgress"];
}

#pragma mark - <IMYWebViewDelegate>
- (void)webViewDidFinishLoad:(UIWebView *)webView
{
    YSSLog(@"加载完成");
    self.placeHolderLabel.hidden = YES;
}

@end
