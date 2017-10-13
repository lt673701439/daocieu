<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/include.jsp" %>
<title>祝福语运营平台</title>
<link href="<%=basePath%>/static/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/jquery-ui.custom.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/icon.css">
<script type="text/javascript" src="<%=basePath%>/static/js/main.js"></script>
<script type="text/javascript">
    jQuery.ajaxSetup({
        cache : false
    });//ajax不缓存
</script>
</head>
<body class="easyui-layout">
    <!-- <div region="north" border="true" class="cs-north">
        <div class="cs-north-bg">
        <div class="box01"></div>
        <div class="box03"></div>
        <div id="operator">
        </div>
        <ul class="ui-skin-nav">
            <li class="li-skinitem" title="退出"><a href="${rootPath}/logout" >退出</a></li>
            <li class="li-skinitem" title="修改密码"><a href="javascript:uptPwd();">修改密码</a></li>               
            <li class="li-skinitem" title="gray"><span class="gray" rel="gray"></span></li>
            <li class="li-skinitem" title="default"><span class="default" rel="default"></span></li>
            <li class="li-skinitem" title="bootstrap"><span class="bootstrap" rel="bootstrap"></span></li>
            <li class="li-skinitem" title="black"><span class="black" rel="black"></span></li>
            <li class="li-skinitem" title="metro"><span class="metro" rel="metro"></span></li>
        </ul>
        <ul class="ui-time-nav">
            <li class="li-timeitem" title="当前系统时间"><span id="showDate">&nbsp;</span></li>
        </ul>   
        </div>
    </div>-->
    <div region="north" class="head-north" split="true" border="false"  >
            <div class="head head-right" > 
                <span id="showDate" class="showDate" style="margin-right:50px"></span>
                <!-- <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" title="机构" data-options="iconCls:'icon-flag_france'"></a>
                <span id="showCom">机构：<app:show type="C5691" value="${comName}"/></span> -->
                <a href="javascript:void(0)" class="easyui-menubutton" data-options="menu:'#mm_user',iconCls:'icon-user'">当前用户:${operatorName}</a>
                <div id="mm_user" style="width:150px;">  
<!--                     <div data-options="iconCls:'icon-rainbow'" class="myconfig">个人设置</div>   -->
                    <div data-options="iconCls:'icon-edit'" class="changepwd">修改密码</div>  
                    <div class="menu-sep"></div>  
                    <div data-options="iconCls:'icon-user_go'" class="loginOut">安全退出</div>  
                </div> 
            </div>
            <B><span class="head-left" style="font-size:20px;">&nbsp;&nbsp;&nbsp;&nbsp;祝&nbsp;福&nbsp;语&nbsp;运&nbsp;营&nbsp;平&nbsp;台</span></B>
         
        </div>
    <div region="west" border="true" split="true" title="功能导航区" class="cs-west">
        <div class="easyui-accordion" fit="true" border="false">
            <c:set var="menuList" value="${fns:getMenuList()}" />           
            <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">                
                <c:if test="${menu.parentId == '0'}">
                <div title="${menu.menuName}" data-options="iconCls:'icon-mini-add'" style="padding:10px;overflow:auto;">
                    <ul class="easyui-tree" id="${menu.menuId}">
                    <script type="text/javascript">
						$('#' + ${menu.menuId}).tree({
						    url:'${rootPath}/menu/getMenuTree?id=' + ${menu.menuId},
						    id:'id',
						    state:'state',
						    text:'text',
						    onSelect:function(node) {
						    	if (node.checkUrl != null && node.checkUrl!="" && node.checkUrl!="null") {
						    	    wrapper.addTab(node.menuName,node.url,'');
						    	}
						    }
						});
					</script>
                        <%-- <c:forEach items="${menuList}" var="menuChild"> 
                            <c:if test="${menuChild.parentId ==menu.menuId }">
                                <li><a><span onClick=" wrapper.addTab('${menuChild.menuName}','${menuChild.url}','')" >${menuChild.menuName}</span></a></li>
                            </c:if>
                        </c:forEach> --%>
                    </ul>
                </div>              
                </c:if>
            </c:forEach>            
        </div>
    </div>
    <div id="mainPanle" region="center" border="true">
         <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
                <div title="我的桌面" style="overflow:hidden;" id="home">
                    
                    <div id="pp" style="position:relative">
                        <div style="width:100%;">
<!--                             <div id="p1" title="快捷菜单" collapsible="false"   closable="false" style="height:190px;padding:5px;">                          -->
<!--                                 <table border="0" cellspacing="8" cellpadding="0"> -->
                                    
<!--                                 </table> -->
<!--                             </div> -->
<!--                             <div id="p2" title="系统公告" collapsible="true"   closable="true" style="height:220px;padding:5px;">    -->
<!--                                 <p style="text-align:center;font-size:20px"></p> -->
<%--                                 <span>${content}</span> --%>
<!--                             </div>                           -->
                            <div id="p3" title="" collapsible="false"   closable="false" style="text-align:center;height:100px;padding:5px;">   
                                <table border="0" cellpadding="0" width="100%">
                                    
                                </table>
                            </div>                           
                        </div>
                </div>
            </div>
        </div>
    </div>

    <div region="south" border="false" id="south"><center>Copyright © 2017 All Rights Reserved</center></div>
    
     <div id="rightMenu" class="easyui-menu hide" style="width:150px;">
            <div id="refresh">刷新</div>
            <div class="menu-sep"></div>
            <div id="close">关闭</div>
            <div id="closeall">全部关闭</div>
            <div id="closeother">除此之外全部关闭</div>
            <div class="menu-sep"></div>
            <div id="closeright">关闭右侧标签</div>
            <div id="closeleft">关闭左侧标签</div>
            <div class="menu-sep"></div>
            <div id="exit">退出</div>
        </div>
        
     <div id="w"></div>
     <div id="notity"></div>
</body>
</html>

