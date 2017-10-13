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
	        <input name="imgId" type="hidden" >
			<div class="fitem">
		    	<label>图片编号:</label>
		        <input name="imgCode" class="easyui-textbox" disabled>
			</div>
			<div class="fitem">
		    	<label>图片名称:</label>
		        <input name="imgName" class="easyui-textbox" data-options="required:true" validType="length[0,50]">
			</div>
			<div class="fitem">
		    	<label>图片类型:</label>
		        <select class="easyui-combobox" name="imgType" style="width:80px" panelHeight="60px" required="required">
						<option value="0">背景图</option>
						<option value="1">示意图</option>
				 </select>
			</div>
			<div class="fitem">
				<label>图片URL:</label>
				<input class="easyui-filebox" name="newImgUrl" data-options="prompt:'请选择一个图片'">
			</div>
			<div class="fitem">
				<label>图片:</label>
		        <c:forEach  items="${urlList}" var="picture" varStatus="s">
						<img src="${picture.url}" width="80" height="50" />
				</c:forEach>
		    </div>
			</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack(0)" style="width:90px">取消</a>
   </div>
</div>
    
<script type="text/javascript">

var rowId;
jQuery(function(){ 

	
	rowId ='${imgId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/benisonImg/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});

//保存记录
function saveOrUpdate(){
	
	$('#dataForm').form('submit', { 
	        url: "${rootPath}/benisonImg/save", 
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
