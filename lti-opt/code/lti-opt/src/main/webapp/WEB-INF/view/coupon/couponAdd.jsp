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
		<div class="fitem">
	    	<label>优惠码类型:</label>
	        <input name="couponTypeId" id="couponTypeId" type="hidden">
	        <input name="couponName" id="couponName" class="easyui-textbox" style="width:180px" required="required" readonly="readonly">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="width:30px"
	        	onclick="findPage('优惠码类型','CouponType.queryCouponTypeForPage','couponTypeId','couponTypeCode','couponName','coupon_type_id','coupon_type_code','coupon_name')"></a>
		</div>
		<div class="fitem">
			<label>发布对象类别:</label>
		    <input type="radio" name="publishTargetType" value="0" style="width:52px" checked onclick="javascript:changeLevel(this.value)" >个人</input> 
		    <input type="radio" name="publishTargetType" value="1" style="width:52px" onclick="javascript:changeLevel(this.value)" >商户</input> 
		</div>
		<div class="fitem" id="personal">
			<div class="fitem">
				<label>姓名:</label>
				 <input name="personalName" id="personalName" class="easyui-textbox" >
			</div>
			<div class="fitem">
			    <label>身份证号:</label>
			     <input name="idCard" id="idCard" class="easyui-textbox" >
		     </div>
		</div>
		<div class="fitem" id="merchant" style="display:none;">
			<div class="fitem">
				<label>商户名称:</label>
			 	<input name="merchantName" id="merchantName" class="easyui-textbox" >
			</div>
			<div class="fitem">
				<label>营业执照编号:</label>
		    	 <input name="businessLicence"  id="businessLicence" class="easyui-textbox" >
			</div>
		</div>
		<div class="fitem">
			<label>个数:</label>
			 <input name="couponNum" id="couponNum" class="easyui-numberbox" required="required" style="width:60px">
		</div>
		<div class="fitem">
	    	<label>有效期:</label>
	        <input name="validDate" class="easyui-datebox"  style="width:120px" required="required" editable = "editable">
		</div>
	</form>
    <div id="dlg-buttons" align="center">
   	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var changeLevel= function(value) { 
	
    if (value==0) {//个人
    	$("#personal").show();
		$("#merchant").hide();
		$('#merchantName').textbox('clear');
		$('#businessLicence').textbox('clear');
    }else if(value==1){//商户
    	$("#personal").hide();
		$("#merchant").show();
		$('#personalName').textbox('clear');
		$('#idCard').textbox('clear');
    }  
}  

//保存记录
function saveOrUpdate()
{
	var val=$('input:radio[name="publishTargetType"]:checked').val();
	
	//校验输入框
	if(val == null){
		$.messager.alert("提示", "请选择发布对象类型");
        return false;
    }else if(val == 0){
    	var personalName = $('#personalName').textbox('getValue');
    	var idCard = $('#idCard').textbox('getValue');
		if(personalName == null || personalName == ''){
			$.messager.alert("提示", "请您输入个人姓名");
			return false;
    	}
		if(idCard == null || idCard == ''){
			$.messager.alert("提示", "请您输入身份证号");
			return false;
    	}
    }else if(val == 1){
    	var merchantName = $('#merchantName').textbox('getValue');
    	var businessLicence = $('#businessLicence').textbox('getValue');
    	if(merchantName == null  || merchantName == ''){
    		$.messager.alert("提示", "请您输入商户名称");
			return false;
    	}
		if(businessLicence == null  || businessLicence == ''){
			$.messager.alert("提示", "请您输入营业执照编号");
			return false;
    	}
    }
	
	var couponNum = $('#couponNum').numberbox('getValue');
	
	if(couponNum <= 0){
		$.messager.alert("提示", "优惠码个数大于0");
		return false;
	}
	
	var r = $('#dataForm').form('validate');
	if(!r) {
		return false;
	}
	else
	{		
		$('#save').linkbutton('disable');
		$.post("${rootPath}/coupon/publish",$("#dataForm").serializeArray(),
		function(data)
		{			
			if(data.result == 'true' || data.result == true)
			{
			 	$.messager.alert("提示", data.msg);
				goBack(1);
			}
			else
			{
				$.messager.alert("提示", data.msg);
				$('#save').linkbutton('enable');
			}
		});
	}
}
 
</script>

</body>
</html>
