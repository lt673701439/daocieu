//
//  YSSHttpTool.m
//  BJCenterCommonwealAPP
//
//  Created by GentleZ on 2017/5/8.
//  Copyright © 2017年 liketry. All rights reserved.
//

#import "YSSHttpTool.h"
#import "AppDelegate.h"

#import "NSDictionary+Property.h"

static const float timeoutInterval = 20.0f;

@implementation YSSHttpTool
#pragma mark - GET
/** GET 请求 */
+ (void)get:(NSString *)url params:(NSDictionary *)params success:(void (^)(id json))success failure:(void(^)(NSError *error))failure
{
    [self get:url params:params isBase64:NO success:success responseDataError:nil failure:failure];
}

/** GET请求  带加密参数 */
+ (void)get:(NSString *)url params:(NSDictionary *)params isBase64:(BOOL)isBase64 success:(void (^)(id json)) success failure:(void(^)(NSError *error))failure
{
    [self get:url params:params isBase64:isBase64 success:success responseDataError:nil failure:failure];
}

/** GET请求 带error回调 */
+ (void)get:(NSString *)url params:(NSDictionary *)params isBase64:(BOOL)isBase64 success:(void (^)(id json)) success responseDataError:(void (^)(id json))requestError failure:(void(^)(NSError *error))failure
{
    NSDictionary *newParam = isBase64 ? [YSSCommonTool paramWithBase64:params] : params;
    if (params) {
        YSSLog(@"\n请求URL:【%@%@】\n参数:%@", url, [newParam createHttpTest], params);
    }else{
        YSSLog(@"\n请求URL:【%@】", url);
    }
    
    AFHTTPSessionManager *manager = [self getHttpSessionIsTimeOut:YES];
    
//JWT校验
//    NSString *token = [NSString stringWithFormat:@"Bearer %@", [[NSUserDefaults standardUserDefaults] objectForKey:@"token"]];
//    YSSLog(@"token ===  %@", token);
//    if (((NSString *)[[NSUserDefaults standardUserDefaults] objectForKey:@"token"]).length == 0) {
//        YSSLog(@"=======登录过期，请重新登录======");
//    }
//    [manager.requestSerializer setValue:token forHTTPHeaderField:@"authorization"];
    
    
    [manager GET:url parameters:newParam progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        
        YSSLog(@"\n返回数据:%@", responseObject);
        
        if ([self isRepsponseSucceedCode:responseObject]) {
            //success
            if (success)
            {
                success (responseObject);
            }
        }else{
            //error
            if (requestError) {
                requestError(responseObject);
            }
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        [self requestFailed:error];
        if (failure)
        {
            failure(error);
        }
    }];
}

