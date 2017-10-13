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
		<table id="dataTable" title="收入汇总统计列表" height="100%"></table>
	</div>
</div>
    
<script type="text/javascript">

jQuery(function(){ 
	
	jQuery.ajaxSetup({
		cache : false
	});
	
	jQuery.ajaxSetup({
		cache : false
	});
	
	//组装列名
	var List = getColumns(JSON.parse('${columns}'));
	
	//初始化图表
	getDataForHighcharts();
	
	//初始化订单汇总列表
   	$('#dataTable').datagrid({
   		singleSelect : true,
   		rownumbers:true,
   		pagination:true,
//    		fitColumns:true,
   		showFooter: true,
   		url : '${rootPath}/orderTotal/getOrderCountForData',
   		loadMsg: '数据加载中，请稍后...',
   		queryParams:{nameBy:"pay_price"},
   		method : 'post',		
		idField : 'id',//此处根据实际情况进行填写
		columns:[ List.firstList 
			   	, List.secondList ], 
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
		url:'${rootPath}/orderTotal/getOrderCountForPic?nameBy=pay_price',//请求的路径  
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
                    var childrenList = data[i].typeOrderList;
                    
                    if(childrenList.length >0){
                    	 var list3=new Array();
                    	 for(var j=0;j<childrenList.length;j++){
                    		 var list4 = [''+(childrenList[j].typeName),childrenList[j].total];
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
		        text: '收入汇总统计'
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
 
	//动态组装列名
	function getColumns(jsonColumns){
		
		var List = {};
		//第一行列名
		var firstList = new Array();
		var firstColumsList = jsonColumns.firstColumsList;
		
		if(firstColumsList.length>0){
			var firstColum = {};
			firstColum.field = 'playDate';
			firstColum.title = '日期/屏幕';
			firstColum.width = 90;
			firstColum.rowspan = 2;
			firstList.push(firstColum);
			//动态获取屏幕列
			for(var i=0;i<firstColumsList.length;i++){
				var firstColum = {};
				firstColum.field = firstColumsList[i].screenCode;
				firstColum.width = 200;
				firstColum.title = firstColumsList[i].screenName;
				firstColum.colspan = firstColumsList[i].colspan;
				firstList.push(firstColum);
			}
		}
		
	 	//第二行列
		var secondList = new Array();
		var secondColumsList = jsonColumns.secondColumsList;
		
		if(secondColumsList.length>0){
			//动态获取类型列
			for(var i=0;i<secondColumsList.length;i++){
				var secondColum = {};
				secondColum.field = secondColumsList[i].typeField;
				secondColum.width = 40;
				secondColum.formatter = com.formatMoney;
				secondColum.title = secondColumsList[i].TypeName;
				secondList.push(secondColum);
			}
		}
		List.firstList = firstList;
		List.secondList = secondList;
		return List;
	}
 
</script>

</body>
</html>
