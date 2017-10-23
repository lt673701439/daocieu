//
//  YSSLaunchGroupView.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/4.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSLaunchGroupView.h"

@interface YSSLaunchGroupView()<UIScrollViewDelegate>

@property (nonatomic, strong) NSArray *imageNameArr; /**< 图片数组 */
@property (nonatomic, strong) UIPageControl *pageControl;
@property (nonatomic, strong) UIButton *goBtn;
@end

@implementation YSSLaunchGroupView

- (NSArray *)imageNameArr
{
    if (_imageNameArr == nil) {
        _imageNameArr = @[@"", @"", @"", @""];
    }
    return _imageNameArr;
}

+ (void)show
{
    YSSLaunchGroupView *view = [[YSSLaunchGroupView alloc] initWithFrame:MainScreenBounds];
    [[UIApplication sharedApplication].keyWindow addSubview:view];
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
    UIScrollView *scrollVeiw = [[UIScrollView alloc] initWithFrame:self.bounds];
    [self addSubview:scrollVeiw];
    scrollVeiw.delegate = self;
    scrollVeiw.bounces = NO;
    scrollVeiw.contentSize = CGSizeMake(ScreenW * self.imageNameArr.count, 0);
    scrollVeiw.pagingEnabled = YES;
    scrollVeiw.showsHorizontalScrollIndicator = NO;
    
    for (int i = 0; i < self.imageNameArr.count; i++) {
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(ScreenW * i, 0, ScreenW, ScreenH)];
        imageView.image = [UIImage imageNamed:self.imageNameArr[i]];
        imageView.backgroundColor = YSSRandomColor;
        [scrollVeiw addSubview:imageView];
    }
    
    UIPageControl *pageControl = [[UIPageControl alloc] init];
    self.pageControl = pageControl;
    pageControl.numberOfPages = self.imageNameArr.count;
    pageControl.currentPage = 0;
    [self addSubview:pageControl];
    [pageControl makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.bottom.equalTo(self).offset(- Value(30));
    }];
    
    UIButton *goBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    self.goBtn = goBtn;
    goBtn.hidden = YES;
    [goBtn setTitle:@"立即体验" forState:UIControlStateNormal];
    [goBtn addTarget:self action:@selector(dismiss) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:goBtn];
    [goBtn makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.bottom.equalTo(pageControl.top).offset(- Value(10));
        make.width.equalTo(@(Value(100)));
        make.height.equalTo(@(Value(30)));
    }];
}

- (void)dismiss
{
    [UIView animateWithDuration:0.5 animations:^{
        self.transform = CGAffineTransformMakeScale(1.5, 1.5);
        self.alpha = 0.05;
    } completion:^(BOOL finished) {
        [self removeFromSuperview];
    }];
    
    
}

#pragma mark - <UIScrollViewDelegate>
- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    self.pageControl.currentPage = floor(scrollView.contentOffset.x / ScreenW + 0.5);
    if (floor(scrollView.contentOffset.x / ScreenW + 0.5) == self.imageNameArr.count - 1) {
        self.goBtn.hidden = NO;
    }
}

@end
