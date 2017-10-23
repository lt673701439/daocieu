package com.liketry.api;

import com.liketry.domain.MakeMoney;
import com.liketry.service.MakeMoneyService;
import com.liketry.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 赚钱课堂接口
 * @author pengyy
 */
@Api(value="赚钱课堂接口",description="供C端调用")
@RestController
@RequestMapping("api/make_money_api")
public class MakeMoneyApi extends BaseController<MakeMoneyService,MakeMoney>{
	
}