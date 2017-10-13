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
		    <input name="benisonId" id = "benisonId" type="hidden">
			<!-- <div class="fitem">
		    	<label>祝福语类型ID:</label>
		        <input name="typeId" class="easyui-textbox" disabled>
			</div> --> 
			<div class="fitem">
		    	<label>祝福语类型:</label>
		        <input name="typeName" id="typeName" class="easyui-textbox" style="width:200px" disabled>
			</div>
			<div class="fitem">
		    	<label>祝福语编号:</label>
		        <input name="benisonCode" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>祝福语内容:</label>
		        <input name="benisonContent" class="easyui-textbox" data-options="multiline:true" style="width:230px;height:100px" validType="length[0,200]" required="required">
			</div>
			<div class="fitem">
		    	<label>规则内容:</label>
		        <input name="ruleContent" class="easyui-textbox" data-options="multiline:true" style="width:230px;height:100px" validType="length[0,200]">
			</div>
			<div class="fitem" style="margin-left: 35px;color:#F00">
				<span>规则内容中符号的匹配规则：</span>
			</div>
			<div class="fitem" style="margin-left: 70px;color:#F00">
				<span >&nbsp;&nbsp;/n&nbsp;&nbsp;&nbsp; ：换行</span><br>
				<span >&nbsp;&nbsp;/r&nbsp;&nbsp;&nbsp; ：判断长度换行</span><br>
				<span>@blessName ：祝福对象</span><br>
				<span>@writeName ：落款人</span><br>
				<span>@--@：文本域宽度（如：@-300-@）</span><br>
				<span>@**@：每一行的起始横坐标（如：@*90*@）</span><br>
			</div>
			</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack(0)" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;
jQuery(function(){ 

	rowId ='${benisonId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/benison/getOne?rowId=' + rowId;
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
		$.post("${rootPath}/benison/save",$("#dataForm").serializeArray(),
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
 
//返回父页面  
// function goBack(flag){
// 	parent.returnParent(flag);
// }
</script>

</body>
</html>
