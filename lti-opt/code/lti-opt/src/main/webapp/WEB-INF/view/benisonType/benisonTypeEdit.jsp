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
		<input type="hidden" name=typeId id="typeId">
		<div class="fitem">
	    	<label>类型编号:</label>
	        <input name="typeCode" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>类型名称:</label>
	        <input name="typeName" class="easyui-textbox" data-options="required:true" validType="length[0,50]">
		</div>
		<div class="fitem">
		    <label>是否有效:</label>
			<select class="easyui-combobox" name="effectFlag" style="width:80px" panelHeight="60px" required="required">
				<option value="0">无效</option>
				<option value="1">有效</option>
			</select>
		</div>
		<div class="fitem">
	    	<label>排序:</label>
	        <input name="sortNum" class="easyui-numberbox" >
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

	//初始化下拉框-示例，请根据需要自定义实现
	
	rowId ='${typeId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/benisonType/getOne?rowId=' + rowId;
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
		$.post("${rootPath}/benisonType/save",$("#dataForm").serializeArray(),
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
