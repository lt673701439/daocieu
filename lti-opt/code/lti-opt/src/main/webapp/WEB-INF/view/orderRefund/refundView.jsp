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
	<form id="dataForm2">
	<br>
		<table style="width:100%; height:1%; overflow: hidden;" border="0" cellpadding="0" cellspacing="0"  class="kTable" >
			<tr>
				<td class="kTableLabel lbl" style="height:10px;">订单编号:</td>
				<td style="height:10px;"><input name="orderCode" class="easyui-textbox" style="width:160px" readonly></td>
				<td class="kTableLabel lbl" style="height:10px;">用户昵称:</td>
				<td style="height:10px;"><input name="userNickname" class="easyui-textbox" style="width:160px" readonly></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl" style="height:10px;">下单时间:</td>
				<td style="height:10px;"><input name="createdTime" class="easyui-textbox" style="width:160px" readonly></td>
				<td class="kTableLabel lbl" style="height:10px;">屏幕名称:</td>
				<td style="height:10px;"><input name="screenName" class="easyui-textbox" style="width:160px" readonly></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl" style="height:10px;">支付金额:</td>
				<td style="height:10px;"><input name="payPrice" class="easyui-numberbox" precision="2" style="width:160px" readonly></td>
			</tr>
		</table> 
		<br>
	<table id="dataTableD" title="订单商品列表"></table>
	<input type="hidden" name="orderId">
	<input type="hidden" name="payType">
	<br>
	<table style="margin-left: 25px"; width:100%; height:1%; overflow: hidden;" border="0" cellpadding="0" cellspacing="0"  class="kTable" >
		<tr style="height:45px">
			<td class="kTableLabel lbl" style="height:10px;">退款金额:</td>
			<td style="height:10px;"><input name="backPrice"  class="easyui-numberbox" precision="2" max="100000.00" size="9" maxlength="9"  style="width:160px" readonly></td>
		</tr>
		<tr style="height:120px">
			<td class="kTableLabel lbl" style="height:10px;">退单原因:</td>
			<td style="height:10px;"><input name="backReason" class="easyui-textbox" data-options="multiline:true"  style="width:430px;height:100px;" readonly></td>
		</tr>
		<tr>
			<td class="kTableLabel lbl" style="height:10px;">备注:</td>
			<td style="height:10px;"><input name="backRemarks" class="easyui-textbox" data-options="multiline:true"  style="width:430px;height:100px;" readonly></td>
		</tr>
	</table>
	</form>
	<br>
    <div id="dlg-buttons" align="center">
   	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">退款</a>
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
		var url='${rootPath}/orderRefund/getOne?rowId=' + rowId;
		$('#dataForm2').form('load', url);
	}
});

//保存记录
function saveOrUpdate()
{
	var r = $('#dataForm2').form('validate');
	if(!r) {
		return false;
	}
	else
	{		
		$('#save').linkbutton('disable');
		$.post("${rootPath}/orderRefund/refund",$("#dataForm2").serializeArray(),
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
 
//初始化列表
	$('#dataTableD').datagrid({
		singleSelect : true,
		rownumbers:true,
	border:0,
		fitColumns:true,
		url : '${rootPath}/orderDetail/list?orderId=${rowId}',
		method : 'post',		
	idField : 'detail_id',//此处根据实际情况进行填写
	columns:[[	
				{field:'commodity_name',title:'商品名称',width:100},
				{field:'play_date',title:'播放日期',width:65},
				{field:'time_start',title:'开始',width:40},
				{field:'commodity_price',title:'价格(元)',width:50}
	]],
	onLoadSuccess : function(data) {
		$('#dataTableD').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
	}
});
</script>

</body>
</html>
