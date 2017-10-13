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
		<input type="hidden" name="userId">
		<div class="fitem">
	    	<label>openId:</label>
	        <input name="openId" class="easyui-textbox" data-options="required:true">
		</div>
		<div class="fitem">
	    	<label>昵称:</label>
	        <input name="userNickname" class="easyui-textbox" validType="length[0,50]" disabled>
		</div>
		<div class="fitem">
	    	<label>性别:</label>
	        <input name="userSex" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>电子邮箱:</label>
	        <input name="userMail" class="easyui-textbox" validType="length[0,64]" >
		</div>
		<div class="fitem">
	    	<label>电话:</label>
	        <input name="userPhone" class="easyui-textbox" validType="length[0,20]">
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
	// 注意：不要读取缓存
	jQuery.ajaxSetup({
		cache : false
	});
	
	rowId ='${rowId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/bUser/getOne?rowId=' + rowId;
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
		$.post("${rootPath}/bUser/save",$("#dataForm").serializeArray(),
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
