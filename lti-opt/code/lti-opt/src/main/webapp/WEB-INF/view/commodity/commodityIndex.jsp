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
					<td><input name="commodityCode" id ="commodityCode" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">名称:</td>
					<td><input name="commodityName" id ="commodityName" class="easyui-textbox" ></td>
					<td class="kTableLabel lbl">日期:</td>
					<td><input name="commodityDate" class="easyui-datebox"  style="width:205px"></td>
					<td class="kTableLabel lbl">状态:</td>
					<td><select class="easyui-combobox" name="commodityStatus" style="width:80px" panelHeight="60px">
				            <option value=""></option>				        
							<option value="1">正常</option>
							<option value="2">下架</option>
			 			</select>
					</td>
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
		<table id="dataTable" title="商品列表" height="100%"></table>
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
   		url : '${rootPath}/commodity/list',
   		method : 'post',		
		idField : 'commodity_id',//此处根据实际情况进行填写
		columns:[[
	          				// 注意：这里是字段名SELLER_CODE，不是变量名sellerCode
// 							{field : 'ck',checkbox : true},
							{field:'commodity_id',title:'commodityId',width:80,hidden:true},
							{field:'commodity_code',title:'编号',width:130},
							{field:'commodity_name',title:'名称',width:120},
							{field:'commodity_status',title:'状态',width:40,
		                        formatter: function(value,row,index) {
		                        	if(value=='1'){
		                                return '正常';
		                            }else if(value=='2'){
		                                return '下架';
		                            }
		                        }},
							{field:'screen_name',title:'屏幕名称',width:80},
							{field:'start_date',title:'开始日期',width:80},
							{field:'end_date',title:'结束日期',width:80},
							{field:'start_time',title:'开始时间',width:50},
							{field:'end_time',title:'结束时间',width:50},
							{field:'export_time',title:'导出时间',width:70,hidden:true},
							{field:'export_by',title:'导出人',width:60,hidden:true},
							{
								field : 'operate',
								title : '操作',
								width : 240,
								formatter : function(value, row,index) {
									return "<a href='#' onclick=editrow('"+row.commodity_id+"') style='margin-left:10px'>[修改]</a>"
									+"<a href='#' onclick=viewrow('"+row.commodity_id+"') style='margin-left:10px'>[详细]</a>"
									+"<a href='#' onclick=delerow('"+row.commodity_id+"') style='margin-left:10px'>[删除]</a>"
// 									+"<a href='#' onclick=exportImg('"+row.commodity_id+"') style='margin-left:10px'>[导出]</a>"
									+"<a href='#' onclick=costindex('"+row.commodity_id+"') style='margin-left:10px'>[价格体系]</a>"
// 									+"<a href='#' onclick=skuindex('"+row.commodity_id+"') style='margin-left:10px'>[sku]</a>"
// 									+"<a href='${rootPath}/commoditySku?commodityId" +row.commodity_id+ "' style='margin-left:20px'>[sku]</a>"
								}
							},
							{field:'commodity_description',title:'commodityDescription',width:80,hidden:true},
							{field:'commodity_type',title:'commodityType',width:80,hidden:true},
							{field:'commodity_price',title:'commodityPrice',width:80,hidden:true},
							{field:'screen_id',title:'screenId',width:80,hidden:true},
							{field:'time_frame',title:'timeFrame',width:80,hidden:true},
							{field:'shelf_time',title:'shelfTime',width:80,hidden:true},
							{field:'plan_number',title:'planNumber',width:80,hidden:true},
							{field:'single_time',title:'singleTime',width:80,hidden:true},
							{field:'play_times',title:'playTimes',width:80,hidden:true},
							{field:'set_name',title:'setName',width:80,hidden:true},
							{field:'set_price',title:'setPrice',width:80,hidden:true},
							{field:'set_system',title:'setSystem',width:80,hidden:true},
							{field:'created_time',title:'createdTime',width:80,hidden:true},
							{field:'created_by',title:'createdBy',width:80,hidden:true},
							{field:'modified_time',title:'modifiedTime',width:80,hidden:true},
							{field:'modified_by',title:'modifiedBy',width:80,hidden:true},
							{field:' ',title:'version',width:80,hidden:true},
							{field:'delflag',title:'delflag',width:80,hidden:true},
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
		var url = '${rootPath}/commodity/list';
		$('#dataTable').datagrid('reload',url); //设置好查询参数 reload 一下就可以了
	}
	
	//清空查询条件
	function clearForm() {
		$('#dataForm1').form('clear');
		searchInfo();
	}

   	//新增
   	function addrow(){
	    url = '${rootPath}/commodity/add';
		$("#divDialog").dialog({
			title : "添加商品",
			width : 650,
			height : 480,
			href : url,
			cache : false,
			closed : false,
			modal : true
		});
   	}
	
   	// SKU
//    	function skuindex(rowId){
// 	    url = '${rootPath}/commoditySku?rowId='+rowId;
// 	    window.location.href=url;
//    	}
   	
   	// cost
   	function costindex(rowId){
	    url = '${rootPath}/commodityCost?rowId='+rowId;
	    window.location.href=url;
   	}
   	
	// 修改
   	function editrow(rowId){   
       if (rowId){
    	   	url = '${rootPath}/commodity/edit?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "修改商品信息",
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
    	   	url = '${rootPath}/commodity/view?rowId='+ rowId;
	   		$("#divDialog").dialog({
	   			title : "查看商品信息",
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
                    $.post('${rootPath}/commodity/del',{rowId:rowId},function(data){
                    	
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
     	   $.messager.alert('提示', "请选择你要操作的记录", 'info');
 			return;
 		}
    }
    
  	//导出点播图片
    function exportImg(rowId){
    	if (rowId){
	    	$('#dataForm1').form('submit', { 
   		        url: "${rootPath}/commodity/exportImg?rowId="+rowId, 
	    		success: function (data) {  //表单提交成功后的回调函数，里面参数data是我们调用方法的返回值。 
					var json = JSON.parse(data)
					if(json.result == 'true' || json.result == true){
						$.messager.alert("提示", json.msg);
						goBack(1);
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