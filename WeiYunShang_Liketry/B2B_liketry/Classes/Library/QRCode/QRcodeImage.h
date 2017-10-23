//
//  QRcodeImage.h
//  ShortURL
//
//  Created by zqadmin on 16/3/29.
//  Copyright © 2016年 JUN. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface QRcodeImage : NSObject

//小图
+ (UIImage *)imageSmallFormURL:(NSString *)url;

//大图
+ (UIImage *)imageBigFormURL:(NSString *)url;

@end
