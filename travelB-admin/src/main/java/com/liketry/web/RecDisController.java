package com.liketry.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liketry.domain.Merchant;
import com.liketry.domain.RecDis;
import com.liketry.service.CodeService;
import com.liketry.service.MerChantService;
import com.liketry.service.RecDisService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.util.ShiroUtils;
import com.liketry.web.BaseController;
import com.liketry.web.vm.ResultVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * author pengyy
 * 收付单
 */
@Api(value="收付单服务",description="供平台管理调用")
@RestController
@RequestMapping("sys/rec_dis")
public class RecDisController extends BaseController<RecDisService, RecDis> {
	

  @Autowired
  MerChantService merChantService;
  
  @Autowired
  CodeService codeService;
	
  /**
    * 新增
    * @param t
    * @return
    */
  @ApiOperation(value="新增收付单")
  @ApiImplicitParams({
 	 @ApiImplicitParam(name = "t", value = "收付单实体对象", required = true,dataType = "RecDis")
  })
   @PostMapping
   public ResultVM create(@RequestBody RecDis t) {
	  
	   //查询商户，确定分成规则
	   Merchant merChant = merChantService.selectById(t.getCommdityId());
	   if(merChant == null){ 
	      return ResultVM.error("请选择商户！");
	   }
	
	   //制单信息
	   t.setTouchTime(new Date());
	   t.setRecIdsCode(codeService.getBillCode(merChant.getMerchantCode(),true));
	   t.setTouchBy(ShiroUtils.getUserId());
	   t.setRecDisStatus(Constants.rec_dis_status_free);
	   t.setRecDisType(Constants.rec_dis_type_gat);
	   
	   t.setCreateUserId(ShiroUtils.getUserId());
	   t.setCreateTime(new Date());
	   
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
  @ApiOperation(value="更新收付单")
  @ApiImplicitParams({
 	 @ApiImplicitParam(name = "t", value = "收付单实体对象", required = true,dataType = "RecDis")
  })
   @PutMapping
   public ResultVM update(@RequestBody RecDis t) {
	   
	   //校验状态
	   String msg = checkStatus(t);
	   if(msg!=null){
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
    * 删除
    * @param id
    * @return
    */
  @ApiOperation(value="删除收付单")
  @ApiImplicitParams({
 	 @ApiImplicitParam(name = "id", value = "收付单ID", required = true,dataType = "String",paramType="path")
  })
   @DeleteMapping("/{id}")
   public ResultVM delete(@PathVariable String id) {
	   
	   RecDis recDis = service.selectById(id);
	   
	   if(recDis!=null){
		   String msg = checkStatus(recDis);
		   if(msg!=null){
			   return ResultVM.error(msg);  
		   }
	   }
	   
       if(service.deleteById(id)){
           return ResultVM.ok();
       }else{
           return ResultVM.error();
       }
   }
   
   
   /**
    * 收付单审核(自由状态下的收款单)
    * @param id
    * @return
    */
  @ApiOperation(value="审核收付单")
  @ApiImplicitParams({
 	 @ApiImplicitParam(name = "id", value = "收付单ID", required = true,dataType = "String",paramType="path"),
 	@ApiImplicitParam(name = "status", value = "审核状态", required = true,dataType = "String",paramType="path")
  })
   @GetMapping("/approve/{id}/{status}")
   public ResultVM approve(@PathVariable String id,@PathVariable String status) {
	   
	   RecDis recDis = service.selectById(id);
	   Date date = new Date();
	   
	   if(recDis!=null){
		   
		   if(!Constants.rec_dis_type_gat.equals(recDis.getRecDisType())){
			   return ResultVM.error("请选择收款单进行审核！");
		   }
		   
		   String msg = checkStatus(recDis);
		   if(msg!=null){
			   return ResultVM.error("该收付单已经审核过，请勿重复操作！");
		   }
		   
		   recDis.setRecDisStatus(status);
		   recDis.setEffectTime(date);
		   recDis.setUpdateTime(date);
		   recDis.setUpdateUserId(ShiroUtils.getUserId());
		   
		   if(service.updateById(recDis)){
	           return ResultVM.ok();
	       }else{
	           return ResultVM.error();
	       }
		   
	   }else{
		   return ResultVM.error("收付单记录不存在！");
	   }
   }
   
   
   /**
    * 确认转账
    * @param id
    * @return
    */
  @ApiOperation(value="确认转账")
  @ApiImplicitParams({
	  @ApiImplicitParam(name = "t", value = "收付单实体对象", required = true,dataType = "RecDis")
  })
   @PostMapping("/isTranAcount")
   public ResultVM isTranAcount(@RequestBody RecDis t) {
	   
	   RecDis recDis = service.selectById(t.getId());
	   Date date = new Date();
	   
	   if(recDis!=null){
		   
		   String msg = checkStatus(recDis);
		   if(msg!=null){
			   return ResultVM.error("该收付单已确认过，请勿重复操作！");
		   }
		   
		   if(!Constants.rec_dis_type_pay.equals(recDis.getRecDisType())){
			   return ResultVM.error("请选择付款单进行确认！");
		   }
		   
		   recDis.setRecDisStatus(t.getRecDisStatus());
		   recDis.setEffectTime(date);
		   recDis.setTransAccountTime(t.getTransAccountTime());
		   recDis.setUpdateTime(date);
		   recDis.setUpdateUserId(ShiroUtils.getUserId());
		   
		   Map<String,Object> map = service.updateRecAndMerchant(recDis);
		   
		   if(Boolean.valueOf(map.get("flag").toString())){
	           return ResultVM.ok();
	       }else{
	           return ResultVM.error(map.get("msg").toString());
	       }
		   
	   }else{
		   return ResultVM.error("收付单记录不存在！");
	   }
   }
   
   /**
    * 确认到账
    * @param id
    * @return
    */
  @ApiOperation(value="确认到账")
  @ApiImplicitParams({
	  @ApiImplicitParam(name = "t", value = "收付单实体对象", required = true,dataType = "RecDis")
  })
  @PostMapping("/isTranIntoAcount")
   public ResultVM isTranIntoAcount(@RequestBody RecDis t) {
	   
	   RecDis recDis = service.selectById(t.getId());
	   Date date = new Date();
	   
	   if(recDis!=null){
		   
		   if(!Constants.rec_dis_type_pay.equals(recDis.getRecDisType())){
			   return ResultVM.error("请选择付款单进行确认！");
		   }
		   
		   if(Constants.rec_dis_status_pass.equals(recDis.getRecDisStatus())&&recDis.getIntoAccountTime()==null){
			   
			   recDis.setRecDisStatus(t.getRecDisStatus());
			   recDis.setEffectTime(date);
			   recDis.setIntoAccountTime(t.getIntoAccountTime());
			   recDis.setUpdateTime(date);
			   recDis.setUpdateUserId(ShiroUtils.getUserId());
			   
			   if(service.updateById(recDis)){
				   return ResultVM.ok();
			   }else{
				   return ResultVM.error();
			   }
		   }else{
			   return ResultVM.error("请确认转账后再进行到账确认！");
		   }
		   
	   }else{
		   return ResultVM.error("收付单记录不存在！");
	   }
   }
   
   /**
    * 余额重算
    * @param commdityIds 商户ID串
    * @return
    */
  @ApiOperation(value="余额重算")
  @ApiImplicitParams({
 	 @ApiImplicitParam(name = "commdityIds", value = "商户ID串", required = true,dataType = "String"),
  })
   @GetMapping("/retryBalance")
   public ResultVM retryBalance(String commdityIds){
	   
	   if(StringUtils.isBlank(commdityIds)){
		 return ResultVM.error("商户ID串不能为空！");   
	   }
	   
	   List<String> commdityIdList = Arrays.asList(commdityIds.split(","));
	   List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
	   
	   for(String commdityId:commdityIdList){
		   
		   Merchant merchant = merChantService.selectById(commdityId);
		   if(merchant==null){
			   return ResultVM.error("商户不存在！");
		   }
		   
		   Map<String,Object> map = new HashMap<String,Object>();
		   
		   //商户所有收款单和
		   BigDecimal recPrice = service.findAllRecOrDis(commdityId, Constants.rec_dis_type_gat);
		   
		   //商户所有付款单和
		   BigDecimal disPrice = service.findAllRecOrDis(commdityId, Constants.rec_dis_type_gat);
		   
		   map.put("merchantId", commdityId);
		   map.put("merchantName", merchant.getMerchantShopname());
		   map.put("merchantBalance", merchant.getMerchantBalance());
		   map.put("retryBalance", recPrice.subtract(disPrice));
		   
		   mapList.add(map);
	   }
	   
	   return ResultVM.ok(mapList);
	   
   }
   
   /**
    * 余额更新
    * @param json串
    * @return
    */
  @ApiOperation(value="余额更新")
  @ApiImplicitParams({
 	 @ApiImplicitParam(name = "data", value = "json串：商户ID:commdityId,余额:price", required = true,dataType = "String"),
  })
  @PostMapping("/updateBalance")
   public ResultVM updateBalance(@RequestBody JSONObject data){
	  
	   if(data == null){
		   return ResultVM.error("数据不能为空");   
	   }
	   
	   List<Map<String,Object>> list = (List<Map<String,Object>>)data.get("data");
	   List<Merchant> merchantList = new ArrayList<Merchant>();
	   
	   for(Map<String,Object> map:list){
		   
		   String commodityId = String.valueOf(map.get("commdityId"));
		   BigDecimal price = new BigDecimal(String.valueOf(map.get("price")));
		   Merchant merchant = merChantService.selectById(commodityId);
		   if(merchant==null){
			   return ResultVM.error("商户不存在！");
		   }
		   
		   merchant.setMerchantBalance(price);
		   merchantList.add(merchant);
		   
	   }
	   
	   if(merChantService.updateBatchById(merchantList)){
		   return ResultVM.ok();
	   }else{
		   return ResultVM.error();
	   }
	   
   }
   
   /**
    * 校验收付单状态
    * @param t
    * @return
    */
   private String checkStatus(RecDis t){
	   
	   String msg = null;
	   String status = t.getRecDisStatus();
	   
	   if(!Constants.rec_dis_status_free.equals(status)){
		   msg = "收付单状态不是自有，不允许修改或删除！";
	   }
	   return msg;
   }
   
}
