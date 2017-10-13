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
		<input type="hidden" name="promotionId">
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>编号:</label> -->
<!-- 	        <input name="promotionCode" class="easyui-textbox" disabled> -->
<!-- 		</div> -->
		<div class="fitem">
	    	<label>名称:</label>
	        <input name="promotionName" class="easyui-textbox" validType="length[0,50]" style="width:240px" required="required">
		</div>
		<div class="fitem">
		    <label>状态:</label>
			<select class="easyui-combobox" name="promotionStatus" style="width:80px" panelHeight="60px" required="required">
				<option value="1">上架</option>
				<option value="2">下架</option>
			</select>
		</div>
		<div class="fitem">
			<label>图片URL:</label>
			<input class="easyui-filebox" name="promotionImg" id="promotionImg" data-options="prompt:'请选择一个图片'" data-options="required:true">
		</div>
		<div class="fitem">
	    	<label>备注:</label>
	        <input name="backUp" class="easyui-textbox" validType="length[0,50]" style="width:240px" >
		</div>
		<div class="fitem">
	    	<label>排序:</label>
	        <input name="sortNum" class="easyui-numberbox" style="width:240px" >
		</div>
		<div class="fitem">
	    	<label>上架时间:</label>
	        <input name="addTime" class="easyui-datetimebox"  style="width:160px" required="required">
		</div>
		<div class="fitem">
	    	<label>下架时间:</label>
	        <input name="removeTime" class="easyui-datetimebox"  style="width:160px" required="required">
		</div>
		<div class="fitem">
	    	<label>开始时间:</label>
	        <input name="startTime" class="easyui-datetimebox"  style="width:160px" required="required">
		</div>
		<div class="fitem">
	    	<label>结束时间:</label>
	        <input name="endTime" class="easyui-datetimebox"  style="width:160px" required="required">
		</div>
		<div class="fitem">
			<label>是否可与其他活动及优惠券一起使用：</label>
			<select class="easyui-combobox" name="isTogether" style="width:80px" panelHeight="60px" required="required">
				<option value="1">否</option>
				<option value="2">是</option>
			</select>
		</div>
<!-- 		<div class="fitem"> -->
<!-- 	    	<label>描述:</label> -->
<!-- 	        <input name="promotionDescription" class="easyui-textbox" validType="length[0,100]" style="width:400px"> -->
<!-- 		</div> -->
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
		var url='${rootPath}/promotion/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});

//保存记录
function saveOrUpdate()
{
	$('#dataForm').form('submit', { 
		        url: "${rootPath}/promotion/save", 
		        onSubmit: function () {        //表单提交前的回调函数 
					//校验必填项
					var r = $('#dataForm').form('validate');
					if(!r) {
						return false;
					}
					//验证是否有文件
					var imgFileArr=$(":file");
					if(imgFileArr){
						var isNum=false;
						for(i=0;i<imgFileArr.length;i++){
							var fileName=imgFileArr[i];
							console.log($(fileName).val());
							if($.trim($(fileName).val())!=""&&$(fileName).val()!=null){
								isNum=true;
							}
						}		
						if(!isNum){
							$.messager.alert("提示", "请上传一张图片！");				
							return false;
						}
					}else{
						$.messager.alert("提示", "请上传至少一张图片！");				
						return false;
					}
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
