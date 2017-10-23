//
//  YSSHttpTool.h
//  BJCenterCommonwealAPP
//
//  Created by GentleZ on 2017/5/8.
//  Copyright © 2017年 liketry. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YSSHttpTool : NSObject

#pragma mark - GET
/** GET请求 */
+ (void)get:(NSString *)url params:(NSDictionary *)params success:(void (^)(id json)) success failure:(void(^)(NSError *error))failure;

/** GET请求 带加密参数 */
+ (void)get:(NSString *)url params:(NSDictionary *)params isBase64:(BOOL)isBase64 success:(void (^)(id json)) success failure:(void(^)(NSError *error))failure;

/** GET请求 带error回调 */
+ (void)get:(NSString *)url params:(NSDictionary *)params isBase64:(BOOL)isBase64 success:(void (^)(id json)) success responseDataError:(void (^)(id json))requestError failure:(void(^)(NSError *error))failure;


#pragma mark - POST

/** POST 请求 */
+ (void)post:(NSString *)url params:(NSDictionary *)params isJsonSerializer:(BOOL)isJsonSerializer success:(void(^)(id json))success failure:(void(^)(NSError *error ))failure;

/** POST 请求 带error回调 */
+ (void)post:(NSString *)url params:(NSDictionary *)params isJsonSerializer:(BOOL)isJsonSerializer success:(void(^) (id json))success dataError:(void(^)(id json))dataError failure:(void(^)(NSError *error))failure;


/** 上传图片 */
+ (void)postImage:(NSString *)url params:(NSDictionary *)params image:(UIImage *)image fileName:(NSString *)fileName success:(void (^)(id json)) success failure:(void(^)(NSError *error))failure;

#pragma mark - PUT
/** PUT 请求 */
+(void)put:(NSString *)url params:(NSDictionary *)params success:(void(^)(id json))success failure:(void(^)(NSError *error ))failure;

#pragma mark - DEL
/** DEL 请求 */
+(void)del:(NSString *)url params:(NSDictionary *)params success:(void(^)(id json))success failure:(void(^)(NSError *error ))failure;


#pragma mark - 多图上传
/**
 *  上传带图片的内容，允许多张图片上传（URL）POST
 *
 *  @param url                 网络请求地址
 *  @param imagesDict          图片字典
 *  @param parameters          其他参数字典
 *  @param ratio               图片的压缩比例（0.0~1.0之间）
 *  @param succeedBlock        成功的回调
 *  @param failedBlock         失败的回调
 *  @param uploadProgressBlock 上传进度的回调
 */
+(void)startMultiPartUploadTaskWithURL:(NSString *)url
                            imagesDict:(NSDictionary *)imagesDict
                        parametersDict:(NSDictionary *)parameters
                      compressionRatio:(float)ratio
                          succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                           failedBlock:(void(^)(id operation, NSError *error))failedBlock
                   uploadProgressBlock:(void(^)(float uploadPercent,long long totalBytesWritten,long long totalBytesExpectedToWrite))uploadProgressBlock;

/** 上传多张图 && images key is pics[] */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                        imagesDataArray:(NSArray *)imagesData
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void(^)(id operation, NSError *error))failedBlock;

/** 上传多张图 && images key != pics[] */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                        imagesDataArray:(NSArray *)imagesData
                     parameterImagesKey:(NSString *)parameterImageKey
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void(^)(id operation, NSError *error))failedBlock;



/** 带进度条 */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                        imagesDataArray:(NSArray *)imagesData
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void(^)(id operation, NSError *error))failedBlock
                    uploadProgressBlock:(void (^)(float uploadPercent,long long totalBytesWritten,long long totalBytesExpectedToWrite))uploadProgressBlock;

+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                        imagesDataArray:(NSArray *)imagesData
                     parameterImagesKey:(NSString *)parameterImageKey
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void(^)(id operation, NSError *error))failedBlock
                    uploadProgressBlock:(void (^)(float uploadPercent,long long totalBytesWritten,long long totalBytesExpectedToWrite))uploadProgressBlock;

+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                             imagesDict:(NSDictionary *)imagesDict
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void (^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void (^)(id operation, NSError *error))failedBlock
                    uploadProgressBlock:(void (^)(float uploadPercent,long long totalBytesWritten,long long totalBytesExpectedToWrite))uploadProgressBlock;

/** 上传多个图片数组 */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                             imagesDict:(NSDictionary *)imagesDict
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void (^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void (^)(id operation, NSError *error))failedBlock;

/**
 *  判断是否正确返回码 200
 *
 *  @param json 返回的json
 *
 */
+ (BOOL)isRepsponseSucceedCode:(NSDictionary *)json;

/** 获取错误提示 */
+ (NSString *)getErrorMessage:(NSDictionary *)json;

/** 返回 json 中的 data, 单条数据 */
+ (NSDictionary *)getResponseJsonData:(NSDictionary *)json;

/** 返回 json 中的 data, 一组数据 */
+ (NSArray *)getResponseJsonDataArray:(NSDictionary *)json;

@end
