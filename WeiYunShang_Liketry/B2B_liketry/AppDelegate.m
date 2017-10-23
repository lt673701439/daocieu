//
//  AppDelegate.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/4.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "AppDelegate.h"

#import "YSSNavigationController.h"
#import "YSSLoginViewController.h"
#import "YSSHomeViewController.h"
#import "YSSActivityDetailVC.h"
#import "YSSOrderViewController.h"
#import "YSSMessageViewController.h"
#import "YSSBalanceViewController.h"

//test
#import "YSSStoreRegistVC.h"
#import "YSSSubmitSuccessVC.h"
#import "YSSSubmitAptitudesVC.h"

#import "YSSLaunchGroupView.h"

#import <AMapFoundationKit/AMapFoundationKit.h>
#import <UMessage.h>
#import <UserNotifications/UserNotifications.h>

@interface AppDelegate ()<UNUserNotificationCenterDelegate>

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    
    //启动窗口
    [self setupWindow];
    
    //引导图
    [self setupLaunchGroup];
    
    //地图
    [self setupMap];
    
    //友盟
    [self setupUMengUShare];
    
    //推送
    [self setupUMessageWithOptions:launchOptions];
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
}


- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}


- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
}


- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}


- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

// 支持所有iOS系统
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
{
    //6.3的新的API调用，是为了兼容国外平台(例如:新版facebookSDK,VK等)的调用[如果用6.2的api调用会没有回调],对国内平台没有影响
    BOOL result = [[UMSocialManager defaultManager] handleOpenURL:url sourceApplication:sourceApplication annotation:annotation];
    if (!result) {
        // 其他如支付等SDK的回调
    }
    return result;
}

//仅支持iOS9以上系统，iOS8及以下系统不会回调
- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey, id> *)options
{
    //6.3的新的API调用，是为了兼容国外平台(例如:新版facebookSDK,VK等)的调用[如果用6.2的api调用会没有回调],对国内平台没有影响
    BOOL result = [[UMSocialManager defaultManager]  handleOpenURL:url options:options];
    if (!result) {
        // 其他如支付等SDK的回调
    }
    return result;
}

- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url
{
    BOOL result = [[UMSocialManager defaultManager] handleOpenURL:url];
    if (!result) {
        // 其他如支付等SDK的回调
    }
    return result;
}


#pragma mark - <UNUserNotificationCenterDelegate>

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    // 1.2.7版本开始不需要用户再手动注册devicetoken，SDK会自动注册
    YSSLog(@"devicetoken ===== %@",[[[[deviceToken description] stringByReplacingOccurrencesOfString: @"<" withString: @""]
                  stringByReplacingOccurrencesOfString: @">" withString: @""]
                 stringByReplacingOccurrencesOfString: @" " withString: @""]);
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
    NSString *error_str = [NSString stringWithFormat: @"%@", error];
    YSSLog(@"Failed to get token, error:%@", error_str);
}


//iOS10以下使用这个方法接收通知
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
//    [[NSNotificationCenter defaultCenter] postNotificationName:@"userInfoNotification" object:self userInfo:userInfo];
    [self pushHandleWithData:userInfo];
    [UMessage didReceiveRemoteNotification:userInfo];
}

