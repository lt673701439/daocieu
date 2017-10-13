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
<div>
	<br>
	<form id="dataForm">
	    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto;"></div>
	</form>
	<div data-options="region:'center',border:false" style="overflow: hidden;height:390px;" >
		<table id="dataTable" title="屏幕时间段订单分析列表" height="100%"></table>
	</div>
</div>
    
<script type="text/javascript">

jQuery(function(){ 
	
	jQuery.ajaxSetup({
		cache : false
	});
	
	//初始化图表
	getDataForHighcharts();
	
	//初始化订单汇总列表
   	$('#dataTable').datagrid({
   		singleSelect : true,
   		rownumbers:true,
   		pagination:true,
   		fitColumns:true,
   		url : '${rootPath}/screenTimeOrder/getOrderCountForData',
   		loadMsg: '数据加载中，请稍后...',
   		method : 'post',		
		idField : 'id',//此处根据实际情况进行填写
		columns:[ 
				[{field:'screenName',title:'屏幕/时间段',width:80,rowspan:2},
				{field:'timeQuantum1',title:'08:00-09:00',width:80,colspan:2},
				{field:'timeQuantum2',title:'09:00-10:00',width:80,colspan:2},
				{field:'timeQuantum3',title:'10:00-11:00',width:80,colspan:2},
				{field:'timeQuantum4',title:'11:00-12:00',width:80,colspan:2},
				{field:'timeQuantum5',title:'12:00-13:00',width:80,colspan:2},
				{field:'timeQuantum6',title:'13:00-14:00',width:80,colspan:2},
				{field:'timeQuantum7',title:'14:00-15:00',width:80,colspan:2},
				{field:'timeQuantum8',title:'15:00-16:00',width:80,colspan:2},
				{field:'timeQuantum9',title:'16:00-17:00',width:80,colspan:2},
				{field:'timeQuantum10',title:'17:00-18:00',width:80,colspan:2}] 
			   	, [{field:'total0',title:'订单数',width:25},
			   	{field:'rate0',title:'占比',width:25},
			   	{field:'total1',title:'订单数',width:25},
			   	{field:'rate1',title:'占比',width:25},
			   	{field:'total2',title:'订单数',width:25},
			   	{field:'rate2',title:'占比',width:25},
			   	{field:'total3',title:'订单数',width:25},
			   	{field:'rate3',title:'占比',width:25},
			   	{field:'total4',title:'订单数',width:25},
			   	{field:'rate4',title:'占比',width:25},
			   	{field:'total5',title:'订单数',width:25},
			   	{field:'rate5',title:'占比',width:25},
			   	{field:'total6',title:'订单数',width:25},
			   	{field:'rate6',title:'占比',width:25},
			   	{field:'total7',title:'订单数',width:25},
			   	{field:'rate7',title:'占比',width:25},
			   	{field:'total8',title:'订单数',width:25},
			   	{field:'rate8',title:'占比',width:25},
			   	{field:'total9',title:'订单数',width:25},
			   	{field:'rate9',title:'占比',width:25}
			   	]], 
		onLoadSuccess : function(data) {
			$('#dataTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
		}
	});
});

/**获取柱状图所需数据*/  
function getDataForHighcharts() {  
	
    var name=null;  
    var categories=[];  
    var list={};  
    var list2={}; 
    
    $.ajax({  
        async : false,  
        cache:false,  
        type: 'GET',  
        dataType : "json",  
		url:'${rootPath}/screenTimeOrder/getOrderCountForPic?nameBy=order_id',//请求的路径  
        success:function(data){
        	
            if(data.length > 0){ 
            	
            	name = "屏幕名称";  
           		list=new Array(); 
                list2 = new Array();
                
                for(var i=0;i<data.length;i++){
	            	//一级柱状图内容
	            	var serie={};
	            	serie.y=data[i].total;//次数(y轴) 
	            	serie.name = data[i].screenName; //分类名称
	            	serie.drilldown = data[i].screenName; //下一级名称
	            	list.push(serie);
	            	//二级柱状图内容
	            	var data2={};
                    data2.name = data[i].screenName//name
                    data2.id = data[i].screenName//id
                    var childrenList = data[i].timeOrderList;
                    
                    if(childrenList.length >0){
                    	 var list3=new Array();
                    	 for(var j=0;j<childrenList.length;j++){
                    		 var list4 = [''+(childrenList[j].timeQuantum),childrenList[j].total];
                    		 list3.push(list4);
                    	 }
                    }
                    data2.data = list3;
                    list2.push(data2);
                }
        		showHighCharts(list,list2,name);  
        	}  
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
		        text: '订单汇总统计'
		    },
		    subtitle: {
		        text: ''
		    },
		    xAxis: {
		    	type: 'category'//动态显示横坐标，（针对钻取功能）
		    },
		    yAxis: {
		        title: {
		            text: '订单个数(个)'
		        }
		
		    },
		    legend: {
		        enabled: false
		    },
		    plotOptions: {
		        series: {
		            borderWidth: 0,
		            dataLabels: {
		                enabled: true,
		                format: '{point.y}'
		            }
		        }
		    },
		    tooltip: {
		        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
		        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> 个<br/>'
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
