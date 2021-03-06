<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@ include file="/include.jsp"%>
	
	<link href="<%=basePath%>/static/css/login.css" rel="stylesheet" type="text/css" />

    <title>欢迎登陆</title>
</head>
<script type="text/javascript">
if(window!=top){
    top.location = "${rootPath}/login";
}
</script>

<body>
        <div class="second_body">
            <form id="loginForm" method="post" action="${rootPath}/login" >
<%--             	<div class="logo"><img src="<%=basePath%>/static/images/login/logo.jpg"/></div> --%>
                <div class="title-zh">祝福语网站运营平台</div>
                <div class="title-en">Blessing language website operation platform </div>
                
				<div class="message" >
<!-- 					<label id="loginError" class="error">用户或密码错误, 请重试.</label> -->
				</div>
				
                <table border="0" style="width:300px;">
                    <tr>
                        <td style="padding-bottom: 5px;width:55px;">用户名：</td>
                        <td colspan="2"><input class="login easyui-textbox" name="username"  id="username" value=""  data-options="prompt:'登陆用户名',iconWidth:18,required:true"></td> 
                    </tr>
                    <br>
                    <tr>
                        <td class="lable" style="letter-spacing: 0.5em; vertical-align: middle">密码：</td>
                        <td colspan="2"><input class="login easyui-textbox" name="password"  id="password" value="" type="password" data-options="prompt:'登陆密码',iconWidth:18,required:true"></td> 
                    </tr>
                    <tr><td colspan="3">&nbsp;</td></tr>
                    <tr>
                        <td colspan="3" style="text-align:center">
                            <input type="button" value="登录" class="login_button" id="login" onclick="loginAction()"/>
                            <input type="button" value="重置" class="reset_botton" onclick="resetClick()"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
<script type="text/javascript">

$(function(){
	$('#username').textbox('textbox').focus();	
});

// 回车控制登录按钮
document.onkeydown = function(e){
    var event = e || window.event;  
    var code = event.keyCode || event.which || event.charCode;
    if (code == 13) {
    		$('#login').focus();
			setTime('10',loginAction);
    	   	
    }
}

this.resetClick = function () {
	 $('#loginForm').form('clear');
};

function loginAction(){
	var r = $('#loginForm').form('validate');
	if(!r) {
		return false;
	} else {
		$("#loginForm").submit();
	}
}
</script>
</html>