//
//  YSSConst.h
//  BJCenterCommonwealAPP
//
//  Created by GentleZ on 2017/5/8.
//  Copyright © 2017年 liketry. All rights reserved.
//

#import <UIKit/UIKit.h>

#ifdef DEBUG
// 不能看层级视图的bug
//#import "UITextView+NoFailure.h"
#endif

#pragma mark - 第三方
//define this constant if you want to use Masonry without the 'mas_' prefix
#define MAS_SHORTHAND
////define this constant if you want to enable auto-boxing for default syntax
#define MAS_SHORTHAND_GLOBALS
#import <Masonry.h>
#import <AFNetworking.h>
#import <MJRefresh.h>
#import <MJExtension.h>
#import <UIImageView+WebCache.h>
#import <UIButton+WebCache.h>
#import <SVProgressHUD.h>
#import <WXApi.h>
#import <UMSocialCore/UMSocialCore.h>
#import <UShareUI/UShareUI.h>
#import <IMYWebView.h>
#import <YYText.h>
#import <JQFMDB/JQFMDB.h>
#import "UMessage.h"
//#import <NSString+Hash.h>
//#import "PDPullToRefresh.h"
//#import <SDCycleScrollView.h>
//#import <IQKeyboardManager.h>

#pragma mark - 分类
#import "UIColor+Extention.h"
#import "NSDictionary+Property.h"
#import "UIView+YSSExtention.h"
#import "UITextView+YSSTextCenterVertical.h"
#import "UIImage+YSSExtention.h"
#import "NSData+YSSExtention.h"
#import "UITableView+YSSExtention.h"
#import "CALayer+YSSExtention.h"
#import "NSString+YSSExtention.h"
#import "UIImageView+YSSExtention.h"

#pragma mark - 配置
#import "YSSHttpTool.h"
#import "YSSCommonTool.h"
#import "YSSHttpUrl.h"
#import "YSSBaseButton.h"
#import "YSSVerticalButton.h"
#import "YSSAlphaView.h"
#import "YSSMerChanModel.h"
#import "YSSUserModel.h"
#import "QRcodeImage.h"
//#import "YSSSubTitleButton.h"
//#import "Blessings_liketry-Swift.h"
//#import "YSSAccountModel.h"
//#import "YSSAccountManager.h"


UIKIT_EXTERN CGFloat const YSSStatusBarBarHeight;   /**< 状态栏高度 */
UIKIT_EXTERN CGFloat const YSSNavigationBarHeight;  /**< 导航栏高度 */
UIKIT_EXTERN CGFloat const YSSTabBarHeight;         /**< tabBar高度 */

UIKIT_EXTERN CGFloat const YSSVerticalMargin;   /**< 垂直方向间距 */
UIKIT_EXTERN CGFloat const YSSLeftMargin;       /**< 左间距 */
UIKIT_EXTERN CGFloat const YSSRightMargin;      /**< 右间距 */

UIKIT_EXTERN CGFloat const YSSViewCornerRadius; /**< 圆角弧度 */


// 上线隐藏
UIKIT_EXTERN CGFloat const YSSReleaseHidden1;


#pragma mark - color
UIKIT_EXTERN NSString * const YSSBGColor;
UIKIT_EXTERN NSString * const YSSYellowBGColor;
UIKIT_EXTERN NSString * const YSSLineDarkColor;
UIKIT_EXTERN NSString * const YSSLineLightColor;
UIKIT_EXTERN NSString * const YSSYellowColor;
UIKIT_EXTERN NSString * const YSSTextBlackColor;
UIKIT_EXTERN NSString * const YSSBlueColor;


// 颜色
#define YSSColor(r, g, b) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 alpha:1.0]
// 有透明度颜色
#define YSSColorA(r, g, b,a) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 alpha:a/1.0]
// 随机色
#define YSSRandomColor YSSColor(arc4random_uniform(256), arc4random_uniform(256), arc4random_uniform(256))
// 16 进制颜色
#define YSSColorHexString(s) ([UIColor colorWithHexString:s])

#define YSS_BackgoroundColor [UIColor colorWithHexString:YSSBGColor]
#define YSS_TextTintColor [UIColor colorWithHexString:YSSTextTintColor]
#define YSS_BlueColor [UIColor colorWithHexString:YSSBlueColor]

