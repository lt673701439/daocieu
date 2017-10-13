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
		<input type="hidden" name="promotionId">
		<div class="fitem">
	    	<label>编号:</label>
	        <input name="promotionCode" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>名称:</label>
	        <input name="promotionName" class="easyui-textbox" validType="length[0,50]" style="width:240px" disabled>
		</div>
		<div class="fitem">
		    <label>状态:</label>
			<select class="easyui-combobox" name="promotionStatus" style="width:80px" panelHeight="60px" disabled>
				<option value="1">上架</option>
				<option value="2">下架</option>
			</select>
		</div>
		<div class="fitem">
	    	<label>图片URL:</label>
	        <input name="promotionImg" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
			<label>图片:</label>
	        <c:forEach  items="${urlList}" var="picture" varStatus="s">
					<img src="${picture.url}" width="80" height="50" />
			</c:forEach>
	    </div>
	    <div class="fitem">
	    	<label>备注:</label>
	        <input name="backUp" class="easyui-textbox" validType="length[0,50]" style="width:240px" disabled>
		</div>
		<div class="fitem">
	    	<label>排序:</label>
	        <input name="sortNum" class="easyui-numberbox" style="width:240px" disabled>
		</div>
		<div class="fitem">
	    	<label>上架时间:</label>
	        <input name="addTime" class="easyui-datetimebox"  style="width:160px" disabled>
		</div>
		<div class="fitem">
	    	<label>下架时间:</label>
	        <input name="removeTime" class="easyui-datetimebox"  style="width:160px" disabled>
		</div>
		<div class="fitem">
	    	<label>开始时间:</label>
	        <input name="startTime" class="easyui-datetimebox"  style="width:160px" disabled>
		</div>
		<div class="fitem">
	    	<label>结束时间:</label>
	        <input name="endTime" class="easyui-datetimebox"  style="width:160px" disabled>
		</div>
		<div class="fitem">
			<label>是否可与其他活动及优惠券一起使用：</label>
			<select class="easyui-combobox" name="isTogether" style="width:80px" panelHeight="60px" disabled>
				<option value="1">否</option>
				<option value="2">是</option>
			</select>
		</div>
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>描述:</label> -->
<!-- 	        <input name="promotionDescription" class="easyui-textbox" validType="length[0,100]" style="width:400px" disabled> -->
<!-- 		</div> -->
	</form>
    <div id="dlg-buttons" align="center">
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
		var url='${rootPath}/promotion/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});


</script>

</body>
</html>
