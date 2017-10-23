package com.liketry.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.Merchant;
import com.liketry.domain.TbDict;
import com.liketry.service.CodeService;
import com.liketry.service.MerChantService;
import com.liketry.service.TbDictService;
import com.liketry.service.UserService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.util.FileUtils;
import com.liketry.web.BaseController;
import com.liketry.web.vm.ResultVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * author Simon
 * create 2017/8/29
 * 商户
 */
@Slf4j
@RestController
@Api(value = "商户接口")
@RequestMapping(value = "api/merchant_api", method = RequestMethod.POST)
public class MerchantApi extends BaseController<MerChantService, Merchant> {
    private final CodeService codeService;
    private final UserService userService;
    @Autowired
    private TbDictService tbDictService;

    @Autowired
    public MerchantApi(CodeService codeService, UserService userService) {
        this.codeService = codeService;
        this.userService = userService;
    }

    private final int ERROR_DATA_EMPTY = 1001;//数据为空
    private final int ERROR_ADDRESS_EMPTY = 1002;//省市景区地址为空
    private final int ERROR_SHOPNAME_EMPTY = 1003;//店铺名称为空
    private final int ERROR_TYPE_ID_EMPTY = 1004;//店铺类型为空
    private final int ERROR_TIME_EMPTY = 1005;//店铺起始终止日期为空
    private final int ERROR_USER_ID_EMPTY = 1006;//用户id为空 merchangid为空
    private final int ERROR_BUSINESS_LICENCE_EMPTY = 1008;//执照地址为空
    private final int ERROR_ID_CARD_EMPTY = 1009;//身份证地址为空
    private final int ERROR_NOT_FIND_USER = 1011;//用户不存在
    private final int ERROR_TYPE_FAILED = 1012;//不支持的类型
    private final int ERROR_STATUS_FAILED = 1013;//0和3 能提交审核
    private final int ERROR_CONTACT_NAME_FAILED = 1014;//商户联系人校验失败
    private final int ERROR_CONTACT_MOBILE_FAILED = 1015;//商户联系人电话校验失败
    private final int ERROR_ICON_EMPTY = 1016;//商户icon是空

    @Override
    @PostMapping
    public ResultVM create(@RequestBody Merchant merchant) {
        return ResultVM.error();
    }

    @RequestMapping("saveMerchant")
    public ResultVM saveMerchant(Merchant merchant, @RequestParam boolean isProof) {
        if (merchant == null || StringUtils.isEmpty(merchant.getId()))
            return ResultVM.error(String.valueOf(Constants.ERROR_ID_EMPTY));
        Merchant oldMerchant = service.selectById(merchant.getId());
        if (oldMerchant == null)
            return ResultVM.error(String.valueOf(ERROR_NOT_FIND_USER));
        if (oldMerchant.getMerchantCensorStatus() == null || (oldMerchant.getMerchantCensorStatus() != Constants.CENSOR_DRAFT && oldMerchant.getMerchantCensorStatus() != Constants.CENSOR_FAILED))
            return ResultVM.error(String.valueOf(ERROR_STATUS_FAILED));
        if (isProof) {//提交认证
            int validate = addValidate(merchant);
            if (validate != Constants.SUCCESS) {//内容不符合要求
                return ResultVM.error(validate, null);
            }
            merchant.setMerchantCensorStatus(Constants.CENSOR_PUT);
        }
        boolean isUpdate = service.updateById(merchant);
        return isUpdate ? ResultVM.ok() : ResultVM.error(Constants.ERROR_UPDATE_FAILED, null);
    }

    @RequestMapping("getMerchant")
    public ResultVM getMerchant(@RequestParam String userId) {
        if (StringUtils.isBlank(userId))
            return ResultVM.error(ERROR_USER_ID_EMPTY, null);
        if (userService.selectById(userId) == null)
            return ResultVM.error(ERROR_NOT_FIND_USER, null);
        if (StringUtils.isBlank(userId))
            return ResultVM.error(ERROR_USER_ID_EMPTY, null);
        EntityWrapper<Merchant> wrapper = new EntityWrapper<Merchant>();
        wrapper.where("merchant_user_id = {0} and merchant_delflag = 1", userId);
        Merchant merchant = service.selectOne(wrapper);
        if (merchant != null) {
            return ResultVM.ok(addExtraInfo(merchant.getId()));
        } else {
            merchant = new Merchant();
            merchant.setMerchantUserId(userId);
            merchant.setId(CommonUtils.getId());
            merchant.setMerchantDelflag(1);
            merchant.setMerchantLevel(1);
            merchant.setMerchantCensorStatus(Constants.CENSOR_DRAFT);
            merchant.setMerchantBalance(new BigDecimal(0f));
            merchant.setMerchantCode(codeService.getCTMCode());
            boolean isInsert = service.insert(merchant);
            return isInsert ? ResultVM.ok(merchant) : ResultVM.error(Constants.ERROR_INSERT_FAILED, null);
        }
    }

