<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
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
<input type="hidden" name="reqMenuId">
<div class="easyui-layout" data-options="fit : true,border : false">
	<div  data-options="region:'north',height:60">
		<form id="dataHosForm" method="post">
			<div id="condition" class="container_12" style="position: relative;">
			    <br/>
				<div class="grid_1 lbl" style="width: 5%">登录名</div>
				<div class="grid_2 val" style="width: 130px">
					<input class="z-text easyui-textbox" name="userCode" id="userCode" />
				</div>
				<div class="grid_1 lbl" style="width: 5%">用户姓名</div>
				<div class="grid_2 val"  style="width: 130px">
					<input class="z-text easyui-textbox" name="userName" id="userName"/>
				</div>
				<div class="grid_2 val" style="width: 70px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="quertUsrBtn"  style="width:85px">查询</a>
				</div>
				<div class="grid_1 lbl">
                        <a href="javascript:void(0)" class="easyui-linkbutton"
                            iconCls="icon-empty" onclick="clearForm()" style="width: 75px">清空</a>
                </div>
			</div>
		</form>
		</div>
     <div data-options="region:'center',border:false"   style="overflow: hidden;">
             <table id="dataTable" >
             </table> 
     </div>
   <div id="divDialog"></div>
</div>
<script type="text/javascript">

function setOrganize(userId,userName){
      url='${rootPath}/user/toSetOrg?userId='+userId+"&userName="+userName;
      $("#divDialog").dialog({
	        title: "设置机构",
	        width: 620,
	        height: 450,
	        href:url,
			cache: false,
			closed: false,    
			modal:true
	    });
    }
function setRole(userId,userName){
	 url='${rootPath}/user/toSetRo?userCode='+userId+"&userName="+userName;
	 $("#divDialog").dialog({
		        title: "设置角色",
		        width: 620,
		        height: 450,
		        href:url,
				cache: false,
				closed: false,    
				modal:true
		    });
  }
var reqMenuId;
 jQuery(function() {
	 reqMenuId = '${reqMenuId}';
// 	 var dictList = getDictList('usertype');
	//初始化列表
   	$('#dataTable').datagrid({
   		iconCls:'icon-tip',
   		singleSelect : true,
   		url:"${rootPath}/user/list",
   		fit:true,
   		rownumbers:true,
   		pagination:true,
   		method : 'post',
   		idField : 'User_Id',//此处根据实际情况进行填写
		columns:[[
	                {field:'ck',checkbox:true},
            		{field:'User_Code',title:'登录名',width:120},
					{field:'User_Name',title:'用户姓名',width:120},
// 					{field:'User_Type',title:'用户类型',width:70,formatter : function(value, row,index) {
// 						return getDictName(dictList,"usertype",value);
// 					}},
// 					{field:'User_Nature',title:'用户性质',width:70,
//                         formatter: function(value,row,index) {
//                             if(value=='H'){
//                                 return '总公司';
//                             }else if(value=='B'){
//                                 return '分公司';
//                             }
//                         }},
					{field:'Work_Status',title:'是否在岗',width:70,
						formatter: function(value,row,index) {
                            if(value=='1'){
                            	return '在岗';
                            }else if(value=='0'){
                            	return '离岗';
                            }
						}
					},
// 					{field:'User_Dept',title:'所属部门',width:100},
					/* {field:'User_Com',title:'所属公司',width:110},
					{field:'Com_Coy',title:'分公司',width:100},
					{field:'Com_Branch',title:'中支',width:120},
					{field:'Com_Agenter',title:'营销服务部',width:120}, */
					{field:'aa',title:'操作', width:265,
						formatter: function(value,row,index){
							return "<a href='#' onclick=setRole('"+row.User_Id+"','"+row.User_Name+"') style='margin-left:10px'>&nbsp<span class='icon icon-group'>&nbsp;</span>[设置角色]</a>";
							//<a href='#' onclick=setOrganize('"+row.User_Id+"','"+row.User_Name+"');><span class='icon icon-org'>&nbsp</span>[设置机构]</a>
						}
			    }
					//注：最后一行后面的逗号要去掉
				]],
		toolbar : [{
			/*	id : 'btnflash',
			text : '刷新',
			iconCls : 'icon-arrow_refresh',
			handler : function() {
				queryUserList();
			}
		}, '-', {*/
			id : 'btnadd',
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				addUser();
			}
		}, '-', {
				id : 'btnedit',
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() {
				editUserrow();
			}
		}, '-', {
			id : 'btnrset',
			text : '密码重置',
			iconCls : 'icon-key',
			handler : function() {
				rSetPsw();
			}
		}, '-', {
			id : 'btndel',
			text : '删除',
			iconCls : 'icon-cross',
			handler : function() {
				deleUserrow();
			}
// 		},'-', {
// 			id : 'btnexport',
// 			text : '导出',
// 			iconCls : 'icon-page_excel',
// 			handler : function() {
// 				exportTable();
// 			}
		}],
		onLoadSuccess : function(data) {
			$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
           	var toolbar = $('#dataTable').datagrid('options').toolbar;
			// 设置按钮是否可用
			var btnurl = "${rootPath}/menu/btnAccess?reqMenuId=" + reqMenuId;
           	btnCheck(btnurl,toolbar);
		}
	});
    
   	// 查询按钮事件
	$('#quertUsrBtn').click( function () {
		queryUserList();
	});
});
 
