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
					<td class="kTableLabel lbl">编号:</td>
					<td><input name="promotionCode" id ="promotionCode" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">名称:</td>
					<td><input name="promotionName" id ="promotionName" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">状态:</td>
					<td><select class="easyui-combobox" name="promotionStatus" id ="promotionStatus" style="width:80px" panelHeight="60px">
				            <option value=""></option>				        
							<option value="1">上架</option>
							<option value="2">下架</option>
			 			</select>
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
		<table id="dataTable" title="促销活动列表" height="100%"></table>
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
   		url : '${rootPath}/promotion/list',
   		method : 'post',		
		idField : 'promotion_id',//此处根据实际情况进行填写
		columns:[[
	          				// 注意：这里是字段名SELLER_CODE，不是变量名sellerCode
// 							{field : 'ck',checkbox : true},
							{field:'promotion_code',title:'编号',width:60},
							{field:'promotion_name',title:'名称',width:60},
							{field:'promotion_status',title:'状态',width:25,
		                        formatter: function(value,row,index) {
		                        	if(value=='1'){
		                                return '上架';
		                            }else if(value=='2'){
		                                return '下架';
		                            }
		                        }},
		                    {field:'sort_num',title:'排序',width:60},
							{field:'add_time',title:'上架时间',width:70},
							{field:'remove_time',title:'下架时间',width:70},
							{field:'start_time',title:'开始时间',width:70},
							{field:'end_time',title:'结束时间',width:70},
							{
								field : 'operate',
								title : '操作',
								width : 150,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.promotion_id+"') style='margin-left:10px'>[修改]</a>"
									+"<a href='#' onclick=viewrow('"+row.promotion_id+"') style='margin-left:10px'>[详细]</a>"
									+"<a href='#' onclick=delerow('"+row.promotion_id+"') style='margin-left:10px'>[删除]</a>"
									+"<a href='#' onclick=descrow('"+row.promotion_id+"') style='margin-left:10px'>[描述]</a>"
									+"<a href='#' onclick=detailindex('"+row.promotion_id+"') style='margin-left:10px'>[促销商品]</a>"
									+"<a href='#' onclick=preview('"+row.promotion_id+"') style='margin-left:10px'>[预览]</a>"
								}
							},
							{field:'promotion_id',title:'promotion_id',width:80,hidden:true}
						//注：最后一行后面的逗号要去掉
		]],
		toolbar : [{
			id : 'btnadd',
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				addrow();
			}
// 		},'-', {
// 			id : 'btnsku',
// 			text : '商品sku',
// 			iconCls : 'icon-page_excel',
// 			handler : function() {
// 				skuindex();
// 			}
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
		var url = '${rootPath}/promotion/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo();
	}

   	//新增
   	function addrow(){
	    url = '${rootPath}/promotion/add';
		$("#divDialog").dialog({
			title : "添加促销活动",
			width : 650,
			height : 480,
			href : url,
			cache : false,
			closed : false,
			modal : true
		});
   	}
	
	// 修改
   	function editrow(rowId){   
       if (rowId){
    	   	url = '${rootPath}/promotion/edit?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改促销活动信息",
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
   
   	// 查看
   	function viewrow(rowId){    	
       if (rowId){
    	   	url = '${rootPath}/promotion/view?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "查看促销活动信息",
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
   	
    //删除
    function delerow(rowId){
        if (rowId){
            $.messager.confirm('提示','确定要删除行记录吗？',function(r){
                if (r){
                    $.post('${rootPath}/promotion/del',{rowId:rowId},function(data){
                    	
                    	if(data.result == 'true' || data.result == true){
                    		$.messager.alert("提示", "删除成功！");
 							goBack(1);
    					}else{
    						$.messager.alert("提示", "删除失败 ！");
    					}                    	
                    });
                }
            });
        } else {
     	   $.messager.alert('提示', "请选择你要操作的记录", 'info');
 			return;
 		}
    }
    
    // 描述
    function descrow(rowId){    	
    	url = '${rootPath}/promotion/desc?rowId='+rowId;
	    window.location.href=url;
    }
    
 	// 活动详情
   	function detailindex(rowId){
	    url = '${rootPath}/promotionDetail?rowId='+rowId;
	    window.location.href=url;
   	}
    
    
 	// 预览
   	function preview(rowId){    	
       if (rowId){
    	   	url = '${rootPath}/promotion/preview?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "促销活动预览",
	   			width : 350,
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