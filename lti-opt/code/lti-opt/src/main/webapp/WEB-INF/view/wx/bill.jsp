<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@ include file="/include.jsp"%>
    <title>对账单下载</title>
</head>
<body>
<div>
	<br>
	<form id="dataForm" method="post" action="${rootPath}/wx/downloadBill">
	    <div class="fitem"><input type="hidden" id="tradeType" name="tradeType">
	    	<label>对账日期:</label>
	        <input id="startDate" name="startDate" class="easyui-datebox" style="width:90px" data-options="required:true,editable:false"> ~ 
	        <input id="endDate" name="endDate" class="easyui-datebox" style="width:90px" data-options="required:true,editable:false">
		</div>
<!-- 		<div class="fitem"> -->
<!-- 		    <label>对账单类型:</label> -->
<!-- 			<select class="easyui-combobox" name="billType" style="width:90px" panelHeight="80px" required="required"> -->
<!-- 				<option value="ALL" selected>全部订单</option> -->
<!-- 				<option value="SUCCESS">支付订单</option> -->
<!-- 				<option value="REFUND">退款订单</option> -->
<!-- 			</select> -->
<!-- 		</div> -->
		<div class="fitem">
			<br>
			<label></label>
		    <B><font color="#f24945" style="font-size:10px;">注意：如果时间跨度较大，点击【下载】后，请耐心等待！因为循环调用微信接口，所以获取数据会很慢哦！</font></B>
		</div>
	</form>
	<br>
    <div id="dlg-buttons" style="margin-left: 140px;">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-page_excel" id="btnexportAPP" onclick="downloadBill('APP')" style="width:150px">下载APP对账单</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-page_excel" id="btnexportJS" onclick="downloadBill('JS')" style="width:150px">下载小程序对账单</a>
   </div>
</div>
    
<script type="text/javascript">

jQuery(function(){ 
	$("#startDate").datebox({  
		onSelect : function(beginDate){
			$('#endDate').datebox().datebox('calendar').calendar({  
				validator: function(date){  
					return beginDate<=date;
				}
			});  
		}  
	});
});

//保存记录
function downloadBill(tradeType){
	$('#tradeType').val(tradeType);
	$('#btnexport').linkbutton('disable');
	$('#dataForm').form('submit');
	$('#btnexport').linkbutton('enable');
}
</script>

</body>
</html>
