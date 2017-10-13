<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@ include file="/include.jsp"%>
	<script src="<%=basePath%>/static/js/highcharts/highcharts.js"></script>
	<script src="<%=basePath%>/static/js/highcharts/data.js"></script>
	<script src="<%=basePath%>/static/js/highcharts/drilldown.js"></script>
    <title></title>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
	<!-- <form id="dataForm">
	    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto;"></div>
	</form> -->
    <div class="easyui-panel" title="查询条件" data-options="region:'north',height:110,collapsible:false,border : false">
		<!-- 注意：form ID 要加 1，用于区别子页面 form -->
		<br>
		<form id="dataForm1" method="post">
			 <table style="width:100%; height:1%; overflow: hidden;" border="0" 
				cellpadding="0" cellspacing="0"  class="kTable" >
				<tr>
					<td style="height:10px;" class="kTableLabel lbl">屏幕名称:</td>
					<td style="height:10px;"><input id="screenName" class="easyui-combobox" name="screenName"></td>
				</tr>
              </table>
		</form>
    </div>
    <div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dataTable" title="屏幕收入汇总列表" height="100%"></table>
	</div>
</div>
    
<script type="text/javascript">

jQuery(function(){ 
	
	jQuery.ajaxSetup({
		cache : false
	});
	
	//初始化下拉框，并加载列表列
	$('#screenName').combobox({
	    url:'${rootPath}/screen/getScreenNameList',
	    valueField:'id',
	    textField:'text',
	    onSelect: function(rec){
	    	initList(rec.id)
	    },
	    onLoadSuccess: function (data) {
	    	
            if (data) {
            	//设置下拉框默认值
            	screenId = data[0].id;
                $('#screenName').combobox('setValue',screenId);
                //加载列表
                initList(screenId);
            }
        }
	});
	
	//初始化图表
// 	getDataForHighcharts();
	
});

//动态获取列并加载列表
function initList(screenId){
	
	$.get('${rootPath}/screenOrderTotal/getColumns', {screenId:screenId},function(data){
    	//初始化列表
    	if(data != null){
    		
    		//添加价格显示规则
    		if(data.thirdColumsList!=null && data.thirdColumsList.length>0){
    			
    			data.thirdColumsList.forEach(function(thirdColum){
    				thirdColum.formatter = com.formatMoney;
    			});
    			
    		}
    		console.log(data);
    		
    		$('#dataTable').datagrid({
    			singleSelect : true,
    			rownumbers:true, 
    			pagination:true,
//     			fitColumns:true,
    			showFooter: true,
   		   		url : '${rootPath}/screenOrderTotal/getOrderCountForData',
    			loadMsg: '数据加载中，请稍后...',
    			queryParams:{nameBy:"pay_price",screenId:screenId},
    			method : 'post',		
    			idField : 'id',//此处根据实际情况进行填写
    			columns:[ data.firstColumsList 
    				   	, data.secondColumsList
    				   	, data.thirdColumsList], 
    			onLoadSuccess : function(data) {
    				$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
    			}
    		});
    		
    	}
	});
	
}

/**获取柱状图所需数据*/  
function getDataForHighcharts() {  
	
    $.ajax({  
        async : false,  
        cache:false,  
        type: 'GET',  
        dataType : "json",
		url:'${rootPath}/screenOrderTotal/getOrderCountForPic?nameBy=pay_price',//请求的路径  
		beforeSend: function () {
            $.showLoading();    // 数据加载成功之前，使用loading组件
        },
        success:function(data){
        	$.hideLoading();    // 成功后，隐藏loading组件
       		showHighCharts(data.screenList,data.typeList,data.name);  
        }
     });   
}

 //创建图表
 function showHighCharts(list,list2,name){  
	
        var series={};  
        series.name=name; 
        series.colorByPoint=true;//颜色变幻
        series.data=list;  
        
		chart = new Highcharts.Chart({  
		    chart: {
		    	renderTo: 'container',          //图表放置的容器，关联DIV#id   
		        type: 'column',					//竖的，即柱形图  
		        reflow:false                    //自适应div的大小  
		    },
		    title: {
		        text: '屏幕收入汇总'
		    },
		    subtitle: {
		        text: ''
		    },
		    xAxis: {
		    	type: 'category'//动态显示横坐标，（针对钻取功能）
		    },
		    yAxis: {
		        title: {
		            text: '价格（元）'
		        }
		
		    },
		    legend: {
		        enabled: false
		    },
		    plotOptions: {
		        series: {
		        	animation: false, 
		            borderWidth: 0,
		            dataLabels: {
		                enabled: true,
		                format: '{point.y:.2f}'
		            }
		        }
		    },
		    tooltip: {
		        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
		        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}</b> 元<br/>'
		    },
		    series: [series],
		    drilldown: {
		    	drillUpButton: {
	                relativeTo: 'spacingBox',
	                position: {
	                    y: -10,
	                    x: 0
	                },
		    	},
		    	series:list2
		    }
		});
}
 
</script>

</body>
</html>
