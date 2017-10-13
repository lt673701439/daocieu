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
    <div class="easyui-panel" title="商品信息" data-options="region:'north',height:60,collapsible:false,border : false">
		<!-- 注意：form ID 要加 1，用于区别子页面 form -->
		<form id="dataForm1" method="post">
			 <table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr><input name="commodityId" id ="commodityId" value="${commodityId}" hidden="true">
					<td class="kTableLabel lbl">商品编号:</td>
					<td><input name="commodityCode" id ="commodityCode" class="easyui-textbox" value="${commodityCode}" disabled></td>
					<td class="kTableLabel lbl">商品名称:</td>
					<td><input name="commodityName" id ="commodityName" class="easyui-textbox" value="${commodityName}" disabled></td>
				</tr>
              </table>
		</form>
    </div>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable" title="价格体系条目列表" height="100%"></table>
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
   		url : '${rootPath}/commodityCost/list?commodityId=${commodityId}',
   		method : 'post',		
		idField : 'cost_id',//此处根据实际情况进行填写
		columns:[[
							{field:'cost_id',title:'costId',width:80,hidden:true},
							{field:'cost_type',title:'类型',width:100},
							{field:'cost_name',title:'名称',width:100},
							{field:'cost_price',title:'价格',width:40},
							{field:'sku_id',title:'序号',width:40},
							{
								field : 'operate',
								title : '操作',
								width : 140,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.cost_id+"') style='margin-left:10px'>[修改]</a>"
									+"<a href='#' onclick=viewrow('"+row.cost_id+"') style='margin-left:20px'>[详细]</a>"
									+"<a href='#' onclick=delerow('"+row.cost_id+"') style='margin-left:20px'>[删除]</a>"
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
		}],
		onLoadSuccess : function(data) {
			$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
});

   	//新增
   	function addrow(){
	    url = '${rootPath}/commodityCost/add?commodityId=${commodityId}';
		$("#divDialog").dialog({
			title : "添加价格体系条目",
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
	    url = '${rootPath}/commodity';
	    window.location.href=url;
   	}
   
	// 修改
  	function editrow(rowId){   
      if (rowId){
   	   	url = '${rootPath}/commodityCost/edit?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改价格体系条目信息",
	   			width : 650,
				height : 240,
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
   	   	url = '${rootPath}/commodityCost/view?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "查看价格体系条目信息",
	   			width : 650,
				height : 240,
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
                   $.post('${rootPath}/commodityCost/del',{rowId:rowId},function(data){
                   	
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
		var url = '${rootPath}/commodityCost/list?commodityId=${commodityId}';
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