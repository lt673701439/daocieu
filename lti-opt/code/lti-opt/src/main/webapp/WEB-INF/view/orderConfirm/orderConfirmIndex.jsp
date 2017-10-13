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
    <div class="easyui-panel" title="查询条件" data-options="region:'north',height:110,collapsible:false,border : false">
		<!-- 注意：form ID 要加 1，用于区别子页面 form -->
		<form id="dataForm1" method="post">
			 <table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td style="height:10px;" class="kTableLabel lbl">订单编号:</td>
					<td style="height:10px;"><input name="orderCode" id ="orderCode" class="easyui-textbox" ></td>
					<td style="height:10px;" class="kTableLabel lbl">交易单号:</td>
					<td style="height:10px;"><input name="transactionNo" id ="transactionNo" class="easyui-textbox" ></td>
					<td style="height:10px;" class="kTableLabel lbl">用户昵称:</td>
					<td style="height:10px;"><input name="userNickname" id ="userNickname" class="easyui-textbox" ></td>
				</tr>
				<tr>
					<td style="height:10px;" class="kTableLabel lbl">日期:</td>
					<td style="height:10px;"><input name="startDate" id ="startDate" class="easyui-datebox" style="width:100px"> ~ <input name="endDate" id ="endDate" class="easyui-datebox" style="width:100px"></td>
					<td style="height:25px;" class="kTableLabel lbl">屏幕:</td>
					<td style="height:10px;"><input name="screenName" id ="screenName" class="easyui-textbox" ></td>
					<td style="height:10px;" class="kTableLabel lbl">确认状态:</td>
					<td style="height:10px;"><select class="easyui-combobox" name="confirmStatus" id="confirmStatus" style="width:80px" panelHeight="100px">
						            <option value="">&nbsp;</option>				        
									<option value="0" selected>待确认</option>
									<option value="1">确认</option>
									<option value="2">拒绝</option>
						 </select></td>
				</tr>
				<tr>
				<td style="height:10px;" valign="middle" align="center" colspan="6" >
                 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo()" style="width:90px">查询</a>
                	&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-empty" onclick="clearForm()" style="width:90px;margin-left:30px">清空</a>
                </td>
                </tr>
                </table>
		</form>
    </div>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable" title="订单列表" height="100%"></table>
	</div>
	<div id="divDialog"></div>
</div>

<script type="text/javascript">

jQuery(function(){	  
	
	jQuery.ajaxSetup({
		cache : false
	});
	
	// 循环刷新列表数据（1分钟）
	window.setInterval(searchInfo, 60000); 
	
	// 初始化列表
   	$('#dataTable').datagrid({
   		singleSelect : true,
   		rownumbers:true,
   		pagination:true,
   		fitColumns:true,
   		url : '${rootPath}/orderConfirm/list?orderStatus=2&confirmStatus=0',
   		method : 'post',		
		idField : 'order_id',//此处根据实际情况进行填写
		columns:[[
	          			// 注意：这里是字段名SELLER_CODE，不是变量名sellerCode
// 							{field : 'ck',checkbox : true},
							{field:'created_time',title:'创建时间',width:60},
							{field:'order_code',title:'订单编号',width:80},
							{field:'transaction_no',title:'交易单号',width:80},
// 							{field:'order_status',title:'订单状态',width:30,
// 		                        formatter: function(value,row,index) {
// 		                            // 0-草稿，1-待支付，2-已支付，3-已播放，4-取消，5-已退单，6-已退款。
// 		                        	if(value=='0'){
// 		                                return '草稿';
// 		                            }else if(value=='1'){
// 		                                return '待支付';
// 		                            }else if(value=='2'){
// 		                                return '已支付';
// 		                            }else if(value=='3'){
// 		                                return '已播放';
// 		                            }else if(value=='4'){
// 		                                return '已取消';
// 		                            }else if(value=='5'){
// 		                                return '已退单';
// 		                            }else if(value=='6'){
// 		                                return '已退款';
// 		                            }
// 		                        }},
							{field:'order_type',title:'订单类型',width:30,
		                        formatter: function(value,row,index) {
		                        	if(value=='0'){
		                                return '普通';
		                            }else if(value=='1'){
		                                return '自制上传';
		                            }else if(value=='2'){
		                                return 'DIY排版';
		                            }else if(value=='3'){
		                                return '定制设计';
		                            }
		                        }},
							{field:'confirm_status',title:'确认状态',width:30,
		                        formatter: function(value,row,index) {
		                        	if(value=='0'){
		                                return '待确认';
		                            }else if(value=='1'){
		                                return '通过';
		                            }else if(value=='2'){
		                                return '拒绝';
		                            }
		                        }},
							{field:'user_nickname',title:'用户昵称',width:45},
							{field:'screen_name',title:'屏幕',width:60},
							{
								field : 'operate',
								title : '操作',
								width : 60,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=viewrow('"+row.order_id+"','"+row.order_type+"') style='margin-left:10px'>[详细]</a>"
									+"<a href='#' onclick=confirmrow('"+row.order_id+"','"+row.order_type+"') style='margin-left:20px'>[确认]</a>"
								}
							}
						//注：最后一行后面的逗号要去掉
		]],
// 		toolbar :
// 			[{
// 				id : 'btnexport',
// 				text : '导出',
// 				iconCls : 'icon-page_excel',
// 				handler : function() {
// 					$('#dataForm1').form('submit');
// 				}
// 			}],
		onLoadSuccess : function(data) {
			$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
   	
   });
   
	// 表格查询
	function searchInfo() {
		//查询参数直接添加在queryParams中
		var queryParams = $('#dataTable').datagrid('options').queryParams;
		var fields = $('#dataForm1').serializeArray(); //自动序列化表单元素为JSON对象
	
		$.each(fields, function(i, field) {
			queryParams[field.name] = field.value; //设置查询参数
	
		});
		var url = '${rootPath}/orderConfirm/list?orderStatus=2&';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	// 清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
// 		$('#confirmStatus').combobox('setValue','0');
		searchInfo();
	}

   // 确认
   function confirmrow(rowId,orderType){    	  
       if (rowId){
    	   	url = '${rootPath}/orderConfirm/handle?rowId='+ rowId + '&orderType=' + orderType;
	   		$("#divDialog").dialog({
	   			title : "订单确认",
	   			width : 650,
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
   
   // 查看
   function viewrow(rowId,orderType){    	
       if (rowId){
    	   	url = '${rootPath}/orderConfirm/view?rowId='+ rowId + '&orderType=' + orderType;
	   		$("#divDialog").dialog({
	   			title : "订单确认详细信息",
	   			width : 650,
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