/** 带进度条的get请求 */
+(void)get:(NSString *)url params:(NSDictionary *)params progress:(void (^)(NSProgress *  downloadProgress))progress  success:(void (^)(id json)) success failure:(void(^)(NSError *error))failure
{
    if (params) {
        YSSLog(@"请求URL:【%@%@】\n参数:%@", url, [params createHttpTest], params);
    }else{
        YSSLog(@"请求URL:%@", url);
    }
    
    AFHTTPSessionManager *session = [self getHttpSessionIsTimeOut:YES];
    
    [session GET:url parameters:[self appendParams:params] progress:^(NSProgress * _Nonnull downloadProgress) {
        if (progress) {
            progress(downloadProgress);
        }
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        YSSLog(@"返回数据:%@", responseObject);
        
        if (success) {
            success (responseObject);
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        [self requestFailed:error];
        
        if (failure)
        {
            failure(error);
        }
        
    }];
}

#pragma mark - POST
/** POST 请求 */
+ (void)post:(NSString *)url params:(NSDictionary *)params isJsonSerializer:(BOOL)isJsonSerializer success:(void(^)(id json))success failure:(void(^)(NSError *error ))failure
{
    [self post:url params:params isJsonSerializer:isJsonSerializer success:success dataError:nil failure:failure];
}

/** POST 带error回调 */
+ (void)post:(NSString *)url params:(NSDictionary *)params isJsonSerializer:(BOOL)isJsonSerializer success:(void(^) (id json))success dataError:(void(^) (id json))dataError failure:(void(^)(NSError *error))failure
{
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
    
    if (isJsonSerializer) {
        manager.requestSerializer = [AFJSONRequestSerializer serializer];
        manager.responseSerializer = [AFJSONResponseSerializer serializer];
    }else{
        manager.requestSerializer = [AFHTTPRequestSerializer serializer];
        manager.responseSerializer = [AFJSONResponseSerializer serializer];
    }
    
    
    //JWT校验
    //    NSString *token = [NSString stringWithFormat:@"Bearer %@", [[NSUserDefaults standardUserDefaults] objectForKey:@"token"]];
    //    YSSLog(@"token ===  %@", token);
    //    if (((NSString *)[[NSUserDefaults standardUserDefaults] objectForKey:@"token"]).length == 0) {
    //        YSSLog(@"=======登录过期，请重新登录======");
    //    }
    //    [manager.requestSerializer setValue:token forHTTPHeaderField:@"authorization"];
    
    
    [manager POST:url parameters:params progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        if (params) {
            YSSLog(@"请求URL:【%@%@】 \n参数:%@", url, [params createHttpTest], params);
        }else{
            YSSLog(@"请求URL:%@", url);
        }
        YSSLog(@"返回数据:%@", responseObject);
        
        if ([self isRepsponseSucceedCode:responseObject]) {
            
            if (success)
            {
                success (responseObject);
            }
            
        }else{
            
            if (dataError) {
                dataError(responseObject);
            }
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (params) {
            YSSLog(@"请求URL:【%@%@】 \n参数:%@", url, [params createHttpTest], params);
        }else{
            YSSLog(@"请求URL:%@", url);
        }
        [self requestFailed:error];
        
        if (error) {
            failure(error);
        }
    }];
}

+ (void)postImage:(NSString *)url params:(NSDictionary *)params image:(UIImage *)image fileName:(NSString *)fileName success:(void (^)(id json)) success failure:(void(^)(NSError *error))failure
{
    NSData *data = UIImageJPEGRepresentation(image, 0.3);
    
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    
//    NSString *token = [NSString stringWithFormat:@"Bearer %@", [[NSUserDefaults standardUserDefaults] objectForKey:@"token"]];
//    [manager.requestSerializer setValue:token forHTTPHeaderField:@"authorization"];
    
    [manager POST:url parameters:params constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        [formData appendPartWithFileData:data name:@"img" fileName:fileName mimeType:@"image/jpeg"];
    } progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        YSSLog(@"返回数据:%@", responseObject);
        
        if (success)
        {
            success (responseObject);
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        [self requestFailed:error];
        
        if (error) {
            failure(error);
        }
    }];
}



#pragma mark - PUT
/**
 *  发送put 请求
 *
 *  @param url     请求地址
 *  @param params  请求参数
 *  @param success 成功
 *  @param failure 失败
 */
+ (void)put:(NSString *)url params:(NSDictionary *)params success:(void(^)(id json))success failure:(void(^)(NSError *error ))failure {
    if (params) {
        YSSLog(@"请求URL:%@%@", url, [params createHttpTest]);
    }else{
        YSSLog(@"请求URL:%@", url);
    }
    
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    manager.requestSerializer = [AFJSONRequestSerializer serializer];
    manager.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [manager PUT:url parameters:[self appendParams:params] success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        YSSLog(@"返回数据:%@", responseObject);
        
        if (success) {
            success (responseObject);
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        [self requestFailed:error];
        
        if (error) {
            failure(error);
        }
    }];
}

/**
 *  发送del 请求
 *
 *  @param url     请求地址
 *  @param params  请求参数
 *  @param success 成功
 *  @param failure 失败
 */
+ (void)del:(NSString *)url params:(NSDictionary *)params success:(void(^)(id json))success failure:(void(^)(NSError *error ))failure {
    if (params) {
        YSSLog(@"请求URL:%@%@", url, [params createHttpTest]);
    }else{
        YSSLog(@"请求URL:%@", url);
    }
    
    AFHTTPSessionManager *manager = [self getHttpSessionIsTimeOut:NO];
    [manager DELETE:url parameters:[self appendParams:params] success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        YSSLog(@"返回数据:%@", responseObject);
        if (success) {
            success (responseObject);
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        
        [self requestFailed:error];
        
        if (error) {
            
            failure(error);
            
        }
    }];
}

/** 上传多张图 && images key is pics[] */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                        imagesDataArray:(NSArray *)imagesData
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void(^)(id operation, NSError *error))failedBlock
{
    [self startMultiPartUploadTaskWithURL:url
                          imagesDataArray:imagesData
                       parameterImagesKey:@"pics[]"
                           parametersDict:parameters
                             succeedBlock:succeedBlock
                              failedBlock:failedBlock];
}

