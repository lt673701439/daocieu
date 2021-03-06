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
	<br>
	<form id="dataForm">
	    <div class="fitem">
	    	<input name="customId" type="hidden">
	    	<div class="fitem">
		    	<label>定制类型:</label>
		    	<select class="easyui-combobox" name="customType" style="width:80px" panelHeight="60px" editable="false" readonly>
						<option value="1">自制上传</option>
						<option value="2">DIY排版</option>
						<option value="3">定制设计</option>
				 </select>
			</div>
	    	<label>价格:</label>
	        <input name="customPrice" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        												prefix="￥" validType="length[0,10]" style="width:120px" readonly></input> 元
		</div>
	</form>
	<br>
    <div id="dlg-buttons" style="margin-left: 140px;">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack(0)" style="width:90px">关闭</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;

jQuery(function(){ 
	
	rowId ='${customId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/confCustom/getOne?rowId='+rowId;
		$('#dataForm').form('load', url);
	}
	
});

</script>

</body>
</html>