    @RequestMapping(value = "putImg", method = RequestMethod.POST)
    public ResultVM putImg(@RequestParam String merchantId, @RequestParam int type, @RequestParam MultipartFile img) {
        if (StringUtils.isBlank(merchantId))
            return ResultVM.error(ERROR_USER_ID_EMPTY, null);
        Merchant merchant = service.selectById(merchantId);
        if (merchant == null || merchant.getMerchantDelflag() != 1 || (merchant.getMerchantCensorStatus() != Constants.CENSOR_DRAFT && merchant.getMerchantCensorStatus() != Constants.CENSOR_FAILED))
            return ResultVM.error(ERROR_NOT_FIND_USER, null);
        String suffix = img.getOriginalFilename();
        if (StringUtils.isBlank(suffix) || suffix.split("\\.").length < 2)
            return ResultVM.error(ERROR_TYPE_FAILED, null);
        String[] split = suffix.split("\\.");
        suffix = split[split.length - 1].toLowerCase();
        if (!"jpg".equals(suffix) && !"jpeg".equals(suffix) && !"png".equals(suffix))
            return ResultVM.error(ERROR_TYPE_FAILED, null);
        String path;
        switch (type) {
            case 1://身份证正面
                path = FileUtils.getPathAndCreate(Constants.ID_UP_LOCAL, suffix);
                merchant.setMerchantIdCardUp(path);
                break;
            case 2://身份证反面
                path = FileUtils.getPathAndCreate(Constants.ID_DOWN_LOCAL, suffix);
                merchant.setMerchantIdCardDown(path);
                break;
            case 3://营业执照
                path = FileUtils.getPathAndCreate(Constants.LICENSE_LOCAL, suffix);
                merchant.setMerchantBusinessLicence(path);
                break;
            case 4://商户icon
                path = FileUtils.getPathAndCreate(Constants.MERCHANT_ICON_LOCAL, suffix);
                merchant.setMerchantBusinessLicence(path);
                break;
            default:
                return ResultVM.error(ERROR_TYPE_FAILED, null);
        }
        boolean saveStatus = FileUtils.saveFile(path, img);
        log.info("merchantId: " + merchantId + ",path: " + path + ",status: " + saveStatus);
        if (saveStatus)
            saveStatus = service.updateById(merchant);
        if (saveStatus)
            return ResultVM.ok(path);
        else
            return ResultVM.error(Constants.ERROR_INSERT_FAILED, null);
    }

//    @RequestMapping(value = "qrCode", method = RequestMethod.POST)
//    public ResultVM getImg(@RequestParam String merchantId) {
//        return ResultVM.ok(QRCodeUtils.getMINACode(merchantId));
//    }

    /**
     * @param merchantId 商户id
     * @param type       1 更改手机号 2 更改地址  3 更改服务内容
     * @param value      数据内容
     * @return 更新状态
     */
    @ApiOperation(value = "信息更新")
    @PostMapping("updateInfo")
    public ResultVM getImg(@RequestParam String merchantId, @RequestParam int type, String value) {
        Merchant merchant = service.selectById(merchantId);
        if (merchant == null) {
            return ResultVM.error(ERROR_NOT_FIND_USER, null);
        }
        switch (type) {
            case 1:
                if (CommonUtils.valudateMobile(value)) {
                    merchant.setMerchantContactMobile(value);
                } else {
                    return ResultVM.error(ERROR_CONTACT_MOBILE_FAILED, null);
                }
                break;
            case 2:
                merchant.setMerchantAddress(value);
                break;
            case 3:
                if (!StringUtils.isBlank(value)) {
                    String[] ids = value.split(",");
                    for (String id : ids) {
                        if (tbDictService.selectById(id) == null)
                            return ResultVM.error(Constants.ERROR_DATA_NOT_COMPLETE, null);
                    }
                    merchant.setMerchantContentId(value);
                } else {
                    merchant.setMerchantAddress(null);
                }
                break;
            default:
                return ResultVM.error(ERROR_TYPE_FAILED, null);
        }
        boolean isUpdate = service.updateById(merchant);
        merchant = addExtraInfo(merchantId);
        return isUpdate ? ResultVM.ok(merchant) : ResultVM.error(Constants.ERROR_UPDATE_FAILED, null);
    }


