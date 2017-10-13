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
		<div class="fitem">
	    	<label>商户名称:</label>
	        <input name="sellerName" class="easyui-textbox" validType="length[0,50]" required="required" data-options="required:true,validType: ['unnormal']  ">
		</div>
		<div class="fitem">
	    	<label>联系人:</label>
	        <input name="sellerLinkman" class="easyui-textbox" validType="length[0,50]" required="required" data-options="required:true,validType: ['unnormal']  ">
		</div>
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>商户类型:</label> -->
<!-- 	    	<input class="easyui-combobox" name="sellerType" value="1" style="width:100px" required="required" panelHeight="auto" -->
<%-- 		 	url="${rootPath}/getDictCombox?dictType=MultiState" valueField="dictId" textField="dictName"> --%>
<!-- 		</div> -->
		<div class="fitem">
		<label>商户类型:</label>
		<input class="easyui-combobox" name="sellerType" value="1" style="width:100px" editable="editable" required="required" panelHeight="auto"
				 	url="${rootPath}/getDictCombox?dictType=MultiState" valueField="dictId" textField="dictName">
		</div>
		<div class="fitem">
	    	<label>商户服务电话:</label>
	        <input name="sellerTel" class="easyui-textbox" validType="length[0,30]" required="required" data-options="required:true,validType: ['phoneAndMobile']  ">
		</div>
		<div class="fitem">
	    	<label>商户服务手机:</label>
	        <input name="sellerMobile" class="easyui-textbox" validType="length[0,30]" required="required" data-options="required:true,validType: ['mobile']  ">
		</div>
		<div class="fitem">
	    	<label style="vertical-align:top;">商户地址:</label>
			<textarea name="sellerAddress" style=" border:1px solid #99bbe8;"  rows="4" cols="31" class="easyui-validatebox" validType="length[0,200]"></textarea>
		</div>
		<div class="fitem">
	    	<label>站点:</label>
			<input class="easyui-combobox" name="site" value="010" style="width:100px" editable="editable" required="required" panelHeight="auto"
		 	url="${rootPath}/getDictCombox?dictType=SITE" valueField="dictId" textField="dictName">
<!-- 	    	<input class="easyui-combobox" id="site" name="site" value="001" style="width:100px" required="required"> -->
		</div>
		<div class="fitem">
	    	<label>开户银行:</label>
	        <input name="bankName" class="easyui-textbox" validType="length[0,100]" data-options="required:true,validType: ['unnormal']  ">
		</div>
		<div class="fitem">
	    	<label>银行账号:</label>
	        <input name="bankAccount" class="easyui-textbox" validType="length[0,50]" data-options="required:true,validType: ['integer']  ">
		</div>
		
		<div class="fitem">
	    	<label>开户名:</label>
	        <input name="accountHolder" class="easyui-textbox" validType="length[0,50]" data-options="required:true,validType: ['unnormal']  ">
		</div>
		<div class="fitem">
	    	<label>登录名:</label>
	        <input name="loginName" class="easyui-textbox" validType="length[0,30]" required="required" data-options="required:true,validType: ['username']  ">
		</div>
		<div class="fitem">
	    	<label>初始密码:</label>
	        <input name="loginInitPwd" type="password" class="easyui-textbox" validType="length[0,30]" required="required" data-options="required:true,validType: ['unnormal']  ">
		</div>
	</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

jQuery(function(){ 
	
	jQuery.ajaxSetup({
		cache : false
	});
	
// 	$('#site').combobox({
//         url:'${rootPath}/getDictCombox?dictType=SITE',    
//         valueField:'dictId',    
//         textField:'dictName',
//         panelWidth: 100,
//         panelHeight:'auto'
//  }); 
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
// 			 	$.messager.show({title:'提示',msg:data.msg,showType:'show'});
			 	$.messager.alert("提示"," 添加成功！");
				goBack(1);
			}
			else
			{
// 				$.messager.alert('提示',data.msg,'error');
				$.messager.alert("提示", " 登录名已存在,请重新输入！");
				$('#save').linkbutton('enable');
			}
		});
	}
}
 
//返回父页面  
// function goBack(flag){
// 	parent.returnParent(flag);
// }
</script>

</body>
</html>
