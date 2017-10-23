//
//  YSSNavigationController.m
//  BJCenterCommonwealAPP
//
//  Created by GentleZ on 2017/5/8.
//  Copyright © 2017年 liketry. All rights reserved.
//

#import "YSSNavigationController.h"

//#import "YSSOrderDetailViewController.h"
//#import "YSSViewLocationViewController.h"

@interface YSSNavigationController ()<UIGestureRecognizerDelegate>
@property (nonatomic, strong) YSSAlphaView *alphaView;
@end

@implementation YSSNavigationController

+ (void)load
{
    UINavigationBar *navBar = [UINavigationBar appearanceWhenContainedIn:self, nil];
    [navBar setBarTintColor:[UIColor whiteColor]];
    
    NSMutableDictionary *attrs = [NSMutableDictionary dictionary];
    attrs[NSForegroundColorAttributeName] = [UIColor blackColor];
    attrs[NSFontAttributeName] = [UIFont fontWithName:@"ArialRoundedMTBold" size:Value(16)];
    [navBar setTitleTextAttributes:attrs];

}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self addGesture];
}

/** 添加全屏滑动返回手势 */
- (void)addGesture
{
    // 获取系统自带滑动手势的target对象
    id target = self.interactivePopGestureRecognizer.delegate;
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wundeclared-selector"
    // 创建全屏滑动手势，调用系统自带滑动手势的target的action方法
    UIPanGestureRecognizer *pan = [[UIPanGestureRecognizer alloc] initWithTarget:target action:@selector(handleNavigationTransition:)];
#pragma clang diagnostic pop
    
    // 设置手势代理，拦截手势触发
    pan.delegate = self;
    
    // 给导航控制器的view添加全屏滑动手势
    [self.view addGestureRecognizer:pan];
    
    // 禁止使用系统自带的滑动手势
    self.interactivePopGestureRecognizer.enabled = NO;
}

- (void)pushViewController:(UIViewController *)viewController animated:(BOOL)animated
{
    if (self.viewControllers.count > 0) {
        
        // 如果现在push的不是栈底控制器(最先push进来的那个控制器)
        viewController.hidesBottomBarWhenPushed = YES;
        // 设置导航栏按钮
        UIButton *backBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        backBtn.adjustsImageWhenHighlighted = NO;
        [backBtn setImage:[UIImage imageNamed:@"返回"] forState:UIControlStateNormal];
        backBtn.frame = CGRectMake(0, Value(10), Value(40), Value(40));
        backBtn.imageEdgeInsets = UIEdgeInsetsMake(0, 0, 0, Value(30));
        [backBtn addTarget:self action:@selector(back) forControlEvents:UIControlEventTouchUpInside];
        viewController.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:backBtn];
        
        //去掉黑线
        [self findHairlineImageViewUnder:self.navigationBar].hidden = YES;
        
        //添加阴影
        if (!self.alphaView) {
            self.alphaView = [[YSSAlphaView alloc] initWithFrame:CGRectMake(0, 44, ScreenW, Value(5))];
            [self.navigationBar addSubview:self.alphaView];
        }
    }
    
    [super pushViewController:viewController animated:animated];
}

- (void)back
{
    [self popViewControllerAnimated:YES];
}

#pragma mark - <UIGestureRecognizerDelegate>
- (BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer
{
    // 注意：只有非根控制器才有滑动返回功能，根控制器没有。
    // 判断导航控制器是否只有一个子控制器，如果只有一个子控制器，肯定是根控制器
    if (self.childViewControllers.count == 1) {
        // 表示用户在根控制器界面，就不需要触发滑动手势，
        return NO;
    }
    
//    UIViewController *tempVC = self.childViewControllers.lastObject;
//    //解决百度地图手势冲突
//    if ([tempVC isKindOfClass:[YSSViewLocationViewController class]]) {
//        return NO;
//    }
//    
//    //支付跳转的订单详情页面禁止手势返回
//    if ([tempVC isKindOfClass:[YSSOrderDetailViewController class]]) {
//        if (((YSSOrderDetailViewController *)tempVC).isToRoot == YES) {
//            return NO;
//        }
//    }

    return YES;
}


#pragma mark - custom
- (UIImageView *)findHairlineImageViewUnder:(UIView *)view {
    if ([view isKindOfClass:UIImageView.class] && view.bounds.size.height <= 1.0) {
        return (UIImageView *)view;
    }
    for (UIView *subview in view.subviews) {
        UIImageView *imageView = [self findHairlineImageViewUnder:subview];
        if (imageView) {
            return imageView;
        }
    }
    return nil;
}

@end
