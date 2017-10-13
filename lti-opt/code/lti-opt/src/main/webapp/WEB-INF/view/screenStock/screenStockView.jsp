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
	        <input name="stockId" id="stockId"  type="hidden">
	        <div class="fitem">
		    	<label>屏幕名称:</label>
		        <input name="screenName" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>开始日期:</label>
		        <input name="startDate" class="easyui-datebox" style="width:120px" disabled>
			</div>
			<div class="fitem">
		    	<label>结束日期:</label>
		        <input name="endDate" class="easyui-datebox" style="width:120px" disabled>
			</div>
			<div class="fitem">
		    	<label>开始时间:</label>
		        <input name="startTime" class="easyui-timespinner" style="width:80px" disabled>
			</div>
			<div class="fitem">
		    	<label>结束时间:</label>
		        <input name="endTime" class="easyui-timespinner" style="width:80px" disabled>
			</div>
	</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack(0)" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;
jQuery(function(){ 

	rowId ='${stockId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/screenStock/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});

// //返回父页面  
// function goBack(flag){
// 	parent.returnParent(flag);
// }
</script>

</body>
</html>
