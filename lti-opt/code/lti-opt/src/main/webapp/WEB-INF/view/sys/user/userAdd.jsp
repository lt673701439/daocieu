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
<body style="height:55">
<div>
	<form id="dataForm">
			<div class="fitem">
		    	<label>用户姓名:</label>
		        <input name="userName" class="easyui-textbox" data-options="required:true">
			</div>
			<div class="fitem">
		    	<label>登录名:</label>
		        <input name="userCode" class="easyui-textbox" data-options="required:true">
			</div>
			<div class="fitem">
		    	<label>登录密码:</label>
		       <input  name="userPwd" type="password" class="easyui-textbox" data-options="required:true" />   
			</div>
			<div class="fitem">
		    	<label>手机号:</label>
		        <input name="phoneNum" class="easyui-textbox" data-options="required:true" validType="mobile">
			</div>
			<div class="fitem">
		    	<label>邮箱账号:</label>
		        <input name="userEmail" class="easyui-textbox" validType="mail">
			</div>
			<div class="fitem">
		    	<label>邮箱密码:</label>
		       <input  name="emailPwd"  class="easyui-textbox" />   
			</div>
		</form>
    <div id="dlg-buttons" align="center">
<!--        页面按钮有无权限控制 -->
<%--        <shiro:hasPermission name="sys:user:edit"> --%>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
<%--        </shiro:hasPermission> --%>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack(1)" style="width:90px">取消</a>
   </div>
</div>
    
    
<script type="text/javascript">

/**
 * 保存记录新增用户<br/>
 */
function saveOrUpdate() {
	var r = $('#dataForm').form('validate');
	if(!r) {
		return false;
	} else {
		//$('#save').linkbutton('disable');
		$.post("${rootPath}/user/save",$("#dataForm").serializeArray(),
		function(data) {			
			if(data.result == 'true' || data.result == true) {
				$.messager.alert("提示", data.msg);
				goBack(1);
			} else {
				$.messager.alert("提示", data.msg);
			}
		});
	}
}

</script>

</body>
</html>
