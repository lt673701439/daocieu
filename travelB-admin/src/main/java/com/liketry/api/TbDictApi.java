package com.liketry.api;

import com.alibaba.fastjson.JSONObject;
import com.liketry.domain.TbDict;
import com.liketry.service.TbDictService;
import com.liketry.web.BaseController;
import com.liketry.web.vm.JsTreeVM;
import com.liketry.web.vm.ResultVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="字典内容接口",description="供前端调用")
@Slf4j
@RestController
@RequestMapping("api/dict")
public class TbDictApi extends BaseController<TbDictService, TbDict> {
	
	/**
     * 根据分类编号获取字典树
     * @param code
     * @return
     */
    @ApiOperation(value="根据分类编号获取字典树")
    @ApiImplicitParams({
   	 @ApiImplicitParam(name = "id", value = "字典id", required = true,dataType = "String",paramType="query")
    })
    @GetMapping("/getDictTree")
    public ResultVM getDictTree(String id) {
        List<JsTreeVM> list = service.findDictTreeByClassCode(id);
        return ResultVM.ok(list);
    }

	/**
     * 导入省市
     * @param json
     * @return
     */
    @PostMapping("/insertProvince")
    public ResultVM insertProvince(@RequestBody JSONObject json){
    	
    	String dictClassId = "7765323da47e4da8a970e3159836580d";//行政区域
    	int provinceSort = 0;//起始省排序
    	int provinceCode = 10000;
    	int cityCode = 20000;
    	
    	//导入省（省为json的key）
    	Set<String> set = json.keySet();
    	log.info("<========省：{}==================>",json.keySet());
    	for(String province:set){
    		//新增省
    		TbDict provinceDict = new TbDict();
    		String provinceId = UUID.randomUUID().toString().replace("-", "");
    		provinceDict.setId(provinceId);
    		provinceDict.setParentId("#");
    		provinceDict.setCode(String.valueOf(provinceCode));
    		provinceDict.setSort(provinceSort);
    		provinceDict.setText(province);
    		provinceDict.setDictClassId(dictClassId);
    		provinceDict.setCreateTime(new Date());
    		provinceDict.setCreateUserId("65909bf4abe8440cae422e5aa6e7f13c");
    		service.insert(provinceDict);
    		provinceCode+=10;
    		provinceSort++;
    		//新增市
    		List<String> cityList = (List<String>)json.get(province);
    		log.info("<========{}的市：{}==================>",province,cityList);
    		int citySort = 0;//起始市排序
    		for(String city:cityList){
    			TbDict cityDict = new TbDict();
    			cityDict.setParentId(provinceId);
    			cityDict.setCode(String.valueOf(cityCode));
    			cityDict.setSort(citySort);
    			cityDict.setText(city);
    			cityDict.setDictClassId(dictClassId);
    			cityDict.setCreateTime(new Date());
    			cityDict.setCreateUserId("65909bf4abe8440cae422e5aa6e7f13c");
        		service.insert(cityDict);
        		cityCode+=10;
        		citySort++;
    		}
    	}
    	
    	return ResultVM.ok("导入成功");
    }
}
