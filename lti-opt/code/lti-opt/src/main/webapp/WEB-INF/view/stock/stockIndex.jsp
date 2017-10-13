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
		<form id="dataForm1" method="post" >
			 <table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td class="kTableLabel lbl">日期:</td>
					<td><input name="stockDate" class="easyui-datebox"  style="width:205px"></td>
					<td class="kTableLabel lbl">商品名称:</td>
					<td><input name="commodityName" id ="commodityName" class="easyui-textbox" ></td>
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
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable" title="库存列表" height="100%"></table>
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
   		dataType:'text',
   		url : '${rootPath}/stock/list',
   		method : 'post',		
		idField : 'stock_id',//此处根据实际情况进行填写
		columns:[[
							{field:'stock_date',title:'日期',width:60},
							{field:'start_time',title:'开始时间',width:60},
							{field:'end_time',title:'结束时间',width:60},
							{field:'commodity_name',title:'商品名称',width:80},
							{field:'stock_status',title:'库存状态',width:40,hidden:true,
		                        formatter: function(value,row,index) {
		                        	if(value=='1'){
		                                return '自由';
		                            }else if(value=='2'){
		                                return '锁定';
		                            }else if(value=='3'){
		                                return '占用';
		                            }
		                        }},
	                        {field:'commodity_status',title:'商品状态',width:40,
		                        formatter: function(value,row,index) {
		                        	if(value=='1'){
		                                return '正常';
		                            }else if(value=='2'){
		                                return '下架';
		                            }
		                        }},
							{field:'sales',title:'销量',width:60},
							{field:'stock',title:'存量',width:60},
							{field:'schedule_status',title:'排期状态',width:40,
		                        formatter: function(value,row,index) {
		                        	if(value=='0'){
		                                return '未排期';
		                            }else if(value=='1'){
		                                return '已排期';
		                            }
		                        }},
	                        {field:'export_by',title:'导出人',width:60},
	                        {field:'export_time',title:'导出时间',width:60},
							{
								field : 'operate',
								title : '操作',
								width : 120,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.stock_id+"') style='margin-left:10px'>[修改]</a>"
									+"<a href='#' onclick=viewrow('"+row.stock_id+"') style='margin-left:20px'>[详细]</a>"
									+"<a href='#' onclick=exportImg('"+row.commodity_id+"','"+row.stock_date+"','"+row.commodity_status+"') style='margin-left:10px'>[导出]</a>"
// 									+"<a href='#' onclick=delerow('"+row.stock_id+"') style='margin-left:20px'>[删除]</a>"
								}
							},
							{field:'stock_id',title:'stockId',width:80,hidden:true},
							{field:'commodity_id',title:'commodityId',width:80,hidden:true},
							{field:'created_time',title:'createdTime',width:80,hidden:true},
							{field:'created_by',title:'createdBy',width:80,hidden:true},
							{field:'modified_time',title:'modifiedTime',width:80,hidden:true},
							{field:'modified_by',title:'modifiedBy',width:80,hidden:true},
							{field:'version',title:'version',width:80,hidden:true},
							{field:'delflag',title:'delflag',width:80,hidden:true},
						//注：最后一行后面的逗号要去掉
		]],
// 		toolbar : [{
// 			id : 'btnadd',
// 			text : '添加',
// 			iconCls : 'icon-add',
// 			handler : function() {
// 				addrow();
// 			}
// 		}, '-', {
// 			id : 'btnedit',
// 			text : '更新',
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
   
	//导出点播图片
	function exportImg(rowId,playDate,commodityStatus){
		if (rowId&&playDate&&commodityStatus){
	    	$('#dataForm1').form('submit', { 
			        url: "${rootPath}/commodity/exportImg?rowId="+rowId+"&playDate="+playDate+"&commodityStatus="+commodityStatus, 
					success: function (data) {  //表单提交成功后的回调函数，里面参数data是我们调用方法的返回值。 
						var json = JSON.parse(data);
						if(json.result == 'true' || json.result == true){
							$.messager.alert("提示", json.msg);
							searchInfo();
							//导出压缩包
							$('#dataForm1').form('submit', { 
						        url: '${rootPath}/commodity/compressZip?path='+json.path+"&exportTime="+json.exportTime+"&screenName="+json.screenName
											+"&timeRange="+json.timeRange,  
								success: function (data) {
									
								}
							});
						}else{
							$.messager.alert("提示", json.msg);
							$('#save').linkbutton('enable');
						}
		      	  	}
			});
		} else {
	  	   $.messager.alert('提示', "请选择你要操作的记录", 'info');
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
		var url = '${rootPath}/stock/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}

	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo();
	}

//    //新增
//    function addrow(){
//    		url = '${rootPath}/stock/edit';
// 		openWin(url);
//    }
   
//    //修改
//    function editrow(){    	
//        var row = $('#dataTable').datagrid('getSelected');
	
//        if (row){
//        	url = '${rootPath}/stock/edit?rowId='+ row.rowId;
// 		openWin(url);
//        }
//        else
//        {
//        	$.messager.alert('提示', "请选择你要更新的记录", 'info');
// 		return;
//        }
//    }
   
//    //删除
//    function delerow(){
//        var row = $('#dataTable').datagrid('getSelected');
//        if (row){
//            $.messager.confirm('提示','确定要删除行记录吗？',function(r){
//                if (r){
//                    $.post('${rootPath}/stock/del',{rowId:row.rowId},function(data){
                   	
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
   
//    //点击增加弹出增加窗口
// 	function openWin(url) {
// 		$('#iframeDialogSelect').attr("src", url);
// 		$('#divDialog').window('open');

// 	}
	
// 	//关闭弹出窗口，返回父页面,根据标记决定是否执行查询操作
// 	function returnParent(flag) {
// 		if(flag==1)
// 		{
// 			searchInfo();
// 		}
// 		$("#divDialog").window('close');
// 	}

   // 修改
   function editrow(rowId){   
       if (rowId){
    	   	url = '${rootPath}/stock/edit?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改库存信息",
	   			width : 550,
	   			height : 480,
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
    	   	url = '${rootPath}/stock/view?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "查看库存信息",
	   			width : 550,
	   			height : 480,
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