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
		<input type="hidden" name="sellerId">
		<input type="hidden" name="version">
		<div class="fitem">
	    	<label>商户名称:</label>
	        <input name="sellerName" class="easyui-textbox" validType="length[0,50]" required="required" disabled>
		</div>
		<div class="fitem">
	    	<label>商户编号:</label>
	        <input name="sellerCode" class="easyui-textbox" disabled>
		</div>
		<!-- <div class="fitem">
	    	<label>联系人:</label>
	        <input name="sellerLinkman" class="easyui-textbox" validType="length[0,50]" required="required">
		</div> -->
		<div class="fitem">
	    	<label>商户类型:</label>
	    	<input class="easyui-combobox" id="sellerType" name="sellerType" style="width:100px" editable="editable" required="required" panelHeight="auto"
		 	url="${rootPath}/getDictCombox?dictType=MultiState" valueField="dictId" textField="dictName">
		</div>
		<!-- <div class="fitem">
	    	<label>商户服务电话:</label>
	        <input name="sellerTel" class="easyui-textbox" validType="length[0,30]" required="required">
		</div>
		<div class="fitem">
	    	<label style="vertical-align:top;">商户地址:</label>
			<textarea name="sellerAddress" style=" border:1px solid #99bbe8;"  rows="4" cols="31" class="easyui-validatebox" validType="length[0,200]" required="required" ></textarea>
		</div>
		<div class="fitem">
	    	<label>站点:</label>
	    	<input class="easyui-combobox" name="site" style="width:100px" required="required" panelHeight="auto"
		 	url="${rootPath}/getDictCombox?dictType=SITE" valueField="dictId" textField="dictName">
		</div>
		<div class="fitem">
	    	<label>开户银行:</label>
	        <input name="bankName" class="easyui-textbox" validType="length[0,100]" required="required">
		</div>
		<div class="fitem">
	    	<label>银行账号:</label>
	        <input name="bankAccount" class="easyui-textbox" validType="length[0,50]" required="required">
		</div>
		
		<div class="fitem">
	    	<label>开户名:</label>
	        <input name="accountHolder" class="easyui-textbox" validType="length[0,50]" required="required">
		</div>
		<div class="fitem">
	    	<label>商户状态:</label>
	    	<input class="easyui-combobox" name="sellerState" style="width:100px" required="required" panelHeight="auto"
		 	url="${rootPath}/getDictCombox?dictType=status" valueField="dictId" textField="dictName">
		</div> -->
	</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="resetPwd" onclick="resetPwd()" style="width:90px">重置密码</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var sellerId;
jQuery(function(){ 
	// 注意：不要读取缓存
	jQuery.ajaxSetup({
		cache : false
	});
	
	sellerId ='${sellerId}';
	
	if (sellerId != null && sellerId != "" && sellerId!=0){
		var url='${rootPath}/seller/getOne?sellerId=' + sellerId;
		$('#dataForm').form('load', url);
	}
});

//保存记录
function saveOrUpdate()
{
	var r = $('#dataForm').form('validate');
	if(!r) {
		return false;
	}
	else
	{		
		$('#save').linkbutton('disable');
		$.post("${rootPath}/seller/save",$("#dataForm").serializeArray(),
		function(data)
		{			
			if(data.result == 'true' || data.result == true)
			{
			 	$.messager.alert("提示", data.msg);
				goBack(1);
			}
			else
			{
// 				$.messager.alert('提示',data.msg,'error');
				$.messager.alert("提示", data.msg);
				$('#save').linkbutton('enable');
			}
		});
	}
}
 

// 重置密码
function resetPwd(){
	$.post("${rootPath}/seller/resetPwd",$("#dataForm").serializeArray(),
	function(data){			
		if(data.result == 'true' || data.result == true){
		 	$.messager.alert("提示", data.msg);
			goBack(1);
		}else{
			$.messager.alert("提示", data.msg);
		}
	});
}

//返回父页面  
// function goBack(flag){
// 	parent.returnParent(flag);
// }
</script>

</body>
</html>
