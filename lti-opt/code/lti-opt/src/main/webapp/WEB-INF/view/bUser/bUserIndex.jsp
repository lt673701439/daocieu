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
					<td class="kTableLabel lbl">openId:</td>
					<td><input name="openId" id ="openId" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">昵称:</td>
					<td><input name="userNickname" id ="userNickname" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">电子邮箱:</td>
					<td><input name="userMail" id ="userMail" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">电话:</td>
					<td><input name="userPhone" id ="userPhone" class="easyui-textbox" ></td>
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
		<table id="dataTable" title="用户列表" height="100%"></table>
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
	   		url : '${rootPath}/bUser/list',
	   		method : 'post',		
			idField : 'user_id',//此处根据实际情况进行填写
			columns:[[
		          			// 注意：这里是字段名SELLER_CODE，不是变量名sellerCode
// 							{field : 'ck',checkbox : true},
							{field:'open_id',title:'openId',width:80},
							{field:'user_nickname',title:'昵称',width:80},
							{field:'user_sex',title:'性别',width:60},
							{field:'user_mail',title:'电子邮箱',width:100},
							{field:'user_phone',title:'电话',width:80},
							{
								field : 'operate',
								title : '操作',
								width : 60,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.user_id+"') style='margin-left:10px'>[修改]</a>"
// 									+"<a href='#' onclick=delerow('"+row.SELLER_ID+"','"+row.VERSION+"') style='margin-left:10px'>[删除]</a>";
								}
							},
							{field:'user_id',title:'userId',width:80,hidden:true},
							{field:'user_type',title:'userType',width:80,hidden:true},
							{field:'user_icon',title:'userIcon',width:80,hidden:true},
							{field:'user_birthday',title:'userBirthday',width:80,hidden:true},
							{field:'user_province',title:'userProvince',width:80,hidden:true},
							{field:'user_city',title:'userCity',width:80,hidden:true},
							{field:'user_address',title:'userAddress',width:80,hidden:true},
							{field:'created_time',title:'createdTime',width:80,hidden:true},
							{field:'delflag',title:'delflag',width:80,hidden:true},
						//注：最后一行后面的逗号要去掉
		]],
// 		toolbar : [
// 		{
// 			id : 'btnadd',
// 			text : '添加',
// 			iconCls : 'icon-add',
// 			handler : function() {
// 				addrow();
// 			}
// 		}, '-', {
// 			id : 'btnedit',
// 			text : '修改',
// 			iconCls : 'icon-edit',
// 			handler : function() {
// 				editrow();
// 			}
// 		}, '-', {
// 			id : 'btndel',
// 			text : '删除',
// 			iconCls : 'icon-remove',
// 			handler : function() {
// 				delerow();
// 			}
// 		}],
		onLoadSuccess : function(data) {
			$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
   	
   	//初始化下拉框-示例，请根据需要自定义实现
   	/*
   	 $('#combo1').combobox({    
  	        url:'${rootPath}/getDictlist?dicttypeid=userstatus',    
  	        valueField:'dictid',    
  	        textField:'dictname',
  	     	panelHeight:'auto'
  	    }); 
  	  */ 
   });
   
   //表格查询
	function searchInfo() {
		//查询参数直接添加在queryParams中
		var queryParams = $('#dataTable').datagrid('options').queryParams;
		var fields = $('#dataForm1').serializeArray(); //自动序列化表单元素为JSON对象
	
		$.each(fields, function(i, field) {
			queryParams[field.name] = field.value; //设置查询参数
	
		});
		var url = '${rootPath}/bUser/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo();
	}

   //新增
//    function addrow(){
//    		url = '${rootPath}/bUser/edit';
// 		openWin(url);
//    }
   
//    //修改
//    function editrow(){    	
//        var row = $('#dataTable').datagrid('getSelected');
	
//        if (row){
//        	url = '${rootPath}/bUser/edit?rowId='+ row.rowId;
// 		openWin(url);
//        }
//        else
//        {
//        	$.messager.alert('提示', "请选择你要更新的记录", 'info');
// 		return;
//        }
//    }
   
   // 修改
   function editrow(rowId){   
       if (rowId){
    	   	url = '${rootPath}/bUser/edit?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改用户信息",
	   			width : 450,
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
//    function delerow(){
//        var row = $('#dataTable').datagrid('getSelected');
//        if (row){
//            $.messager.confirm('提示','确定要删除行记录吗？',function(r){
//                if (r){
//                    $.post('${rootPath}/bUser/del',{rowId:row.rowId},function(data){
                   	
//                    	if(data.result == 'true' || data.result == true)
//    					{
//                    		$('#dataTable').datagrid('reload');    // reload the user data
//    					}
//    					else
//    					{
//    						$.messager.alert('提示',data.msg,'error');
//    					}                    	
//                    });
//                }
//            });
//        }
//    }
   
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