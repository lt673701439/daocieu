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
		<input type="hidden" name="commodityId" >
		<div class="fitem">
	    	<label>编号:</label>
	        <input name="commodityCode" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>名称:</label>
	        <input name="commodityName" class="easyui-textbox" validType="length[0,50]" style="width:240px" required="required">
		</div>
		<div class="fitem">
	    	<label>屏幕:</label>
	        <input name="screenName" id="screenName" class="easyui-textbox" style="width:240px" disabled>
		</div>
		<div class="fitem">
		    <label>状态:</label>
			<select class="easyui-combobox" name="commodityStatus" style="width:80px" panelHeight="60px" required="required">
				<option value="1">正常</option>
				<option value="2">下架</option>
			</select>
		</div>
		<div class="fitem">
			<label>图片URL:</label>
			<input class="easyui-filebox" name="newCommodityImg" data-options="prompt:'请选择一个图片'">
		</div>
		<div class="fitem">
			<label>图片:</label>
	        <c:forEach  items="${urlList}" var="picture" varStatus="s">
					<img src="${picture.url}" width="80" height="50" />
			</c:forEach>
	    </div>
		<div class="fitem">
	    	<label>价格:</label>
	        <input name="commodityPrice" class="easyui-numberbox"  precision="2" groupSeparator="," decimalSeparator="." 
	        												prefix="￥" validType="length[0,10]" style="width:120px" required="required"></input> 元
		</div>
		<div class="fitem">
	    	<label>开始日期:</label>
	        <input name="startDate" class="easyui-datebox"  style="width:120px" disabled>
		</div>
		<div class="fitem">
	    	<label>结束日期:</label>
	        <input name="endDate" class="easyui-datebox"  style="width:120px" disabled>
		</div>
		<div class="fitem">
	    	<label>时间频率:</label>
	        <select class="easyui-combobox" name="timeFrame" style="width:80px" panelHeight="130px" disabled>
				<option value="1">每天</option>
				<option value="2">每周</option>
				<option value="3">每月</option>
				<option value="4">每季度</option>
				<option value="5">每年</option>
			</select>
		</div>
		<div class="fitem">
	    	<label>开始时间:</label>
	        <input name="startTime" class="easyui-timespinner"  style="width:80px" disabled>
		</div>
		<div class="fitem">
	    	<label>结束时间:</label>
	        <input name="endTime" class="easyui-timespinner"  style="width:80px" disabled>
		</div>
		<div class="fitem">
	    	<label>下架时间:</label>
	        <input name="shelfTime" class="easyui-timespinner"  style="width:80px" disabled>
		</div>
		<div class="fitem">
	    	<label>计划售卖数量:</label>
	        <input name="planNumber" class="easyui-numberbox"  precision="0" validType="length[0,5]" style="width:80px" disabled></input> 个
		</div>
		<div class="fitem">
	    	<label>单次播放时间:</label>
	        <input name="singleTime" class="easyui-numberbox"  precision="0" validType="length[0,5]" style="width:80px" disabled> 秒
		</div>
		<div class="fitem">
	    	<label>播放次数:</label>
	        <input name="playTimes" class="easyui-numberbox"  precision="0" validType="length[0,5]" style="width:80px" disabled>
	        </input> 次
		</div>
		<div class="fitem">
	    	<label>描述:</label>
	        <input name="commodityDescription" class="easyui-textbox" validType="length[0,100]" style="width:400px">
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
	// 注意：不要读取缓存
	jQuery.ajaxSetup({
		cache : false
	});
	
	rowId ='${rowId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/commodity/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});

//保存记录
function saveOrUpdate()
{
	$('#dataForm').form('submit', { 
		        url: "${rootPath}/commodity/save", 
		        onSubmit: function () {        //表单提交前的回调函数 
					//校验必填项
					var r = $('#dataForm').form('validate');
					if(!r) {
						return false;
					}
					//验证是否有文件
					$('#save').linkbutton('disable');
		        }, 
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
}

</script>

</body>
</html>
