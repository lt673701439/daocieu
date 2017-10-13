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
<div class="easyui-panel" data-options="region:'north',collapsible:false,border : false" align="center">
	<br>
	<form id="dataForm" align="center" >
		<!-- <div class="fitem">
	    	<label>用户编号:</label>
	        <input name="userId" class="easyui-textbox" validType="length[0,50]" required="required" data-options="required:true,validType: ['unnormal']  ">
		</div> -->
		<table style="width:100%; height:1%; overflow: hidden;" border="0" 
			cellpadding="0" cellspacing="0"  class="kTable" >
			<tr>
				<td class="kTableLabel lbl">手机号：</td>
				<td>
					<input name="userPhone" class="easyui-textbox" required="required" >
				</td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">微信号：</td>
				<td>
					<input name="openId" class="easyui-textbox" required="required" >
				</td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">微信头像：</td>
				<td>
					<input name="userIcon" class="easyui-textbox" required="required">
				</td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">昵称：</td>
				<td>
					<input name="user" class="easyui-textbox" required="required" >
				</td>
			</tr>
		</table>
	</form>
	<br>
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
		$.post("${rootPath}/user/save",$("#dataForm").serializeArray(),
		function(data)
		{			
			if(data.result == 'true' || data.result == true)
			{
			 	$.messager.alert("提示"," 添加成功！");
				goBack(1);
			}
			else
			{
				$.messager.alert("提示", "openId已存在,请重新输入！");
				$('#save').linkbutton('enable');
			}
		});
	}
}
 
</script>

</body>
</html>
