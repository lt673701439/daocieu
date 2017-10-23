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
<div>
	<form id="dataForm">
		<input type="hidden" name="couponTypeId"/>
		<input type="hidden" name="couponTypeValue"/>
		<input type="hidden" name="publishTypeValue"/>
		<div class="fitem">
	    	<label>编号:</label>
	        <input name="couponTypeCode" class="easyui-textbox" validType="length[0,50]" disabled>
		</div>
		<div class="fitem">
	    	<label>名称:</label>
	        <input name="couponName" class="easyui-textbox" validType="length[0,50]" >
		</div>
		<div class="fitem">
		    <label>是否有效:</label>
			<select class="easyui-combobox" name="validType"  style="width:80px" panelHeight="80px" >
	            <option value="0">有效</option>				        
				<option value="1">无效</option>
 			</select>
		</div>
		<div class="fitem">
		    <label>是否发布:</label>
			<select class="easyui-combobox" name="publishType" style="width:80px" panelHeight="80px"  disabled>
	            <option value="0">已发布</option>				        
				<option value="1">未发布</option>
 			</select>
		</div>
		<div class="fitem">
		    <label>类型:</label>
			<select class="easyui-combobox" name="couponType" id ="couponType" style="width:80px" panelHeight="80px">
 			</select>
		</div>
		<div class="fitem" id="specialOffer">
	    	<label>特价金额:</label>
	        <input name="specialOffer" id="number1" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        											min="0"	prefix="￥" validType="length[0,10]" style="width:120px" ></input> 元
		</div>
		<div class="fitem" style="display:none;" id="discount">
	    	<label>折扣比例:</label>
	        <input name="discount" id="number2" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        											min="0"	max="100" validType="length[0,10]" style="width:120px" ></input>
		</div>
		<div class="fitem" style="display:none;" id="deduction" >
	    	<label>抵扣金额:</label>
	        <input name="deduction" id="number3" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        										min="0"	prefix="￥" validType="length[0,10]" style="width:120px" ></input> 元
		</div>
		<div class="fitem">
	    	<label>屏幕:</label>
	        <input name="screenIds" id="screenIds" class="easyui-textbox" >
		</div>
		<div class="fitem">
	    	<label>商品:</label>
	        <input name="commodityIds" id="commodityIds" class="easyui-textbox" >
		</div>
		<div class="fitem">
	    	<label>祝福类型:</label>
	        <input name="benisonTypeIds" id="benisonTypeIds" class="easyui-textbox" >
		</div>
	</form>
    <div id="dlg-buttons" align="center">
   	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;

jQuery(function(){ 

	//初始化下拉框-示例，请根据需要自定义实现
	
	rowId ='${rowId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/couponType/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
	
	//按类型显示
	var couponType ='${couponTypeValue}';

	if(couponType == '0'){
		$("#specialOffer").show();
		$("#discount").hide();
		$("#deduction").hide();
	}else if(couponType == '1'){
		$("#specialOffer").hide();
		$("#discount").show();
		$("#deduction").hide();
	}else if(couponType == '2'){
		$("#specialOffer").hide();
		$("#discount").hide();
		$("#deduction").show();
	}
	
	//按发布类型看是否修改
	var publishType ='${publishTypeValue}';
	if(publishType == '0'){
		$("#couponType").combobox({
			disabled:true
		});
		$("#number1").numberbox({
			disabled:true
		});
		$("#number2").numberbox({
			disabled:true
		});
		$("#number3").numberbox({
			disabled:true
		});
		$("#screenIds").textbox({
			disabled:true
		});
		$("#commodityIds").textbox({
			disabled:true
		});
		$("#benisonTypeIds").textbox({
			disabled:true
		});
	}
	
});

//按类型显示
$("#couponType").combobox({
	valueField: 'couponType',
    textField: 'name',
    data:[{'name':'特价','couponType':'0'},{'name':'折扣','couponType':'1'},{'name':'抵扣','couponType':'2'}],
    value:"0",
	onChange: function (newValue,oldValue) {
		
		$("#number1").numberbox('clear');
		$("#number2").numberbox('clear');
		$("#number3").numberbox('clear');
		
		if(newValue == '0'){
			$("#specialOffer").show();
			$("#discount").hide();
			$("#deduction").hide();
		}else if(newValue == '1'){
			$("#specialOffer").hide();
			$("#discount").show();
			$("#deduction").hide();
		}else if(newValue == '2'){
			$("#specialOffer").hide();
			$("#discount").hide();
			$("#deduction").show();
		}
	}

});
	
//保存记录
function saveOrUpdate()
{
	var value = $("#couponType").combobox('getValue');
	var value1 = $("#number1").numberbox('getValue');
	var value2 = $("#number2").numberbox('getValue');
	var value3 = $("#number3").numberbox('getValue');
	
	//校验输入框
	if(value == '0' && value1 <= 0){
		$.messager.alert("提示", "请您输入特价金额(金额大于0)");
		return false;
	}else if(value == '1' && value2 <= 0){
		$.messager.alert("提示", "请您输入折扣比例(比例大于0且小于100)");
		return false;
	}else if(value == '2' && value3 <= 0){
		$.messager.alert("提示", "请您输入抵扣金额(金额大于0)");
		return false;
	}
	
	var r = $('#dataForm').form('validate');
	if(!r) {
		return false;
	}
	else
	{		
		$('#save').linkbutton('disable');
		$.post("${rootPath}/couponType/save",$("#dataForm").serializeArray(),
		function(data)
		{			
			if(data.result == 'true' || data.result == true)
			{
			 	$.messager.alert("提示", data.msg);
				goBack(1);
			}
			else
			{
				$.messager.alert("提示", data.msg);
				$('#save').linkbutton('enable');
			}
		});
	}
}
 
</script>

</body>
</html>
