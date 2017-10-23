//
//  YSSSpotModel.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/18.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YSSSpotModel : NSObject

@property (nonatomic, strong) NSString *spotId;
@property (nonatomic, strong) NSString *spotCode;
@property (nonatomic, strong) NSString *spotName;
@property (nonatomic, assign) NSInteger spotStatus;
@property (nonatomic, strong) NSString *spotProvince;
@property (nonatomic, strong) NSString *spotCity;
@property (nonatomic, strong) NSString *spotAddress;
@property (nonatomic, strong) NSString *spotDescription;
@property (nonatomic, strong) NSArray *screens;

@end
