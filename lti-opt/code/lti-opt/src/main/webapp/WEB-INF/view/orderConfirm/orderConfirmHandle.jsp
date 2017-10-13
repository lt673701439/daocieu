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
	<form id="dataForm" enctype="multipart/form-data" accept="image/gif,image/jpeg,image/jpg,image/png" method="post">
		<input type="hidden" name="orderId">
		<input type="hidden" name="payType">
		<div class="fitem">
	    	<label>订单编号:</label>
	        <input name="orderCode" class="easyui-textbox" readonly>
		</div>
		<div class="fitem">
	    	<label>交易单号:</label>
	        <input name="transactionNo" class="easyui-textbox" readonly>
		</div>
		<div class="fitem">
		    <label>订单类型:</label>
			<select class="easyui-combobox" name="orderType" style="width:80px" panelHeight="60px" readonly>
			            <option value=""></option>				        
						<option value="0">普通</option>
						<option value="1">自制上传</option>
						<option value="2">DIY排版</option>
						<option value="3">定制设计</option>
			</select>
		</div>
		<c:if test="${orderType != '0'}">
		<div class="fitem">
			<br>
			<label></label>
		    <B><font color="#f24945" style="font-size:10px;">注意：通过时，请上传结果图片！拒绝时，不保存上传的图片。</font></B>
		</div>
		<div class="fitem">
			<label>结果图片:</label>
			<input class="easyui-filebox" name="newResultUrl"  data-options="prompt:'请选择一个图片'" >
		</div>
		</c:if>
		<div class="fitem">
			<br>
			<label></label>
			<B><font color="#f24945" style="font-size:10px;">注意：拒绝时，请填写拒绝原因！通过时，不保存拒绝原因。</font></B>
		</div>
		<div class="fitem">
		    <label>拒绝原因:</label>
		    <input id="confirmReason" name="confirmReason" class="easyui-textbox" data-options="multiline:true"  style="width:400px;height:60px">
		</div>
	</form>
    <div id="dlg-buttons" align="center">
   	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="pass" onclick="passConfirm()" style="width:90px">通过</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-user-reject" id="reject" onclick="rejectConfirm()" style="width:90px">拒绝</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">
var rowId;
var orderType;
jQuery(function(){ 
	// 注意：不要读取缓存
	jQuery.ajaxSetup({
		cache : false
	});
	
	rowId ='${rowId}';
	orderType = '${orderType}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/orderConfirm/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
	
});

// 通过
function passConfirm()
{
	//校验必填项
	var r = $('#dataForm').form('validate');
	if(!r) {
		return false;
	}
	
	$('#dataForm').form('submit', { 
        url: "${rootPath}/orderConfirm/pass", 
        onSubmit: function (){
			
			// 当订单类型为‘定制’时，需要验证是否有图片文件
			if(orderType == '3'){
				var imgFileArr=$(":file");
				var fileName=imgFileArr[0];
				if($.trim($(fileName).val()) == "" || $(fileName).val() == null){
					$.messager.alert("提示", "请上传一张图片！");				
					return false;
				}
			}
	
			$('#pass').linkbutton('disable');
        }, 
        success: function (data) {  //表单提交成功后的回调函数，里面参数data是我们调用方法的返回值。 
			var json = JSON.parse(data)
			if(json.result == 'true' || json.result == true){
				$.messager.alert("提示", json.msg);
				goBack(1);
			}else{
				$.messager.alert("提示", json.msg);
				$('#pass').linkbutton('enable');
			}

// 		if(data.result == 'true' || data.result == true)
// 		{
// 		 	$.messager.alert("提示", data.msg);
// 			goBack(1);
// 		}
// 		else
// 		{
// 			$.messager.alert("提示", data.msg);
// 			$('#save').linkbutton('enable');
// 		}
        } 
    });
	
}

// 拒绝
function rejectConfirm()
{
	var confirmReason = $('#confirmReason').textbox('getValue');
	if(confirmReason == '') {
		$.messager.alert("提示", '拒绝时，请填写拒绝原因！');
		return false;
	}
	else
	{		
		$('#reject').linkbutton('disable');
		$.post("${rootPath}/orderConfirm/reject",$("#dataForm").serializeArray(),
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
				$('#reject').linkbutton('enable');
			}
		});
	}
}
 

</script>

</body>
</html>
