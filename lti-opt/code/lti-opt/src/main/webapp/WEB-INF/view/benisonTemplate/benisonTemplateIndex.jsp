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
    <div class="easyui-panel" title="查询条件" data-options="region:'north',height:90,collapsible:false,border : false">
		<!-- 注意：form ID 要加 1，用于区别子页面 form -->
		<form id="dataForm1" method="post">
			 <table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td class="kTableLabel lbl">祝福语模板编号:</td>
					<td><input name="templateCode" id ="templateCode" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">祝福语模板名称:</td>
					<td><input name="templateName" id ="templateName" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">屏幕名称:</td>
					<td><input name="screenName" class="easyui-textbox" ></td>
				</tr>
				<tr>
				<td valign="middle" align="center" colspan="8" >
                 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo()" style="width:90px">查询</a>
                	&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-empty" onclick="clearForm()" style="width:90px;margin-left:30px">清空</a>
                </td>
                </tr>
                </table>
		</form>
    </div>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable" title="祝福语模板列表" height="100%"></table>
	</div>
	<div id="divDialog"></div>
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
   		url : '${rootPath}/benisonTemplate/list',
   		method : 'post',		
		idField : 'template_id',//此处根据实际情况进行填写
		columns:[[
							{field:'template_code',title:'祝福语模板编号',width:80},
							{field:'template_name',title:'祝福语模板名称',width:80},
							{field:'screen_name',title:'屏幕名称',width:80},
// 							{field:'bg_img_id',title:'背景图片ID',width:80},
// 							{field:'sm_img_id',title:'示意图ID',width:80},
							{field:'title_x',title:'抬头X坐标',width:80,hidden:true},
							{field:'title_y',title:'抬头Y坐标',width:80,hidden:true},
							{field:'title_colour',title:'抬头颜色',width:80,hidden:true},
							{field:'title_size',title:'抬头字号',width:80,hidden:true},
							{field:'title_type',title:'抬头字体',width:80,hidden:true},
							{field:'body_x',title:'主体X坐标',width:80,hidden:true},
							{field:'body_y',title:'主体Y坐标',width:80,hidden:true},
							{field:'body_colour',title:'主体颜色',width:80,hidden:true},
							{field:'body_size',title:'主体字号',width:80,hidden:true},
							{field:'body_type',title:'主体字体',width:80,hidden:true},
							{field:'tail_x',title:'落款X坐标',width:80,hidden:true},
							{field:'tail_y',title:'落款Y坐标',width:80,hidden:true},
							{field:'tail_colour',title:'落款颜色',width:80,hidden:true},
							{field:'tail_size',title:'落款字号',width:80,hidden:true},
							{field:'tail_type',title:'落款字体',width:80,hidden:true},
							{
								field : 'operate',
								title : '操作',
								width : 120,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.template_id+"') style='margin-left:10px'>[修改]</a>"
									+"<a href='#' onclick=viewrow('"+row.template_id+"') style='margin-left:20px'>[查看详细]</a>"
									+"<a href='#' onclick=delerow('"+row.template_id+"') style='margin-left:20px'>[删除]</a>"
								}
							},
							{field:'template_id',title:'祝福语模板ID',width:80,hidden:true},
							{field:'screen_id',title:'屏幕ID',width:80,hidden:true},
							{field:'type_id',title:'祝福语类型ID',width:80,hidden:true},
							{field:'benison_id',title:'祝福语ID',width:80,hidden:true}
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
		var url = '${rootPath}/benisonTemplate/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo()
	}

	//新增
	   function addrow(){
			url = '${rootPath}/benisonTemplate/add';
	   		$("#divDialog").dialog({
	   			title : "新增祝福语模板信息",
	   			width : 500,
	   			height : 450,
	   			href : url,
	   			cache : false,
	   			closed : false,
	   			modal : true
	   		});
	   }
	   
	   // 修改
	   function editrow(rowId){   
	       if (rowId){
	    	   	url = '${rootPath}/benisonTemplate/edit?rowId='+ rowId;
		   		$("#divDialog").dialog({
		   			title : "修改祝福语模板信息",
		   			width : 500,
		   			height : 450,
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
	    	   	url = '${rootPath}/benisonTemplate/view?rowId='+ rowId;
		   		$("#divDialog").dialog({
		   			title : "查看祝福语模板信息",
		   			width : 450,
		   			height : 450,
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
//	        var row = $('#dataTable').datagrid('getSelected');
	       if (rowId){
	           $.messager.confirm('提示','确定要删除行记录吗？',function(r){
	               if (r){
	                   $.post('${rootPath}/benisonTemplate/del',{rowId:rowId},function(data){
	                   	
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