//
//  YSSCommonTool.m
//  BJCenterCommonwealAPP
//
//  Created by GentleZ on 2017/5/8.
//  Copyright © 2017年 liketry. All rights reserved.
//

#import "YSSCommonTool.h"

#import <ifaddrs.h>
#import <arpa/inet.h>

#import <ImageIO/ImageIO.h>

#import "YSSMsgModel.h"

@implementation YSSCommonTool
+ (void)setScaleAspectFillContentModel:(UIView *)view
{
    view.contentMode = UIViewContentModeScaleAspectFill;
    view.clipsToBounds = YES;
}

+ (int)parseInt:(NSObject *)value
{
    if (value == nil || [value isKindOfClass:[NSNull class]])
    {
        return 0;
    }
    return ((NSNumber *)value).intValue;
}

+ (long long)parseLongLong:(NSObject *)value
{
    if (value == nil || [value isKindOfClass:[NSNull class]])
    {
        return 0;
    }
    return ((NSNumber *)value).longLongValue;
}

+ (NSString *)parseString:(NSObject *)value
{
    if (value == nil || [value isKindOfClass:[NSNull class]] || ![value isKindOfClass:[NSString class]])
    {
        return @"";
    }
    return (NSString *)value;
}

+ (double)parseDouble:(NSObject *)value
{
    if (value == nil || [value isKindOfClass:[NSNull class]])
    {
        return 0.0;
    }
    return ((NSNumber *)value).doubleValue;
}

+ (NSArray *)parseArray:(NSObject *)value
{
    if (value == nil || [value isKindOfClass:[NSNull class]] || ![value isKindOfClass:[NSArray class]])
    {
        return nil;
    }
    return (NSArray *)value;
}

+ (NSDictionary *)parseDictionary:(NSObject *)value
{
    if (value == nil || [value isKindOfClass:[NSNull class]] || ![value isKindOfClass:[NSDictionary class]])
    {
        return nil;
    }
    return (NSDictionary *)value;
}

+ (NSString *)getTodayDate:(YSSDateFormatterType)datetype
{
    NSDate *date = [NSDate date];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    switch (datetype) {
        case YSSDateFormatterTypeLine:
            formatter.dateFormat = @"yyyy-MM-dd";
            break;
        case YSSDateFormatterTypeText:
            formatter.dateFormat = @"yyyy年MM月dd日";
            break;
        case YSSDateFormatterTypeDot:
            formatter.dateFormat = @"yyyy.MM.dd";
            break;
        default:
            formatter.dateFormat = @"yyyy-MM-dd";
            break;
    }
    
    NSString *today = [formatter stringFromDate:date];
    return today;
}

+ (NSTimeInterval)getCountDownStringWithEndTime:(NSString *)endTime {
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd HH:mm"];
    dateFormatter.timeZone = [NSTimeZone systemTimeZone];
    
    YSSLog(@"当前时间:%@", [dateFormatter stringFromDate:[NSDate date]]);
    
    NSDate *endDate = [dateFormatter dateFromString:endTime];
    NSTimeInterval interval = [endDate timeIntervalSinceNow];
    
    return interval;
}

+ (NSString *)changeTimeStr:(NSString *)timeStr fromFormatter:(NSString *)fromFormatter toFormatter:(NSString *)toFormatter
{
    NSDateFormatter *from = [[NSDateFormatter alloc] init];
    from.dateFormat = fromFormatter;
    
    NSDate *date = [from dateFromString:timeStr];
    
    NSDateFormatter *to = [[NSDateFormatter alloc] init];
    to.dateFormat = toFormatter;
    
    return [to stringFromDate:date];
}

+ (void)showHUD
{
    [SVProgressHUD show];
    [SVProgressHUD setDefaultMaskType:SVProgressHUDMaskTypeClear];
}

+ (void)showInfoWithStatus:(NSString*)status
{
    [SVProgressHUD showInfoWithStatus:status];
    [self setHUDDefaultMaskTypeandDismiss:SVProgressHUDMaskTypeClear];
}

+ (void)showSuccessWithStatus:(NSString*)status
{
    [SVProgressHUD showSuccessWithStatus:status];
    [self setHUDDefaultMaskTypeandDismiss:SVProgressHUDMaskTypeClear];
}

