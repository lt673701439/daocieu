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
		<input type="hidden" name="screenId" id="screenId">
		<!-- <input type="hidden" name="spotId" id="spotId"> -->
		<!-- <div class="fitem">
	    	<label>景区ID:</label>
	        <input name="spotId" class="easyui-textbox" disabled>
		</div> -->
		<div class="fitem">
	    	<label>景点:</label>
	        <input name="spotName" id="spotName" class="easyui-textbox" style="width:200px" disabled>
		</div>
		<div class="fitem">
	    	<label>屏幕编号:</label>
	        <input name="screenCode" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>屏幕名称:</label>
	        <input name="screenName" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>图片URL:</label>
	        <input name="screenImg" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
			<label>图片:</label>
	        <c:forEach  items="${urlList}" var="picture" varStatus="s">
					<img src="${picture.url}" width="80" height="50" />
			</c:forEach>
	    </div>
		<div class="fitem">
	    	<label>状态:</label>
	    	<select class="easyui-combobox" name="screenStatus" style="width:80px" panelHeight="60px" disabled>
						<option value="0">失效</option>
						<option value="1">有效</option>
			 </select>
		</div>
		<div class="fitem">
	    	<label>位置:</label>
	        <input name="screenLocation" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>经度:</label>
	        <input name="screenLongitude" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>纬度:</label>
	        <input name="screenDimension" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>长:</label>
	        <input name="screenLong" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>宽:</label>
	        <input name="screenWide" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>尺寸:</label>
	        <input name="screenSize" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>分辨率:</label>
	        <input name="screenResolution" class="easyui-textbox" disabled>
		</div>
		<div class="fitem">
	    	<label>屏幕描述:</label>
	        <input name="screenDescription" class="easyui-textbox" disabled>
		</div>
	</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack()" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;
jQuery(function(){ 

	rowId ='${screenId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/screen/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});

//返回父页面  
// function goBack(flag){
// 	parent.returnParent(flag);
// }
</script>

</body>
</html>
