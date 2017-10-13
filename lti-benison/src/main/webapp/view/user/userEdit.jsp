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
<div class="easyui-panel" data-options="region:'north',collapsible:false,border : false" align="center">
	<br>
	<form id="dataForm" align="center" >
	
		<input type="hidden" name="userId" />
		
		<table style="width:100%; height:1%; overflow: hidden;" border="0" 
			cellpadding="0" cellspacing="0"  class="kTable" >
			<tr>
				<td class="kTableLabel lbl">手机号：</td>
				<td>
					<input name="userPhone" class="easyui-textbox"  disabled="disabled">
				</td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">微信号：</td>
				<td>
					<input name="openId" class="easyui-textbox" disabled="disabled">
				</td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">微信头像：</td>
				<td>
					<img src="#">
				</td>
			</tr>
			<tr>
				<td class="kTableLabel lbl">昵称：</td>
				<td>
					<input name="user" class="easyui-textbox" >
				</td>
			</tr>
		</table>
	</form>
	<br>
	<div data-options="region:'center',border:false" style="overflow: hidden; ">
		<table id="orderTable" title="订单列表" height="300px"></table>
	</div>
	<br>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

jQuery(function(){ 
	
	jQuery.ajaxSetup({
		cache : false
	});
	
	var userId ='${userId}';
	
	if (userId != null && userId != "" && userId!=0){
		var url='${rootPath}/user/getOne?userId=' + userId;
		$('#dataForm').form('load', url);
	}
	
	//初始化订单列表
   	$('#orderTable').datagrid({
   		singleSelect : true,
   		rownumbers:true,
   		pagination:true,
   		fitColumns:true,
   		url : '${rootPath}/order/listByUserId?userId='+userId,
   		method : 'post',		
		idField : 'user_id',//此处根据实际情况进行填写
		columns:[[
						{field : 'ck',checkbox : true},
	          			{field:'orderId',title:'订单ID',align:'center',width:80,hidden:true},
						{field:'orderCode',title:'订单编号',align:'center'},
						{field:'orderStatus',title:'订单状态',align:'center'},
						{field:'userId',title:'用户ID',align:'center',width:60,hidden:true},
						{field:'user',title:'昵称',width:60,align:'center',hidden:true},
						{field:'userPhone',title:'用户电话',align:'center',width:80,hidden:true},
						{field:'blessName',title:'祝福对象名称',align:'center'},
						{field:'writeName',title:'落款人名称',align:'center',width:80,hidden:true},
						{field:'templateId',title:'祝福模板ID',align:'center',width:80,hidden:true},
						{field:'totalPrice',title:'总价',align:'center'},
						{field:'payType',title:'支付方式',align:'center',width:80,hidden:true},
						{field:'payTime',title:'支付时间',align:'center',width:80,hidden:true},
						{field:'payPrice',title:'支付价格',align:'center',width:80,hidden:true},
						{field:'backTime',title:'退单时间',align:'center',width:80,hidden:true},
						{field:'resultUrl',title:'结果图URL',align:'center',width:80,hidden:true},
						{field:'createdTime',title:'创建日期',align:'center',width:80,hidden:true},
						{field:'createdBy',title:'创建人',align:'center',width:80,hidden:true},
						{field:'modifiedTime',title:'修改日期',align:'center',width:80,hidden:true},
						{field:'modifiedBy',title:'修改人',align:'center',width:80,hidden:true},
						{field:'version',title:'版本号',align:'center',width:80,hidden:true},
						{field:'delflag',title:'删除标记',align:'center',width:80,hidden:true},
						/* {
							field : 'operate',
							title : '操作',
							width : 100,
							formatter : function(value, row,index) {
								return "<a href='#' onclick=viewrow('"+row.userId+"') style='margin-left:20px'>[查看详细信息]</a>"
								+"<a href='#' onclick=editrow('"+row.userId+"') style='margin-left:10px'>[修改]</a>"
							}
						}, */
					//注：最后一行后面的逗号要去掉
		]],
//			toolbar : [{
//				id : 'btnadd',
//				text : '添加用户',
//				iconCls : 'icon-add',
//				handler : function() {
//					addrow();
//				}
//			}],
		onLoadSuccess : function(data) {
			$('#orderTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
	
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
		$.post("${rootPath}/user/save",$("#dataForm").serializeArray(),
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