+ (void)showErrorWithStatus:(NSString*)status
{
    [SVProgressHUD showErrorWithStatus:status];
    [self setHUDDefaultMaskTypeandDismiss:SVProgressHUDMaskTypeClear];
}

+ (void)setHUDDefaultMaskTypeandDismiss:(SVProgressHUDMaskType)type
{
    [SVProgressHUD setDefaultMaskType:type];
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [SVProgressHUD dismiss];
    });
}

+ (void)alertZoomIn:(UIView *)view andAnimationDuration:(float)duration
{
    CAKeyframeAnimation  *animation;
    animation = [CAKeyframeAnimation animationWithKeyPath:@"transform"];
    animation.duration = duration;
    //animation.delegate = self;
    animation.removedOnCompletion = NO;
    animation.fillMode = kCAFillModeForwards;
    NSMutableArray *values = [NSMutableArray array];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.1, 0.1, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.1, 1.1, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.9, 0.9, 0.9)]];
    //    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.1, 1.1, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.0, 1.0, 1.0)]];
    animation.values = values;
    animation.timingFunction = [CAMediaTimingFunction functionWithName: @"easeInEaseOut"];
    [view.layer addAnimation:animation forKey:nil];
}

+ (void)heartAniZoomIn:(UIView *)view andAnimationDuration:(float)duration
{
    CAKeyframeAnimation  *animation;
    animation = [CAKeyframeAnimation animationWithKeyPath:@"transform"];
    animation.duration = duration;
    //animation.delegate = self;
    animation.removedOnCompletion = NO;
    animation.fillMode = kCAFillModeForwards;
    NSMutableArray *values = [NSMutableArray array];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.3, 1.3, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.9, 0.9, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.3, 1.3, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.0, 1.0, 1.0)]];
    animation.values = values;
    animation.timingFunction = [CAMediaTimingFunction functionWithName: @"easeInEaseOut"];
    [view.layer addAnimation:animation forKey:nil];
}

+ (NSString *)timeWithTimeIntervalString:(NSString *)timeString
{
    // 格式化时间
    NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
    formatter.timeZone = [NSTimeZone systemTimeZone];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"yyyy年MM月dd日 HH:mm"];
    
    // 毫秒值转化为秒
    NSDate* date = [NSDate dateWithTimeIntervalSince1970:[timeString doubleValue]/ 1000.0];
    NSString* dateString = [formatter stringFromDate:date];
    return dateString;
}

+ (NSDictionary *)paramWithBase64:(NSDictionary *)param
{
    NSData *data = [NSJSONSerialization dataWithJSONObject:param options:NSJSONWritingPrettyPrinted error:nil];
    NSString *firstStr = [data base64EncodedStringWithOptions:0];
    NSString *key = @"likebenisontry";
    NSString *totolStr = [NSString stringWithFormat:@"%@%@", key, firstStr];
    NSString *finalStr = [[totolStr dataUsingEncoding:NSUTF8StringEncoding] base64EncodedStringWithOptions:0];
    NSDictionary *params  = @{
                              @"data" : finalStr
                              };
    return params;
}


//获取ip地址
+ (const char *)getIpAddresses
{
    NSString *address = @"error";
    struct ifaddrs *interfaces = NULL;
    struct ifaddrs *temp_addr = NULL;
    int success = 0;
    // retrieve the current interfaces - returns 0 on success
    success = getifaddrs(&interfaces);
    if (success == 0)
    {
        // Loop through linked list of interfaces
        temp_addr = interfaces;
        while(temp_addr != NULL)
        {
            if(temp_addr->ifa_addr->sa_family == AF_INET)
            {
                // Check if interface is en0 which is the wifi connection on the iPhone
                if([[NSString stringWithUTF8String:temp_addr->ifa_name] isEqualToString:@"en0"])
                {
                    // Get NSString from C String
                    address = [NSString stringWithUTF8String:inet_ntoa(((struct sockaddr_in *)temp_addr->ifa_addr)->sin_addr)];
                }
            }
            temp_addr = temp_addr->ifa_next;
        }
    }
    // Free memory
    freeifaddrs(interfaces);
    return [address UTF8String];
}

+ (UIImage*)captureScreen:(UIView *)viewToCapture
{
    UIGraphicsBeginImageContextWithOptions(viewToCapture.bounds.size, NO, 0.0);
    [viewToCapture.layer renderInContext:UIGraphicsGetCurrentContext()];
    UIImage *viewImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return viewImage;
}

