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
		<input type="hidden" name="orderId">
		<div class="fitem">
	    	<label>订单编号:</label>
	        <input name="orderCode" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>交易单号:</label>
	        <input name="transactionNo" class="easyui-textbox">
		</div>
		<div class="fitem">
		    <label>订单类型:</label>
			<select class="easyui-combobox" name="orderType" style="width:80px" panelHeight="60px" disabled >
			            <option value=""></option>				        
						<option value="0">普通</option>
						<option value="1">自制上传</option>
						<option value="2">DIY排版</option>
						<option value="3">定制设计</option>
			</select>
		</div>
		<div class="fitem">
		    <label>订单状态:</label>
			<select class="easyui-combobox" name="orderStatus" style="width:80px" panelHeight="180px" required="required">
			            <option value=""></option>				        
						<option value="0">草稿</option>
						<option value="1">待支付</option>
						<option value="2">已支付</option>
						<option value="3">已播放</option>
						<option value="4">已取消</option>
						<option value="5">已退单</option>
						<option value="6">已退款</option>
			</select>
		</div>
		<div class="fitem">
	    	<label>用户昵称:</label>
	        <input name="userNickname" class="easyui-textbox">
		</div>
		<div class="fitem">
	    	<label>联系电话:</label>
	        <input name="userPhone" class="easyui-textbox">
		</div>
		<div class="fitem">
	    	<label>祝福对象名称:</label>
	        <input name="blessName" class="easyui-textbox">
		</div>
		<div class="fitem">
	    	<label>落款人名称:</label>
	        <input name="writeName" class="easyui-textbox">
		</div>
		<div class="fitem">
	    	<label>订单总价:</label>
	        <input name="totalPrice" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        												prefix="￥" validType="length[0,10]" style="width:120px" required="required"></input> 元
		</div>
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>支付价格:</label> -->
<!-- 	        <input name="payPrice" class="easyui-textbox"> -->
<!-- 		</div> -->
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
		var url='${rootPath}/order/getOne?rowId=' + rowId;
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
		$.post("${rootPath}/order/save",$("#dataForm").serializeArray(),
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
