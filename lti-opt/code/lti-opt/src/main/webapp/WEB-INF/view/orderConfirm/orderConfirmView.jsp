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
	<div>
		<form id="dataForm" method="post" action="${rootPath}/orderConfirm/downloadImg">
		<div id="baseId" class="easyui-panel" title="基本信息" style="width:100%;height:115px;padding:1px;"> 
			
			<table style="width:100%; height:1%; overflow: hidden;" border="0" cellpadding="0" cellspacing="0"  class="kTable" >
				<tr><input type="hidden" name="orderId">
					<td class="kTableLabel lbl" style="height:10px;">订单编号:</td>
					<td style="height:10px;"><input name="orderCode" class="easyui-textbox" style="width:160px"></td>
					<td class="kTableLabel lbl" style="height:10px;">交易单号:</td>
					<td style="height:10px;"><input name="transactionNo" class="easyui-textbox" style="width:160px"></td>
				</tr>
				<tr>
					<td class="kTableLabel lbl" style="height:10px;">订单类型:</td>
					<td style="height:10px;"><select class="easyui-combobox" name="orderType" style="width:80px" panelHeight="60px" disabled>
				            <option value=""></option>				        
							<option value="0">普通</option>
							<option value="1">定制</option>
				 		</select></td>
					<td class="kTableLabel lbl" style="height:10px;">确认状态:</td>
					<td style="height:10px;">
							<select class="easyui-combobox" name="confirmStatus" id="confirmStatus" style="width:80px" panelHeight="100px" disabled>
							            <option value="">&nbsp;</option>				        
										<option value="0" selected>待确认</option>
										<option value="1">确认</option>
										<option value="2">拒绝</option>
							 </select></td>
				</tr>
				<tr>
					<td class="kTableLabel lbl"  style="height:10px;">用户昵称:</td>
					<td style="height:10px;"><input name="userNickname" class="easyui-textbox" style="width:120px"></td>
					<td class="kTableLabel lbl" style="height:10px;">联系电话:</td>
					<td style="height:10px;"><input name="userPhone" class="easyui-textbox" style="width:120px"></td>
				</tr>
    		</table>
    	</div>
<!--     	<br style="display:inline;line-height:10px;"> -->
    	<div id="confirmDataId" class="easyui-panel" title="待确认信息" style="width:100%;height:120px;padding:1px;"> 
	    	<table style="width:100%; height:1%; overflow: hidden;" border="0" cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td class="kTableLabel lbl"  style="height:10px;">祝福对象:</td>
					<td style="height:10px;"><input name="blessName" class="easyui-textbox" style="width:120px"></td>
					<td class="kTableLabel lbl" style="height:10px;">落款人:</td>
					<td style="height:10px;"><input name="writeName" class="easyui-textbox" style="width:120px"></td>
				</tr>
				<c:if test="${orderType != '0'}">
				<tr><input type="hidden" name="bkimgUrl"><input type="hidden" name="bkimgName">
					<td class="kTableLabel lbl" style="height:10px;">背景图:</td>
					<td style="height:10px;" colspan="3">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="downloadImg()" style="width:60px">下&nbsp;&nbsp;载</a></td>
				</tr>
				<tr>
					<td class="kTableLabel lbl" style="height:10px;">祝福语:</td>
					<td style="height:10px;" colspan="3"><input name="benisonContent" class="easyui-textbox" style="width:450px"></td>
				</tr>
				</c:if>
	    	</table>
	    </div>
<!-- 	    <br style="display:inline;line-height:10px;"> -->
	    <div id="confirmResultId" class="easyui-panel" title="确认结果" style="width:100%;height:110px;padding:1px;"> 
	    	<table style="width:100%; height:1%; overflow: hidden;" border="0" cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td class="kTableLabel lbl" style="height:10px;">确认人:</td>
					<td style="height:10px;"><input name="confirmBy" class="easyui-textbox" style="width:120px"></td>
					<td class="kTableLabel lbl" style="height:10px;">确认时间:</td>
					<td style="height:10px;"><input name="confirmTime" class="easyui-textbox" style="width:150px"></td>
				</tr>
				<tr><td style="height:5px;" colspan="4"></td></tr>
				<tr>
					<td class="kTableLabel lbl" style="height:10px;">拒绝原因:</td>
					<td style="height:10px;" colspan="3">
					<input name="confirmReason" class="easyui-textbox" data-options="multiline:true" style="width:400px;height:40px"></td>
				</tr>
	    	</table>
	    </div>
	    
		</form>
	</div>
    <div id="dlg-buttons" align="center">
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
		var url='${rootPath}/orderConfirm/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
	
});


/**
 * 下载背景图
 */
function downloadImg(){
	$('#dataForm').form('submit');
}

</script>

</body>
</html>
