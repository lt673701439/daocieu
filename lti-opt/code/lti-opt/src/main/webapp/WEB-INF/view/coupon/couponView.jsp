<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@ include file="/include.jsp"%>
    <title></title>
</head>
<body>
<div>
	<form id="dataForm">
		<br>
		<input type="hidden" name="couponId" />
		<input type="hidden" name="publishTargetTypeValue" />
		<div class="fitem">
	    	<label>优惠码名称:</label>
	        <input name="couponName" id="couponName" class="easyui-textbox" style="width:180px" required="required" disabled>
		</div>
		<div class="fitem">
	    	<label>优惠码类型:</label>
			<select class="easyui-combobox" name="couponType"  style="width:80px" panelHeight="80px" disabled>
				<option value="0">特价</option>				        
				<option value="1">折扣</option>
				<option value="2">抵扣</option>
 			</select>
		</div>
		<div class="fitem">
	    	<label>优惠码:</label>
			<input name="couponCode"  class="easyui-textbox" style="width:180px" required="required" disabled>
		</div>
		<div class="fitem">
	    	<label>发布人:</label>
			<input name="publishBy"  class="easyui-textbox" style="width:180px" required="required" disabled>
		</div>
		<div class="fitem">
	    	<label>发布日期:</label>
			<input name="publishDate"  class="easyui-datebox" style="width:180px" required="required" disabled>
		</div>
		<div class="fitem">
			<label>发布对象类别:</label>
		    <input type="radio" name="publishTargetType" value="0" style="width:52px"  disabled>个人</input> 
		    <input type="radio" name="publishTargetType" value="1" style="width:52px"  disabled>商户</input> 
		</div>
		<div class="fitem" id="personal">
			<div class="fitem">
				<label>姓名:</label>
				 <input name="personalName" id="personalName" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
			    <label>身份证号:</label>
			     <input name="idCard" id="idCard" class="easyui-textbox" disabled>
		     </div>
		</div>
		<div class="fitem" id="merchant" >
			<div class="fitem">
				<label>商户名称:</label>
			 	<input name="merchantName" id="merchantName" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
				<label>营业执照编号:</label>
		    	 <input name="businessLicence"  id="businessLicence" class="easyui-textbox" disabled>
			</div>
		</div>
		<div class="fitem">
			<label>个数:</label>
			 <input name="couponNum" id="couponNum" class="easyui-numberbox" required="required" style="width:60px" disabled>
		</div>
		<div class="fitem">
	    	<label>有效期:</label>
	        <input name="validDate" class="easyui-datebox"  style="width:120px" required="required" editable = "editable" disabled>
		</div>
		<div class="fitem">
	    	<label>是否使用:</label>
	    	<select class="easyui-combobox" name="useType" style="width:80px" panelHeight="80px" disabled>
				<option value="0">已使用</option>				        
				<option value="1">未使用</option>
				<option value="2">已过期</option>
 			</select>
		</div>
		<div class="fitem">
	    	<label>使用日期:</label>
	        <input name="useTime" class="easyui-datetimebox"  style="width:150px" required="required" editable = "editable" disabled>
		</div>
		<div class="fitem">
	    	<label>订单编号:</label>
	    	<input name="orderCode" class="easyui-textbox"  style="width:200px" required="required" editable = "editable" disabled>
		</div>
	</form>
    <div id="dlg-buttons" align="center">
   	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;

jQuery(function(){ 

	//初始化下拉框-示例，请根据需要自定义实现
	
	rowId ='${rowId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/coupon/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
	
	//按类型显示
	var publishTargetTypeValue ='${publishTargetTypeValue}';

	if(publishTargetTypeValue == '0'){
		$("#personal").show();
		$("#merchant").hide();
	}else if(publishTargetTypeValue == '1'){
		$("#personal").hide();
		$("#merchant").show();
	}
	
});
 
</script>

</body>
</html>
