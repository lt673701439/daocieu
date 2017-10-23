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
		<form id="dataForm1" method="post" action="${rootPath}/coupon/download">
			 <table style="width:100%; height:2%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td class="kTableLabel lbl">优惠码类型:</td>
					<td><select class="easyui-combobox" name="couponType" style="width:80px" panelHeight="100px">
							<option value="">&nbsp;</option>		
				            <option value="0">特价</option>				        
							<option value="1">折扣</option>
							<option value="2">抵扣</option>
			 			</select>
					</td>
					<td class="kTableLabel lbl">名称:</td>
					<td><input name="couponName" class="easyui-textbox" ></td>
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
		<table id="dataTable" title="优惠码列表" height="100%"></table>
	</div>
	<div id="divDialog"></div>
</div>
<script type="text/javascript">
	
	jQuery(function(){	  
   	//初始化列表
   	$('#dataTable').datagrid({
   		iconCls:'icon-tip',
   		singleSelect : true,
   		rownumbers:true,
   		pagination:true,
   		fitColumns:true,
   		url : '${rootPath}/coupon/list',
   		method : 'post',		
		idField : 'rowId',//此处根据实际情况进行填写
		columns:[[
							{field:'coupon_id',title:'couponId',width:80,hidden:true},
							{field:'coupon_type_id',title:'couponTypeId',width:80,hidden:true},
							{field:'coupon_code',title:'优惠码',width:80},
							{field:'coupon_name',title:'优惠码名称',width:80},
							{field:'coupon_type',title:'优惠码类型',width:40,
		                        formatter: function(value,row,index) {
		                        	if(value =='0'){
		                                return '特价';
		                            }else if(value =='1'){
		                                return '折扣';
		                            }else if(value =='2'){
		                                return '抵扣';
		                            }
		                        }
							},
							{field:'use_type',title:'是否使用',width:40,
		                        formatter: function(value,row,index) {
		                        	if(value =='0'){
		                                return '已使用';
		                            }else if(value =='1'){
		                                return '未使用';
		                            }else if(value =='2'){
		                                return '已过期';
		                            }
		                        }
							},
							{field:'publish_date',title:'发布日期',width:80},
							{field:'publish_target_type',title:'发布对象',width:80,
		                        formatter: function(value,row,index) {
		                        	if(value =='0'){
		                                return '个人';
		                            }else if(value =='1'){
		                                return '商户';
		                            }
		                        }
							},
							{field:'personal_name',title:'个人姓名',width:80},
							{field:'merchant_name',title:'商户姓名',width:80},
							{field:'coupon_num',title:'发布数量',width:80},
							{field:'valid_date',title:'有效日期',width:80},
							{field:'publish_by',title:'发布人',width:80},
							{
								field : 'operate',
								title : '操作',
								width : 120,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=viewrow('"+row.coupon_id+"','"+row.publish_target_type+"') style='margin-left:20px'>[查看详细]</a>"
// 									"<a href='#' onclick=editrow('"+row.coupon_id+"','"+row.coupon_type+"','"+row.publish_type+"') style='margin-left:10px'>[修改]</a>"
// 									+"<a href='#' onclick=delerow('"+row.coupon_type_id+"','"+row.publish_type+"') style='margin-left:20px'>[删除]</a>"
								}
							},
							{field:'valid_type',title:'validType',width:80,hidden:true},
							{field:'publish_type',title:'publishType',width:80,hidden:true},
							{field:'id_card',title:'idCard',width:80,hidden:true},
							{field:'business_licence',title:'businessLicence',width:80,hidden:true},
							{field:'order_code',title:'orderCode',width:80,hidden:true},
							{field:'use_time',title:'useTime',width:80,hidden:true},
							{field:'created_time',title:'createdTime',width:80,hidden:true},
							{field:'created_by',title:'createdBy',width:80,hidden:true},
							{field:'modified_time',title:'modifiedTime',width:80,hidden:true},
							{field:'modified_by',title:'modifiedBy',width:80,hidden:true},
							{field:'version',title:'version',width:80,hidden:true},
							{field:'delflag',title:'delflag',width:80,hidden:true}
						//注：最后一行后面的逗号要去掉
		]],
		toolbar : [{
			id : 'btnadd',
			text : '发布',
			iconCls : 'icon-add',
			handler : function() {
				addrow();
			}
		},{
			id : 'btnexport',
			text : '导出',
			iconCls : 'icon-page_excel',
			handler : function() {
				$('#dataForm1').form('submit');
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
		var url = '${rootPath}/coupon/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo()
	}

	//新增
	   function addrow(){
			url = '${rootPath}/coupon/add';
	   		$("#divDialog").dialog({
	   			title : "新增优惠码",
	   			width : 450,
	   			height : 450,
	   			href : url,
	   			cache : false,
	   			closed : false,
	   			modal : true
	   		});
	   }
	   
	   // 修改
	   function editrow(rowId,couponType,publishType){   
	       if (rowId){
	    	   	url = '${rootPath}/coupon/edit?rowId='+ rowId+'&couponTypeValue='+couponType+'&publishType='+publishType;
		   		$("#divDialog").dialog({
		   			title : "修改优惠码类型",
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
	   
	   //查看
	   function viewrow(rowId,publishTargetType){    	
	       if (rowId){
	    	   	url = '${rootPath}/coupon/view?rowId='+ rowId+'&publishTargetType='+publishTargetType;
		   		$("#divDialog").dialog({
		   			title : "查看优惠码信息",
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
	   function delerow(rowId,publishType){
		   if(publishType == '0'){
			   $.messager.alert('提示','该优惠码已发布，不能删除');
			   return false;
		   }
//	        var row = $('#dataTable').datagrid('getSelected');
	       if (rowId){
	           $.messager.confirm('提示','确定要删除行记录吗？',function(r){
	               if (r){
	                   $.post('${rootPath}/coupon/del',{rowId:rowId},function(data){
	                   	
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