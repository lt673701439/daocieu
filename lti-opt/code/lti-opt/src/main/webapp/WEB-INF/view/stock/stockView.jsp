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
		<input type="hidden" name="stockId">
		<table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0">
			<tr>
				<td class="kTableLabel lbl" >日期:</td>
				<td class="kTableLabel lbl"><input name="stockDate" class="easyui-textbox" disabled></td>
				<td class="kTableLabel lbl">开始时间:</td>
				<td class="kTableLabel lbl"><input name="startTime" class="easyui-textbox" disabled></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">结束时间:</td>
				<td class="kTableLabel lbl"><input name="endTime" class="easyui-textbox" disabled></td>
				<td class="kTableLabel lbl">商品名称:</td>
				<td class="kTableLabel lbl"><input name="commodityName" class="easyui-textbox" disabled></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">库存状态:</td>
				<td class="kTableLabel lbl">
					<select class="easyui-combobox" name="stockStatus" style="width:120px" panelHeight="150px" required="required"  disabled>
			            <option value=""></option>				        
						<option value="1">自由</option>
						<option value="2">锁定</option>
						<option value="3">占用</option>
			 		</select>
			 	</td>
				<td class="kTableLabel lbl">销量:</td>
				<td class="kTableLabel lbl"><input name="sales" class="easyui-textbox" disabled></td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">存量:</td>
				<td class="kTableLabel lbl"><input name="stock" class="easyui-textbox" disabled></td>
				<td class="kTableLabel lbl">排期状态:</td>
				<td class="kTableLabel lbl">
					<select class="easyui-combobox" name="scheduleStatus" style="width:150px" panelHeight="60px" required="required" disabled>
				            <option value=""></option>				        
							<option value="0">未排期</option>
							<option value="1">已排期</option>
				 	</select>
			 	</td>
			</tr>
		</table>
	</form>
	<br>
		<table id="stockDetailTable" title="库存明细列表" style="height: 260px"> </table>
	<br>
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
		var url='${rootPath}/stock/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
	
	//初始化库存明细列表
	$('#stockDetailTable').datagrid({
   		iconCls:'icon-tip',
		singleSelect : true,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		border:0,
		url:'${rootPath}/stockDetail/getStockDetailListById?stockId='+rowId,
		method : 'post',		
		idField : 'detail_id',//此处根据实际情况进行填写
		columns:[[
							{field:'detail_id',title:'明细ID',width:80,hidden:true},
							{field:'stock_id',title:'库存ID',width:80,hidden:true},
							{field:'order_id',title:'订单ID',width:80,hidden:true},
							{field:'order_code',title:'订单编号',width:80},
							{field:'start_time',title:'开始时间',width:80},
							{field:'end_time',title:'结束时间',width:80},
// 							{field:'created_time',title:'创建时间',width:80},
// 							{field:'created_by',title:'创建人',width:80},
						//注：最后一行后面的逗号要去掉
		]],
		onLoadSuccess : function(data) {
			$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
});

</script>

</body>
</html>
