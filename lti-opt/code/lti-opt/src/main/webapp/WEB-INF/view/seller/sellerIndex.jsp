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
	<div class="easyui-panel" title="查询条件" data-options="region:'north',height:100,collapsible:false,border : false">
		<!-- 注意：form ID 要加 1，用于区别子页面 form -->
		<form id="dataForm1" method="post" action="${rootPath}/seller/downloadSeller">
		  <!--   <div id="condition" class="container_12" style="position: relative;">
		    <br/>
				<div class="grid_1 lbl">商户编号:</div>
				<div class="grid_2 val">
					<input name="sellerCode" id ="sellerCode" class="easyui-textbox" >
				</div>
				<div class="grid_1 lbl">商户名称:</div>
				<div class="grid_2 val">
					<input name="sellerName" id ="sellerName" class="easyui-textbox" >
				</div>
				<div class="grid_1 lbl">商户类型:</div>
				<div class="grid_2 val">
				<input class="easyui-combobox" name="sellerType" id ="sellerType" style="width:80px" editable="editable" panelHeight="auto"
		 			url="${rootPath}/getDictCombox?dictType=MultiState" valueField="dictId" textField="dictName">
				</div>				
				<div class="grid_1 lbl">站点:</div>
				<div class="grid_2 val">
				<input class="easyui-combobox" name="site" id ="sellerType" style="width:80px" editable="editable" panelHeight="auto"
		 			url="${rootPath}/getDictCombox?dictType=SITE" valueField="dictId" textField="dictName">
				</div>
					<div class="grid_1 lbl">
                 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo()" style="width:90px">查询</a>
                </div>
				<div class="grid_2 val">
                	&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-empty" onclick="clearForm()" style="width:90px;margin-left:30px">清空</a>
                </div>
			</div>
			 -->
			 	<table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
				<td class="kTableLabel lbl">商户编号:</td>
				<td>
					<input name="sellerCode" id ="sellerCode" class="easyui-textbox" >
				</td>
				<td class="kTableLabel lbl">商户名称:</td>
				<td>
				<input name="sellerName" id ="sellerName" class="easyui-textbox" >
				</td>
				<td class="kTableLabel lbl">商户类型:</td>
				<td>
				<input class="easyui-combobox" name="sellerType" id ="sellerType" style="width:80px" editable="editable" panelHeight="auto"
		 			url="${rootPath}/getDictCombox?dictType=MultiState" valueField="dictId" textField="dictName">
				</td>
				<td class="kTableLabel lbl">站点:</td>
				<td>
				<input class="easyui-combobox" name="site" id ="sellerType" style="width:80px" editable="editable" panelHeight="auto"
		 			url="${rootPath}/getDictCombox?dictType=SITE" valueField="dictId" textField="dictName">
				</td>
				</tr>
				<tr>
				<td valign="middle" align="center" colspan="6" >
                 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo()" style="width:90px">查询</a>
                	&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-empty" onclick="clearForm()" style="width:90px;margin-left:30px">清空</a>
                </td>
                </tr>
                </table>
		</form>
    </div>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable" title="商户列表" height="100%"></table>
	</div>
	<div id="divDialog"></div>
