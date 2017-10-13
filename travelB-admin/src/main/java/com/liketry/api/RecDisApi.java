package com.liketry.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.BankCard;
import com.liketry.domain.DivideRule;
import com.liketry.domain.Merchant;
import com.liketry.domain.RecDis;
import com.liketry.domain.User;
import com.liketry.service.BankCardService;
import com.liketry.service.CodeService;
import com.liketry.service.DivideRuleService;
import com.liketry.service.MerChantService;
import com.liketry.service.RecDisService;
import com.liketry.service.UserService;
import com.liketry.util.Constants;
import com.liketry.web.BaseController;
import com.liketry.web.vm.ResultVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * author pengyy
 * 收付单接口
 */
@Api(value="收付单接口",description="供C端调用")
@RestController
@RequestMapping("api/rec_dis_api")
@Slf4j
public class RecDisApi extends BaseController<RecDisService, RecDis> {
	
	@Autowired
	private MerChantService merChantService;
	
	@Autowired
	private DivideRuleService divideRuleService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private BankCardService bankCardService;
	
	@Autowired
	private UserService userService;
	
   /**
     * 收付单同步
     * @param t
     * @return
     */
	@ApiOperation(value="收付单同步")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "data", value = "json数据串", required = true,dataType = "String")
	   })
    @PostMapping(value="/recDicSync")
    public ResultVM recDicSync(@RequestBody JSONObject json) {
		
		log.info("<====同步收付单json数据:{}=========>",json);
    	
    	String msg = checkData(json);
    	
    	if(msg!=null){
    		return ResultVM.error(msg);
    	}
    	
    	BigDecimal recDisPrice = json.getBigDecimal("recDisPrice");//金额
    	BigDecimal price = json.getBigDecimal("price");//订单金额
    	String commodityId = json.getString("commodityId");//商户ID
		BigDecimal resultPrice = BigDecimal.valueOf(0);//分成金额
    	
    	
		//查询商户，确定分成规则
		Merchant merChant = merChantService.selectById(commodityId);
		log.info("<===商户id:{}====商户为:{}=========>",commodityId,merChant);
		Integer  divideRuleType = null;
		if(merChant != null){
			divideRuleType = merChant.getMerchantDivideUpRule();//商户的分成规则
		}
    	
		json.put("code",codeService.getBillCode(merChant.getMerchantCode(),true));
		log.info("<=====订单金额：{},分成类型,{}==============>",recDisPrice,divideRuleType);
    	if(recDisPrice.compareTo(new BigDecimal(0))==1){
    		//收（付）款单，直接算出分成金额，修改商户余额
    		Map<String,Object> map = getDividePrice(recDisPrice,divideRuleType);
    		if(!Boolean.getBoolean(map.get("flag").toString())){
    			ResultVM.error(map.get("msg").toString());
    		}
    		resultPrice = (BigDecimal)map.get("msg");
    	}else if(recDisPrice.compareTo(new BigDecimal(0))==-1){
    		//收（退）款单,算出商户需要退款的金额（负数）,修改商户余额
    		recDisPrice = price.add(recDisPrice);
    		Map<String,Object> map = getDividePrice(recDisPrice,divideRuleType);
    		if(!Boolean.valueOf(map.get("flag").toString())){
    			ResultVM.error(map.get("msg").toString());
    		}
    		Map<String,Object> allCountMap = getDividePrice(price,divideRuleType);
    		if(!Boolean.valueOf(allCountMap.get("flag").toString())){
    			ResultVM.error(allCountMap.get("msg").toString());
    		}
    		resultPrice = ((BigDecimal)map.get("msg")).subtract((BigDecimal)allCountMap.get("msg"));
    	}
    	
    	log.info("<===商户分到的金额：=======>",resultPrice);
    	
    	Map<String,Object> map = service.insertRecAndOrder(json,resultPrice,merChant);	
    	
 		if(Boolean.valueOf((map.get("flag").toString()))){
 			return ResultVM.ok(map.get("msg"));
 		}else{
 			return ResultVM.error(map.get("msg").toString());
 		}
    	
    }
    
    /**
     * 提现额度查询
     * @param commdityId 商户ID
     * @return
     */
	@ApiOperation(value="提现额度查询")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "commdityId", value = "商户ID", required = true,dataType = "String")
	   })
    @GetMapping(value="/getCashBalance")
    public ResultVM getCashBalance(String commdityId) {
    	
		JSONObject json = new JSONObject();
		
    	if(StringUtils.isBlank(commdityId)){
    		return ResultVM.error("请选择您想要查询的商户！");
    	}
    	
    	BankCard bankCard = new BankCard();
    	bankCard.setBcMerchantId(commdityId);
    	//查询商户的银行卡信息
    	List<BankCard> bankCardList = bankCardService.selectList(new EntityWrapper<BankCard>(bankCard));
    	
    	if(bankCardList!=null&&bankCardList.size()>0){
    		json.put("bankCard", bankCardList.get(0));
    	}else{
    		json.put("bankCard", "");
    	}
    	
    	json.put("price", service.findAllRecAndDis(commdityId));
    	
    	return ResultVM.ok(json);
    }
    
    /**
     * 提现申请
     * @param json
     * @return
     */
	@ApiOperation(value="提现申请")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "data", value = "json数据串（提现金额：recDisPrice，可提现金额：allRecDisPrice，商户ID：commodityId，提现密码：cashPwd，用户ID：id"
	  	 		+ "银行卡ID：bankCardId）",
	  			 required = true,dataType = "String")
	   })
    @PostMapping(value="/appCash")
    public ResultVM appCash(@RequestBody JSONObject json) {
    	
    	BigDecimal recDisPrice = json.getBigDecimal("recDisPrice");//提现金额
    	BigDecimal allRecDisPrice = json.getBigDecimal("allRecDisPrice");//可提现金额
    	String commodityId = json.getString("commodityId");//商户ID
    	String pwd = json.getString("cashPwd");//提现密码
    	String id = json.getString("id");
		Date date = new Date();
    	
		//校验提现金额
		if(recDisPrice.compareTo(allRecDisPrice)==1){
			return ResultVM.error("提现金额不能大于可提现额度！");
		}
		
		//查询商户
		Merchant merChant = merChantService.selectById(commodityId);
		if(merChant == null){
			return ResultVM.error("商户不存在！");
		}
		
		//查询用户
		User user = userService.selectById(id);
		if(merChant == null){
			return ResultVM.error(Constants.code_user_noExist,"用户不存在！");
		}
		
		//校验提现密码是否正确
		if(StringUtils.isBlank(pwd) || 
				!new Sha256Hash(pwd).toHex().equals(user.getCashPwd())){
			return ResultVM.error(Constants.error_pwd,"密码输入错误，请重新输入！");
		}
		
		//新增付款单记录
		RecDis recDis = new RecDis();
		recDis.setId(UUID.randomUUID().toString().replace("-", ""));
		recDis.setBankCardId(json.getString("bankCardId"));
		recDis.setRecDisStatus(Constants.rec_dis_status_free); //自由
		recDis.setRecIdsCode(codeService.getBillCode(merChant.getMerchantCode(),true)); //付款单号
		recDis.setRecDisType(Constants.rec_dis_type_pay); //付款单
		recDis.setCommdityId(merChant.getId()); //商户ID
		recDis.setRecDisPrice(recDisPrice); //申请提现金额
		recDis.setAskTime(date); //申请提现时间
		recDis.setEffectTime(date);//生效时间
		recDis.setTouchTime(date);//制单时间
		recDis.setTouchBy(json.getString("id"));//制单人
		recDis.setCreateUserId(json.getString("id"));
		recDis.setCreateTime(date);
    	
 		if(service.insert(recDis)){
 			return ResultVM.ok(recDis);
 		}else{
 			return ResultVM.error();
 		}
 		
    }
    
    /**
     * 获取该商户的收入和支出金额
     * @param commdityId 商户ID
     * @return
     */
	@ApiOperation(value="获取该商户的收入和支出金额")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "commdityId", value = "商户ID", required = true,dataType = "String")
	   })
    @GetMapping(value="/getRecAndDisPrice")
    public ResultVM getRecAndDisPrice(String commdityId) {
    	
    	if(StringUtils.isBlank(commdityId)){
    		return ResultVM.error("商户Id不能为null!");
    	}
    	
    	JSONObject returnJson = new JSONObject();
    	
    	//收入金额（该商户所有收款单金额和）
    	returnJson.put("recPrice", service.findAllRecOrDis(commdityId, Constants.rec_dis_type_gat));
    	//支出金额（该商户所有付款单金额和）
    	returnJson.put("disPrice", service.findAllRecOrDis(commdityId, Constants.rec_dis_type_pay));
    	
    	return ResultVM.ok(returnJson);
    }
    
    /**
     * 校验数据
     * @param t
     * @return
     */
    private String checkData(JSONObject json){
    	
    	String msg = null;
    	
    	BigDecimal recDisPrice = json.getBigDecimal("recDisPrice");
    	if(recDisPrice==null){
    		msg = "请输入收付单金额！";
    	}
    	
    	BigDecimal price = json.getBigDecimal("price");
    	if(price==null){
    		msg = "请输入订单金额！";
    	}
    	
    	String commodityId = json.getString("commodityId");
    	if(StringUtils.isBlank(commodityId)){
    		msg = "请选择商户！";
    	}
    	
    	String userId = json.getString("userId");
    	if(StringUtils.isBlank(userId)){
    		msg = "请输入用户ID！";
    	}
    	
    	return msg;		
    }
    
    /**
     * 计算分成金额
     * @param price 需要分成的金额
     * @param divideRuleType  分成规则类型
     * @return
     */
    private Map<String,Object> getDividePrice(BigDecimal recDisPrice,Integer divideRuleType){
    	
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	BigDecimal price = BigDecimal.valueOf(0);
    	
		//确定分成范围
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("recDisPrice", recDisPrice);
		map.put("type", divideRuleType);
		
		log.info("<======分成规则列表请求参数：{}===========>",map);
		
		List<DivideRule> divideRuleList = divideRuleService.selectNewListByMap(map);
		
		log.info("<======符合要求的分成规则列表：{}===========>",divideRuleList);
		
		if(divideRuleList!=null && divideRuleList.size()>0){
			
			if(divideRuleList.size()>2){
				returnMap.put("flag",false);
				returnMap.put("msg", "该商户分成规则不符合规定！");
				return returnMap;
			}
			
			DivideRule divideRule = new DivideRule();
	    	
			if(divideRuleList.size()==1){
				//当查出分成规则只有一条时
				DivideRule oneDivideRule = divideRuleList.get(0);
				/*
				 * 如果该值不满足以下两个条件，则视为区间范围内
				 * 1,如果该值等于区间上限，且该区间上限为不包含,则该值不属于该区间
				 * 2,如果该值等于区间下限，且该区间下限为不包含,则该值不属于该区间
				 */
				if(!((oneDivideRule.getUpperLimit() == recDisPrice && 
						Constants.divideType_exclusive.equals(String.valueOf(oneDivideRule.getIsUpper())))
						||
					(oneDivideRule.getLowerLimit() == recDisPrice && 
						Constants.divideType_exclusive.equals(String.valueOf(oneDivideRule.getIsLower()))))){
					divideRule = oneDivideRule;
				}
				
			}else if(divideRuleList.size()==2){
				//当分成规则有两条时，说明该值一定在边界上（第一条的下限或第二条的上限）
				DivideRule firstDivideRule = divideRuleList.get(0);
				if(Constants.divideType_include.equals(String.valueOf(firstDivideRule.getIsLower()))){
					//第一条下限
					divideRule = firstDivideRule;
				}
				
				DivideRule secondDivideRule = divideRuleList.get(1);
				if(Constants.divideType_include.equals(String.valueOf(secondDivideRule.getIsUpper()))){
					//第二条上限
					divideRule = secondDivideRule;
				}
				
			}
			
			log.info("<======商户分成规则：{}===========>",divideRule);
			//计算分成金额
			if(Constants.divideType_ratio.equals(String.valueOf(divideRuleType))){
				//按比例
				BigDecimal ratio = divideRule.getRatio();
				if(ratio!=null){
					BigDecimal newRatio = ratio.divide(BigDecimal.valueOf(100));
					price = recDisPrice.multiply(newRatio);
					log.info("<======按比例分成后为：{}===========>",price);
				}
				
			}else if(Constants.divideType_price.equals(String.valueOf(divideRuleType))){
				//按金额
				if(divideRule.getPrice()!=null){
					price = divideRule.getPrice();
					log.info("<======按金额分成后为：{}===========>",price);
				}
			}
			
		}
		
		returnMap.put("flag", true);
		returnMap.put("msg", price);
		return returnMap;
		
    }
    
}
