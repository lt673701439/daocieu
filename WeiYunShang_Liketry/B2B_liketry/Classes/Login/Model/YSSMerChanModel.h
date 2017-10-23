//
//  YSSMerChanModel.h
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/12.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YSSMerChanModel : NSObject

@property (nonatomic, strong) NSString *id;  /**< 商户id */
@property (nonatomic, strong) NSString *merchantCity;  /**< 所在城市 code */
@property (nonatomic, strong) NSString *merchantScenicSpot;  /**< 所在景区 */
@property (nonatomic, strong) NSString *merchantAddress;  /**< 公司地址 */
@property (nonatomic, strong) NSString *merchantTypeId;   /**< 商户分类id */
@property (nonatomic, strong) NSString *merchantTypeName;  /**< 商户分类名称 */
@property (nonatomic, strong) NSString *merchantContactName;  /**< 联系人姓名 */
@property (nonatomic, strong) NSString *merchantContactMobile; /**< 联系人手机号 */
@property (nonatomic, strong) NSString *merchantOperatingStart;  /**< 营业时间开始 */
@property (nonatomic, strong) NSString *merchantOperatingEnd;  /**< 营业时间结束 */
@property (nonatomic, strong) NSString *merchantContentId;  /**< 服务内容id */
@property (nonatomic, strong) NSString *merchantBusinessLicence;  /**< 营业执照 */
@property (nonatomic, strong) NSString *merchantIdCardUp;  /**< 身份证正面 */
@property (nonatomic, strong) NSString *merchantIdCardDown;   /**< 身份证反面 */
@property (nonatomic, assign) CGFloat merchantBalance;  /**< 余额 */
@property (nonatomic, strong) NSArray *dicts;  /**< 服务内容数组 */
@property (nonatomic, strong) NSString *merchantOperatingSummer;
@property (nonatomic, strong) NSString *merchantUserId;  /**< 用户id */
@property (nonatomic, strong) NSString *merchantLevel;  /**< 商户等级 */
@property (nonatomic, assign) NSInteger merchantCensorStatus;  /**< 商户状态:草稿态0 审核通过1 提交2 审核不通过3 停止合作4  违规5  违法6 */
@property (nonatomic, strong) NSString *merchantIcon;  /**< 商户头像 */
@property (nonatomic, strong) NSString *merchantProvinceName;  /**< 商户所在 省 */
@property (nonatomic, strong) NSString *merchantProvince;  /**< 商户省code */
@property (nonatomic, strong) NSString *merchantCityName; /**< 商户所在 市 */
@property (nonatomic, strong) NSString *merchantShopname;  /**< 商户名称 */
@property (nonatomic, strong) NSString *merchantQrCode;  /**< 商户二维码 */
@property (nonatomic, strong) NSString *merchantOperatingSpring;
@property (nonatomic, strong) NSString *merchantDescription;
@property (nonatomic, strong) NSString *createUserId;
@property (nonatomic, strong) NSString *merchantOperatingAutumn;
@property (nonatomic, strong) NSString *merchantOperatingWed;
@property (nonatomic, strong) NSString *merchantCode;
@property (nonatomic, strong) NSString *merchantOperatingSat;
@property (nonatomic, strong) NSString *merchantDivideUpRule;
@property (nonatomic, strong) NSString *merchantDelflag;
@property (nonatomic, strong) NSString *merchantOperatingTue;
@property (nonatomic, strong) NSString *merchantOperatingSun;
@property (nonatomic, strong) NSString *updateUserId;
@property (nonatomic, strong) NSString *createTime;
@property (nonatomic, strong) NSString *merchantOperatingMon;
@property (nonatomic, strong) NSString *changeDescription;
@property (nonatomic, strong) NSString *merchantOperatingWinter;

@property (nonatomic, strong) UIImage *merchantBusinessLicenceImage;
@property (nonatomic, strong) UIImage *merchantIdCardUpImage;
@property (nonatomic, strong) UIImage *merchantIdCardDownImage;

@property (nonatomic, strong) NSMutableDictionary *infoDict;

+ (instancetype)sharedInstence;

+ (instancetype)sharedInstenceWithModel:(YSSMerChanModel *)model isReset:(BOOL)isReset;

@end
