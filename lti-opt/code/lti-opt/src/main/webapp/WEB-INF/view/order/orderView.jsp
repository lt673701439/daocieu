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
	<div>
		<form id="dataForm">
		<table style="width:100%; height:1%; overflow: hidden;" border="0" cellpadding="0" cellspacing="0"  class="kTable" >
			<tr><input type="hidden" name="userId">
				<td class="kTableLabel lbl" style="height:10px;">订单编号:</td>
				<td style="height:10px;"><input name="orderCode" class="easyui-textbox" style="width:160px" readonly></td>
				<td class="kTableLabel lbl" style="height:10px;">交易单号:</td>
				<td style="height:10px;"><input name="transactionNo" class="easyui-textbox" style="width:160px" readonly></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl"  style="height:10px;">用户昵称:</td>
				<td style="height:10px;"><input name="userNickname" class="easyui-textbox" style="width:120px" readonly></td>
				<td class="kTableLabel lbl"  style="height:10px;">祝福对象:</td>
				<td style="height:10px;"><input name="blessName" class="easyui-textbox" style="width:120px" readonly></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl" style="height:10px;">联系电话:</td>
				<td style="height:10px;"><input name="userPhone" class="easyui-textbox" style="width:120px" readonly></td>
				<td class="kTableLabel lbl" style="height:10px;">落款人:</td>
				<td style="height:10px;"><input name="writeName" class="easyui-textbox" style="width:120px" readonly></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl" style="height:10px;">订单类型:</td>
				<td style="height:10px;"><select class="easyui-combobox" name="orderType" style="width:80px" panelHeight="60px" disabled>
			            <option value=""></option>				        
						<option value="0">普通</option>
						<option value="1">自制上传</option>
						<option value="2">DIY排版</option>
						<option value="3">定制设计</option>
			 		</select></td>
				<td class="kTableLabel lbl" style="height:10px;">订单状态:</td>
				<td style="height:10px;"><select class="easyui-combobox" name="orderStatus" style="width:80px" panelHeight="80px" disabled>
			            <option value=""></option>				        
						<option value="0">草稿</option>
						<option value="1">待支付</option>
						<option value="2">已支付</option>
						<option value="3">已播放</option>
						<option value="4">已取消</option>
						<option value="5">已退单</option>
						<option value="6">已退款</option>
			 		</select></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl" style="height:10px;">订单总价:</td>
				<td style="height:10px;"><input name="totalPrice" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        							prefix="￥" validType="length[0,10]" style="width:120px" required="required" readonly></input> 元</td>
				<td class="kTableLabel lbl" style="height:10px;">退单人:</td>
				<td style="height:10px;"><input name="backBy" class="easyui-textbox" style="width:120px" readonly></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl" style="height:10px;">模版:</td>
				<td style="height:10px;"><input name="templateName" class="easyui-textbox" style="width:160px" readonly></td>
				<td class="kTableLabel lbl" style="height:10px;">退单时间:</td>
				<td style="height:10px;"><input name="backTime" class="easyui-textbox" style="width:160px" readonly></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl" style="height:10px;">退款金额:</td>
				<td style="height:10px;"><input name="backPrice" class="easyui-textbox" style="width:160px" readonly></td>
				<td class="kTableLabel lbl" style="height:10px;"></td>
				<td style="height:10px;"></td>
			</tr>
			
    	</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTableD" title="订单商品列表" height="200px"></table>
	</div>
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
		var url='${rootPath}/order/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
	
	//初始化列表
   	$('#dataTableD').datagrid({
   		singleSelect : true,
   		rownumbers:true,
//    		pagination:true,
		border:0,
   		fitColumns:true,
   		url : '${rootPath}/orderDetail/list?orderId=${rowId}',
   		method : 'post',		
		idField : 'detail_id',//此处根据实际情况进行填写
		columns:[[
	          			// 注意：这里是字段名SELLER_CODE，不是变量名sellerCode
							{field:'commodity_code',title:'商品编号',width:120},
							{field:'commodity_name',title:'商品名称',width:100},
							{field:'commodity_price',title:'价格(元)',width:50},
							{field:'play_date',title:'播放日期',width:65},
							{field:'time_start',title:'开始',width:40},
							{field:'time_end',title:'结束',width:40},
							{field:'single_time',title:'单次(秒)',width:50},
							{field:'play_times',title:'次数',width:30}
// 							{field:'sku_code',title:'sku编号',width:140},
// 							{field:'sku_price',title:'sku价格(元)',width:60}
						//注：最后一行后面的逗号要去掉
		]],
		onLoadSuccess : function(data) {
			$('#dataTableD').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
});

</script>

</body>
</html>
