package com.liketry.interaction.benison.api;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.BenisonService;
import com.liketry.interaction.benison.vo.api.BenisonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 祝福语控制器
 *
 * @author Simon
 */
@RestController
@RequestMapping("benison_api")
public class BenisonApiController {
    @Autowired
    BenisonService benisonService;

    @RequestMapping("getBenison")
    Result<List<BenisonVo>> getBenison() {
        return new Result<>(SystemConstants.RESULT_SUCCESS, benisonService.findBenisonAll());
    }

    /**
     * 根据屏幕id，祝福语类型和id返回模板信息
     *
     * @param screenId  屏幕id
     * @param typeId    祝福语类型
     * @param benisonId 祝福语id
     * @return
     */
    @RequestMapping("getTemplet")
    Result<List<BenisonTemplate>> getBenisonTemplate(@RequestParam String screenId, @RequestParam String imgId, String typeId, String benisonId) {
        List<BenisonTemplate> benisonTemplateList = benisonService.findBenisonTemplate(screenId, imgId, typeId, benisonId);
        if(benisonTemplateList != null && benisonTemplateList.size()>0){
        	return new Result<>(SystemConstants.RESULT_SUCCESS, benisonTemplateList);
        }else{
        	return new Result<>(SystemConstants.RESULT_FALSE, "模板不存在！", null);
        }
    }
}