    @Override
    @GetMapping("/{id}")
    public ResultVM getInfo(@PathVariable String id) {
        Merchant merchant = service.selectById(id);
        merchant.setCreateUserId(null);
        merchant.setUpdateUserId(null);
        if(merchant!=null){
            return  ResultVM.ok(merchant);
        }else{
            return  ResultVM.error();
        }
    }

    //验证是否合法化 SUCCESS 代表验证通过，其他代表错误号
    private int addValidate(Merchant merchant) {
        if (merchant == null)
            return ERROR_DATA_EMPTY;
        if (StringUtils.isBlank(merchant.getId()))
            return Constants.ERROR_ID_EMPTY;
        if (StringUtils.isBlank(merchant.getMerchantUserId()))
            return ERROR_USER_ID_EMPTY;
        if (StringUtils.isBlank(merchant.getMerchantShopname()))
            return ERROR_SHOPNAME_EMPTY;
//        if (StringUtils.isBlank(merchant.getMerchantIcon()))
//            return ERROR_ICON_EMPTY;
        if (StringUtils.isBlank(merchant.getMerchantTypeId()))
            return ERROR_TYPE_ID_EMPTY;
//        StringUtils.isEmpty(merchant.getMerchantProvince()) ||
        if (StringUtils.isEmpty(merchant.getMerchantCity()) || StringUtils.isEmpty(merchant.getMerchantScenicSpot()) || StringUtils.isEmpty(merchant.getMerchantAddress()))
            return ERROR_ADDRESS_EMPTY;
        if (merchant.getMerchantOperatingStart() == null || merchant.getMerchantOperatingEnd() == null)
            return ERROR_TIME_EMPTY;
        if (StringUtils.isBlank(merchant.getMerchantBusinessLicence()))
            return ERROR_BUSINESS_LICENCE_EMPTY;
        if (StringUtils.isBlank(merchant.getMerchantIdCardUp()) || StringUtils.isBlank(merchant.getMerchantIdCardDown()))
            return ERROR_ID_CARD_EMPTY;
        if (StringUtils.isBlank(merchant.getMerchantContactName()))
            return ERROR_CONTACT_NAME_FAILED;
        if (!CommonUtils.valudateMobile(merchant.getMerchantContactMobile()))
            return ERROR_CONTACT_MOBILE_FAILED;
        return Constants.SUCCESS;
    }

    //添加附加信息
    private Merchant addExtraInfo(String id) {
        Merchant merchant = service.selectById(id);
        if (merchant == null)
            return null;
        String merchantContentId = merchant.getMerchantContentId();
        if (!StringUtils.isBlank(merchantContentId)) {
            String[] merchantContentIds = merchantContentId.split(",");
            TbDict[] result = new TbDict[merchantContentIds.length];
            for (int i = 0; i < merchantContentIds.length; i++) {
                result[i] = tbDictService.selectById(merchantContentIds[i]);
            }
            merchant.setDicts(result);
        }
        EntityWrapper<TbDict> ew = new EntityWrapper<TbDict>();
        ew.eq("code", merchant.getMerchantProvince());
        TbDict dict = tbDictService.selectOne(ew);
        if (dict != null) {
            merchant.setMerchantProvinceName(dict.getText());
        }
        ew = new EntityWrapper<TbDict>();
        ew.eq("code", merchant.getMerchantCity());
        dict = tbDictService.selectOne(ew);
        if (dict != null) {
            merchant.setMerchantCityName(dict.getText());
        }
        ew = new EntityWrapper<TbDict>();
        ew.eq("id", merchant.getMerchantTypeId());
        dict = tbDictService.selectOne(ew);
        if (dict != null) {
            merchant.setMerchantTypeName(dict.getText());
        }
        return merchant;
    }
}