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
		<form id="dataForm1" method="post" action="${rootPath}/order/download">
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
					<td style="height:10px;" class="kTableLabel lbl">状态:</td>
					<td style="height:10px;"><select class="easyui-combobox" name="orderStatus" style="width:80px" panelHeight="180px">
						            <option value=""></option>				        
									<option value="0">草稿</option>
									<option value="1">待支付</option>
									<option value="2">已支付</option>
									<option value="3">已播放</option>
									<option value="4">已取消</option>
									<option value="5">已退单</option>
									<option value="6">已退款</option>
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
	
	//初始化列表
   	$('#dataTable').datagrid({
   		singleSelect : true,
   		rownumbers:true,
   		pagination:true,
   		fitColumns:false,
   		url : '${rootPath}/order/list',
   		method : 'post',		
		idField : 'order_id',//此处根据实际情况进行填写
		columns:[[
	          			// 注意：这里是字段名SELLER_CODE，不是变量名sellerCode
// 							{field : 'ck',checkbox : true},
							{field:'created_time',title:'创建时间',width:120},
							{field:'order_code',title:'订单编号',width:160},
							{field:'transaction_no',title:'交易单号',width:180},
							{field:'order_type',title:'订单类型',width:60,
		                        formatter: function(value,row,index) {
		                            // 0-草稿，1-待支付，2-已支付，3-已播放，4-取消，5-已退单，6-已退款。
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
							{field:'order_status',title:'订单状态',width:45,
		                        formatter: function(value,row,index) {
		                            // 0-草稿，1-待支付，2-已支付，3-已播放，4-取消，5-已退单，6-已退款。
		                        	if(value=='0'){
		                                return '草稿';
		                            }else if(value=='1'){
		                                return '待支付';
		                            }else if(value=='2'){
		                                return '已支付';
		                            }else if(value=='3'){
		                                return '已播放';
		                            }else if(value=='4'){
		                                return '已取消';
		                            }else if(value=='5'){
		                                return '已退单';
		                            }else if(value=='6'){
		                                return '已退款';
		                            }
		                        }},
							{field:'total_price',title:'价格(元)',width:45,
		                        formatter:function(val,rowData,rowIndex){
							        if(val!=null)
							            return val.toFixed(2);
							  			 }
			                        },
							{field:'user_nickname',title:'用户昵称',width:60},
							{field:'screen_name',title:'屏幕',width:180},
							{
								field : 'operate',
								title : '操作',
								width : 250,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.order_id+"') style='margin-left:10px'>[修改]</a>"
									+"<a href='#' onclick=viewrow('"+row.order_id+"') style='margin-left:10px'>[订单详情]</a>"
									+"<a href='#' onclick=applyOrder('"+row.order_status+"','"+row.confirm_status+"','"+row.order_id+"') style='margin-left:10px'>[退单申请]</a>"
									+"<a href='#' onclick=returnOrder('"+row.order_status+"','"+row.confirm_status+"','"+row.order_id+"','"+row.back_reason+"') style='margin-left:10px'>[退单确认]</a>"
								}
							},
							{field:'back_reason',title:'backReason',width:80,hidden:true}
// 							{field:'user_phone',title:'用户电话',width:80,hidden:true},
// 							{field:'order_id',title:'orderId',width:80,hidden:true},
// 							{field:'user_id',title:'userId',width:80,hidden:true},
// 							{field:'bless_name',title:'blessName',width:80,hidden:true},
// 							{field:'write_name',title:'writeName',width:80,hidden:true},
// 							{field:'template_id',title:'templateId',width:80,hidden:true},
// 							{field:'pay_type',title:'payType',width:80,hidden:true},
// 							{field:'pay_time',title:'payTime',width:80,hidden:true},
// 							{field:'pay_price',title:'payPrice',width:80,hidden:true},
// 							{field:'back_time',title:'backTime',width:80,hidden:true},
// 							{field:'result_url',title:'resultUrl',width:80,hidden:true},
// 							{field:'created_time',title:'createdTime',width:80,hidden:true},
// 							{field:'created_by',title:'createdBy',width:80,hidden:true},
// 							{field:'modified_time',title:'modifiedTime',width:80,hidden:true},
// 							{field:'modified_by',title:'modifiedBy',width:80,hidden:true},
// 							{field:'version',title:'version',width:80,hidden:true},
// 							{field:'delflag',title:'delflag',width:80,hidden:true},
						//注：最后一行后面的逗号要去掉
		]],
		toolbar :
			[{
				id : 'btnexport',
				text : '导出',
				iconCls : 'icon-page_excel',
				handler : function() {
					$('#dataForm1').form('submit');
				}
			}],
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
   
	//表格查询
	function searchInfo() {
		//查询参数直接添加在queryParams中
		var queryParams = $('#dataTable').datagrid('options').queryParams;
		var fields = $('#dataForm1').serializeArray(); //自动序列化表单元素为JSON对象
	
		$.each(fields, function(i, field) {
			queryParams[field.name] = field.value; //设置查询参数
	
		});
		var url = '${rootPath}/order/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo();
	}

//    //新增
//    function addrow(){
//    		url = '${rootPath}/order/edit';
// 		openWin(url);
//    }
   
   //修改
//    function editrow(){    	
//        var row = $('#dataTable').datagrid('getSelected');
	
//        if (row){
//        	url = '${rootPath}/order/edit?rowId='+ row.rowId;
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
    	   	url = '${rootPath}/order/edit?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改订单信息",
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
   
   //查看
   function viewrow(rowId){    	
       if (rowId){
    	   	url = '${rootPath}/order/view?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "查看订单详情",
	   			width : 650,
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
   
 //退单
   function returnOrder(status,confirm_status,rowId,backReason){
	 
	   if(backReason == null || backReason == 'undefined'){
		   $.messager.show({title:'提示', msg:'请先提交退单申请', timeout:3000, style:{right:'',bottom:''}});
	 		return;
	   }
	 
	   if(status !== "2" && status !== "3"){
	 		$.messager.show({title:'提示', msg:'只支持已支付和已播放退单', timeout:3000, style:{right:'',bottom:''}});
	 		return;
	 	}
	   if("1" !== confirm_status){
	 		$.messager.show({title:'提示', msg:'未订单确认，不可执行操作', timeout:3000, style:{right:'',bottom:''}});
	 		return;
	 	}
       if (rowId){
    	   	url = '${rootPath}/order/returnView?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "退单", 
	   			width : 650, height : 580, href : url,
	   			cache : false, closed : false, modal : true
	   		});
       }
       else
       {
       	$.messager.alert('提示', "请选择你要操作的记录", 'info');
		return;
       }
   }
 //退单申请
 function applyOrder(status,confirm_status,rowId){
	 
	 	if(status !== "2" && status !== "3"){
	 		$.messager.show({title:'提示', msg:'只支持已支付和已播放退单申请', timeout:3000, style:{right:'',bottom:''}});
	 		return;
	 	}
	 	if("1" !== confirm_status){
	 		$.messager.show({title:'提示', msg:'未订单确认，不可执行操作', timeout:3000, style:{right:'',bottom:''}});
	 		return;
	 	}
	 	if (rowId){
    	   	url = '${rootPath}/order/applyOrderView?rowId=' + rowId;
	   		$("#divDialog").dialog({
	   			title : "退单申请",
	   			width : 650, height : 450, href : url,
	   			cache : false, closed : false, modal : true,
	   			closable:false, onBeforeClose:function(){$("#divDialog").dialog({closable:true});}
	   		});
       } else {
			$.messager.alert('提示', "请选择你要操作的记录", 'info');
			return;
       }
 }
   
//    //删除
//    function delerow(){
//        var row = $('#dataTable').datagrid('getSelected');
//        if (row){
//            $.messager.confirm('提示','确定要删除行记录吗？',function(r){
//                if (r){
//                    $.post('${rootPath}/order/del',{rowId:row.rowId},function(data){
                   	
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