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
			<input type="hidden" name="templateId" id= "templateId">
			<div class="fitem">
		    	<label>模板编号:</label>
		        <input name="templateCode" style="width: 180px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>模板名称:</label>
		        <input name="templateName" style="width: 180px;" class="easyui-textbox" disabled>
			</div>
			<!--<div class="fitem">
		    	<label>屏幕ID:</label>
		        <input name="screenId" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>祝福语类型ID:</label>
		        <input name="typeId" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>祝福语ID:</label>
		        <input name="benisonId" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>背景图片ID:</label>
		        <input name="bgImgId" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>示意图ID:</label>
		        <input name="smImgId" class="easyui-textbox" disabled>
			</div> -->
			<div class="fitem">
		    	<label>屏幕:</label>
		        <input name="screenName" id="screenName" class="easyui-textbox" style="width:200px" disabled>
			</div>
			<div class="fitem">
		    	<label>祝福语类型:</label>
		        <input name="typeName" id="typeName" class="easyui-textbox" style="width:200px" disabled>
			</div>
			<div class="fitem">
		    	<label>祝福语:</label>
		        <input name="benisonContent" id="benisonContent" class="easyui-textbox" style="width:200px" disabled>
			</div>
			<div class="fitem">
		    	<label>背景图:</label>
		        <input name="imgName" id="imgName" class="easyui-textbox" style="width:200px" disabled>
			</div>
			<div class="fitem">
		    	<label>抬头X坐标:</label>
		        <input name="titleX" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>抬头Y坐标:</label>
		        <input name="titleY" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>抬头颜色:</label>
		        <input name="titleColour" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>抬头字号:</label>
		        <input name="titleSize" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>抬头字体:</label>
		    	<input class="easyui-combobox" name="titleType" style="width:100px" disabled>
			</div>
			<div class="fitem">
		    	<label>中心X坐标:</label>
		        <input name="bodyX" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>中心Y坐标:</label>
		        <input name="bodyY" style="width: 100px;" class="easyui-textbox" disabled >
			</div>
			<div class="fitem">
		    	<label>主体颜色:</label>
		        <input name="bodyColour" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>主体字号:</label>
		        <input name="bodySize" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>主体字体:</label>
		    	<input class="easyui-combobox" name="bodyType" style="width:100px" disabled>
			</div>
			<div class="fitem">
		    	<label>落款X坐标:</label>
		        <input name="tailX" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>落款Y坐标:</label>
		        <input name="tailY" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>落款颜色:</label>
		        <input name="tailColour" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>落款字号:</label>
		        <input name="tailSize" style="width: 100px;" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>落款字体:</label>
		    	<input class="easyui-combobox" name="tailType" style="width:100px" disabled>
			</div>
			<div class="fitem">
				<label>图片:</label>
		        <c:forEach  items="${urlList}" var="picture" varStatus="s">
						<img src="${picture.url}" width="80" height="50" />
				</c:forEach>
		    </div>
		    <div class="fitem">
		    	<label>描边偏移X:</label>
		        <input name="strokeX" class="easyui-numberbox"  style="width: 100px;"  disabled>
			</div>
			<div class="fitem">
		    	<label>描边偏移Y:</label>
		        <input name="strokeY" class="easyui-numberbox"  style="width: 100px;"  disabled>
			</div>
			<div class="fitem">
		    	<label>描边透明度:</label>
		        <input name="strokeAlpha" class="easyui-numberbox" precision="1" groupSeparator="," decimalSeparator="." style="width: 100px;" disabled>
			</div>
			</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack(0)" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;
jQuery(function(){ 
	
	rowId ='${templateId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/benisonTemplate/getOne?rowId=' + rowId;
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
