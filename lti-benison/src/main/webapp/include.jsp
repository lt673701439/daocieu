<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%
String baseUrl = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+baseUrl;
%>

<c:set var="rootPath" value="<%=baseUrl%>"></c:set>
<c:set var="basePath" value="<%=basePath%>"></c:set>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/easyui-1.5.2/themes/default/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/easyui-1.5.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/easyui-1.5.2/themes/color.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/easyui-1.5.2/themes/form.css">
<script type="text/javascript" src="<%=basePath%>/static/easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/json.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>