//+ (void)getLocation:(AMapLocatingCompletionBlock)completionBlock
//{
//    [AMapServices sharedServices].apiKey = YSSMapAPPKey;
//    
//    AMapLocationManager *locationManager = [[AMapLocationManager alloc] init];
//    // 带逆地理信息的一次定位（返回坐标和地址信息）
//    [locationManager setDesiredAccuracy:kCLLocationAccuracyHundredMeters];
//    //   定位超时时间，最低2s，此处设置为2s
//    locationManager.locationTimeout =2;
//    //   逆地理请求超时时间，最低2s，此处设置为2s
//    locationManager.reGeocodeTimeout = 2;
//    
//    // 带逆地理（返回坐标和地址信息）。将下面代码中的 YES 改成 NO ，则不会返回地址信息。
//    [locationManager requestLocationWithReGeocode:YES completionBlock:^(CLLocation *location, AMapLocationReGeocode *regeocode, NSError *error) {
//        
//        if (error)
//        {
//            YSSLog(@"locError:{%ld - %@};", (long)error.code, error.localizedDescription);
//            
//            if (error.code == AMapLocationErrorLocateFailed)
//            {
//                return;
//            }
//        }
//        
//        if (regeocode)
//        {
//            YSSLog(@"reGeocode:%@", regeocode);
//        }
//        
//        if (completionBlock) {
//            completionBlock(location, regeocode, error);
//        }
//    }];
//}


//+ (CGSize)getImageSizeWithURL:(id)imageURL
//{
//    NSURL* URL = nil;
//    if([imageURL isKindOfClass:[NSURL class]]){
//        URL = imageURL;
//    }
//    if([imageURL isKindOfClass:[NSString class]]){
//        URL = [NSURL URLWithString:imageURL];
//    }
//    if(URL == nil)
//        return CGSizeZero;                  // url不正确返回CGSizeZero
//    
//    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:URL];
//    NSString* pathExtendsion = [URL.pathExtension lowercaseString];
//    
//    CGSize size = CGSizeZero;
//    if([pathExtendsion isEqualToString:@"png"]){
//        size =  [self getPNGImageSizeWithRequest:request];
//    }
//    else if([pathExtendsion isEqual:@"gif"])
//    {
//        size =  [self getGIFImageSizeWithRequest:request];
//    }
//    else{
//        size = [self getJPGImageSizeWithRequest:request];
//    }
//    if(CGSizeEqualToSize(CGSizeZero, size))                    // 如果获取文件头信息失败,发送异步请求请求原图
//    {
//        
//        NSData* data = [NSURLConnection sendSynchronousRequest:[NSURLRequest requestWithURL:URL] returningResponse:nil error:nil];
//        
//        UIImage* image = [UIImage imageWithData:data];
//        if(image)
//        {
//            size = image.size;
//        }
//    }
//    return size;
//}

//  获取PNG图片的大小
+ (CGSize)getPNGImageSizeWithRequest:(NSMutableURLRequest*)request
{
    [request setValue:@"bytes=16-23" forHTTPHeaderField:@"Range"];
    NSData* data = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    if(data.length == 8)
    {
        int w1 = 0, w2 = 0, w3 = 0, w4 = 0;
        [data getBytes:&w1 range:NSMakeRange(0, 1)];
        [data getBytes:&w2 range:NSMakeRange(1, 1)];
        [data getBytes:&w3 range:NSMakeRange(2, 1)];
        [data getBytes:&w4 range:NSMakeRange(3, 1)];
        int w = (w1 << 24) + (w2 << 16) + (w3 << 8) + w4;
        int h1 = 0, h2 = 0, h3 = 0, h4 = 0;
        [data getBytes:&h1 range:NSMakeRange(4, 1)];
        [data getBytes:&h2 range:NSMakeRange(5, 1)];
        [data getBytes:&h3 range:NSMakeRange(6, 1)];
        [data getBytes:&h4 range:NSMakeRange(7, 1)];
        int h = (h1 << 24) + (h2 << 16) + (h3 << 8) + h4;
        return CGSizeMake(w, h);
    }
    return CGSizeZero;
}

