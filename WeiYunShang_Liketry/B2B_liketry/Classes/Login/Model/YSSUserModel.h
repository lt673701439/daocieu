//
//  YSSUserModel.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/22.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YSSUserModel : NSObject

@property (nonatomic, strong) NSString *updateUserId;
@property (nonatomic, strong) NSString *userPwd;
@property (nonatomic, strong) NSString *userStatus;
@property (nonatomic, strong) NSString *userPhone;
@property (nonatomic, strong) NSString *userSex;
@property (nonatomic, strong) NSString *openId;
@property (nonatomic, strong) NSString *updateTime;
@property (nonatomic, strong) NSString *userBirthday;
@property (nonatomic, strong) NSString *userType;
@property (nonatomic, strong) NSString *version;
@property (nonatomic, strong) NSString *delflag;
@property (nonatomic, strong) NSString *userCity;
@property (nonatomic, strong) NSString *id;
@property (nonatomic, strong) NSString *userNickname;
@property (nonatomic, strong) NSString *userIcon;
@property (nonatomic, strong) NSString *cashPwd;
@property (nonatomic, strong) NSString *userProvince;
@property (nonatomic, strong) NSString *loginName;
@property (nonatomic, strong) NSString *userAddress;
@property (nonatomic, strong) NSString *createTime;
@property (nonatomic, strong) NSString *userMail;
@property (nonatomic, strong) NSString *createUserId;

+ (instancetype)sharedInstence;

+ (instancetype)sharedInstenceWithModel:(YSSUserModel *)model isReset:(BOOL)isReset;

@end
