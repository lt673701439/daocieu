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
		<input type="hidden" name="commodityId" value="${commodityId}">
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>sku名称:</label> -->
<!-- 	        <input name="skuName" class="easyui-textbox" validType="length[0,50]" style="width:240px" required="required"> -->
<!-- 		</div> -->
<!-- 		<div class="fitem"> -->
<!-- 		    <label>sku状态:</label> -->
<!-- 			<select class="easyui-combobox" name="skuStatus" style="width:80px" panelHeight="60px" required="required"> -->
<!-- 				<option value="1">正常</option> -->
<!-- 				<option value="2">下架</option> -->
<!-- 			</select> -->
<!-- 		</div> -->
		<div class="fitem">
	    	<label>祝福语类型ID:</label>
	        <input name="typeId" class="easyui-textbox" validType="length[0,32]" style="width:240px" required="required">
		</div>
		<div class="fitem">
	    	<label>祝福语ID:</label>
	        <input name="benisonId" class="easyui-textbox" validType="length[0,32]" style="width:240px">
		</div>
		<div class="fitem">
	    	<label>sku价格:</label>
	        <input name="skuPrice" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        												prefix="￥" validType="length[0,10]" style="width:120px" required="required"></input> 元
		</div>
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>模板ID:</label> -->
<!-- 	        <input name="templateId" class="easyui-textbox" validType="length[0,32]" style="width:240px"> -->
<!-- 		</div> -->
		<div class="fitem">
	    	<label>sku描述:</label>
	        <input name="skuName" class="easyui-textbox" validType="length[0,100]" style="width:400px">
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
		$.post("${rootPath}/commoditySku/save",$("#dataForm").serializeArray(),
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
