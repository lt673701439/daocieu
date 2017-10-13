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
		<input type="hidden" name="costId">
		<div class="fitem">
	    	<label>类型:</label>
	        <input name="costType" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>名称:</label>
	        <input name="costName" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>价格:</label>
	        <input name="costPrice" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        												prefix="￥" validType="length[0,10]" style="width:120px" disabled></input> 元
		</div>
		<div class="fitem">
	    	<label>序号:</label>
	        <input name="skuId" class="easyui-numberbox"  min="1" max="99"
	        			validType="length[0,2]" style="width:30px" disabled></input>
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
		var url='${rootPath}/commodityCost/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});

</script>

</body>
</html>
