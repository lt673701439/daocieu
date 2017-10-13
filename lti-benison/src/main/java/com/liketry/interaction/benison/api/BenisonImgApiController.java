package com.liketry.interaction.benison.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.BenisonImg;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.BenisonImgService;
import com.liketry.interaction.benison.util.UserUtils;

/**
 * 背景图接口
 *
 * @author pengyy
 */
@RestController
@RequestMapping("benisonImg_api")
public class BenisonImgApiController {
	
	private static final Logger log = LoggerFactory.getLogger(BenisonImgApiController.class);
	
    @Autowired
    BenisonImgService benisonImgService;

    /**
     * 根据图片名称获取背景图
     * @param imgName 图片名称，例：（表白-黄山）
     * @return
     */
    @GetMapping("getBenisonImgList")
    Result<List<BenisonImg>> getBenisonImgList(String data) {
    	
    	//base64转码
    	JSONObject json = UserUtils.decrypt2Str(data);
    	String imgName = json.getString("imgName");
    	
    	log.info("<=====BenisonImgApiController.getBenisonImgList=入参：{}============>",imgName);
        return new Result<>(SystemConstants.RESULT_SUCCESS, benisonImgService.findBenisonImgAll(imgName));
    }
}