//  获取gif图片的大小
+ (CGSize)getGIFImageSizeWithRequest:(NSMutableURLRequest*)request
{
    [request setValue:@"bytes=6-9" forHTTPHeaderField:@"Range"];
    NSData* data = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    if(data.length == 4)
    {
        short w1 = 0, w2 = 0;
        [data getBytes:&w1 range:NSMakeRange(0, 1)];
        [data getBytes:&w2 range:NSMakeRange(1, 1)];
        short w = w1 + (w2 << 8);
        short h1 = 0, h2 = 0;
        [data getBytes:&h1 range:NSMakeRange(2, 1)];
        [data getBytes:&h2 range:NSMakeRange(3, 1)];
        short h = h1 + (h2 << 8);
        return CGSizeMake(w, h);
    }
    return CGSizeZero;
}

//  获取jpg图片的大小
+ (CGSize)getJPGImageSizeWithRequest:(NSMutableURLRequest*)request
{
    [request setValue:@"bytes=0-209" forHTTPHeaderField:@"Range"];
    NSData* data = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    
    if ([data length] <= 0x58) {
        return CGSizeZero;
    }
    
    if ([data length] < 210) {// 肯定只有一个DQT字段
        short w1 = 0, w2 = 0;
        [data getBytes:&w1 range:NSMakeRange(0x60, 0x1)];
        [data getBytes:&w2 range:NSMakeRange(0x61, 0x1)];
        short w = (w1 << 8) + w2;
        short h1 = 0, h2 = 0;
        [data getBytes:&h1 range:NSMakeRange(0x5e, 0x1)];
        [data getBytes:&h2 range:NSMakeRange(0x5f, 0x1)];
        short h = (h1 << 8) + h2;
        return CGSizeMake(w, h);
    } else {
        short word = 0x0;
        [data getBytes:&word range:NSMakeRange(0x15, 0x1)];
        if (word == 0xdb) {
            [data getBytes:&word range:NSMakeRange(0x5a, 0x1)];
            if (word == 0xdb) {// 两个DQT字段
                short w1 = 0, w2 = 0;
                [data getBytes:&w1 range:NSMakeRange(0xa5, 0x1)];
                [data getBytes:&w2 range:NSMakeRange(0xa6, 0x1)];
                short w = (w1 << 8) + w2;
                short h1 = 0, h2 = 0;
                [data getBytes:&h1 range:NSMakeRange(0xa3, 0x1)];
                [data getBytes:&h2 range:NSMakeRange(0xa4, 0x1)];
                short h = (h1 << 8) + h2;
                return CGSizeMake(w, h);
            } else {// 一个DQT字段
                short w1 = 0, w2 = 0;
                [data getBytes:&w1 range:NSMakeRange(0x60, 0x1)];
                [data getBytes:&w2 range:NSMakeRange(0x61, 0x1)];
                short w = (w1 << 8) + w2;
                short h1 = 0, h2 = 0;
                [data getBytes:&h1 range:NSMakeRange(0x5e, 0x1)];
                [data getBytes:&h2 range:NSMakeRange(0x5f, 0x1)];
                short h = (h1 << 8) + h2;
                return CGSizeMake(w, h);
            }
        } else {
            return CGSizeZero;
        }
    }
}

+ (CGSize)getImageSizeWithURL:(id)URL{
    NSURL * url = nil;
    if ([URL isKindOfClass:[NSURL class]]) {
        url = URL;
    }
    if ([URL isKindOfClass:[NSString class]]) {
        url = [NSURL URLWithString:URL];
    }
    if (!URL) {
        return CGSizeZero;
    }
    CGImageSourceRef imageSourceRef = CGImageSourceCreateWithURL((CFURLRef)url, NULL);
    CGFloat width = 0, height = 0;
    if (imageSourceRef) {
        CFDictionaryRef imageProperties = CGImageSourceCopyPropertiesAtIndex(imageSourceRef, 0, NULL);
        if (imageProperties != NULL) {
            CFNumberRef widthNumberRef = CFDictionaryGetValue(imageProperties, kCGImagePropertyPixelWidth);
            if (widthNumberRef != NULL) {
                CFNumberGetValue(widthNumberRef, kCFNumberFloat64Type, &width);
            }
            CFNumberRef heightNumberRef = CFDictionaryGetValue(imageProperties, kCGImagePropertyPixelHeight);
            if (heightNumberRef != NULL) {
                CFNumberGetValue(heightNumberRef, kCFNumberFloat64Type, &height);
            }
            CFRelease(imageProperties);
        }
        CFRelease(imageSourceRef);
    }
    return CGSizeMake(width, height);
}


