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
		<!-- 注意：form ID 要加 1，用于区别子页面 form -->
		<br>
		<form id="userForm">
		 	<table style="width:100%; height:1%; overflow: hidden;" border="0" 
			cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
				<td class="kTableLabel lbl">手机号:</td>
				<td>
				<input name="userPhone" id ="userPhone" class="easyui-textbox" >
				</td>
				<td class="kTableLabel lbl">微信号:</td>
				<td>
				<input name="openId" id ="openId" class="easyui-textbox" >
				</td>
				<td class="kTableLabel lbl">昵称:</td>
				<td>
				<input name="user" id ="user" class="easyui-textbox" >
				</td>
				</tr>
            </table>
            <br>
            <div id="dlg-buttons" align="center">
            	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo()" style="width:90px;margin-right: 10%;">查询</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-empty" onclick="clearForm()" style="width:90px;margin-left:30px">清空</a>
		   </div>
		</form>
    </div>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="userTable" title="用户列表" height="100%"></table>
	</div>
	<div id="divDialog"></div>
</div>
<script type="text/javascript">

	jQuery(function(){
		
		jQuery.ajaxSetup({
			cache : false
		});
		// 获取【列表】全部字典数据[商户类型、站点]
// 		var dictList = getDictList('MultiState,status,SITE');
		//var dictListSite=getDictList('Seller,SITE')
   	
		//初始化列表
	   	$('#userTable').datagrid({
	   		singleSelect : true,
	   		rownumbers:true,
	   		pagination:true,
	   		fitColumns:true,
	   		url : '${rootPath}/user/list',
	   		method : 'post',		
			idField : 'user_id',//此处根据实际情况进行填写
			columns:[[
							{field : 'ck',checkbox : true},
		          			{field:'userId',title:'用户编号',align:'center',width:80},
							{field:'userPhone',title:'手机号',align:'center',width:60},
							{field:'openId',title:'微信号',align:'center',width:60},
							{field:'userIcon',title:'微信头像',align:'center',width:60,
								 formatter:function(value,row,index){
									 return '<img width="50" height:"50" src="'+row.userIcon+'" />'
								 }	
							},
							{field:'user',title:'昵称',width:60,align:'center'},
							{field:'createdTime',title:'创建时间',align:'center',width:80,hidden:true},
							{field:'delflag',title:'删除标记',align:'center',width:80,hidden:true},
							{
								field : 'operate',
								title : '操作',
								width : 100,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=viewrow('"+row.userId+"') style='margin-left:20px'>[查看详细信息]</a>"
									+"<a href='#' onclick=editrow('"+row.userId+"') style='margin-left:10px'>[修改]</a>"
								}
							}
						//注：最后一行后面的逗号要去掉
			]],
// 			toolbar : [{
// 				id : 'btnadd',
// 				text : '添加用户',
// 				iconCls : 'icon-add',
// 				handler : function() {
// 					addrow();
// 				}
// 			}],
			onLoadSuccess : function(data) {
				$('#userTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
   });
	
   //表格查询
	function searchInfo() {
		//查询参数直接添加在queryParams中
		var queryParams = $('#userTable').datagrid('options').queryParams;
		var fields = $('#userForm').serializeArray(); //自动序列化表单元素为JSON对象
	
		$.each(fields, function(i, field) {
			queryParams[field.name] = field.value; //设置查询参数
	
		});
		var url = '${rootPath}/user/list';
		$('#userTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm() {
		$('#userForm').form('clear');
		searchInfo();
	
	}

   //新增
   function addrow(){
	    url = '${rootPath}/user/add';
		$("#divDialog").dialog({
			title : "添加用户",
			width : 450,
			height : 450,
			href : url,
			cache : false,
			closed : false,
			modal : true
		});
   }
   
   //修改
   function editrow(rowId){   
       if (rowId){
    	   	url = '${rootPath}/user/edit?userId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改用户信息",
	   			width : 450,
	   			height : 508,
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
    	   	url = '${rootPath}/user/view?userId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "查看用户信息",
	   			width : 450,
	   			height : 464,
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
   
	function goBack(flag) {
		if (flag == 1) {
			searchInfo();
		}
		$("#divDialog").window('close');
	}
		
</script>
</body>
</html>