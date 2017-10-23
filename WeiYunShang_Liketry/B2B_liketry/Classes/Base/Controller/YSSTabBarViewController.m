//
//  YSSTabBarViewController.m
//  BJCenterCommonwealAPP
//
//  Created by GentleZ on 2017/5/8.
//  Copyright © 2017年 liketry. All rights reserved.
//

#import "YSSTabBarViewController.h"
#import "YSSNavigationController.h"
//#import "YSSHomeViewController.h"

@interface YSSTabBarViewController ()

@end

@implementation YSSTabBarViewController

+ (void)load
{
    // 获取哪个类中UITabBarItem
    UITabBarItem *item = [UITabBarItem appearanceWhenContainedIn:self, nil];
    // 设置按钮选中标题的颜色:富文本:描述一个文字颜色,字体,阴影,空心,图文混排
    
    // 创建一个描述文本属性的字典
    NSMutableDictionary *attrs = [NSMutableDictionary dictionary];
    attrs[NSForegroundColorAttributeName] = [UIColor colorWithHexString:YSSLineDarkColor];
    [item setTitleTextAttributes:attrs forState:UIControlStateSelected];
    
    // 设置字体尺寸:只有设置正常状态下,才会有效果
//    NSMutableDictionary *attrsNor = [NSMutableDictionary dictionary];
//    attrsNor[NSFontAttributeName] = [UIFont systemFontOfSize:12];
//    [item setTitleTextAttributes:attrsNor forState:UIControlStateNormal];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self addChildViewControllers];
    
//    [self setupTabBar];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self setupTabBar];
}

/** 添加所有子控制器 */
- (void)addChildViewControllers
{
    [self addChildVc:[[UIViewController alloc] init] title:@"首页" image:nil selectedImage:nil];
    [self addChildVc:[[UIViewController alloc] init] title:@"好物" image:nil selectedImage:nil];
    [self addChildVc:[UITableViewController new] title:@"发现" image:nil selectedImage:nil];
    [self addChildVc:[UITableViewController new] title:@"我的" image:nil selectedImage:nil];
}

- (void)setupTabBar
{
    self.tabBar.translucent = NO;
    [[UITabBar appearance] setBarTintColor:[UIColor whiteColor]];
}

/** 添加子控制器 */
- (void)addChildVc:(UIViewController *)childVc title:(NSString *)title image:(NSString *)image selectedImage:(NSString *)selectedImage
{
    // 设置子控制器的文字(可以设置tabBar和navigationBar的文字)
    childVc.title = title;
    
    [childVc.tabBarItem setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:YSSColorHexString(YSSLineDarkColor),NSForegroundColorAttributeName, nil] forState:UIControlStateNormal];
    [childVc.tabBarItem setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:[UIColor blackColor],NSForegroundColorAttributeName, nil] forState:UIControlStateSelected];
    
    // 设置子控制器的tabBarItem图片
    if ([image isEqualToString:@""])
    {
        childVc.tabBarItem.image = [UIImage new];
        childVc.tabBarItem.selectedImage = [UIImage new];
    }
    else
    {
        childVc.tabBarItem.image = [UIImage imageNamed:image];
        // 禁用图片渲染
        childVc.tabBarItem.selectedImage = [[UIImage imageNamed:image] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    }
    
    // 为子控制器包装导航控制器
    YSSNavigationController *navigationVc = [[YSSNavigationController alloc] initWithRootViewController:childVc];
    
    // 添加子控制器
    [self addChildViewController:navigationVc];
}

@end
