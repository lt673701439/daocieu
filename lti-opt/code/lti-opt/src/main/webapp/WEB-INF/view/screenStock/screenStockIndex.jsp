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
    <div class="easyui-panel" title="查询条件" data-options="region:'north',height:120,collapsible:false,border : false">
    	<br>
		<!-- 注意：form ID 要加 1，用于区别子页面 form -->
		<form id="dataForm1" method="post">
			 <table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td class="kTableLabel lbl">开始日期:</td>
					<td><input name="startDate" class="easyui-datebox" style="width:90px" editable = "editable" >
					-- 结束日期:<input name="endDate" class="easyui-datebox" style="width:90px" editable = "editable"/></td>
					<td class="kTableLabel lbl">屏幕名称:</td>
					<td><input name="newScreenName" id ="newScreenName" class="easyui-textbox"  ></td>
				</tr>
				<tr>
				<td valign="middle" align="center" colspan="4">
                 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo()" style="width:90px">查询</a>
                	&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-empty" onclick="clearForm()" style="width:90px;margin-left:30px">清空</a>
                </td>
                </tr>
                </table>
		</form>
    </div>
    <br>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable" title="屏幕库存列表" height="100%"></table>
	</div>
	<div id="divDialog"></div>
</div>
<script type="text/javascript">
	
   	//初始化列表
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
   		url : '${rootPath}/screenStock/list',
   		method : 'post',		
		idField : 'stock_id',//此处根据实际情况进行填写
		columns:[[
							{field:'screen_name',title:'屏幕名称',width:80},
							{field:'start_date',title:'开始日期',width:80},
							{field:'end_date',title:'结束日期',width:80},
							{field:'start_time',title:'开始时间',width:80},
							{field:'end_time',title:'结束时间',width:80},
							{
								field : 'operate',
								title : '操作',
								width : 120,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.stock_id+"') style='margin-left:10px'>[修改]</a>"
									+"<a href='#' onclick=viewrow('"+row.stock_id+"') style='margin-left:20px'>[查看详情]</a>"
									+"<a href='#' onclick=delerow('"+row.stock_id+"') style='margin-left:20px'>[删除]</a>"
								}
							},
							{field:'created_time',title:'创建时间',width:80,hidden:true},
							{field:'created_by',title:'创建人',width:80,hidden:true},
							{field:'modified_time',title:'修改时间',width:80,hidden:true},
							{field:'modified_by',title:'修改人',width:80,hidden:true},
							{field:'version',title:'版本号',width:80,hidden:true},
							{field:'delflag',title:'删除标记',width:80,hidden:true},
							{field:'stock_id',title:'库存ID',width:80,hidden:true},
							{field:'screen_id',title:'屏幕ID',width:80,hidden:true}
						//注：最后一行后面的逗号要去掉
		]],
		toolbar : [{
			id : 'btnadd',
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				addrow();
			}
		}],
		onLoadSuccess : function(data) {
			$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
   	
   });
   
   //表格查询
	function searchInfo() {
		//查询参数直接添加在queryParams中
		var queryParams = $('#dataTable').datagrid('options').queryParams;
		var fields = $('#dataForm1').serializeArray(); //自动序列化表单元素为JSON对象
	
		$.each(fields, function(i, field) {
			queryParams[field.name] = field.value; //设置查询参数
	
		});
		var url = '${rootPath}/screenStock/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo()
	}

	// 新增	
   function addrow(){   
   	   	url = '${rootPath}/screenStock/add';
   		$("#divDialog").dialog({
   			title : "新增屏幕库存信息",
   			width : 450,
   			height : 350,
   			href : url,
   			cache : false,
   			closed : false,
   			modal : true
   		});
   }
   
	// 修改
   function editrow(rowId){   
       if (rowId){
    	   	url = '${rootPath}/screenStock/edit?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改屏幕库存信息",
	   			width : 450,
	   			height : 350,
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
   
   //查看
   function viewrow(rowId){    	
       if (rowId){
    	   	url = '${rootPath}/screenStock/view?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "查看屏幕库存信息",
	   			width : 450,
	   			height : 350,
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
//        var row = $('#dataTable').datagrid('getSelected');
       if (rowId){
           $.messager.confirm('提示','确定要删除行记录吗？',function(r){
               if (r){
                   $.post('${rootPath}/screenStock/del',{rowId:rowId},function(data){
                   	
                   	if(data.result == 'true' || data.result == true)
   					{
                   		$('#dataTable').datagrid('reload');    // reload the user data
   					}
   					else
   					{
   						$.messager.alert('提示',data.msg,'error');
   					}                    	
                   });
               }
           });
       }
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