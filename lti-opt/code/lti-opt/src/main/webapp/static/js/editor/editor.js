// 编辑器
if(typeof(KindEditor) != "undefined") { 
 // 编辑器
if(typeof(KE) != "undefined") {
	KE.show({
		id: "editor",
		height: "300px", 
		items: ['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste','plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript','superscript', '|', 'selectall', '-','title', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold','italic', 'underline', 'strikethrough', 'removeformat', '|', 'image','flash', 'media', 'advtable', 'hr', 'emoticons', 'link', 'unlink'],
		imageUploadJson: "/opt/file/ajaxFile",
		fileManagerJson: "/opt/file/ajaxBrowser",
		allowFileManager: true,
		autoSetDataMode: true
	});
} 
}