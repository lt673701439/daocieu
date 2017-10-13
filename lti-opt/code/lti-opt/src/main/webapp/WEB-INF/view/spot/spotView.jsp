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
		<input type="hidden" name="spotId" id="spotId">
		<div class="fitem">
	    	<label>景点编号:</label>
	        <input name="spotCode" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>景点名称:</label>
	        <input name="spotName" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>景点状态:</label>
	    	<select class="easyui-combobox" name="spotStatus" style="width:80px" panelHeight="60px" disabled>
			            <option value=""></option>				        
						<option value="0">失效</option>
						<option value="1">有效</option>
			 </select>
		</div>
		<div class="fitem">
	    	<label>省:</label>
	        <input name="spotProvince" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>市:</label>
	        <input name="spotCity" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>详细地址:</label>
	        <input name="spotAddress" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>景点描述:</label>
	        <input name="spotDescription" class="easyui-textbox" disabled>
		</div>
	</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;
jQuery(function(){ 

	rowId ='${spotId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/spot/getOne?rowId=' + rowId;
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
