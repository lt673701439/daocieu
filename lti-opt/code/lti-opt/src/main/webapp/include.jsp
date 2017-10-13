<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%
String baseUrl = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+baseUrl;
%>

<c:set var="rootPath" value="<%=baseUrl%>"></c:set>
<c:set var="basePath" value="<%=basePath%>"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=11" />
 <!--加载共通CSS-->
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/claim.css">
<!--加载960CSS框架-->
<link href="<%=basePath%>/static/css/960/fluid/reset.css" rel="stylesheet" type="text/css"  />
<link href="<%=basePath%>/static/css/960/fluid/text.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/static/css/960/fluid/grid.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]><link href="<%=basePath%>/static/css/960/fluid/ie6.css" rel="stylesheet" type="text/css" /><![endif]-->
<!--[if IE 7]><link href="<%=basePath%>/static/css/960/fluid/ie.css"  rel="stylesheet" type="text/css" /><![endif]-->
<!--加载按钮CSS-->
<link href="<%=basePath%>/static/css/btns/css3btn.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/static/css/btns/sexybuttons.css" rel="stylesheet" type="text/css" />
 <!--加载组件CSS-->  
<link href="<%=basePath%>/static/js/jquery-plugin/showloading/showLoading.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/static/js/jquery-plugin/jnotify/jquery.jnotify.css" rel="stylesheet" type="text/css" />
<!--加载样式修正CSS-->
<link href="<%=basePath%>/static/css/hack/fix.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui/themes/default/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui/themes/color.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui/themes/form.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/farbtastic.css"/>

<script type="text/javascript" src="<%=basePath%>/static/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/json.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/loadmask/jquery.loadmask.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/farbtastic.js"></script>

<script type="text/javascript" src="<%=basePath%>/static/js/jquery-plugin/showloading/jquery.showLoading.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script> 
<script type="text/javascript" src="<%=basePath%>/static/js/utils.js"></script> 
<script type="text/javascript" src="<%=basePath%>/static/js/jquery-plugin/jnotify/jquery.jnotify.js"></script>
<!-- <script type="text/javascript" src="<%=basePath%>/static/js/common/common.js"></script> -->
<!-- 爱佑汇的共通js -->
<script type="text/javascript" src="<%=basePath%>/static/js/common_iu.js"></script> 