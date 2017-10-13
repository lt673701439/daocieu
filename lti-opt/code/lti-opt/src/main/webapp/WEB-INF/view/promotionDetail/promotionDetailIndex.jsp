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
<div class="easyui-layout" data-options="fit : true,border : false">
    <div class="easyui-panel" title="促销活动信息" data-options="region:'north',height:60,collapsible:false,border : false">
		<!-- 注意：form ID 要加 1，用于区别子页面 form -->
		<form id="dataForm1" method="post">
			 <table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr><input name="promotionId" id ="promotionId" value="${promotionId}" hidden="true">
					<td class="kTableLabel lbl">促销活动编号:</td>
					<td><input name="promotionCode" id ="promotionCode" class="easyui-textbox" value="${promotionCode}" disabled></td>
					<td class="kTableLabel lbl">促销活动名称:</td>
					<td><input name="promotionName" id ="promotionName" class="easyui-textbox" value="${promotionName}" disabled></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td class="kTableLabel lbl">上架时间:</td> -->
<!-- 					<td><input name="addTime" class="easyui-datetimebox"  style="width:160px" disabled></td> -->
<!-- 					<td class="kTableLabel lbl">下架时间:</td> -->
<!-- 					<td><input name="removeTime" class="easyui-datetimebox"  style="width:160px" disabled></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="kTableLabel lbl">开始时间:</td> -->
<!-- 					<td><input name="startTime" class="easyui-datetimebox"  style="width:160px" disabled></td> -->
<!-- 					<td class="kTableLabel lbl">结束时间:</td> -->
<!-- 					<td><input name="endTime" class="easyui-datetimebox"  style="width:160px" disabled></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="kTableLabel lbl">状态:</td> -->
<!-- 					<td><select class="easyui-combobox" name="promotionStatus" style="width:80px" panelHeight="60px" disabled> -->
<!-- 							<option value="1">上架</option> -->
<!-- 							<option value="2">下架</option> -->
<!-- 						</select></td> -->
<!-- 					<td class="kTableLabel lbl">是否可与其他活动及优惠券一起使用：</td> -->
<!-- 					<td><select class="easyui-combobox" name="isTogether" style="width:80px" panelHeight="60px" disabled> -->
<!-- 							<option value="1">否</option> -->
<!-- 							<option value="2">是</option> -->
<!-- 						</select></td> -->
<!-- 				</tr> -->
              </table>
		</form>
    </div>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable" title="促销商品列表" height="100%"></table>
	</div>
	<div id="divDialog"></div>
</div>
<script type="text/javascript">
jQuery(function(){	  
	
	jQuery.ajaxSetup({
		cache : false
	});
	
	//初始化列表
   	$('#dataTable').datagrid({
   		singleSelect : true,
   		rownumbers:true,
   		pagination:true,
   		fitColumns:true,
   		url : '${rootPath}/promotionDetail/list?promotionId=${promotionId}',
   		method : 'post',		
		idField : 'detail_id',//此处根据实际情况进行填写
		columns:[[
							{field:'detail_id',title:'detail_id',width:80,hidden:true},
							{field:'commodity_id',title:'商品ID',width:120,hidden:true},
							{field:'commodity_code',title:'商品编号',width:120},
							{field:'commodity_name',title:'商品名称',width:120},
							{field:'commodity_num',title:'促销商品个数',width:120},
							{field:'commodity_price',title:'商品价格(元)',width:120},
							{field:'discount_ratio',title:'打折比例',width:80},
							{
								field : 'operate',
								title : '操作',
								width : 140,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.detail_id+"') style='margin-left:10px'>[修改]</a>"
									+"<a href='#' onclick=viewrow('"+row.detail_id+"') style='margin-left:20px'>[详细]</a>"
									+"<a href='#' onclick=delerow('"+row.detail_id+"') style='margin-left:20px'>[删除]</a>"
								}
							}
						//注：最后一行后面的逗号要去掉
		]],
		toolbar : [{
			id : 'btnadd',
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				addrow();
			}
		}, '-', {
			id : 'btnreturn',
			text : '返回',
			iconCls : 'icon-arrow_turn_left',
			handler : function() {
				returnCommodity();
			}
// 		}, '-', {
// 			id : 'btndel',
// 			text : '删除',
// 			iconCls : 'icon-remove',
// 			handler : function() {
// 				delerow();
// 			}
		}],
		onLoadSuccess : function(data) {
			$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
});

   	//新增
   	function addrow(){
	    url = '${rootPath}/promotionDetail/add?promotionId=${promotionId}';
		$("#divDialog").dialog({
			title : "添加促销商品",
			width : 650,
			height : 240,
			href : url,
			cache : false,
			closed : false,
			modal : true
		});
   	}
   	
   	// 返回
   	function returnCommodity(){
	    url = '${rootPath}/promotion';
	    window.location.href=url;
   	}
   
	// 修改
  	function editrow(rowId){   
      if (rowId){
   	   	url = '${rootPath}/promotionDetail/edit?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改促销商品信息",
	   			width : 650,
				height : 280,
	   			href : url,
	   			cache : false,
	   			closed : false,
	   			modal : true
	   		});
      }
      else
      {
      	$.messager.alert('提示', "请选择你要操作的记录", 'info');
		return;
      }
  }
  
  	// 查看
  	function viewrow(rowId){    	
      if (rowId){
   	   	url = '${rootPath}/promotionDetail/view?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "查看促销商品信息",
	   			width : 650,
				height : 280,
	   			href : url,
	   			cache : false,
	   			closed : false,
	   			modal : true
	   		});
      }
      else
      {
      	$.messager.alert('提示', "请选择你要操作的记录", 'info');
		return;
      }
  	}
   
   //删除
   function delerow(rowId){
       if (rowId){
           $.messager.confirm('提示','确定要删除行记录吗？',function(r){
               if (r){
                   $.post('${rootPath}/promotionDetail/del',{rowId:rowId},function(data){
                   	
                   	if(data.result == 'true' || data.result == true){
                   		$.messager.alert("提示", "删除成功！");
							goBack(1);
   					}else{
   						$.messager.alert("提示", "删除失败 ！");
   					}                    	
                   });
               }
           });
       } else {
    	   $.messager.alert('提示', "请选择你要操作的记录", 'info');
			return;
		}
   }
   
 	//表格查询
	function searchInfo() {
// 		//查询参数直接添加在queryParams中
// 		var queryParams = $('#dataTable').datagrid('options').queryParams;
// 		var fields = $('#dataForm1').serializeArray(); //自动序列化表单元素为JSON对象
	
// 		$.each(fields, function(i, field) {
// 			queryParams[field.name] = field.value; //设置查询参数
	
// 		});
		var url = '${rootPath}/promotionDetail/list?promotionId=${promotionId}';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
   
	// 关闭弹出窗口，返回父页面
	function goBack(flag) {
		if (flag == 1) {
			searchInfo();
		}
		$("#divDialog").window('close');
	}
</script>
</body>
</html>