function queryUserList() {
	//查询参数直接添加在queryParams中
	var queryParams = $('#dataTable').datagrid('options').queryParams;
	var fields = $('#dataHosForm').serializeArray(); //自动序列化表单元素为JSON对象

	$.each(fields, function(i, field) {
		queryParams[field.name] = field.value; //设置查询参数

	});
	var url = '${rootPath}/user/list';
	$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	
// 	var url='${rootPath}/user/Conlist';
// 	$('#dataTable').datagrid('reload',url);
	
}
// function exportTable(){
// 	var usercode = $("#usercode").val();
// 	var username = $("#username").val();
// 	var usertype = $('#usertype').combobox('getValue');
// 	//dataHosForm
// 	 $('#dataHosForm').form('submit', {    
// 			 url: "${rootPath}/user/downLoadUser",  
// 		    onSubmit: function(){    
// 		        // do some check    
// 		        // return false to prevent submit;    
// 		    },    
// 		    success:function(data){    
// 		       // alert(data)    
// 		    }    
// 		});  

// $.post("${rootPath}/user/downLoadUser",$("#dataForm").serializeArray(),
// 		function (data) {
// 		    	//alert('expert success！！')
// 		    }
// 		)
// 	   $.ajax({
// 		    type: "POST",
// 		    url: "${rootPath}/user/downLoadUser",
// 		    data: {user_code:usercode,user_name:username,user_type:usertype},
// 		    success: function (data) {
// 		    	alert('expert success！！')
// 		    }
// 		  });

// }

/**
 * 增加用户<br/>
 */
function addUser() {
	url = '${rootPath}/user/add';
	$("#divDialog").dialog({
        title: "新增用户",
        width: 450,
        height: 250,
        href:url,
		cache: false,
		closed: false,    
	    modal:true
    });
}

/**
 * 修改用户<br/>
 */
function editUserrow() {
	var row = $('#dataTable').datagrid('getSelected');

	if (row) {
		url = '${rootPath}/user/edit?rowId=' + row.User_Id;
		$("#divDialog").dialog({
			title : "编辑用户信息",
			width : 450,
			height : 250,
			href : url,
			cache : false,
			closed : false,
			modal : true
		});
		// openWin(url);
	} else {
       	$.messager.alert('提示', "请选择你要更新的记录", 'info');
		return;
	}
}
 
//重置密码
function rSetPsw(){
	   var row = $('#dataTable').datagrid('getSelected');
     if (row){
    	 com.message('confirm','确定要重置密码为'+row.User_Code,'提示',function(r){
  		    if (r){
		         $.ajax({
		 		    type: "POST",
		 		    url: "${rootPath}/user/rSetPwd",
		 		    data: {userId:row.User_Id,userCode:row.User_Code},
		 		    dataType: "json",
		 		    success: function (data) {
		 		    	if(data.result == 'true' || data.result == true)
		 				{
		 		    		$.messager.alert("success", "密码重置密码成功！");
		 					goBack(1);
		 				}
		 				else
		 				{
		 					$.messager.alert("error", "密码重置密码失败 ！");
		 				}
		 		    }
		 		  });
  		   }
  	});
  } else {
		$.messager.alert("warning","请选择你要操作的记录!");
		return;
  }
  	
}

/**
 * 删除用户<br/>
 */
function deleUserrow() {
	var row = $('#dataTable').datagrid('getSelected');
	if (row) {
		com.message('confirm', '确定删除该用户吗？请谨慎操作！', '提示', function(r) {
			if (r) {
				$.post('${rootPath}/user/del', {
					rowId : row.User_Id
				}, function(data) {
					if (data.result == 'true' || data.result == true) {
						$.messager.alert("success", "用户删除成功！");
						goBack(2);
					} else {
						$.messager.alert("error", "用户删除失败 ！");
					}
				});
			}
		});
	} else {
        $.messager.alert("warning","请选择你要操作的记录!");
        return;
  }
}

function goBack(flag) {
	if (flag == 1) {
	    $("#divDialog").window('close');
		queryUserList();
	} else if (flag == 2) {
        queryUserList();
    }
}
//清空查询条件
function clearForm() {
    $('#dataHosForm').form('clear');

}
</script>
</body>
</html>