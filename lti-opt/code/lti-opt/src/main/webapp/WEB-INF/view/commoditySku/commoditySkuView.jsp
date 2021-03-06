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
		<input type="hidden" name="commodityId">
		<input type="hidden" name="skuId">
		<div class="fitem">
	    	<label>sku编号:</label>
	        <input name="skuCode" class="easyui-textbox" disabled>
		</div>
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>sku名称:</label> -->
<!-- 	        <input name="skuName" class="easyui-textbox" validType="length[0,50]" style="width:240px" required="required"> -->
<!-- 		</div> -->
		<div class="fitem">
		    <label>sku状态:</label>
			<select class="easyui-combobox" name="skuStatus" style="width:80px" panelHeight="60px" required="required" disabled>
				<option value="1">正常</option>
				<option value="2">下架</option>
			</select>
		</div>
		<div class="fitem">
	    	<label>sku价格:</label>
	        <input name="skuPrice" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        												prefix="￥" validType="length[0,10]" style="width:120px" required="required" disabled></input> 元
		</div>
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>模板ID:</label> -->
<!-- 	        <input name="templateId" class="easyui-textbox" validType="length[0,32]" style="width:240px"> -->
<!-- 		</div> -->
		<div class="fitem">
	    	<label>祝福语类型ID:</label>
	        <input name="typeId" class="easyui-textbox" validType="length[0,32]" style="width:240px" required="required" disabled>
		</div>
		<div class="fitem">
	    	<label>祝福语ID:</label>
	        <input name="benisonId" class="easyui-textbox" validType="length[0,32]" style="width:240px" disabled>
		</div>
		<div class="fitem">
	    	<label>sku描述:</label>
	        <input name="skuName" class="easyui-textbox" validType="length[0,100]" style="width:400px" disabled>
		</div>

	</form>
    <div id="dlg-buttons" align="center">
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
		var url='${rootPath}/commoditySku/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});
</script>

</body>
</html>
