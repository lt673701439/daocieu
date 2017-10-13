package com.liketry.interaction.opt.chart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * testChart
 */
@Controller("testChart")
@RequestMapping(value="/testChart")
public class TestChartController {
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showTestChartIndexPage(String flag) {
		
		if(flag == null || "1".equals(flag)){
			return "chart/testChart";
		}else {
			return "chart/testChart"+flag;
		}
		
	}
}
