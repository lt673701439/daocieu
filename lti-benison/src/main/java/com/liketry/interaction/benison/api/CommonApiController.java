package com.liketry.interaction.benison.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.util.PropertiesUtils;

/**
 * 共通api
 *
 * @author pengyy
 */
@RestController
@RequestMapping("common_api")
public class CommonApiController {
	
	private static PropertiesUtils pro = PropertiesUtils.getInstance();
	
	/**
	 * 获取限制的时间范围
	 * @return
	 */
    @GetMapping("getLimitDay")
    Result<Map<String,Object>> getLimitDay() {
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("commodity_day_range_after", pro.getValue("commodity_day_range_after"));
    	
        return new Result<>(SystemConstants.RESULT_SUCCESS, "查询成功",map);
    }

}