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
		<input type="hidden" name="screenId" id="screenId">
		<!-- <input type="hidden" name="spotId" id="spotId"> -->
		<!-- <div class="fitem">
	    	<label>景区ID:</label>
	        <input name="spotId" class="easyui-textbox" disabled>
		</div>  -->
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
	        <input name="screenName" class="easyui-textbox" validType="length[0,50]">
		</div>
		<div class="fitem">
			<label>图片URL:</label>
			<input class="easyui-filebox" name="newScreenImg" data-options="prompt:'请选择一个图片'">
		</div>
		<div class="fitem">
			<label>图片:</label>
	        <c:forEach  items="${urlList}" var="picture" varStatus="s">
					<img src="${picture.url}" width="80" height="50" />
			</c:forEach>
	    </div>
		<div class="fitem">
	    	<label>状态:</label>
	    	<select class="easyui-combobox" name="screenStatus" style="width:80px" panelHeight="60px" required="required">
						<option value="0">失效</option>
						<option value="1">有效</option>
			 </select>
		</div>
		<div class="fitem">
	    	<label>位置:</label>
	        <input name="screenLocation" class="easyui-textbox" validType="length[0,50]">
		</div>
		<div class="fitem">
	    	<label>经度:</label>
	        <input name="screenLongitude" class="easyui-textbox" validType="length[0,10]">
		</div>
		<div class="fitem">
	    	<label>纬度:</label>
	        <input name="screenDimension" class="easyui-textbox" validType="length[0,10]">
		</div>
		<div class="fitem">
	    	<label>长:</label>
	        <input name="screenLong" class="easyui-textbox" validType="length[0,10]">
		</div>
		<div class="fitem">
	    	<label>宽:</label>
	        <input name="screenWide" class="easyui-textbox" validType="length[0,10]">
		</div>
		<div class="fitem">
	    	<label>尺寸:</label>
	        <input name="screenSize" class="easyui-textbox" validType="length[0,10]">
		</div>
		<div class="fitem">
	    	<label>分辨率:</label>
	        <input name="screenResolution" class="easyui-textbox" validType="length[0,10]">
		</div>
		<div class="fitem">
	    	<label>屏幕描述:</label>
	        <input name="screenDescription" class="easyui-textbox" validType="length[0,100]">
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

	rowId ='${screenId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/screen/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});

//保存记录
function saveOrUpdate()
{
	$('#dataForm').form('submit', { 
		        url: "${rootPath}/screen/save", 
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
 
//返回父页面  
// function goBack(flag){
// 	parent.returnParent(flag);
// }
</script>

</body>
</html>
