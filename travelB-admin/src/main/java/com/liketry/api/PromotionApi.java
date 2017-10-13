package com.liketry.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liketry.domain.Promotion;
import com.liketry.service.PromotionService;
import com.liketry.web.BaseController;

/**
 * 活动接口
 *
 * @author pengyy
 */
@RestController
@RequestMapping("api/promotion_api")
public class PromotionApi extends BaseController<PromotionService,Promotion>{
	
}