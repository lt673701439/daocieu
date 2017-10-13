<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@ include file="/include.jsp"%>
	
	<!-- 图片URL例子：http://211.159.151.156:8080/lti-opt/static/upload/PRO2/PRO2decef77c530b4e63994a376e22e0b637.jpg -->
    <!-- 编辑器依赖 -->
    <link rel="stylesheet" href="<%=basePath%>/static/js/kindeditor/plugins/code/prettify.css" />
	<link rel="stylesheet" href="<%=basePath%>/static/js/kindeditor/themes/default/default.css" />
	<script charset="utf-8" src="<%=basePath%>/static/js/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="<%=basePath%>/static/js/kindeditor/zh_CN.js"></script>
	<script charset="utf-8" src="<%=basePath%>/static/js/kindeditor/plugins/code/prettify.js"></script>
    <title>促销活动描述</title>
</head>
<body>
	<br><br>
	<form id="dataForm" enctype="multipart/form-data">
		<input type="hidden" name="promotionId">
		<textarea id="content" name="promotionDescription" class="easyui-validatebox" validType="length[0,10000]" style="width: 100%;height: 380px;"></textarea>
	</form>
	<div id="dlg-buttons" align="center">
   	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="save" onclick="saveOrUpdate()" style="width:90px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-arrow_turn_left" onclick="returnLast()" style="width:90px">返回</a>
    </div>

    <script type="text/javascript">
    
    var rowId;
    var editor;
    
    jQuery(function(){ 
     	
     	// 注意：不要读取缓存
     	jQuery.ajaxSetup({
     		cache : false
     	});
     	
     	// 编辑器的初始化
     	initEditor();

    });

    // 创建编辑器
    function initEditor(){
		KindEditor.ready(function(K) {
         	editor = K.create('#content', {
    			allowFileManager : true,
    			uploadJson : '${rootPath}/common/editorUpload/',
    			items : [
//     				 		'source', '|',
//     				 		'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste','plainpaste', 'wordpaste', '|', 
//     				 		'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 
//     				 		'outdent', 'subscript','superscript', 'clearhtml', 'quickformat', 'selectall', '|',
//     				 		'fullscreen','formatblock', 'fontname', 'fontsize', '|', 
//     				 		'forecolor', 'hilitecolor', 'bold','italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
//     				 		'image', 'table', 'hr', 'pagebreak','anchor', 'link', 'unlink','undo', 'redo'
					 		'source', '|',
					 		'formatblock','fontname','fontsize','forecolor','hilitecolor','bold','italic','underline','strikethrough','subscript','superscript','|',
					 		'justifyleft','justifycenter','justifyright','justifyfull','insertorderedlist','insertunorderedlist','indent','outdent','lineheight','|',
					 		'image','link','hr','fullscreen','print','preview','undo','redo'
    				 	],
    				 	fontSizeTable : ['9px', '10px', '12px', '14px', '16px', '18px', '24px', '32px'],
    				 	// 编辑器创建完，才可以获取后台数据，form加载成功后，最后在赋值给编辑器
    				 	afterCreate: function(){
    				 		
    				 		$('#dataForm').form({onLoadSuccess:function(data){editor.html(data.promotionDescription);}});
    				 		
    				     	rowId ='${rowId}';
    				     	if (rowId != null && rowId != "" && rowId!=0){
    				     		var url='${rootPath}/promotion/getDesc?rowId=' + rowId;
    				     		$('#dataForm').form('load', url);
    				     	}
    				 	},       
    					//下面这行代码就是关键的所在，当失去焦点时执行 this.sync();        
    					afterBlur: function(){this.sync();}

    			}
    		);
    		prettyPrint(); // 代码高亮
    	});
    }
    
    //保存记录
    function saveOrUpdate(){
    	$('#dataForm').form('submit', { 
   		        url: "${rootPath}/promotion/saveDesc", 
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
   						$('#save').linkbutton('enable');
   					}else{
   						$.messager.alert("提示", json.msg);
   						$('#save').linkbutton('enable');
   					}
   		        } 
   	      }); 
    }
    
   	// 返回
   	function returnLast(){
	    url = '${rootPath}/promotion';
	    window.location.href=url;
   	}
    </script>
</body>
</html>