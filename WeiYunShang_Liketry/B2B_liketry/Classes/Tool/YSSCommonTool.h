//
//  YSSCommonTool.h
//  BJCenterCommonwealAPP
//
//  Created by GentleZ on 2017/5/8.
//  Copyright © 2017年 liketry. All rights reserved.
//

#import <UIKit/UIKit.h>

//#import <AMapFoundationKit/AMapFoundationKit.h>
//#import <AMapLocationKit/AMapLocationKit.h>

typedef NS_ENUM(NSUInteger, YSSDateFormatterType)
{
    /** yyyy-MM-dd */
    YSSDateFormatterTypeLine = 0,
    
    /** yyyy年MM月dd日 */
    YSSDateFormatterTypeText,
    
    /** hh:mm */
    YSSDateFormatterTypeHourMinute,
    
    /** yyyy.MM.dd */
    YSSDateFormatterTypeDot,
};

@interface YSSCommonTool : NSObject
+ (void)setScaleAspectFillContentModel:(UIView *)view;

+ (int)parseInt:(NSObject *)value;

+ (long long)parseLongLong:(NSObject *)value;

+ (NSString *)parseString:(NSObject *)value;

+ (double)parseDouble:(NSObject *)value;

+ (NSArray *)parseArray:(NSObject *)value;

+ (NSDictionary *)parseDictionary:(NSObject *)value;

/** 获取今天日期 */
+ (NSString *)getTodayDate:(YSSDateFormatterType)datetype;

/** 获取当前时间到指定时间的时间差(秒数) */
+ (NSTimeInterval)getCountDownStringWithEndTime:(NSString *)endTime;

/** 时间戳转化为日期 */
+ (NSString *)timeWithTimeIntervalString:(NSString *)timeString;

/** 转换时间字符串日期格式 */
+ (NSString *)changeTimeStr:(NSString *)timeStr fromFormatter:(NSString *)fromFormatter toFormatter:(NSString *)toFormatter;

#pragma mark - HUD
+ (void)showHUD;

+ (void)showInfoWithStatus:(NSString*)status;

+ (void)showSuccessWithStatus:(NSString*)status;

+ (void)showErrorWithStatus:(NSString*)status;

#pragma mark - 动画
/** 类似系统对话框弹出动画 */
+ (void)alertZoomIn:(UIView *)view andAnimationDuration:(float)duration;

/** 心跳动画 */
+ (void)heartAniZoomIn:(UIView *)view andAnimationDuration:(float)duration;


#pragma mark - 加密
/** base64加密参数 */
+ (NSDictionary *)paramWithBase64:(NSDictionary *)param;

+ (const char *)getIpAddresses;

#pragma mark - 截取图片
+ (UIImage *)captureScreen:(UIView*)viewToCapture;

//+ (void)getLocation:(AMapLocatingCompletionBlock)completionBlock;

+ (CGSize)getImageSizeWithURL:(id)imageURL;

#pragma mark - 判断是不是首次登录或者版本更新
+ (BOOL)isFirstLauch;

/** 过滤字典及数组中的NUll值 */
+ (id)processDictionaryIsNSNull:(id)obj;

/** 银行卡号转换银行名称 */
+ (NSString *)returnBankName:(NSString*)idCard;

+ (UIImage *)placeholderImageWithSize:(CGSize)size;


+ (void)getMerchanInfoDict;
+ (void)getMessageIsPushCompletionHandel:(void(^)(void))complectionHandel;
+ (void)getMerchantWithUserId:(NSString *)userId;

@end