#pragma mark - Font
#define YSSBoldSystemFont(FONTSIZE) [UIFont boldSystemFontOfSize:FONTSIZE]
#define YSSSystemFont(FONTSIZE) [UIFont systemFontOfSize:FONTSIZE]
#define YSSCustomFont(NAME, FONTSIZE) [UIFont fontWithName:(NAME) size:(FONTSIZE)]


UIKIT_EXTERN NSString * const YSSPlaceholderImageName;

/** 服务器图片前缀 */
UIKIT_EXTERN NSString * const YSSHttpImagePreStr;

UIKIT_EXTERN NSString * const YSSAppVersion;

#pragma mark - 网络请求配置
UIKIT_EXTERN NSString * const YSSNetWorkProtocol;
UIKIT_EXTERN NSString * YSSHeadUrl;

UIKIT_EXTERN NSString * const YSSTencentUrl;
UIKIT_EXTERN NSString * const YSSAliUrl;
UIKIT_EXTERN NSString * const YSSHaiXiaoUrl;
UIKIT_EXTERN NSString * const YSSYangYangUrl;


#pragma mark - 推送证书
UIKIT_EXTERN NSString * const YSSApnsCerName;

#pragma mark - 地图APPKey
UIKIT_EXTERN NSString * const YSSMapAPPKey; /**< 地图SDK APPKey */
UIKIT_EXTERN NSString * const YSSWeChatAPPKey; /**< 微信APPKey wx9a399202b419f5a5 */
UIKIT_EXTERN NSString * const YSSWeChatAppSecrect;
UIKIT_EXTERN NSString * const YSSUMengUShareAPPKey; /**< 友盟APPKey */

/** 网络请求发送失败 */
UIKIT_EXTERN NSString * const YSSRequestFailHint;


#pragma mark - Device
#define MainScreen ([UIScreen mainScreen])
#define MainScreenBounds (MainScreen.bounds)
#define MainScreenSize (MainScreenBounds.size)
#define ScreenW (MainScreenSize.width)
#define ScreenH (MainScreenSize.height)
#define iOSVersion  [[UIDevice currentDevice].systemVersion doubleValue]
#define DeviceScale (MainScreen.scale)


#pragma mark - block
typedef void (^CommonBlock)(void);

#pragma mark - other

//通知中心
#define NotificationCenter [NSNotificationCenter defaultCenter]

#define WIDTH_SUIT   [UIScreen mainScreen].bounds.size.width / 375
#define HEIGH_SUIT   [UIScreen mainScreen].bounds.size.height / 667
//以iPhone6(4.7寸)为基准 缩放比例
#define ViewScale (ScreenW / 375.0f)
#define Value(n) (ViewScale * n)

#define TableName(name) ([NSString stringWithFormat:@"user%@", name])

#define YSSPlaceholderImage [UIImage imageNamed:@"Globle_PicDefault"]

// 是否为iOS7
#define YSSiOS7 (iOSVersion >= 7.0 && iOSVersion < 8.0)
// 是否为iOS8
#define YSSiOS8 (iOSVersion >= 8.0 && iOSVersion < 9.0)
// 是否为iOS9
#define YSSiOS9 (iOSVersion >= 9.0)

// 是否为iOS10
#define YSSiOS10 (iOSVersion >= 10.0)

//MD5加密
#define YSSMD5(str) [str md5String]

#define YSSDocumentPath NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES).firstObject

#define YSSAppendImgURL(imgurl) [NSURL URLWithString:[NSString stringWithFormat:@"%@%@", YSSHttpImagePreStr, imgurl]]

#pragma mark - 第三方登录

#define kWBRedirectURL @"http://sns.whalecloud.com/sina2/callback"
#define kWXRedirectURL @"http://mobile.umeng.com/social"
#define kQQRedirectURL @"http://mobile.umeng.com/social"


#pragma mark - 环境切换

// 生产 / 测试 环境切换
// 1 是测试状态，0是发布状态, 2是线上测试
#ifdef DEBUG

// 调试状态
//#define YSSDEBUG 1

// 线上发布
#define YSSDEBUG 0

// 线上测试
//#define YSSDEBUG 2

#else

// 发布状态
#define YSSDEBUG 0

#endif


//上APPstore的话，请注释掉以下代码
#define kDebugVesion @"Debug"