+ (BOOL)isFirstLauch{
    //获取当前版本号
    NSDictionary *infoDic = [[NSBundle mainBundle] infoDictionary];
    NSString *currentAppVersion = infoDic[@"CFBundleShortVersionString"];
    //获取上次启动应用保存的appVersion
    NSString *version = [[NSUserDefaults standardUserDefaults] objectForKey:YSSAppVersion];
    //版本升级或首次登录
    if (version == nil || ![version isEqualToString:currentAppVersion]) {
        [[NSUserDefaults standardUserDefaults] setObject:currentAppVersion forKey:YSSAppVersion];
        [[NSUserDefaults standardUserDefaults] synchronize];
        return YES;
    }else{
        return NO;
    }
}

+ (id)processDictionaryIsNSNull:(id)obj{
    const NSString *blank = @"";
    
    if ([obj isKindOfClass:[NSDictionary class]]) {
        NSMutableDictionary *dt = [(NSMutableDictionary*)obj mutableCopy];
        for(NSString *key in [dt allKeys]) {
            id object = [dt objectForKey:key];
            if([object isKindOfClass:[NSNull class]]) {
                [dt setObject:blank
                       forKey:key];
            }
            else if ([object isKindOfClass:[NSString class]]){
                NSString *strobj = (NSString*)object;
                if ([strobj isEqualToString:@"<null>"]) {
                    [dt setObject:blank
                           forKey:key];
                }
            }
            else if ([object isKindOfClass:[NSArray class]]){
                NSArray *da = (NSArray*)object;
                da = [self processDictionaryIsNSNull:da];
                [dt setObject:da
                       forKey:key];
            }
            else if ([object isKindOfClass:[NSDictionary class]]){
                NSDictionary *ddc = (NSDictionary*)object;
                ddc = [self processDictionaryIsNSNull:object];
                [dt setObject:ddc forKey:key];
            }
        }
        return [dt copy];
    }
    else if ([obj isKindOfClass:[NSArray class]]){
        NSMutableArray *da = [(NSMutableArray*)obj mutableCopy];
        for (int i=0; i<[da count]; i++) {
            NSDictionary *dc = [obj objectAtIndex:i];
            dc = [self processDictionaryIsNSNull:dc];
            [da replaceObjectAtIndex:i withObject:dc];
        }
        return [da copy];
    }
    else{
        return obj;
    }
}

+ (NSString *)returnBankName:(NSString*)idCard
{
    
    if(idCard == nil || idCard.length < 16 || idCard.length > 19){
        YSSLog(@"卡号不合法");
        return @"";
        
    }
    NSString *plistPath = [[NSBundle mainBundle] pathForResource:@"bank" ofType:@"plist"];
    NSDictionary* resultDic = [NSDictionary dictionaryWithContentsOfFile:plistPath];
    NSArray *bankBin = resultDic.allKeys;
    
    //6位Bin号
    NSString* cardbin_6 = [idCard substringWithRange:NSMakeRange(0, 6)];
    //8位Bin号
    NSString* cardbin_8 = [idCard substringWithRange:NSMakeRange(0, 8)];
    
    if ([bankBin containsObject:cardbin_6]) {
        return [resultDic objectForKey:cardbin_6];
    }else if ([bankBin containsObject:cardbin_8]){
        return [resultDic objectForKey:cardbin_8];
    }else{
        YSSLog(@"plist文件中不存在请自行添加对应卡种");
        return @"";
    }
    return @"";
    
}

+ (UIImage *)placeholderImageWithSize:(CGSize)size
{
    
    // 占位图的背景色
    UIColor *backgroundColor = [UIColor whiteColor];
    // 中间LOGO图片
    UIImage *image = [UIImage imageNamed:@"Globle_PicDefault"];
    // 根据占位图需要的尺寸 计算 中间LOGO的宽高
    CGFloat logoWH = (size.width > size.height ? size.height : size.width) * 0.5;
    CGSize logoSize = CGSizeMake(logoWH, logoWH);
    // 打开上下文
    UIGraphicsBeginImageContextWithOptions(size,0, [UIScreen mainScreen].scale);
    // 绘图
    [backgroundColor set];
    UIRectFill(CGRectMake(0,0, size.width, size.height));
    CGFloat imageX = (size.width / 2) - (logoSize.width / 2);
    CGFloat imageY = (size.height / 2) - (logoSize.height / 2);
    [image drawInRect:CGRectMake(imageX, imageY, logoSize.width, logoSize.height)];
    UIImage *resImage =UIGraphicsGetImageFromCurrentImageContext();
    // 关闭上下文
    UIGraphicsEndImageContext();
    
    return resImage;
    
}

