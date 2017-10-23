//
//  YSSSubmitAptitudesVC.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/9/8.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "YSSSubmitAptitudesVC.h"
#import "YSSSubmitSuccessVC.h"

#import "YSSStoreRegistHeaderView.h"

@interface YSSSubmitAptitudesVC ()<UINavigationControllerDelegate, UIImagePickerControllerDelegate>
@property (nonatomic, assign) NSInteger picType; /**< 1身份证正面  2身份证背面  3营业执照 */

@property (nonatomic, strong) YSSVerticalButton *uploadCharterBtn;
@property (nonatomic, strong) YSSVerticalButton *uploadFrontIDCardBtn;
@property (nonatomic, strong) YSSVerticalButton *uploadBackIDCardBtn;
@end

@implementation YSSSubmitAptitudesVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupUI];
}

- (void)setupUI
{
    self.title = @"商家资料";
    
    self.view.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    YSSStoreRegistHeaderView *headerView = [[YSSStoreRegistHeaderView alloc] init];
    [headerView configUIWithStep:2];
    [self.view addSubview:headerView];
    [headerView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(64);
        make.height.equalTo(@(Value(180)));
    }];
    
    UIButton *nextStepBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [nextStepBtn addTarget:self action:@selector(nextStep) forControlEvents:UIControlEventTouchUpInside];
    [nextStepBtn setBackgroundImage:[UIImage imageNamed:@"按钮"] forState:UIControlStateNormal];
    [nextStepBtn setTitle:@"提交审核" forState:UIControlStateNormal];
    nextStepBtn.titleLabel.font = YSSBoldSystemFont(Value(16));
    [self.view addSubview:nextStepBtn];
    [nextStepBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self.view);
        make.height.equalTo(@(Value(50)));
    }];
    
    UIView *mainView = [[UIView alloc] init];
    mainView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:mainView];
    [mainView makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(headerView.bottom);
        make.bottom.equalTo(nextStepBtn.top).offset(- Value(10));
    }];
    
    //营业执照
    YSSVerticalButton *uploadCharterBtn = [YSSVerticalButton buttonWithType:UIButtonTypeCustom];
    self.uploadCharterBtn = uploadCharterBtn;
    [uploadCharterBtn addCornerRadius:5 borderColor:nil borderWidth:0];
    [uploadCharterBtn addTarget:self action:@selector(uploadCharterBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    uploadCharterBtn.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    if ([YSSMerChanModel sharedInstence].merchantBusinessLicenceImage) {
        uploadCharterBtn.image_topOffset = 0;
        [uploadCharterBtn setImage:[YSSMerChanModel sharedInstence].merchantBusinessLicenceImage forState:0];
        uploadCharterBtn.imageView.contentMode = UIViewContentModeScaleAspectFill;
        [uploadCharterBtn setTitle:@"" forState:0];
    }else{
        uploadCharterBtn.image_topOffset = 25;
        uploadCharterBtn.title_topOffset = 20;
        [uploadCharterBtn setImage:[UIImage imageNamed:@"工商执照"] forState:0];
        uploadCharterBtn.imageView.contentMode = UIViewContentModeScaleAspectFit;
        [uploadCharterBtn setTitle:@"上传营业执照" forState:0];
    }
    
    uploadCharterBtn.titleLabel.font = YSSSystemFont(Value(14));
    [uploadCharterBtn setTitleColor:[UIColor blackColor] forState:0];
    [mainView addSubview:uploadCharterBtn];
    [uploadCharterBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(mainView).offset(Value(14));
        make.right.equalTo(mainView).offset(- Value(14));
        make.height.equalTo(uploadCharterBtn.width).multipliedBy(293.0 / 686);
    }];
    
    //身份证正面
    YSSVerticalButton *uploadFrontIDCardBtn = [YSSVerticalButton buttonWithType:UIButtonTypeCustom];
    self.uploadFrontIDCardBtn = uploadFrontIDCardBtn;
    uploadFrontIDCardBtn.imageView.contentMode = UIViewContentModeScaleAspectFill;
    [uploadFrontIDCardBtn addCornerRadius:5 borderColor:nil borderWidth:0];
    [uploadFrontIDCardBtn addTarget:self action:@selector(uploadFrontIDCardBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    uploadFrontIDCardBtn.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    if ([YSSMerChanModel sharedInstence].merchantIdCardUpImage) {
        uploadFrontIDCardBtn.image_topOffset = 0;
        [uploadFrontIDCardBtn setImage:[YSSMerChanModel sharedInstence].merchantIdCardUpImage forState:0];
        uploadFrontIDCardBtn.imageView.contentMode = UIViewContentModeScaleAspectFill;
        [uploadFrontIDCardBtn setTitle:@"" forState:0];
    }else{
        uploadFrontIDCardBtn.image_topOffset = 40;
        uploadFrontIDCardBtn.title_topOffset = 20;
        [uploadFrontIDCardBtn setImage:[UIImage imageNamed:@"正面"] forState:0];
        uploadFrontIDCardBtn.imageView.contentMode = UIViewContentModeScaleAspectFit;
        [uploadFrontIDCardBtn setTitle:@"上传身份证正面" forState:0];
    }
    
    uploadFrontIDCardBtn.titleLabel.font = YSSSystemFont(Value(14));
    [uploadFrontIDCardBtn setTitleColor:[UIColor blackColor] forState:0];
    [mainView addSubview:uploadFrontIDCardBtn];
    [uploadFrontIDCardBtn makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(uploadCharterBtn);
        make.top.equalTo(uploadCharterBtn.bottom).offset(Value(5));
        make.width.equalTo(uploadCharterBtn).multipliedBy(0.5).offset(- Value(2.5));
        make.height.equalTo(uploadFrontIDCardBtn.width).multipliedBy(300.0 / 333);
    }];
    
    //身份证背面
    YSSVerticalButton *uploadBackIDCardBtn = [YSSVerticalButton buttonWithType:UIButtonTypeCustom];
    self.uploadBackIDCardBtn = uploadBackIDCardBtn;
    uploadBackIDCardBtn.imageView.contentMode = UIViewContentModeScaleAspectFill;
    [uploadBackIDCardBtn addCornerRadius:5 borderColor:nil borderWidth:0];
    [uploadBackIDCardBtn addTarget:self action:@selector(uploadBackIDCardBtn:) forControlEvents:UIControlEventTouchUpInside];
    uploadBackIDCardBtn.backgroundColor = [UIColor colorWithHexString:YSSYellowBGColor];
    
    if ([YSSMerChanModel sharedInstence].merchantIdCardDownImage) {
        uploadBackIDCardBtn.image_topOffset = 0;
        [uploadBackIDCardBtn setImage:[YSSMerChanModel sharedInstence].merchantIdCardDownImage forState:0];
        uploadBackIDCardBtn.imageView.contentMode = UIViewContentModeScaleAspectFill;
        [uploadBackIDCardBtn setTitle:@"" forState:0];
    }else{
        uploadBackIDCardBtn.image_topOffset = 40;
        uploadBackIDCardBtn.title_topOffset = 20;
        [uploadBackIDCardBtn setImage:[UIImage imageNamed:@"背面"] forState:0];
        uploadBackIDCardBtn.imageView.contentMode = UIViewContentModeScaleAspectFit;
        [uploadBackIDCardBtn setTitle:@"上传身份证背面" forState:0];
    }
    
    uploadBackIDCardBtn.titleLabel.font = YSSSystemFont(Value(14));
    [uploadBackIDCardBtn setTitleColor:[UIColor blackColor] forState:0];
    [mainView addSubview:uploadBackIDCardBtn];
    [uploadBackIDCardBtn makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(uploadCharterBtn);
        make.top.equalTo(uploadCharterBtn.bottom).offset(Value(5));
        make.width.equalTo(uploadCharterBtn).multipliedBy(0.5).offset(- Value(2.5));
        make.height.equalTo(uploadBackIDCardBtn.width).multipliedBy(300.0 / 333);
    }];
}

