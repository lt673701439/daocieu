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
		<form id="dataForm_findPage" method="post">
			 <table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td class="kTableLabel lbl">编号:</td>
					<td><input name="${fNum}" id ="${fNum}" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">名称:</td>
					<td><input name="${fName}" id ="${fName}" class="easyui-textbox" ></td>
				</tr>
				<tr>
				<td valign="middle" align="center" colspan="4" >
                 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo_findPage()" style="width:90px">查询</a>
                	&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-empty" onclick="clearForm_findPage()" style="width:90px;margin-left:30px">清空</a>
                </td>
                </tr>
                </table>
		</form>
    </div>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable_findPage" class="easyui-datagrid" data-options="singleSelect:true,
	   		rownumbers:true,
	   		pagination:true,
	   		fitColumns:true,
	   		url : '${rootPath}/common/findPageList?findSql=${findSql}&cKey=${cKey}&cValue=${cValue}',
	   		method : 'post',		
			idField : '${opId}',
			columns:[[
								{field:'${opId}',title:'ID',width:80,hidden:true},
								{field:'${opNum}',title:'编号',width:80},
								{field:'${opName}',title:'名称',width:80}
			]],
			onClickRow:function(index,row){
				
				var fId = '${fId}';
				var fName = '${fName}';
				var opId = '${opId}';
				var opName = '${opName}';
				
				if (row[opId]){
			    	   	$('#'+fId).val(row[opId]);
			    	   	$('#'+fName).val(row[opName]);
			    	   	$('#'+fName).textbox('setValue',row[opName]);
			    	   	goBack_findPage();
			   	}else {
				       	$.messager.alert('提示', '您选择的数据项有问题，请选择其它数据项！', 'info');
						return;
			    }
			},
			onLoadSuccess : function(data) {
				$('#dataTable_findPage').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}" title="查询列表" height="100%"></table>
	</div>
</div>

<script type="text/javascript">

jQuery(function(){	  
	
	jQuery.ajaxSetup({
		cache : false
	});
	
});
   
	//表格查询
	function searchInfo_findPage() {
		//查询参数直接添加在queryParams中
		var queryParams = $('#dataTable_findPage').datagrid('options').queryParams;
		var fields = $('#dataForm_findPage').serializeArray(); //自动序列化表单元素为JSON对象
	
		$.each(fields, function(i, field) {
			queryParams[field.name] = field.value; //设置查询参数
	
		});
		var url = '${rootPath}/common/findPageList?findSql=${findSql}&cKey=${cKey}&cValue=${cValue}';
		$('#dataTable_findPage').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm_findPage() {
		$('#dataForm_findPage').form('clear');
		searchInfo_findPage();
	}
		
</script>
</body>
</html>