//iOS10新增：处理前台收到通知的代理方法
-(void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler{
    NSDictionary * userInfo = notification.request.content.userInfo;
    if([notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
        //应用处于前台时的远程推送接受
        //关闭U-Push自带的弹出框
        [UMessage setAutoAlert:NO];
        
//        [[NSNotificationCenter defaultCenter] postNotificationName:@"userInfoNotification" object:self userInfo:userInfo];
//        [self pushHandleWithData:userInfo];
        
        //必须加这句代码
        [UMessage didReceiveRemoteNotification:userInfo];
        
    }else{
        //应用处于前台时的本地推送接受
    }
    //当应用处于前台时提示设置，需要哪个可以设置哪一个
//    completionHandler(UNNotificationPresentationOptionSound|UNNotificationPresentationOptionBadge|UNNotificationPresentationOptionAlert);
    completionHandler(UNNotificationPresentationOptionBadge);
}

//iOS10新增：处理后台点击通知的代理方法
-(void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler{
    NSDictionary * userInfo = response.notification.request.content.userInfo;
    YSSLog(@"%@", userInfo);
    if([response.notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
        //应用处于后台时的远程推送接受
        
        //关闭U-Push自带的弹出框
        [UMessage setAutoAlert:NO];
        
//        [[NSNotificationCenter defaultCenter] postNotificationName:@"userInfoNotification" object:self userInfo:userInfo];
        [self pushHandleWithData:userInfo];
        
        //必须加这句代码
        [UMessage didReceiveRemoteNotification:userInfo];
        
    }else{
        //应用处于后台时的本地推送接受
    }
    
    completionHandler(); // 系统要求执行这个方法
}


#pragma mark - custom
- (void)setupWindow
{
    BOOL islogin = [[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] boolValue];
    
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    YSSNavigationController *navVC = [[YSSNavigationController alloc] initWithRootViewController: islogin ? [[YSSHomeViewController alloc] init] : [[YSSLoginViewController alloc] init]];
    self.window.rootViewController = navVC;
    [self.window makeKeyAndVisible];
}

- (void)setupLaunchGroup
{
    if ([YSSCommonTool isFirstLauch]) {
        YSSLog(@"首次进入");
        [YSSLaunchGroupView show];
    }else{
        YSSLog(@"非首次进入");
    }
}

- (void)setupMap
{
    [AMapServices sharedServices].apiKey = YSSMapAPPKey;
    [[AMapServices sharedServices] setEnableHTTPS:YES];
}

- (void)setupUMengUShare
{
    /* 打开调试日志 */
    [[UMSocialManager defaultManager] openLog:YES];
    
    /* 设置友盟appkey */
    [[UMSocialManager defaultManager] setUmSocialAppkey:YSSUMengUShareAPPKey];
    
    [self configUSharePlatforms];
    
    [self confitUShareSettings];
}

- (void)configUSharePlatforms
{
    [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_WechatSession appKey:YSSWeChatAPPKey appSecret:YSSWeChatAppSecrect redirectURL:nil];
}

- (void)confitUShareSettings
{
    /*
     * 打开图片水印
     */
    //[UMSocialGlobal shareInstance].isUsingWaterMark = YES;
    
    /*
     * 关闭强制验证https，可允许http图片分享，但需要在info.plist设置安全域名
     <key>NSAppTransportSecurity</key>
     <dict>
     <key>NSAllowsArbitraryLoads</key>
     <true/>
     </dict>
     */
    [UMSocialGlobal shareInstance].isUsingHttpsWhenShareContent = NO;
}

- (void)setupUMessageWithOptions:(NSDictionary *)launchOptions
{
    //初始化方法,也可以使用(void)startWithAppkey:(NSString *)appKey launchOptions:(NSDictionary * )launchOptions httpsenable:(BOOL)value;这个方法，方便设置https请求。
    [UMessage startWithAppkey:YSSUMengUShareAPPKey launchOptions:launchOptions];
    //注册通知，如果要使用category的自定义策略，可以参考demo中的代码。
    [UMessage registerForRemoteNotifications];
    
    if (iOSVersion >= 10) {
        //iOS10必须加下面这段代码。
        UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
        center.delegate = self;
        UNAuthorizationOptions types10 = UNAuthorizationOptionBadge | UNAuthorizationOptionAlert | UNAuthorizationOptionSound;
        [center requestAuthorizationWithOptions:types10     completionHandler:^(BOOL granted, NSError * _Nullable error) {
            if (granted) {
                //点击允许
                //这里可以添加一些自己的逻辑
            } else {
                //点击不允许
                //这里可以添加一些自己的逻辑
            }
        }];
    }

    //打开日志，方便调试
    [UMessage setLogEnabled:YES];
}

#pragma mark - 消息跳转
- (void)pushHandleWithData:(NSDictionary *)userInfo
{
    YSSLog(@"消息内容 ====== %@", userInfo);
    //2 公告推送  3 活动推送  4 服务推送  5 新增订单  6 支付订单  7 退单  8 退款  9 提现
    NSInteger actionType = [userInfo[@"action_type"] integerValue];
    if (actionType == 5 || actionType == 7) {
        //去订账单界面
        YSSNavigationController *nav = (YSSNavigationController *)self.window.rootViewController;
        [nav pushViewController:[[YSSOrderViewController alloc] init] animated:YES];
        
    }else if (actionType == 2)
    {
        //去首页
        
    }else if (actionType == 3)
    {
        //去活动详情
        YSSNavigationController *nav = (YSSNavigationController *)self.window.rootViewController;
        YSSActivityDetailVC *tempVC = [[YSSActivityDetailVC alloc] init];
        tempVC.messagePromotionId = userInfo[@"promotion_id"];
        [nav pushViewController:tempVC animated:YES];
        
    }else if (actionType == 4)
    {
        //去消息界面
        YSSNavigationController *nav = (YSSNavigationController *)self.window.rootViewController;
        [YSSCommonTool getMerchanInfoDict];
        [YSSCommonTool getMessageIsPushCompletionHandel:^{
            [nav pushViewController:[[YSSMessageViewController alloc] init] animated:YES];
        }];

    }else if (actionType == 6 || actionType == 8 || actionType == 9)
    {
        //去余额界面
        [YSSCommonTool getMerchanInfoDict];
        [YSSCommonTool getMerchantWithUserId:[YSSMerChanModel sharedInstence].merchantUserId];
        YSSNavigationController *nav = (YSSNavigationController *)self.window.rootViewController;
        [nav pushViewController:[[YSSBalanceViewController alloc] init] animated:YES];
        
    }
}


@end