#pragma mark - action
- (void)uploadCharterBtnClick:(UIButton *)button
{
    _picType = 3;
    [self uploadPic];
}

- (void)uploadFrontIDCardBtnClick:(UIButton *)button
{
    _picType = 1;
    [self uploadPic];
}

- (void)uploadBackIDCardBtn:(UIButton *)button
{
    _picType = 2;
    [self uploadPic];
}

- (void)uploadPic
{
    UIImagePickerController *pickerVC = [[UIImagePickerController alloc] init];
    pickerVC.allowsEditing = YES;
    pickerVC.delegate = self;
    pickerVC.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
    [self presentViewController:pickerVC animated:YES completion:nil];
}

- (void)nextStep
{
    YSSLog(@"提交审核");
    YSSMerChanModel *model = [YSSMerChanModel sharedInstence];
    NSMutableDictionary *infoDict = model.infoDict;
    infoDict[@"merchantShopname"] = [YSSCommonTool parseString:model.merchantShopname];
    infoDict[@"merchantCity"] = [YSSCommonTool parseString:model.merchantCity];
    infoDict[@"merchantScenicSpot"] = [YSSCommonTool parseString:model.merchantScenicSpot];
    infoDict[@"merchantAddress"] = [YSSCommonTool parseString:model.merchantAddress];
    infoDict[@"merchantTypeId"] = [YSSCommonTool parseString:model.merchantTypeId];
    infoDict[@"merchantContactName"] = [YSSCommonTool parseString:model.merchantContactName];
    infoDict[@"merchantContactMobile"] = [YSSCommonTool parseString:model.merchantContactMobile];
    infoDict[@"merchantOperatingStart"] = [YSSCommonTool parseString:model.merchantOperatingStart];
    infoDict[@"merchantOperatingEnd"] = [YSSCommonTool parseString:model.merchantOperatingEnd];
    infoDict[@"merchantContentId"] = [YSSCommonTool parseString:model.merchantContentId];
    infoDict[@"merchantIdCardUp"] = [YSSCommonTool parseString:model.merchantIdCardUp];
    infoDict[@"merchantIdCardDown"] = [YSSCommonTool parseString:model.merchantIdCardDown];
    infoDict[@"merchantBusinessLicence"] = [YSSCommonTool parseString:model.merchantBusinessLicence];
    infoDict[@"dicts"] = @[];
    infoDict[@"isProof"] = @"1";
    
    [YSSHttpTool post:SAVE_MERCHANT params:infoDict isJsonSerializer:NO success:^(id json) {
        YSSSubmitSuccessVC *tempVC = [[YSSSubmitSuccessVC alloc] init];
        [self.navigationController pushViewController:tempVC animated:YES];
    } dataError:^(id json) {
        [YSSCommonTool showInfoWithStatus:@"请检查您的信息填写无误"];
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误"];
    }];
}

