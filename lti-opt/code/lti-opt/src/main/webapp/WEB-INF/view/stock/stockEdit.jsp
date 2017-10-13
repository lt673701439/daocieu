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
		<input type="hidden" name="stockId">
		<div class="fitem">
	    	<label>日期:</label>
	        <input name="stockDate" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>开始时间:</label>
	        <input name="startTime" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>结束时间:</label>
	        <input name="endTime" class="easyui-textbox" disabled>
		</div>
				<div class="fitem">
	    	<label>商品名称:</label>
	        <input name="commodityName" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
		    <label>库存状态:</label>
			 <select class="easyui-combobox" name="stockStatus" style="width:80px" panelHeight="60px" required="required">
			            <option value=""></option>				        
						<option value="1">自由</option>
						<option value="2">锁定</option>
						<option value="3">占用</option>
			 </select>
		</div>
		<div class="fitem">
	    	<label>销量:</label>
	        <input name="sales" class="easyui-textbox">
		</div>
		<div class="fitem">
	    	<label>存量:</label>
	        <input name="stock" class="easyui-textbox">
		</div>
		<div class="fitem">
	    	<label>排期状态:</label>
	        <select class="easyui-combobox" name="scheduleStatus" style="width:80px" panelHeight="60px" required="required">
			            <option value=""></option>				        
						<option value="0">未排期</option>
						<option value="1">已排期</option>
			 </select>
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
		var url='${rootPath}/stock/getOne?rowId=' + rowId;
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
		$.post("${rootPath}/stock/save",$("#dataForm").serializeArray(),
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