/** 上传多张图 && images key != pics[] */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                        imagesDataArray:(NSArray *)imagesData
                     parameterImagesKey:(NSString *)parameterImageKey
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void(^)(id operation, NSError *error))failedBlock
{
    [self startMultiPartUploadTaskWithURL:url
                               imagesDict:@{parameterImageKey : imagesData}
                           parametersDict:parameters
                             succeedBlock:succeedBlock
                              failedBlock:failedBlock];
}

/** 多个图片数组 */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                             imagesDict:(NSDictionary *)imagesDict
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void (^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void (^)(id operation, NSError *error))failedBlock
{
    [self startMultiPartUploadTaskWithURL:url
                               imagesDict:imagesDict
                           parametersDict:parameters
                         compressionRatio:0
                             succeedBlock:succeedBlock
                              failedBlock:failedBlock
                      uploadProgressBlock:nil];
}

/** 上传多张图 && images key is pics[] && progress */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                        imagesDataArray:(NSArray *)imagesData
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void(^)(id operation, NSError *error))failedBlock
                    uploadProgressBlock:(void (^)(float uploadPercent,long long totalBytesWritten,long long totalBytesExpectedToWrite))uploadProgressBlock
{
    [self startMultiPartUploadTaskWithURL:url
                          imagesDataArray:imagesData
                       parameterImagesKey:@"pics[]"
                           parametersDict:parameters
                             succeedBlock:succeedBlock
                              failedBlock:failedBlock
                      uploadProgressBlock:uploadProgressBlock];
}

/** 上传多张图 && images key != pics[] && progress */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                        imagesDataArray:(NSArray *)imagesData
                     parameterImagesKey:(NSString *)parameterImageKey
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void(^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void(^)(id operation, NSError *error))failedBlock
                    uploadProgressBlock:(void (^)(float uploadPercent,long long totalBytesWritten,long long totalBytesExpectedToWrite))uploadProgressBlock
{
    [self startMultiPartUploadTaskWithURL:url
                               imagesDict:@{parameterImageKey : imagesData}
                           parametersDict:parameters
                             succeedBlock:succeedBlock
                              failedBlock:failedBlock
                      uploadProgressBlock:uploadProgressBlock];
}

/** 多个图片数组 && progress  */
+ (void)startMultiPartUploadTaskWithURL:(NSString *)url
                             imagesDict:(NSDictionary *)imagesDict
                         parametersDict:(NSDictionary *)parameters
                           succeedBlock:(void (^)(id operation, id responseObject))succeedBlock
                            failedBlock:(void (^)(id operation, NSError *error))failedBlock
                    uploadProgressBlock:(void (^)(float uploadPercent,long long totalBytesWritten,long long totalBytesExpectedToWrite))uploadProgressBlock
{
    [self startMultiPartUploadTaskWithURL:url
                               imagesDict:imagesDict
                           parametersDict:parameters
                         compressionRatio:0
                             succeedBlock:succeedBlock
                              failedBlock:failedBlock
                      uploadProgressBlock:uploadProgressBlock];
}

/** 带进度的上传图片 */
+(void)startMultiPartUploadTaskWithURL:(NSString *)url
                            imagesDict:(NSDictionary *)imagesDict
                        parametersDict:(NSDictionary *)parameters
                      compressionRatio:(float)ratio
                          succeedBlock:(void (^)(id operation, id responseObject))succeedBlock
                           failedBlock:(void (^)(id operation, NSError *error))failedBlock
                   uploadProgressBlock:(void (^)(float uploadPercent,long long totalBytesWritten,long long totalBytesExpectedToWrite))uploadProgressBlock
{
    
    //     if (images.count == 0) {
    //          NSLog(@"上传内容没有包含图片");
    //          return;
    //     }
    //    for (int i = 0; i < images.count; i++)
    //    {
    //        if (![images[i] isKindOfClass:[UIImage class]])
    //        {
    //            SFLog(@"images中第%d个元素不是UIImage对象",i+1);
    //            return;
    //        }
    //    }
//    SFLog(@"---url:%@%@", url, [parameters createHttpTest]);
    
    AFHTTPSessionManager *session = [self getHttpSessionIsTimeOut:NO];
    
    [session POST:url parameters:[self appendParams:parameters] constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        //根据当前系统时间生成图片名称
        //        NSDate *date = [NSDate date];
        //        NSDateFormatter *formatter = [[NSDateFormatter alloc]init];
        //        [formatter setDateFormat:@"yyyy年MM月dd日"];
        //        NSString *dateString = [formatter stringFromDate:date];
        
        int i = 0;
        for (NSString *imageKey in imagesDict.allKeys)
        {
            i = 0;
            for (NSData *imageData in imagesDict[imageKey])
            {
                NSString *fileName = [NSString stringWithFormat:@"%d.png",i];
                //            NSData *imageData;
                //            if (UIImagePNGRepresentation(image))
                //            {
                ////                imageData =  [SFImageTool imageData:image];
                ////                SFLog(@"%ld kb",imageData.length/1024);
                //            }
                [formData appendPartWithFileData:imageData name:imageKey fileName:fileName mimeType:@"image/jpeg"];
                i ++;
            }
        }
        
    } progress:^(NSProgress * _Nonnull uploadProgress) {
        if (uploadProgressBlock) {
            uploadProgressBlock(1.0,uploadProgress.completedUnitCount,uploadProgress.totalUnitCount);
        }
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        
        if (succeedBlock) {
            succeedBlock(session,responseObject);
        }

    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        [self requestFailed:error];
        
        if (failedBlock)
        {
            failedBlock(session,error);
        }
        
    }];
}