#pragma mark - <UIImagePickerControllerDelegate>
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary<NSString *,id> *)info
{
    [self dismissViewControllerAnimated:YES completion:nil];
    YSSLog(@"%@", info[@"UIImagePickerControllerEditedImage"]);
    UIImage *editImg = info[@"UIImagePickerControllerEditedImage"];
    YSSVerticalButton *uploadBtn = nil;
    if (_picType == 3) {
        uploadBtn = self.uploadCharterBtn;
    }else if (_picType == 1)
    {
        uploadBtn = self.uploadFrontIDCardBtn;
     
    }else{
        uploadBtn = self.uploadBackIDCardBtn;
    }
    
    
    uploadBtn.image_topOffset = 0;
    [uploadBtn setImage:editImg forState:0];
    uploadBtn.imageView.contentMode = UIViewContentModeScaleAspectFill;
    [uploadBtn setTitle:@"" forState:0];
    
    NSDictionary *param = @{
                            @"merchantId" : [YSSCommonTool parseString:[YSSMerChanModel sharedInstence].id],
                            @"type" : @(_picType),
                            };
    [YSSHttpTool postImage:UPLOAD_IMG params:param image:editImg fileName:@"img.png" success:^(id json) {
        if (_picType == 3) {
            [YSSMerChanModel sharedInstence].merchantBusinessLicence = json[@"msg"];
            [YSSMerChanModel sharedInstence].merchantBusinessLicenceImage = editImg;
        }else if (_picType == 1)
        {
            [YSSMerChanModel sharedInstence].merchantIdCardUp = json[@"msg"];
            [YSSMerChanModel sharedInstence].merchantIdCardUpImage = editImg;
        }else{
            [YSSMerChanModel sharedInstence].merchantIdCardDown = json[@"msg"];
            [YSSMerChanModel sharedInstence].merchantIdCardDownImage = editImg;
        }
    } failure:^(NSError *error) {
        [YSSCommonTool showInfoWithStatus:@"网络错误，请重新上传"];
    }];
    
    
}
- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    YSSLog(@"取消");
    [self dismissViewControllerAnimated:YES completion:nil];
}

@end
