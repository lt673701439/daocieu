package com.liketry.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liketry.domain.DivideRule;
import com.liketry.service.DivideRuleService;
import com.liketry.util.Constants;
import com.liketry.util.ShiroUtils;
import com.liketry.web.vm.ResultVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by liketry
 */
@Api(value="分成规则接口",description="供平台管理调用")
@RestController
@RequestMapping("/sys/divideRule")
public class DivideRuleController extends BaseController<DivideRuleService,DivideRule>{
	
	/**
     * 新增
     * @param t
     * @return
     */
	@ApiOperation(value="新增分成规则")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "t", value = "分成规则实体对象", required = true,dataType = "DivideRule")
	   })
    @PostMapping
    public ResultVM create(@RequestBody DivideRule t) {
    	
    	String msg = checkLimit(t);
    	
    	if(msg != null){
    		return ResultVM.error(msg);
    	}
    	
        t.setCreateUserId(ShiroUtils.getUserId());
        t.setCreateTime(new Date());
        t.setUpdateTime(new Date());
        t.setUpdateUserId(ShiroUtils.getUserId());
        if(service.insert(t)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }
    
    /**
     * 更新
     * @param t
     * @return
     */
	@ApiOperation(value="更新分成规则")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "t", value = "分成规则实体对象", required = true,dataType = "DivideRule")
	   })
    @PutMapping
    public ResultVM update(@RequestBody DivideRule t) {
    	
    	String msg = checkLimit(t);
    	
    	if(msg != null){
    		return ResultVM.error(msg);
    	}

        t.setUpdateTime(new Date());
        t.setUpdateUserId(ShiroUtils.getUserId());
        if(service.updateById(t)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }
    
    /**
     * 校验分成规则
     * @param t
     * @return
     */
    private String checkLimit(DivideRule t){
    	
    	BigDecimal upperLimit = t.getUpperLimit();//区间上限
    	BigDecimal lowerLimit = t.getLowerLimit();//区间下限
    	
    	//校验上限大于下限
    	if(upperLimit != null && lowerLimit != null){
    		if(upperLimit.compareTo(lowerLimit) == -1){
    			return "上限数值不能小于下限数值";
    		}
    	}
    	
    	//按比例，区间[0,100]
    	if(Constants.divideType_ratio.equals(t.getType())){
    		if( upperLimit!=null && upperLimit.compareTo(new BigDecimal("100")) == 1){
    			return "比例上限不得大于100";
    		}
    		if( lowerLimit!=null && lowerLimit.compareTo(new BigDecimal("0")) == -1){
    			return "比例下限不得小于0";
    		}
    	}
    	
    	//按金额
    	if(Constants.divideType_price.equals(t.getType())){
    		
    		if( upperLimit!=null && (upperLimit.compareTo(new BigDecimal("0")) == -1 
    				||lowerLimit.compareTo(new BigDecimal("0")) == -1)){
    			return "金额区间不得小于0";
    		}
    	}
    	
    	//校验区间范围重叠
    	List<DivideRule> divideRuleList = service.findRepeatLimit(t);
    	
    	if(divideRuleList!=null && divideRuleList.size()>0){
    		
    		for(DivideRule divideRule:divideRuleList){
    			//判断是否是同类型的区间
    			if(divideRule.getType().equals(t.getType())){
    				
    				/* 比较是否包含区间值:除了以下两种都数据都重叠
    				 * 1,下限和上限相等时,如果边界值有一个是不包含，则不重叠
    				 * 2,上限和下限相等时,如果边界值有一个是不包含，则不重叠
    				 */
    				if(!((divideRule.getLowerLimit().compareTo(upperLimit) == 0
    						&& (Constants.divideType_exclusive.equals(divideRule.getIsLower()) || Constants.divideType_exclusive.equals(t.getIsUpper())))
    						||
    						(divideRule.getUpperLimit().compareTo(lowerLimit) == 0
    						&& (Constants.divideType_exclusive.equals(divideRule.getIsUpper()) || Constants.divideType_exclusive.equals(t.getIsLower()))))
    						){
    					return "区间范围重叠，请修正区间信息";
    				}
    			}
    		}
    	}
    	
    	return null;
    }

}
