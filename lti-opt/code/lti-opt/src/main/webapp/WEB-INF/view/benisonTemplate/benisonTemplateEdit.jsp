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
	<form id="dataForm">
			<input type="hidden" name="templateId" id= "templateId">
			<!-- <div class="fitem">
		    	<label>templateId:</label>
		        <input name="templateId" class="easyui-textbox" data-options="required:true">
			</div> -->
			<div class="fitem">
		    	<label>模板编号:</label>
		        <input name="templateCode" class="easyui-textbox" style="width: 180px;" disabled>
			</div>
			<div class="fitem">
		    	<label>模板名称:</label>
		        <input name="templateName" class="easyui-textbox" style="width: 180px;" data-options="required:true" validType="length[0,50]">
			</div>
			<!-- <div class="fitem">
		    	<label>屏幕ID:</label>
		        <input name="screenId" class="easyui-textbox" validType="length[0,32]">
			</div>
			<div class="fitem">
		    	<label>祝福语类型ID:</label>
		        <input name="typeId" class="easyui-textbox" validType="length[0,32]">
			</div>
			<div class="fitem">
		    	<label>祝福语ID:</label>
		        <input name="benisonId" class="easyui-textbox" validType="length[0,32]">
			</div>
			<div class="fitem">
		    	<label>背景图片ID:</label>
		        <input name="bgImgId" class="easyui-textbox" validType="length[0,32]">
			</div> -->
			<!-- <div class="fitem">
		    	<label>示意图ID:</label>
		        <input name="smImgId" class="easyui-textbox" validType="length[0,32]">
			</div> -->
			<div class="fitem">
		    	<label>屏幕:</label>
		        <input name="screenId" id="screenId" type="hidden">
		        <input name="screenName" id="screenName" class="easyui-textbox" style="width:200px" required="required">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="width:30px"
		        	onclick="findPage('屏幕','Screen.queryScreenByConForPage','screenId','screenCode','screenName','screen_id','screen_code','screen_name')"></a>
			</div>
			<div class="fitem">
		    	<label>祝福语类型:</label>
		        <input name="typeId" id="typeId" type="hidden">
		        <input name="typeName" id="typeName" class="easyui-textbox" style="width:200px" required="required">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="width:30px" id="benisonTypeButton"
		        	onclick="findPage('祝福语类型','BenisonType.queryBenisonTypeByConForPage','typeId','typeCode','typeName','type_id','type_code','type_name')"></a>
			</div>
			<div class="fitem">
		    	<label>祝福语:</label>
		        <input name="benisonId" id="benisonId" type="hidden">
		        <input name="benisonContent" id="benisonContent" class="easyui-textbox" style="width:200px" required="required">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="width:30px" id="benisonButton"></a>
			</div>
			<div class="fitem">
		    	<label>背景图:</label>
		        <input name="imgId" id="imgId" type="hidden">
		        <input name="imgName" id="imgName" class="easyui-textbox" style="width:200px" required="required">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="width:30px"
		        	onclick="findPage('背景图','BenisonImg.queryBenisonImgByConForPage','imgId','imgCode','imgName','img_id','img_code','img_name')"></a>
			</div>
			<!-- <div class="fitem">
		    	<label>示意图:</label>
		        <input name="imgId" id="imgId" type="hidden">
		        <input name="imgName" id="imgName" class="easyui-textbox" style="width:200px" required="required">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="width:30px"
		        	onclick="findPage('示意图','BenisonImg.queryBenisonImgByConForPage','imgId','imgCode','imgName','img_id','img_code','img_name')"></a>
			</div> -->
			<div class="fitem">
		    	<label>抬头X坐标:</label>
		        <input name="titleX" class="easyui-numberbox" value="50" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>抬头Y坐标:</label>
		        <input name="titleY" class="easyui-numberbox" style="width: 100px;" value="50" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>抬头颜色:</label>
		        <input name="titleColour" id="titleColour"  style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem" id="picker1" align="center" style="display:none; "></div>
			<div class="fitem">
		    	<label>抬头字号:</label>
		        <input name="titleSize" class="easyui-numberbox" value="20" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>抬头字体:</label>
		        <input class="easyui-combobox" editable="false" name="titleType" style="width:100px"  url="${rootPath}/benisonTemplate/getFontsList" 
					   valueField="fontId" textField="fontName">
			</div>
			<div class="fitem">
		    	<label>中心X坐标:</label>
		        <input name="bodyX" class="easyui-numberbox" value="100" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>中心Y坐标:</label>
		        <input name="bodyY" class="easyui-numberbox" value="100" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>主体颜色:</label>
		        <input name="bodyColour" id="bodyColour"  style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem" id="picker2" align="center" style="display:none; "></div>
			<div class="fitem">
		    	<label>主体字号:</label>
		        <input name="bodySize" class="easyui-numberbox" value="25" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>主体字体:</label>
		        <input class="easyui-combobox" editable="false" name="bodyType" style="width:100px"  url="${rootPath}/benisonTemplate/getFontsList" 
					   valueField="fontId" textField="fontName">
			</div>
			<div class="fitem">
		    	<label>落款X坐标:</label>
		        <input name="tailX" class="easyui-numberbox" value="150" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>落款Y坐标:</label>
		        <input name="tailY" class="easyui-numberbox" value="150" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>落款颜色:</label>
		        <input name="tailColour" id="tailColour" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem" id="picker3" align="center" style="display:none; "></div>
			<div class="fitem">
		    	<label>落款字号:</label>
		        <input name="tailSize" class="easyui-numberbox" value="20" style="width: 100px;" validType="length[0,10]">
			</div>
			<div class="fitem">
		    	<label>落款字体:</label>
		    	<input class="easyui-combobox" editable="false" name="tailType" style="width:100px"  url="${rootPath}/benisonTemplate/getFontsList" 
					   valueField="fontId" textField="fontName">
			</div>
			</form>
    <div id="dlg-buttons" align="center">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="goBack(0)" style="width:90px">取消</a>
   </div>
   <div id="picker" align="center"></div>