+ (AFHTTPSessionManager *)getHttpSessionIsTimeOut:(BOOL)timeOut
{
    AFHTTPSessionManager *session = [AFHTTPSessionManager manager];
    session.requestSerializer = [AFHTTPRequestSerializer serializer];
    session.responseSerializer = [AFJSONResponseSerializer serializer];
    
    if (timeOut)
    {
        session.requestSerializer.timeoutInterval = timeoutInterval;
    }
    
    NSSet *oldSet = [session.responseSerializer acceptableContentTypes];
    NSMutableSet *newSet = [NSMutableSet setWithSet:oldSet];
    [newSet addObjectsFromArray:@[@"application/json", @"text/json", @"text/javascript", @"text/html"]];
    [session.responseSerializer setAcceptableContentTypes:[NSSet setWithSet:newSet]];
    
    [self requestAddHeaderWithSession:session];
    return session;
}

+ (void)requestAddHeaderWithSession:(AFHTTPSessionManager *)session
{
//    NSDictionary *dict = [SFCommonTool getNetworkHeadClientInfo];
//    for (NSString *key in dict.allKeys)
//    {
//        [session.requestSerializer setValue:dict[key] forHTTPHeaderField:key];
//    }
}


+ (void)requestFailed:(NSError *)error
{
    YSSLog(@"错误信息: errorCode %ld, des:%@",(long)error.code, error.debugDescription);
}

+ (BOOL)isLeaveCode:(NSDictionary *)json
{
    
    int code = [YSSCommonTool parseInt:json[@"code"]];
    if (code == 401 || code == 403 || code == 402)
    {
        return YES;
    }
    return NO;
}

+ (BOOL)isRepsponseSucceedCode:(NSDictionary *)json
{
    if ([json isKindOfClass:[NSNull class]]) {
        return NO;
    }
    
    if (![json.allKeys containsObject:@"code"])
    {
        return NO;
    }
    
    if ([json[@"code"] isKindOfClass:[NSNumber class]]) {
        return [json[@"code"] integerValue] == 0;
    }
    
    return ([json[@"code"] isEqualToString:@"success"]);
}

+ (NSString *)getErrorMessage:(NSDictionary *)json
{
    NSString *errorMsg = [YSSCommonTool parseString:json[@"error"]];
    NSString *msgError = [YSSCommonTool parseString:json[@"msg"]];
    errorMsg = errorMsg.length > 0 ? errorMsg : msgError;
    return [errorMsg isEqualToString:@""] ? @"数据异常" : errorMsg;
}

/** 返回 json 中的 data, 单条数据 */
+ (NSDictionary *)getResponseJsonData:(NSDictionary *)json
{
    return [YSSCommonTool parseDictionary:json[@"result"]];
}

/** 返回 json 中的 data, 一组数据 */
+ (NSArray *)getResponseJsonDataArray:(NSDictionary *)json
{
    return [YSSCommonTool parseArray:json[@"result"]];
}

+ (NSDictionary *)appendParams:(NSDictionary *)oldParams
{
    NSMutableDictionary *dict = [NSMutableDictionary dictionaryWithDictionary:oldParams];
    return dict;
}

@end
