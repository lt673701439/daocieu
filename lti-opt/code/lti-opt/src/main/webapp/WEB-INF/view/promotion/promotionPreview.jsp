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
	<form id="dataForm" style="padding:0;margin:0;">
		<input type="hidden" name="promotionId">
<%-- 		<c:set var="endDate" scope="session" target="endTime" property="value" value="${endTime}"/> --%>
		
		<div>
	        <c:forEach  items="${urlList}" var="picture" varStatus="s">
					<img src="${picture.url}" width="335" height="150" />
			</c:forEach>
	    </div>
	    <div>
	    	<br style="display:inline;line-height:6px;">
	    	&nbsp;&nbsp;&nbsp;<B><font style="font-size:10px;">进行中</font></B><font color="grey" style="font-size:10px;}">&nbsp;·&nbsp;截止时间：${endTime}</font><br>
	    	&nbsp;&nbsp;&nbsp;<B><font color="#2577e4" style="font-size:8px;">&nbsp;·&nbsp;</font></B><font color="white" style="font-size:8px;background:#2577e4">限时打折</font><br>
	    </div>
	    <hr>
	    <br style="display:inline;line-height:6px;">
		<div class="easyui-tabs" fit="true" border="false" plain="true" fit="true" tabWidth="155" style="width:300px;height:180px;padding:5px">
			<div id="commodityListDiv" title="产品列表" style="width:100px;height:180px" data-options="border:false">
				<c:forEach  items="${commodityList}" var="clist" varStatus="c">
					<br style="display:inline;line-height:5px;">
					<div style="float:left;width:12%;height:50px;margin:2px;">
						<img src="${clist.commodity_img}" width="40" height="40"/>
					</div>
					<div style="float:left;width:60%;height:50px;margin:2px;">
						&nbsp;<B><font style="font-size:10px;">${clist.screen_name}</font></B>&nbsp;&nbsp;
						<font color="grey" style="font-size:10px;">${clist.start_time}&nbsp;-&nbsp;${clist.end_time}</font>
						<br><br style="display:inline;line-height:5px;">
						&nbsp;<B><font color="#5d9cee" style="font-size:13px;">￥&nbsp;${(clist.commodity_price * clist.discount_ratio)/100}</font></B>&nbsp;&nbsp;
						<font color="grey" style="font-size:8px;">￥</font>
						<font color="grey" style="font-size:8px;text-decoration:line-through;">${clist.commodity_price}</font>
					</div>
					<div style="float:left;width:1px;height:50px;border-left:0.1px #dadbdd solid"></div>
					<div style="float:left;width:23%;height:50px;margin:2px;vertical-align:middle;text-align:center;">
						<br style="display:inline;line-height:15px;">
						<B><font color="#5d9cee" style="font-size:11px;">即&nbsp;刻&nbsp;购&nbsp;买</font></B>
					</div>
					<hr>
				</c:forEach>
			</div>  
			<div id="promotionDescriptionDiv" title="活动详情" style="width:100px;height:180px" data-options="border:false">${promotionDescription}</div>
		</div>
	</form>
</div>
<script type="text/javascript">
var rowId;
jQuery(function(){ 
	// 注意：不要读取缓存
	jQuery.ajaxSetup({
		cache : false
	});
	
// 	rowId ='${rowId}';
	
// 	if (rowId != null && rowId != "" && rowId!=0){
// 		var url='${rootPath}/promotion/getOne?rowId=' + rowId;
// 		$('#dataForm').form('load', url);
// 	}

});


</script>

</body>
</html>
