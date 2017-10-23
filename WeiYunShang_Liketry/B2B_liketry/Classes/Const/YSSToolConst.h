//
//  YSSToolConst.h
//  BJCenterCommonwealAPP
//
//  Created by GentleZ on 2017/5/8.
//  Copyright © 2017年 liketry. All rights reserved.
//

#ifndef YSSToolConst_h
#define YSSToolConst_h

// weak self
#ifndef weakify
#if DEBUG
#if __has_feature(objc_arc)
#define weakify(object) autoreleasepool{} __weak __typeof__(object) weak##_##object = object;
#else
#define weakify(object) autoreleasepool{} __block __typeof__(object) block##_##object = object;
#endif
#else
#if __has_feature(objc_arc)
#define weakify(object) try{} @finally{} {} __weak __typeof__(object) weak##_##object = object;
#else
#define weakify(object) try{} @finally{} {} __block __typeof__(object) block##_##object = object;
#endif
#endif
#endif

// strong self
#ifndef strongify
#if DEBUG
#if __has_feature(objc_arc)
#define strongify(object) autoreleasepool{} __typeof__(object) object = weak##_##object;
#else
#define strongify(object) autoreleasepool{} __typeof__(object) object = block##_##object;
#endif
#else
#if __has_feature(objc_arc)
#define strongify(object) try{} @finally{} __typeof__(object) object = weak##_##object;
#else
#define strongify(object) try{} @finally{} __typeof__(object) object = block##_##object;
#endif
#endif
#endif


#define WS(weakSelf)  __weak __typeof(&*self)weakSelf = self;

#pragma mark - GCD Block
#define GCDBlock(block) dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), block)
#define GCDMainBlock(block) dispatch_async(dispatch_get_main_queue(),block)
#define CGDMainBack GCDMainBlock(^(){})

#pragma mark - 图片，返回UIImage
#define LoadNameImage(name) [UIImage imageNamed:name]
#define LoadDiskImage(name) [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:name ofType:nil]]

#pragma mark - 去警告
#define SuppressPerformSelectorLeakWarning(Stuff) \
do { \
_Pragma("clang diagnostic push") \
_Pragma("clang diagnostic ignored \"-Warc-performSelector-leaks\"") \
Stuff; \
_Pragma("clang diagnostic pop") \
} while (0)

#ifdef DEBUG // 调试状态, 打开LOG功能

#define YSSString [NSString stringWithFormat:@"%s", __func__].lastPathComponent
#define YSSLog(...) printf("%s: %p (line = %d): %s\n\n", [YSSString UTF8String] , &self, __LINE__, [[NSString stringWithFormat:__VA_ARGS__] UTF8String]);

//#else
//#define SFLog(s, ...) NSLog(@"<%@: %p (line = %d)> %@", self.class, self, __LINE__,[NSString stringWithFormat:(s),##__VA_ARGS__])
//#endif
#else // 发布状态, 关闭LOG功能
#define YSSLog(s, ...)
#endif
/**************************/
//#ifdef DEBUG
//
//#define BMLog(...) NSLog(@"%s %d \n%@\n\n",__func__,__LINE__,[NSString stringWithFormat:__VA_ARGS__])
//
//#else
//
//#define BMLog(...)
//
//#endif
/**************************/
#endif