</div>
<script type="text/javascript">

	jQuery(function(){
		
		jQuery.ajaxSetup({
			cache : false
		});
		// 获取【列表】全部字典数据[商户类型、站点]
		var dictList = getDictList('MultiState,status,SITE');
		//var dictListSite=getDictList('Seller,SITE')
   	
		//初始化列表
	   	$('#dataTable').datagrid({
	   		singleSelect : true,
	   		rownumbers:true,
	   		pagination:true,
	   		fitColumns:true,
	   		url : '${rootPath}/seller/list',
	   		method : 'post',		
			idField : 'SELLER_ID',//此处根据实际情况进行填写
			columns:[[
		          			// 注意：这里是字段名SELLER_CODE，不是变量名sellerCode
							{field : 'ck',checkbox : true},
		          			{field:'SELLER_CODE',title:'商户编号',align:'center',width:80},
							{field:'LOGIN_NAME',title:'登录名',align:'center',width:60},//LOGIN_NAME
							{field:'SELLER_NAME',title:'商户名称',align:'center',width:60},
							{field:'SELLER_TYPE',title:'商户类型',align:'center',width:60,formatter : function(value, row, index) {
								return getDictName(dictList,"MultiState",value);
							}},
							{field:'SITE',title:'站点',width:60,align:'center',formatter : function(value, row,index) {
								return getDictName(dictList,"SITE",value);
							}},
							{field:'SELLER_TEL',title:'商户服务电话',align:'center',width:80},
// 							{field:'SELLER_STATE',title:'商户状态',width:80,formatter : function(value, row,index) {
// 								return getDictName(dictList,"status",value);
// 							}},
							{field:'CREATED_TIME',title:'创建时间',align:'center',width:80},
							{
								field : 'operate',
								title : '操作',
								width : 100,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=viewrow('"+row.SELLER_ID+"') style='margin-left:20px'>[查看详细]</a>"
									+"<a href='#' onclick=editrow('"+row.SELLER_ID+"') style='margin-left:10px'>[修改]</a>"
// 									+"<a href='#' onclick=delerow('"+row.SELLER_ID+"','"+row.VERSION+"') style='margin-left:10px'>[删除]</a>";
								}
							},
							
							{field:'SELLER_ID',title:'sellerId',width:80,hidden:true},
							{field:'SELLER_LINKMAN',title:'sellerLinkman',width:80,hidden:true},
							{field:'SELLER_ADDRESS',title:'sellerAddress',width:80,hidden:true},
							//{field:'SITE',title:'site',width:80,hidden:true},
							{field:'BANK_NAME',title:'bankName',width:80,hidden:true},
							{field:'BANK_ACCOUNT',title:'bankAccount',width:80,hidden:true},
							{field:'ACCOUNT_HOLDER',title:'accountHolder',width:80,hidden:true},
							{field:'CREATED_BY',title:'createdBy',width:80,hidden:true},
							{field:'MODIFIED_BY',title:'modifiedBy',width:80,hidden:true},
							{field:'MODIFIED_TIME',title:'modifiedTime',width:80,hidden:true},
							{field:'DELFLAG',title:'delflag',width:80,hidden:true},
							{field:'VERSION',title:'version',width:80,hidden:true}
						//注：最后一行后面的逗号要去掉
			]],
			toolbar : [{
				id : 'btnadd',
				text : '添加商户',
				iconCls : 'icon-add',
				handler : function() {
					addrow();
				}
			},'-', {
				id : 'btnexport',
				text : '导出',
				iconCls : 'icon-page_excel',
				handler : function() {
					download();
				}
			},'-', {
				id : 'btnrset',
				text : '密码重置',
				iconCls : 'icon-key',
				handler : function() {
					rSetPsw();
				}
			}],
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
   });
	
	// 重置密码
	function rSetPsw(){
		   var row = $('#dataTable').datagrid('getSelected');
		     if (row){
		    	 com.message('confirm','确定要重置'+row.LOGIN_NAME,'提示',function(r){
		  		    if (r){
		  				$.post("${rootPath}/seller/resetPwd?sellerId="+row.SELLER_ID,$("#dataForm").serializeArray(),
		  						function(data){			
		  							if(data.result == 'true' || data.result == true){
		  							 	$.messager.alert("提示", data.msg);
		  								goBack(1);
		  							}else{
		  								$.messager.alert("提示", data.msg);
		  							}
		  						});
		  		   }
		  	});
		  } else {
				$.messager.alert("warning","请选择你要操作的记录!");
				return;
		  }

	}
   
   //表格查询
	function searchInfo() {
		//查询参数直接添加在queryParams中
		var queryParams = $('#dataTable').datagrid('options').queryParams;
		var fields = $('#dataForm1').serializeArray(); //自动序列化表单元素为JSON对象
	
		$.each(fields, function(i, field) {
			queryParams[field.name] = field.value; //设置查询参数
	
		});
		var url = '${rootPath}/seller/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
   //导出excel
   function download(){
// 		var url='${rootPath}/seller/downloadSeller';
// 		$('#dataForm1').form('load', url);
	   $("form:first").submit();
   }
	
	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo();
	
	}

   //新增
   function addrow(){
//    	url = '${rootPath}/seller/add';
// 		openWin(url);
	    url = '${rootPath}/seller/add';
		$("#divDialog").dialog({
			title : "添加商户",
			width : 450,
			height : 450,
			href : url,
			cache : false,
			closed : false,
			modal : true
		});
   }
   
   //修改
   function editrow(sellerId){   
       if (sellerId){
    	   	url = '${rootPath}/seller/edit?sellerId='+ sellerId;
	   		$("#divDialog").dialog({
	   			title : "修改商户信息",
	   			width : 450,
	   			height : 200,
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
   function delerow(sellerId, version){
//        var row = $('#dataTable').datagrid('getSelected');
       if (sellerId && version){
           $.messager.confirm('提示','确定要删除行记录吗？',function(r){
               if (r){
                   $.post('${rootPath}/seller/del',{sellerId:sellerId,version:version},function(data){
                   	
                   	if(data.result == 'true' || data.result == true){
                   		$.messager.alert("提示", "角色删除成功！");
						goBack(1);
   					}else{
   						$.messager.alert("提示", "角色删除失败 ！");
   					}                    	
                   });
               }
           });
       } else {
    	   $.messager.alert('提示', "请选择你要操作的记录", 'info');
			return;
		}
   }
   	
   //查看
   function viewrow(sellerId){    	
       if (sellerId){
    	   	url = '${rootPath}/seller/view?sellerId='+ sellerId;
	   		$("#divDialog").dialog({
	   			title : "查看商户信息",
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
   
   //点击增加弹出增加窗口
// 	function openWin(url) {
// 		$('#iframeDialogSelect').attr("src", url);
// 		$('#divDialog').window('open');

// 	}
	
	//关闭弹出窗口，返回父页面,根据标记决定是否执行查询操作
// 	function returnParent(flag) {
// 		if(flag==1)
// 		{
// 			searchInfo();
// 		}
// 		$("#divDialog").window('close');
// 	}
	function goBack(flag) {
		if (flag == 1) {
			searchInfo();
		}
		$("#divDialog").window('close');
	}
		
</script>
</body>
</html>