</div>
    
<script type="text/javascript">

var rowId;
jQuery(function(){ 
	
	rowId ='${templateId}';
	
	if (rowId != null && rowId != "" && rowId!=0){
		var url='${rootPath}/benisonTemplate/getOne?rowId=' + rowId;
		$('#dataForm').form('load', url);
	}
});

//保存记录
function saveOrUpdate()
{
	var r = $('#dataForm').form('validate');
	if(!r) {
		return false;
	}
	else
	{		
		$('#save').linkbutton('disable');
		$.post("${rootPath}/benisonTemplate/save",$("#dataForm").serializeArray(),
		function(data)
		{			
			if(data.result == 'true' || data.result == true)
			{
			 	$.messager.alert("提示", data.msg);
				goBack(1);
			}
			else
			{
				$.messager.alert("提示", data.msg);
				$('#save').linkbutton('enable');
// 				$.messager.confirm("提示",data.msg,function(r){
// 			 	    if (r){
// 			 	    	var strs = data.strs.split(",");
// 			 	    	//替换相应的字体为黑体再提交
// 			 	    	$.each(strs,function(index,value){
// 							$('#'+value).textbox('setValue','黑体');
// 			 	    	});
// 			 	    	saveOrUpdate();
// 			 	    }else{
// 			 	    	$('#save').linkbutton('enable');
// 			 	    }
// 			 	});
			}
		});
	}
}

//调共通页面
$('#benisonButton').bind('click',function(){
	findPage('祝福语','Benison.queryBenisonByConForPage','benisonId','benisonCode','benisonContent','benison_id',
			'benison_code','benison_content','typeId',$(typeId).val());
});

//调色板
$("input[id$='Colour']").bind({
  click: function(event) {
	  var id = this.id;
	  switch(id){
     	case 'titleColour':$("#picker1").toggle(); break;
      	case 'bodyColour':$("#picker2").toggle(); break;
      	case 'tailColour':$("#picker3").toggle(); break;
      	default:break;
      }
  },
  blur: function() {
	  var id = this.id;
	  switch(id){
     	case 'titleColour':$("#picker1").hide(); break;
      	case 'bodyColour':$("#picker2").hide(); break;
      	case 'tailColour':$("#picker3").hide(); break;
      	default:break;
      }
  }
});

$(document).ready(function() {
    $('#picker1').farbtastic('#titleColour');
    $('#picker2').farbtastic('#bodyColour');
    $('#picker3').farbtastic('#tailColour');
 });
 
//返回父页面  
// function goBack(flag){
// 	parent.returnParent(flag);
// }
</script>

</body>
</html>
