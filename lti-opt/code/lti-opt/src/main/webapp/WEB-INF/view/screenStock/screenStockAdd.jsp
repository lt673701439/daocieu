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
	        <!-- <div class="fitem">
		    	<label>屏幕Id:</label>
		        <input name="screenId" class="easyui-textbox"  required="required">
			</div> -->
			<div class="fitem">
		    	<label>屏幕:</label>
		        <input name="screenId" id="screenId" type="hidden">
		        <input name="screenName" id="screenName" class="easyui-textbox" style="width:200px" required="required">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="width:30px"
		        	onclick="findPage('屏幕','Screen.queryScreenByConForPage','screenId','screenCode','screenName','screen_id','screen_code','screen_name')"></a>
			</div>
			<div class="fitem">
		    	<label>开始日期:</label>
		        <input name="startDate" class="easyui-datebox" style="width:120px" required="required" editable = "editable">
			</div>
			<div class="fitem">
		    	<label>结束日期:</label>
		        <input name="endDate" class="easyui-datebox" style="width:120px" required="required" editable = "editable">
			</div>
			<div class="fitem">
		    	<label>开始时间:</label>
		        <input name="startTime" class="easyui-timespinner" style="width:80px" required="required">
			</div>
			<div class="fitem">
		    	<label>结束时间:</label>
		        <input name="endTime" class="easyui-timespinner" style="width:80px" required="required">
			</div>
	</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack(0)" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

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
		$.post("${rootPath}/screenStock/save",$("#dataForm").serializeArray(),
		function(data)
		{			
			if(data.result == 'true' || data.result == true)
			{
			 	$.messager.show({title:'提示',msg:data.msg,showType:'show'});
				goBack(1);
			}
			else
			{
				$.messager.alert('提示',data.msg,'error');
				$('#save').linkbutton('enable');
			}
		});
	}
}
 
// //返回父页面  
// function goBack(flag){
// 	parent.returnParent(flag);
// }
</script>

</body>
</html>
