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
		<input type="hidden" name="promotionId">
		<input type="hidden" name="detailId">
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>促销明细编号:</label> -->
<!-- 	        <input name="promotionCode" class="easyui-textbox" disabled> -->
<!-- 		</div> -->
		<div class="fitem">
	    	<label>商品:</label>
<!-- 	        <input name="commodityId" class="easyui-textbox" style="width:240px" disabled> -->
			<input name="commodityName" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>促销商品个数:</label>
	        <input name="commodityNum" class="easyui-numberbox"  min="0" max="10000"
	        			validType="length[0,10000]" style="width:60px" disabled></input>
		</div>
		<div class="fitem">
	    	<label>打折比例:</label>
	    	<input name="discountRatio" class="easyui-numberbox" precision="2" min="0" max="100"
	        			validType="length[0,6]" style="width:60px"  disabled></input>（打折比例：取值[0,100],0为免费，100为原价；）
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
		var url='${rootPath}/promotionDetail/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});


</script>

</body>
</html>