+ (void)getMerchanInfoDict
{
    NSDictionary *dict = [[NSUserDefaults standardUserDefaults] objectForKey:@"merchanInfoDict"];
    YSSLog(@"商户信息 ==  %@", dict);
    YSSMerChanModel *model = [YSSMerChanModel mj_objectWithKeyValues:dict];
    model.infoDict = [dict mutableCopy];
    [YSSMerChanModel sharedInstenceWithModel:model isReset:YES];
    
    //    [self.headerView configBalanceWithData:dict];
    
    YSSLog(@"id ====== %@", [YSSMerChanModel sharedInstence].id);
    
    NSDictionary *userDict = [[NSUserDefaults standardUserDefaults] objectForKey:@"userInfoDict"];
    YSSUserModel *userModel = [YSSUserModel mj_objectWithKeyValues:userDict];
    [YSSUserModel sharedInstenceWithModel:userModel isReset:YES];
}

+ (void)getMessageIsPushCompletionHandel:(void(^)(void))complectionHandel
{
    YSSLog(@"沙盒路径 : %@", [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) lastObject]);
    JQFMDB *db = [JQFMDB shareDatabase:@"Message.sqlite" path:[NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) lastObject]];
    NSString *tableName = TableName([YSSUserModel sharedInstence].loginName);
    if (![db jq_isExistTable:tableName]) {
        [db jq_createTable:tableName dicOrModel:[YSSMsgModel class]];
    }
    
    NSDictionary *param = @{
                            @"merchantId" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].id]
                            };
    [YSSHttpTool post:GET_MSGLIST params:param isJsonSerializer:NO success:^(id json) {
        NSArray *dictArr = json[@"result"];
        
        for (NSDictionary *dict in dictArr) {
            YSSMsgModel *model = [YSSMsgModel mj_objectWithKeyValues:dict];
            
            
            NSArray *arr = [db jq_lookupTable:tableName dicOrModel:[YSSMsgModel class] whereFormat:@"where id = '%@'", model.id];
            if (arr.count == 0) {
                //新消息插入数据库
                model.isSelect = 0;
                model.isRead = 0;
                model.isDelete = 0;
                
                [db jq_inDatabase:^{
                    [db jq_insertTable:tableName dicOrModel:model];
                }];
            }else{
                //旧消息更新数据
                YSSMsgModel *tempModel = arr[0];
                model.isSelect = tempModel.isSelect;
                model.isRead = tempModel.isRead;
                model.isDelete = tempModel.isDelete;
                [db jq_inDatabase:^{
                    [db jq_updateTable:tableName dicOrModel:model whereFormat:@"where id = '%@'", model.id];
                }];
            }
        }
        
        if (complectionHandel) {
            complectionHandel();
        }
        
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:@"请求失败，新消息加载失败"];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误，新消息加载失败"];
    }];
}

+ (void)getMerchantWithUserId:(NSString *)userId
{
    NSDictionary *param = @{
                            @"userId" : userId
                            };
    [YSSHttpTool post:GET_MERCHANT params:param isJsonSerializer:NO success:^(id json) {
        //审核通过1 提交2  审核不通过3  停止合作4  违规5  违法6
        
        YSSMerChanModel *model = [YSSMerChanModel mj_objectWithKeyValues:json[@"result"]];
        [YSSMerChanModel sharedInstenceWithModel:model isReset:YES];
        
        NSDictionary *info = json[@"result"];
        YSSLog(@"%@", [YSSCommonTool processDictionaryIsNSNull:info]);
        [[NSUserDefaults standardUserDefaults] setObject:[YSSCommonTool processDictionaryIsNSNull:info] forKey:@"merchanInfoDict"];
        
        [YSSMerChanModel sharedInstence].infoDict = [[YSSCommonTool processDictionaryIsNSNull:info] mutableCopy];
        
    } failure:^(NSError *error) {
        
    }];
}


@end
